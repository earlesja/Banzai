package com.pan.banzai;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;

public class MainActivity extends Activity {

	public static final DashboardFragment sDashboardFragment = new DashboardFragment();
	public static final SettingsFragment sSettingsFragment = new SettingsFragment();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		FragmentManager fm = getFragmentManager();
		Fragment frag = fm.findFragmentById(R.id.container);
		if (frag == null) {
			fm.beginTransaction().add(R.id.container, sDashboardFragment)
					.commit();
		}

		// nav stuff
		String[] navOptions = getResources().getStringArray(
				R.array.nav_drawer_items);
		DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		ListView drawerList = (ListView) findViewById(R.id.nav_list);
		drawerList.setAdapter(new NavArrayAdapter(this, navOptions));
		drawerList.setOnItemClickListener(new NavListViewOnItemClickListener(
				this, drawerList, drawerLayout));
		drawerList.setItemChecked(0, true);

	}

	@Override
	public void setTitle(CharSequence title) {
		getActionBar().setTitle(title);
	}

}
