package com.pan.banzai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import microsoft.aspnet.signalr.client.MessageReceivedHandler;
import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonElement;

public class DataCollectorService extends Service {
	
	final static String DATA_RECEIVED = "DATA_RECEIVED";
	private final IBinder dBinder = new DataCollectorBinder();
	
	
	 @Override
	    public void onCreate() {
	        super.onCreate();       
	    }

	    @Override
	    public void onDestroy() {
	       super.onDestroy();       
	    }

		@Override
		public IBinder onBind(Intent intent) {
			return dBinder;
		}
		
		public class DataCollectorBinder extends Binder {
			DataCollectorService getService() {
		            // Return this instance of LocalService so clients can call public methods
		        return DataCollectorService.this;
			}
		}
		
		public void startCollecting(){
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
//        proxy.invoke("join", Arrays.asList("signalRTest"));  
        
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

//        proxy.invoke("join", Arrays.asList("IE usage__6","IE usage__16"));
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
        
        proxy.invoke("join", Arrays.asList("CPU App 1__79", "CPU App 2__80", "CPU App 3__81"));
        proxy.invoke("join", Arrays.asList("Memory App 1__85", "Memory App 2__86", "Memory App 3__87"));
        proxy.invoke("join", Arrays.asList("Disk Utilization App 1__93", "Disk Utilization App 2__94", "Disk Utilization App 3__95"));
       
        proxy.invoke("join", Arrays.asList("CPU Web 1__82", "CPU Web 2__83", "CPU Web 3__84"));
        proxy.invoke("join", Arrays.asList("Memory Web 1__88", "Memory Web 2__89", "Memory Web 3__90"));
        proxy.invoke("join", Arrays.asList("Disk Utilization Web 1__96", "Disk Utilization Web 2__97", "Disk Utilization Web 3__98"));

        proxy.invoke("join", Arrays.asList("CPU DB 1__91"));
        proxy.invoke("join", Arrays.asList("Memory DB 1__92"));
        proxy.invoke("join", Arrays.asList("Disk Utilization DB 1__99"));
        
            
        connection.received(new MessageReceivedHandler() {

			@Override
			public void onMessageReceived(JsonElement json) {
				Log.d("TEST", "RAW received message: " + json.toString());
				
				Intent intent = new Intent();
			       intent.setAction(DATA_RECEIVED);
			      
			       intent.putExtra("dataJson", json.toString());
			      
			       sendBroadcast(intent);
			       
			       
			      

					
//				   ArrayList<String> dataList = new ArrayList<String>();
//				   try {
//					   JSONObject obj = new JSONObject(json.toString());
//					   JSONObject data = (JSONObject) obj.getJSONArray("A").get(0);
//					   dataList.add(data.get("MetricId").toString());
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//			       
//			       if(Integer.parseInt(dataList.get(0)) == 85){
//		             NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//		              String MyText = "Reminder";
//		              Notification mNotification = new Notification(R.drawable.ic_launcher, MyText, System.currentTimeMillis() );
//		              //The three parameters are: 1. an icon, 2. a title, 3. time when the notification appears
//
//		              String MyNotificationTitle = "Medicine!";
//		              String MyNotificationText  = "Don't forget to take your medicine!";
//
//		              Intent MyIntent = new Intent(intent);
//		              PendingIntent StartIntent = PendingIntent.getActivity(getApplicationContext(),0,MyIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//		              //A PendingIntent will be fired when the notification is clicked. The FLAG_CANCEL_CURRENT flag cancels the pendingintent
//
//		              mNotification.setLatestEventInfo(getApplicationContext(), MyNotificationTitle, MyNotificationText, StartIntent);
//
//		              int NOTIFICATION_ID = 1;
//		              notificationManager.notify(NOTIFICATION_ID , mNotification);  
//		              //We are passing the notification to the NotificationManager with a unique id.
//		            }
				}
	
		});
		}

}