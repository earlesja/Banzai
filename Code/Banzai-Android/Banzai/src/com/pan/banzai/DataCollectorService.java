package com.pan.banzai;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
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
	
	final static String DATA_RECEIVED = "DATA_RECEIVED";
	
	
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
//	        proxy.invoke("join", Arrays.asList("signalRTest"));
	        proxy.invoke("join", Arrays.asList("IE 10 usage__25", "Firefox usage__17", "Chrome usage__18"));
//	        proxy.invoke("join", Arrays.asList("Firefox usage__7"));
//	        proxy.invoke("join", Arrays.asList("Chrome usage__8", "Chrome usage__18", "Chrome usage__38"));
//	        proxy.invoke("join", Arrays.asList("IE usage__6", "IE 8 usage__23", "IE 9 usage__24", "IE 10 usage__25", "IE 11 usage__26"));
	        proxy.invoke("join", Arrays.asList("Windows 7 usage__54", "Mac OSX usage__66", "Linux usage__69", "Other OS usage__78"));    
	        
	        
	        connection.received(new MessageReceivedHandler() {

				@Override
				public void onMessageReceived(JsonElement json) {
//					Log.d("TEST", "RAW received message: " + json.toString());
					
					Intent intent = new Intent();
				       intent.setAction(DATA_RECEIVED);
				      
				       intent.putExtra("dataJson", json.toString());
				      
				       sendBroadcast(intent);
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