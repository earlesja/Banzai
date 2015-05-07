package com.pan.banzai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.pan.banzai.DataCollectorService.DataCollectorBinder;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends Activity {

	public static final DashboardFragment sDashboardFragment = new DashboardFragment();
	public static final SettingsFragment sSettingsFragment = new SettingsFragment();
	public static final OsUsageFragment sOsUsageFragment = new OsUsageFragment();
	public static final BrowserUsageFragment sBrowserUsageFragment = new BrowserUsageFragment();

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;

	DataReceiver dataReceiver;

	private static List<Integer> CPU_METRICIDS = Arrays.asList(79, 80, 81, 82,
			83, 84, 91);
	private static List<Integer> RAM_METRICIDS = Arrays.asList(85, 86, 87, 88,
			89, 90, 92);
	private static List<Integer> DISK_METRICIDS = Arrays.asList(93, 94, 95, 96,
			97, 98, 99);
	
	private static boolean isInForeground;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// signalr service
		Intent dataService = new Intent(this, DataCollectorService.class);
		// startService(dataService);
		bindService(dataService, mConnection, Context.BIND_AUTO_CREATE);

		// nav stuff
		String[] navOptions = getResources().getStringArray(
				R.array.nav_drawer_items);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		ListView drawerList = (ListView) findViewById(R.id.nav_list);
		drawerList.setAdapter(new NavArrayAdapter(this, navOptions));
		drawerList.setOnItemClickListener(new NavListViewOnItemClickListener(
				this, drawerList, mDrawerLayout));

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.nav_drawer_open,
				R.string.nav_drawer_close) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		drawerList.setItemChecked(0, true);

		SharedPreferenceHelper.setSharedPreferences(this);
		DefaultValues.storeDefaultInSharedPref();

		FragmentManager fm = getFragmentManager();
		Fragment frag = fm.findFragmentById(R.id.container);
		if (frag == null) {
			fm.beginTransaction().add(R.id.container, sDashboardFragment)
					.commit();
		}
		
		MainActivity.isInForeground = true;

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void setTitle(CharSequence title) {
		getActionBar().setTitle(title);
	}

	public void onResume() {

		dataReceiver = new DataReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(DataCollectorService.DATA_RECEIVED);
		registerReceiver(dataReceiver, intentFilter);
		
		MainActivity.isInForeground = true;

		super.onResume();
	}

	public void onPause() {
		unregisterReceiver(dataReceiver);
		
		MainActivity.isInForeground = false;
		
		super.onPause();

	}

	DataCollectorService mService;
	boolean mBound;
	private ServiceConnection mConnection = new ServiceConnection() {
		// Called when the connection with the service is established
		public void onServiceConnected(ComponentName className, IBinder service) {
			// Because we have bound to an explicit
			// service that is running in our own process, we can
			// cast its IBinder to a concrete class and directly access it.
			DataCollectorBinder binder = (DataCollectorBinder) service;
			mService = binder.getService();
			mService.startCollecting();
			mBound = true;
		}

		// Called when the connection with the service disconnects unexpectedly
		public void onServiceDisconnected(ComponentName className) {
			// Log.e(TAG, "onServiceDisconnected");
			mBound = false;
			mService.stopSelf();
		}
	};

	private class DataReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			Bundle extra = intent.getExtras();

			String datapassed = extra.getString("dataJson");

			try {
				JSONObject obj = new JSONObject(datapassed);
				JSONObject data = (JSONObject) obj.getJSONArray("A").get(0);

				// Toast.makeText(MainActivity.this,
				// "Triggered by Service!\n"
				// + "Data passed: " + String.valueOf(datapassed),
				// Toast.LENGTH_LONG).show();

				ArrayList<String> dataList = new ArrayList<String>();
				dataList.add(data.get("MetricId").toString());
				dataList.add(data.get("Value").toString());

				int metricId = Integer.parseInt(dataList.get(0));
				float value = Float.parseFloat(dataList.get(1));

				boolean shouldNotify = false;
				int metricType = 0;

				if (MainActivity.CPU_METRICIDS.contains(metricId)
						&& value > DefaultValues.getCpuCriticalThreshold()) {
					shouldNotify = true;
					metricType = 1;
				} else if (MainActivity.RAM_METRICIDS.contains(metricId)
						&& value > DefaultValues.getRamCriticalThreshold()) {
					shouldNotify = true;
					metricType = 2;
				} else if (MainActivity.DISK_METRICIDS.contains(metricId)
						&& value > DefaultValues.getStorageCriticalThreshold()) {
					shouldNotify = true;
					metricType = 3;
				}

				if (shouldNotify) {

//					ActivityManager activityManager = (ActivityManager) getApplicationContext()
//							.getSystemService(Context.ACTIVITY_SERVICE);
//					List<RunningTaskInfo> services = activityManager
//							.getRunningTasks(Integer.MAX_VALUE);
//					boolean isActivityFound = false;
//
//					if (services.get(0).topActivity
//							.getPackageName()
//							.toString()
//							.equalsIgnoreCase(
//									getApplicationContext().getPackageName().toString())) {
//						isActivityFound = true;
//					}

//					if (!MainActivity.isInForeground) {

						String tickerText = "Error";
						String titleText = "Error";
						String contentText = "Error";

						// TODO: make this more specific and more dynamic
						switch (metricType) {
						case 1:
							tickerText = "CPU Critical Threshold";
							titleText = "Threshold Triggered!";
							contentText = "Value: " + value;
							break;
						case 2:
							tickerText = "RAM Critical Threshold";
							titleText = "Threshold Triggered!";
							contentText = "Value: " + value;
							break;
						case 3:
							tickerText = "DB Critical Threshold";
							titleText = "Threshold Triggered!";
							contentText = "Value: " + value;
							break;
						default:
							// TODO: log error
							break;

						}
						NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

						Intent MyIntent = new Intent(context,
								MainActivity.class);
						PendingIntent StartIntent = PendingIntent.getActivity(
								getApplicationContext(), 0, MyIntent,
								PendingIntent.FLAG_CANCEL_CURRENT);
						// A PendingIntent will be fired when the notification
						// is clicked. The FLAG_CANCEL_CURRENT flag cancels the
						// pendingintent

						NotificationCompat.Builder builder = new NotificationCompat.Builder(
								context);
						Notification notification = builder
								.setContentIntent(StartIntent)
								.setSmallIcon(R.drawable.ic_launcher)
								.setTicker(tickerText).setAutoCancel(true)
								.setContentTitle(titleText)
								.setContentText(contentText).build();

						int NOTIFICATION_ID = 1;

						notificationManager.notify(NOTIFICATION_ID,
								notification);
						// We are passing the notification to the
						// NotificationManager with a unique id.
//					}

				}

				if (MainActivity.sOsUsageFragment.isVisible()) {
					// Toast.makeText(MainActivity.this,
					// "OsUsageFragment shown",
					// Toast.LENGTH_LONG).show();

					MainActivity.sOsUsageFragment.updateContent(dataList);
				}

				if (MainActivity.sBrowserUsageFragment.isVisible()) {
					// Toast.makeText(MainActivity.this,
					// "OsUsageFragment shown",
					// Toast.LENGTH_LONG).show();

					MainActivity.sBrowserUsageFragment.updateContent(dataList);
				}

				if (MainActivity.sDashboardFragment.isVisible()) {
					MainActivity.sDashboardFragment.updateContent(dataList);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
