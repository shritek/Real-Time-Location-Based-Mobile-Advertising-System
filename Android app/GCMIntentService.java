package com.lba;

import static com.lba.CommonUtilities.SENDER_ID;
import static com.lba.CommonUtilities.displayMessage;

import android.R.string;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;


import com.lba.R;
import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService implements Runnable {
	public static SQLiteHelper db;
	 public static String longi;
	 public static String lati;
	 	public static double latitude;
    	public static double longitude;
    	 public static double d;
    	 static double r;
    	 public static int msg;
    	 public static String message;
    	 public static String la,lo,ra;
    	 
      	public static double lat2,lon2;
      	static Context c;
      	
	private static final String TAG = "GCMIntentService";
	private LocationManager locManager;
    public GCMIntentService() {
        super(SENDER_ID);
    }

  
    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        
        Log.d("NAME", MainActivity.name);
        ServerUtilities.register(context, MainActivity.name, MainActivity.email, registrationId);
    }

    
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        displayMessage(context, getString(R.string.gcm_unregistered));
        ServerUtilities.unregister(context, registrationId);
    }

  
    @Override
    protected void onMessage(Context context, Intent intent) {
   
    	Thread t=new Thread(new GCMIntentService());
    	
    	c=context;
    	
    	msg=1;
       message = intent.getExtras().getString("price");
       la = intent.getExtras().getString("lati");

        lo = intent.getExtras().getString("longi");
        
        ra = intent.getExtras().getString("r");
       
        Log.i("onMessage", "message");
        
       if(ra!=null)
       {
    	   r = Double.parseDouble(ra);
    	   r=r*1000;
       }
       
       Log.i("onMessage", "starting thread");
       t.start();
                 	
    }
      
	
    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = getString(R.string.gcm_deleted, total);
        displayMessage(context, message);
        
        generateNotification(context, message);
    }

   
    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
        displayMessage(context, getString(R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        
        Log.i(TAG, "Received recoverable error: " + errorId);
        displayMessage(context, getString(R.string.gcm_recoverable_error,
                errorId));
        return super.onRecoverableError(context, errorId);
    }

    //Issues a notification to inform the user that server has sent a message.
    private static void generateNotification(Context context, String message) {
        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
        
        String title = context.getString(R.string.app_name);
        
        Intent notificationIntent = new Intent(context, MainActivity.class);
        
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        
        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;
        
        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);      
    }
    
    public static void check(Context context, Double r)
    {
    	try{
    	Log.i("check","in");
     	double a,c;
     	double R=6378.137; //Radius of Earth in km
    
     	latitude=Double.parseDouble(Tracker.lat);
     	longitude=Double.parseDouble(Tracker.lgtd);
    	double dLat = (lat2 - latitude) * Math.PI / 180;
	    double dLon = (lon2 - longitude) * Math.PI / 180;
	    a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	    Math.cos(latitude * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) * Math.sin(dLon/2) * Math.sin(dLon/2);
	    c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    d = R * c;
	    d=d*1000;
    	}
    	catch(Exception e)
    	{
    		
    	}
	    if(d<r)
	    {
	    	Log.i("inserting msg",message);
	    	db = new SQLiteHelper(context);
	    	db.insertMsg(message);
	    }
	    Log.i("check out", "n");
	
    }

	@Override
	public void run() {
		
		Log.i("Run", "in run");
		
		int a=4;
		
		if(MainActivity.flag==1)
	       {
	    	   if(la!=null)
	    	   {   
	    		   Log.i("on Message","la!=null");
	    		   lat2 = Double.parseDouble(la);
	    		   lon2 = Double.parseDouble(lo);
	      
	    		   
	    		   do
	    		   {
	    			   check(c,r);
	    			   
	    			   if(d<r)
	    			   {
	    	   				displayMessage(c, message);
	    	   				// notifies user
	    	   				generateNotification(c, message);
	    	   			} else
	    	   			{
						try {
							Thread.sleep(60000);
							Log.i("Sleep", "sleeping");
						} 
						catch (InterruptedException e) {
							e.printStackTrace();
						}
	    	   			}
	    			   a--;
	    		   }while(d>r && a!=0);
	    			   
	    	   }
	       
	    	}
		
	}
}
