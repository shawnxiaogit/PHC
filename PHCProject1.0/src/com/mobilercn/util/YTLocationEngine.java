package com.mobilercn.util;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class YTLocationEngine implements LocationListener {
	
	static String TAG = YTLocationEngine.class.getSimpleName();
	
	private LocationManager    mLocationMgr;
	
	private YTLocationCallback mCallback;
	
	private Context            mContext;
	
	boolean gpsEnabled         = false;
	boolean networkEnabled     = false;
	
	private static YTLocationEngine g_location = null;
	
	private YTLocationEngine(Context context){
		
		mContext = context;
		
		init();
	}
	
	public static YTLocationEngine getInstance(Context context){
		
		if(null == g_location){
			g_location = new YTLocationEngine(context);
		}
		
		return g_location;
	}
	
	public void setCallback(YTLocationCallback callback){
		
		mCallback = callback;
		
	}
	
	public void init(){
		
		mLocationMgr = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
		
		try{
			
	        gpsEnabled = mLocationMgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
	        
	    } 
		catch(Exception ex){}
	        
		try{
	          
			networkEnabled = mLocationMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER);  
			
	    } 
		catch(Exception ex){}
		

        if(!gpsEnabled && !networkEnabled){
            return;
        }
		
	}

	/**
	 * 如果位置改变则报告位置
	 */
	public void onLocationChanged(Location location) {
		if(location != null){
			
			if(null != mCallback){
				mCallback.reportLocat(location.getLatitude(), location.getLongitude());
			}

		}
		
	}
	//state
	public void stop(){
		
		if(mLocationMgr != null){
			mLocationMgr.removeUpdates(this);
			mLocationMgr = null;
		}
		
	}
	
	public void start(){
		

        
        if(gpsEnabled){
    		Location location=mLocationMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER); 
    		onLocationChanged(location);
        	mLocationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this);
        }
        
        if(networkEnabled){
    		Location location=mLocationMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER); 
    		onLocationChanged(location);
        	mLocationMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, this);
        }
        
        
	}

	public void onProviderDisabled(String arg0) {
		
	}

	public void onProviderEnabled(String arg0) {
		
	}

	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		
	}
	
}
