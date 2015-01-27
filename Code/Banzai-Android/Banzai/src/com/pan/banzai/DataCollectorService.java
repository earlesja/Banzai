package com.pan.banzai;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.gson.JsonElement;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import microsoft.aspnet.signalr.client.*;
import microsoft.aspnet.signalr.client.hubs.*;



public class DataCollectorService extends Service {
	 @Override
	    public void onCreate() {
	        super.onCreate();       
	    }

	    @SuppressWarnings("deprecation")
	    @Override
	    public void onStart(Intent intent, int startId) {      
	            super.onStart(intent, startId);       
	            Toast.makeText(this, "Service Start", Toast.LENGTH_LONG).show();
	            Log.d("TEST","%%%%%Log works just fine%%%%%");

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

	        //Invoke join to start receiving broadcast messages            
	        proxy.invoke("join", Arrays.asList("signalRTest"));
	        
	        proxy.subscribe(new Object() {
				@SuppressWarnings("unused")
				public void messageReceived(String message) {
					Log.d("TEST", message);
				}
			});

//	        //Then call on() to handle the messages when they are received.        
//	            proxy.on( "metricReceived", new SubscriptionHandler() {
////	                @Override
////	                public void run(String msg) {
////	                    Log.d("result := ", msg);                   
////	                }
//
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						 Log.d("result := ", "testtesttest");
//						
//					}
//	            });
	        
	        connection.received(new MessageReceivedHandler() {

				@Override
				public void onMessageReceived(JsonElement json) {
					Log.d("TEST", "RAW received message: " + json.toString());
				}
			});
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
}