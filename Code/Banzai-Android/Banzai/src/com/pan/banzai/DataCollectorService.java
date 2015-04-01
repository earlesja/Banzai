package com.pan.banzai;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import microsoft.aspnet.signalr.client.MessageReceivedHandler;
import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonElement;

public class DataCollectorService extends Service {

	final static String MY_ACTION = "MY_ACTION";
	static int lastRandom = new Random().nextInt(100);

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Toast.makeText(this, "Service Start", Toast.LENGTH_LONG).show();
		Log.d("TEST", "%%%%%Log works just fine%%%%%");

		String server = "http://pan-banzai.cloudapp.net/banzai/signalr";
		HubConnection connection = new HubConnection(server);
		HubProxy proxy = connection.createHubProxy("MetricHub");

		SignalRFuture<Void> awaitConnection = connection.start();
		try {
			awaitConnection.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Invoke join to start receiving broadcast messages
		proxy.invoke("join", Arrays.asList("signalRTest"));
		// proxy.invoke("join", Arrays.asList("Firefox usage__7"));
		// proxy.invoke("join", Arrays.asList("Chrome usage__8"));

		proxy.subscribe(new Object() {
			@SuppressWarnings("unused")
			public void messageReceived(String message) {
				Log.d("TEST", message);
			}
		});

		connection.received(new MessageReceivedHandler() {

			@Override
			public void onMessageReceived(JsonElement json) {
				// Log.d("TEST", "RAW received message: " + json.toString());
				//
				// Intent intent = new Intent();
				// intent.setAction(MY_ACTION);
				//
				// intent.putExtra("DATAPASSED", json.toString());
				//
				// sendBroadcast(intent);
			}
		});

		MyThread myThread = new MyThread();
		myThread.start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public class MyThread extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			for (int i = 0; i < 1000; i++) {
				try {
					Thread.sleep(5000);
					Intent intent = new Intent();
					intent.setAction(MY_ACTION);

					int[] data = generateRandomData();

					for (int j = 0; j < data.length; j++) {
						intent.putExtra("Data" + j, data[j] + "");
					}

					// intent.putExtra("DATAPASSED", i+"");

					// Calendar c = Calendar.getInstance();
					// intent.putExtra("Time",
					// c.get(c.HOUR)+":"+c.get(c.MINUTE)+":"+c.get(c.SECOND));

					sendBroadcast(intent);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			stopSelf();
		}

		private int[] generateRandomData() {

			Random random = new Random();
			int[] returnData = { 0, 0, 0 };
			int start;
			int end;
			int randomNumber = lastRandom;
			long range;
			long fraction;
			int latestSum = 100;

			for (int i = 0; i < 3; i++) {

				if (i == 0) {
					start = lastRandom - 5;
					end = lastRandom + 5;
				} else {
					start = latestSum - randomNumber - 10;
					end = latestSum - randomNumber;
				}
				start = start < 0 ? 0 : start;
				end = end > 100 ? 100 : end;
				end = end < 0 ? 0 : end;

				// get the range, casting to long to avoid overflow problems
				range = (long) end - (long) start + 1;
				// compute a fraction of the range, 0 <= frac < range
				fraction = (long) (range * random.nextDouble());
				randomNumber = (int) (fraction + start);
				latestSum = latestSum - randomNumber;
				latestSum = latestSum < 0 ? 0 : latestSum;
				returnData[i] = randomNumber;

				if (i == 0) {
					lastRandom = randomNumber;
				}
			}

			return returnData;
		}

	}
}