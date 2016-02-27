package com.lba;

import static com.lba.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.lba.CommonUtilities.EXTRA_MESSAGE;
import static com.lba.CommonUtilities.SENDER_ID;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.lba.R;
import com.google.android.gcm.GCMRegistrar;

public class MainActivity extends Activity {
	// label to display gcm messages
	TextView lblMessage;
	

	AsyncTask<Void, Void, Void> mRegisterTask;
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	// Connection detector
	ConnectionDetector cd;
	
	public static String name;
	public static String email;
	public static String glat;
	public static String glgtd;
	public static int flag=1;
	private String[] allColumns = {SQLiteHelper.COLUMN_ID,
		      SQLiteHelper.COLUMN_MESSAGE };
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		int flags=0;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.i("MainActivity", "onCreate");
		Tracker t=new Tracker(MainActivity.this);
		cd = new ConnectionDetector(getApplicationContext());
		
		// Getting name, email from intent
		Intent i = getIntent();
		
		name = i.getStringExtra("name");
		email = i.getStringExtra("email");		
		
		// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(this);

		GCMRegistrar.checkManifest(this);

		lblMessage = (TextView) findViewById(R.id.lblMessage);
		
		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				DISPLAY_MESSAGE_ACTION));
		
		// Get GCM registration id
		final String regId = GCMRegistrar.getRegistrationId(this);

		// Check if regid already present
		if (regId.equals("")) {
			// Registration is not present, register with GCM			
			GCMRegistrar.register(this, SENDER_ID);
		} else {
			// Device is already registered on GCM
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				// Skips registration.				
			} else {
				final Context context = this;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						// Register on our server
						// On server create a new user
						ServerUtilities.register(context, name, email, regId);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};
				mRegisterTask.execute(null, null, null);
			}
		}
		
		MessageSource datasource = new MessageSource(getApplicationContext());
	    datasource.open();
	    datasource.database = datasource.dbHelper.getWritableDatabase();
	    Cursor cursor1 = datasource.database.query(SQLiteHelper.TABLE_MESSAGES,
		        allColumns, null, null,
		        null, null, null);
	    if(!cursor1.isAfterLast())
	    {
	    cursor1.moveToLast();
	    Message newMessage = datasource.cursorToMessage(cursor1);
	    cursor1.close();
	    lblMessage.setText(newMessage.getMessage());
	    }
	}		

	//Receiving push messages
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());
			
			
			
			lblMessage.setText(newMessage);
			
			
			// Releasing wake lock
			WakeLocker.release();
		}
	};
	
	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);
        return true;
    }
	
	public boolean onOptionsItemSelected(MenuItem item)
    {
         
		switch (item.getItemId())
        {
      
        	case R.id.options_stop:
		
        		if (flag==1)
        		{
        			flag=0;
        			item.setTitle("Start messages");
        		}	
		
	
        		else
        		{
        			flag=1;
        			item.setTitle("Stop messages");
        		}
        		return true;
        
        	case R.id.options_history:
    	        Intent intent = new Intent(this, DatabaseActivity.class);
    	        this.startActivity(intent);
    	        return true;
    	        

        		
        	default:
                return super.onOptionsItemSelected(item);
        }
    }    
}

