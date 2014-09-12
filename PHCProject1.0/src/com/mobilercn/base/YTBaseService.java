package com.mobilercn.base;

import java.util.ArrayList;

import com.mobilercn.http.JJAsyncHttpTask;
import com.mobilercn.task.JJAsyncTaskManager;
import com.mobilercn.task.JJTask;
import com.mobilercn.task.JJTaskType;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

public class YTBaseService extends Service {
	public static final String BOUNDARY = "---------------------------7db1c523809b2";

	public static final int   DEFAULT_TIMEOUT = 1 * 60 * 1000;
	
	public static final Integer   HTTP_SERVICE     = new Integer(-1);
	public static final Integer   BT_SERVICE       = new Integer(-2);
	public static final int       HTTP_SERVICE_INT = -1;
	public static final int       BT_SERVICE_INT   = -2;
	
	
	
	public static ArrayList<Long> g_cancelTaskList = new ArrayList<Long>();
	
	public boolean                mIsRunning       = false;
	public static boolean         g_runState       = false;
	
	public static final Handler   mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			
			JJTask task = ( JJTask )msg.obj;
		    YTBaseActivity activity = YTBaseApplication.getActivityByName( task.getActivityName() );	
			if( YTBaseService.isCancelTask( task.getTimeID() ) ){
				if( activity != null ){
					activity.taskFailed( YTBaseService.HTTP_SERVICE,task );
				}				
			}
			switch ( msg.what ) {
			
			case JJTaskType.TASK_HTTP_SUCCESS:{
				if( activity != null ){
					if( !isCancelTask( task.getTimeID() ) ){
						activity.taskSuccess( YTBaseService.HTTP_SERVICE,task );						
					}
					else {
						activity.taskFailed( YTBaseService.HTTP_SERVICE,task );
					}
				}
			}break;
			
			case JJTaskType.TASK_HTTP_FAILED:{
				if( activity != null ){
					activity.taskFailed( YTBaseService.HTTP_SERVICE,task );
				}				
			}break;
			
			}
		}
	};

	  
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public void onCreate(){
		super.onCreate();
		
		mIsRunning = true;
		
	}
	
	public void onDestroy() {
		super.onDestroy();
		
		mIsRunning = false;
		
	}

	public static synchronized void addCancelTask(long l){
		
		g_cancelTaskList.add( new Long( l ) );
		
	}
	
	
	public static synchronized boolean isCancelTask(long l){
		
		boolean result = false;
		Long tmp = new Long( l );
		if( g_cancelTaskList.contains( tmp ) ){
			result = true;
			g_cancelTaskList.remove( tmp );
		}
		return result;
		
	}
	
	public static long addHttpTask(String url, String body, String activity, int type){
		
		long id = System.currentTimeMillis();
		JJAsyncHttpTask task = new JJAsyncHttpTask( url, DEFAULT_TIMEOUT, false, mHandler );
		task.setTimeID( id );
		
		task.setTaskId( type );
		try{
			task.setHttpTaskBody( body.getBytes("UTF-8") );			
		}
		catch(Exception ex){
			task.setHttpTaskBody( body.getBytes() );
		}
		task.setActivityName( activity );
		JJAsyncTaskManager.getInstance().doHttpTask( task );	
		return id;
		
	}

	public static long addMutilpartHttpTask(String url, String[] params, String activity, int type){
		
		long id = System.currentTimeMillis();
		JJAsyncHttpTask task = new JJAsyncHttpTask( url, DEFAULT_TIMEOUT, false, mHandler );
		task.setTimeID( id );
		task.setTaskId( type );
		task.setPostBoundary(BOUNDARY);
		task.setMultipartHttpTaskBody( params );
		task.setActivityName( activity );
		JJAsyncTaskManager.getInstance().doHttpTask( task );	
		return id;
	}


}
