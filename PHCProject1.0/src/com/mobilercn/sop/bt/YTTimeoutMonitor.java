package com.mobilercn.sop.bt;

import java.util.Timer;
import java.util.TimerTask;

import com.vsi.config.D2EConfigures;

import android.os.Handler;
import android.util.Log;

public class YTTimeoutMonitor {
	
	public static final int    TIMEOUT = 999;
	
	protected static final String TAG = YTTimeoutMonitor.class.getSimpleName();
	
	private static final long  DEFAULT_TIMEOUT = 10000L;
	
	public static  YTTimeoutMonitor mSelf;
	
	protected      Handler          mHandler;
	
	private        Timer            mMonitorTimer;
	
	private YTTimeoutMonitor(){}

	public static YTTimeoutMonitor getInstance(){
		
		if(null == mSelf){
			mSelf = new YTTimeoutMonitor();
		}
		
		return mSelf;
		
	}
	
	public void init(Handler handler){
		mHandler = handler;
	}
	
	public void startMonitor(){
				
		if(null != mMonitorTimer){
			mMonitorTimer.cancel();
			mMonitorTimer = null;
		}
		
		mMonitorTimer = new Timer();		
		mMonitorTimer.schedule(new MonitorTask(), DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
	}
	
	public void stopMonitor(){

		if(null != mMonitorTimer){
			mMonitorTimer.cancel();
			mMonitorTimer = null;
		}
		
	}
		
	private class MonitorTask extends TimerTask {

		@Override
		public void run() {
			try{
				if(D2EConfigures.TEST){
					Log.e("MonitorTask-------------->", "MonitorTask");
				}
				mHandler.obtainMessage(SOPBluetoothService.BT_TOAST, -1, -1, SOPBluetoothService.STATE_FAILED).sendToTarget();
				stopMonitor();
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
	}
}
