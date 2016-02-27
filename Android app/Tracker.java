package com.lba;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log; 
import java.lang.Math;

public class Tracker extends Service implements LocationListener{

	private final Context mContext;
	Location location; // location
	
	public static String lat;
	public static String lgtd;
	public static double d;
	double a,c;
	double R=6378.137; //Radius of Earth in km
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	public Tracker(Context context)
	{
		this.mContext=context;
		getLocation();
	}
	
	public void getLocation()
	{
		Log.i("Tracker", "getLocation");
		LocationManager locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
		Location location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		if(location!=null)
		{
			lat=Double.toString(location.getLatitude());
			lgtd=Double.toString(location.getLongitude());

		}
			
		Log.i("getLocation", lat+" "+lgtd);
		
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
	}

	@Override
	public void onLocationChanged(Location location) {
		
		lat=Double.toString(location.getLatitude());
		lgtd=Double.toString(location.getLongitude());
		
	
	    
		Log.i("onLocationChanged", lat+" "+lgtd);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
