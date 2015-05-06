package com.pan.banzai;

import java.util.Arrays;
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
	        
	        proxy.invoke("join", Arrays.asList("Windows Vista usage__49","Windows Vista usage__50","Windows Vista usage__51"));
	        proxy.invoke("join", Arrays.asList("Windows 7 usage__52","Windows 7 usage__53","Windows 7 usage__54"));
	        proxy.invoke("join", Arrays.asList("Windows 8 usage__55","Windows 8 usage__56","Windows 8 usage__57"));
	        proxy.invoke("join", Arrays.asList("Windows 8.1 usage__58","Windows 8.1 usage__59","Windows 8.1 usage__60"));
	        proxy.invoke("join", Arrays.asList("Windows 10 usage__61","Windows 10 usage__62","Windows 10 usage__63"));
	        proxy.invoke("join", Arrays.asList("Mac OSX usage__64","Mac OSX usage__65","Mac OSX usage__66"));
	        proxy.invoke("join", Arrays.asList("Linux usage__67","Linux usage__68","Linux usage__69"));
	        proxy.invoke("join", Arrays.asList("iOS usage__70","iOS usage__71","iOS usage__72"));
	        proxy.invoke("join", Arrays.asList("Android usage__73","Android usage__74","Android usage__75"));
	        proxy.invoke("join", Arrays.asList("Other OS usage__76","Other OS usage__77","Other OS usage__78"));

//	        proxy.invoke("join", Arrays.asList("IE usage__6","IE usage__16"));
	        proxy.invoke("join", Arrays.asList("Firefox usage__7","Firefox usage__17","Firefox usage__37"));
	        proxy.invoke("join", Arrays.asList("Chrome usage__8","Chrome usage__18","Chrome usage__38"));
	        proxy.invoke("join", Arrays.asList("Mobile Chrome usage__29","Mobile Chrome usage__36","Mobile Chrome usage__45"));
	        proxy.invoke("join", Arrays.asList("Safari usage__27","Safari usage__34","Safari usage__43"));
	        proxy.invoke("join", Arrays.asList("Mobile Safari usage__28","Mobile Safari usage__35","Mobile Safari usage__44"));
	        proxy.invoke("join", Arrays.asList("Other browser usage__46","Other browser usage__47","Other browser usage__48"));
	        proxy.invoke("join", Arrays.asList("IE 8 usage__23","IE 8 usage__30","IE 8 usage__39"));
	        proxy.invoke("join", Arrays.asList("IE 9 usage__24","IE 9 usage__31","IE 9 usage__40"));
	        proxy.invoke("join", Arrays.asList("IE 10 usage__25","IE 10 usage__32","IE 10 usage__41"));
	        proxy.invoke("join", Arrays.asList("IE 11 usage__26","IE 11 usage__33","IE 11 usage__42"));






	            
	        connection.received(new MessageReceivedHandler() {

				@Override
				public void onMessageReceived(JsonElement json) {
					Log.d("TEST", "RAW received message: " + json.toString());
					
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