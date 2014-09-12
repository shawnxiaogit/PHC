// +---------------------------------------------------
// |          YTBaseApplication.java
// +---------------------------------------------------
// |
// +---------------------------------------------------
// | Author: wu.s.w 
// +---------------------------------------------------
// | Date  : 2012-07-10
// +---------------------------------------------------

package com.mobilercn.base;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Stack;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKEvent;
import com.baidu.mapapi.MKGeneralListener;
import com.mobilercn.sop.bt.SOPBluetoothService;
import com.vsi.config.D2EConfigures;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;


/**
 * 应用程序类
 *
 */
public class YTBaseApplication extends Application {
	//add by shawn 2012-12-17 Begin 
	//修改经纬度的获取方式，采用百度MapApi
	static YTBaseApplication mApp;
	
	//百度MapAPI管理类
	public BMapManager mBMapMan=null;
	// 授权Key
	// 申请地址：http://developer.baidu.com/map/android-mobile-apply-key.htm
	public String mStrKey="8D1E2A0C06011E7F02A439745F84643A974394FB";
	boolean m_bKeyRight = true;	// 授权Key正确，验证通过
	
	//常用事件监听，用来处理通常的网络错误，授权验证错误等
	public static class MyGeneralListener implements MKGeneralListener{

		@Override
		public void onGetNetworkState(int iError) {
			if(D2EConfigures.TEST){
				Log.e("MyGeneralListener", "onGetNetworkState error is "+iError);
			}
			Toast.makeText(YTBaseApplication.mApp.getApplicationContext(), 
					"你的网络出错啦！", 
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onGetPermissionState(int iError) {
			if(D2EConfigures.TEST){
				Log.e("MyGeneralListener", "onGetPermissionState error is "+iError);
			}
			if(iError==MKEvent.ERROR_PERMISSION_DENIED){
				//授权Key错误
				if(D2EConfigures.TEST){
					Toast.makeText(YTBaseApplication.mApp.getApplicationContext(),
							"请在YTBaseApplication.java中输入正确的授权Key", 
							Toast.LENGTH_LONG).show();
				}
				YTBaseApplication.mApp.m_bKeyRight=false;
			}
		}
		
	}
	//End
	
	public static String MOUSE_METHOD_OUT="mouse_method_out";
	public static String MOUSE_METHOD_IN="mouse_method_in";
	public static String MOUSE_METHOD_CAO="mouse_method_cao";
	
	public static boolean mIsTryConnect = false;
	public static boolean mIsConnected  = false;

	
	private Intent g_httpService;
	Intent g_Service;
	
	public transient static boolean g_Service_runState = false;
		
	public static interface OnLowMemoryListener {
		
		public void onLowMemoryGC();
		
	}
	static final String TAG = YTBaseApplication.class.getSimpleName();

	private ArrayList<WeakReference<OnLowMemoryListener>> mLowMemoryListeners;
	
	private ArrayList<WeakReference<Intent>>              mServices;
	
	private static final Stack<YTBaseActivity>            g_ActivityStack = new Stack<YTBaseActivity>();
	
	public YTBaseApplication(){
		
		mLowMemoryListeners = new ArrayList<WeakReference<OnLowMemoryListener>>();
		mServices           = new ArrayList<WeakReference<Intent>>();
	}
	
	
	@Override
	public void onCreate() {
		//add by shawn 2012-12-17 Begin
		//使用百度地图Api
		if(D2EConfigures.TEST){
			Log.v("YTBaseApplication", "onCreate");
		}
		mApp=this;
		mBMapMan=new BMapManager(this);
		boolean isSuccess=mBMapMan.init(this.mStrKey, new MyGeneralListener());
		//初始化地图sdk成功，设置定位监听时间
		if(isSuccess){
			mBMapMan.getLocationManager().setNotifyInternal(10, 5);
		}else{
			//地图sdk初始化失败，不能使用地图sdk
			if(D2EConfigures.TEST){
				Log.e("mBMapMan.init", "地图sdk初始化失败，不能使用地图sdk");
			}
		}
		
		//End
		super.onCreate();
		if (!YTBaseService.g_runState) {
			g_httpService = new Intent(this, YTBaseService.class);
			startService(g_httpService);
		}
	}
	
	public Class<?> getMainActivityClass(){
		
		return null;
		
	}
	
	public Intent getMainApplicationIntent(){
		
		return null;
		
	}
	
	public void registerService(Class<?> serviceClass){
		
		Intent service = new Intent( this, serviceClass );
		service.setType( serviceClass.getSimpleName().toLowerCase() );
		startService( service );
		
		if( null != mServices ){
			mServices.add( new WeakReference<Intent>( service ) );
		}
		if(D2EConfigures.TEST){
			Log.e(TAG, "startService 【"+ service.toString() +"】");
		}
	}

	public void unregisterService(Class<?> serviceClass){
		
		if( null != mServices ){
			int i = 0;
			while( i < mServices.size() ){
				final Intent l = mServices.get( i ).get();
				if( l == null || 
					l.getType().toLowerCase().equals( serviceClass.getSimpleName().toLowerCase() ) ){
					stopService(l);
					mServices.remove( i );
				}
				else {
					i ++;
				}
			}
		}
		
	}
	
	public void registerOnLowMemoryListener(OnLowMemoryListener listener){
		
		if( null != mLowMemoryListeners ){
			mLowMemoryListeners.add( new WeakReference<OnLowMemoryListener>(  listener ) );
		}
		
	}
	
	public void unregisterOnLowMemoryListener(OnLowMemoryListener listener){
		
		if( null != mLowMemoryListeners ){
			int i = 0;
			while( i < mLowMemoryListeners.size() ){
				final OnLowMemoryListener l = mLowMemoryListeners.get( i ).get();
				if( l == null || l == listener ){
					mLowMemoryListeners.remove( i );
				}
				else {
					i ++;
				}
			}
		}
		
	}
	
	
	public void startJJService(){
		if (!g_Service_runState) {
			if(D2EConfigures.TEST){
				Log.e(TAG, "startJJService");
			}
			g_Service = new Intent(this, SOPBluetoothService.class);
			startService(g_Service);
		}
	}
	public void stopJJService(){
		if (g_Service != null) {
			g_Service_runState = false;
			stopService(g_Service);
		}

	}
	
	public void onLowMemory(){
		
		super.onLowMemory();
		if( null != mLowMemoryListeners ){
			int i = 0;
			while( i < mLowMemoryListeners.size() ){
				final OnLowMemoryListener l = mLowMemoryListeners.get( i ).get();
				if( l == null ){
					mLowMemoryListeners.remove( i );
				}
				else {
					l.onLowMemoryGC();
					i ++;
				}
			}
		}
	}
	
	public static YTBaseActivity getActivityByName(String activityName) {
		
		for ( YTBaseActivity activity : g_ActivityStack ) {
			if ( activity.getClass().getName().indexOf( activityName ) >= 0 ) {
				return activity;
			}
		}
		return null;
		
	}

	
	public void toast(String message){
		
        Toast.makeText( getApplicationContext(), message, Toast.LENGTH_SHORT ).show();
        
	}
	
    public void messageDialog( String message, Context context ){
    	
		AlertDialog.Builder builder = new AlertDialog.Builder( context );
		builder.setTitle( "温馨提示" );
		builder.setMessage(message);
		builder.setNegativeButton( "确定", null );
		builder.create().show();
		
    }

    public void setLanguage(Locale language){
    	
    	if( language == null  ){
    		language = Locale.SIMPLIFIED_CHINESE;
    	}
    	Resources res        = getResources();
    	Configuration config = res.getConfiguration();
    	DisplayMetrics dm    = res.getDisplayMetrics();//will be used later
    	res.updateConfiguration(config, dm);
    	
    }
    
    public static void push(YTBaseActivity activity){
    	
    	if(g_ActivityStack.contains( activity )){
    		return;
    	}
    	
    	g_ActivityStack.push( activity );
    	
    	
    	if(D2EConfigures.TEST){
    		Log.e(TAG, "push activity【"+ g_ActivityStack.size() +"】");
    	}
    }
    
    public static YTBaseActivity pop(){
    	
    	if( g_ActivityStack.size() == 0 ){
    		return null;
    	}
    	
    	for(YTBaseActivity act : g_ActivityStack){
    		
    		Log.e(TAG, act.getClass().getSimpleName());
    		
    	}
    	
    	return g_ActivityStack.pop();
    	
    }
    
    public static YTBaseActivity top(){
    	
    	if( g_ActivityStack.size() == 0 ){
    		return null;
    	}

    	return g_ActivityStack.get(g_ActivityStack.size() - 1);
    	
    }
    
    
    public void dealloc(){
    	
    	if(D2EConfigures.TEST){
    		Log.e("YTBaseApplication------------->", "dealloc()");
    	}

	    for(YTBaseActivity act : g_ActivityStack){
		    act.finish();
		    act = null;
	    }

		if(g_Service != null){
			stopService(g_Service);	
		}
		
		if(g_httpService != null){
			stopService(g_httpService);
		}
		g_Service_runState = false;
	}
    
    public static void remove(YTBaseActivity activity){
    	
    	if(!g_ActivityStack.contains( activity )){
    		return;
    	}
    	g_ActivityStack.remove(activity);

    }
    /**
     * 显示一条信息
     * @param message	
     * 要显示的消息
     */
    public void showMessage(String message){
		if(message == null || message.length() == 0 || message.equals("")){
			return;
		}
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_LONG).show();
	}

    //建议在您app的退出之前调用mapadpi的destroy()函数，避免重复初始化带来的时间消耗
	@Override
	public void onTerminate() {
		if(mBMapMan!=null){
			mBMapMan.destroy();
			mBMapMan=null;
		}
		super.onTerminate();
	}
    
    
    

}
