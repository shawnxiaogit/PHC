package com.mobilercn.base;

import com.mobilercn.sop.bt.SOPBluetoothService;
import com.mobilercn.widget.YTProcessDialog;

import com.vsi.phc.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.LayoutInflater.Factory;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

public abstract class YTBaseActivity extends Activity {
	public static final int  TYPE_BT   = 1;
	public static final int  TYPE_HTTP = 2;
	/** 等待进度框 */
	private static ProgressDialog mProgressDialog = null;
	/** 当前任务ID */
	//当前是否是等待界面
	private boolean        mShowProgressView;
	private static PopupWindow           mPopupWindow;
	public static final long TASK_CANCEL        = -9999;
	private long            mCurTaskID          = TASK_CANCEL;
	private boolean         mShowWaitDlg        = false;
	
	private int mWaitViewType ;
	
	private static YTProcessDialog mProcessDlg  = null;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//隐藏键盘,点击EditText的时候才弹出键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		YTBaseApplication.push(this);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		mShowProgressView = false;
		OnInit();
		
	}
	
	protected Handler myHandler = new Handler(){
		
        public void handleMessage(Message msg) {
        	showProcessDialog("test");
        }
    };
    
    
    public void showWaitDialog(String message){
		mWaitViewType = TYPE_HTTP;
		mShowProgressView = true;
		mProgressDialog = ProgressDialog.show(YTBaseActivity.this, null, message, true);
		mProgressDialog.setOnKeyListener(new OnKeyListener(){
			public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
				return false;
			}
			
		});
	}
	
	private void dismissWaitDialog(){
		mShowProgressView = false;
		YTBaseService.addCancelTask(mCurTaskID);
		if(mProgressDialog != null){
			mProgressDialog.dismiss();
			mProgressDialog = null;			
		}
	}
	
	private void cancelProcess(){
		mShowProgressView = false;
		YTBaseService.addCancelTask(mCurTaskID);
	}
	
	protected void OnInit(){
		
	}
	
	public void hideWaitDialog(){
		
		mShowWaitDlg = false;
		if(mProcessDlg != null){
			mProcessDlg.dismiss();
			mProcessDlg = null;
		}
		
	}
	
	
	public void showProcessDialog(String message){
		mWaitViewType = TYPE_BT;
		mShowProgressView = true;
        LayoutInflater factory = LayoutInflater.from(this);
        final View mProgressView = factory.inflate(R.layout.d2e_progress, null);
        TextView mProcessMessage = (TextView)mProgressView.findViewById(R.id.loading_tip_textview);	        
        ImageView mImageView = (ImageView)mProgressView.findViewById(R.id.loading_anim_imgview);
		mProcessMessage.setText(message);
		final AnimationDrawable mAnimationDrawable = (AnimationDrawable)mImageView.getBackground();         
	   
		if(mPopupWindow == null){
			mPopupWindow = new PopupWindow(mProgressView,LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT,true);//获取PopupWindow对象并设置窗体的大小
			mPopupWindow.setAnimationStyle(R.anim.fade);
		}
		mPopupWindow.showAtLocation(mProgressView, Gravity.CENTER, 0, 0);			

		(new Thread() {
            public void run(){
            	try{
            	Thread.sleep(400);
            	}catch(Exception e){}
            	mAnimationDrawable.start();
            }
        }).start();		
	}
	
	
	public void dismissProcessDialog(){		
		mShowProgressView = false;
    	try{
			if(mPopupWindow != null){
				mPopupWindow.dismiss();
				mPopupWindow = null;
			}			
    	}catch(Exception e){}
    	
    	try{
			if(mProgressDialog != null){
				mProgressDialog.dismiss();
				mProgressDialog = null;			
			}
    	}
    	catch(Exception ex){}
		
	}
	
	
	
	protected void setMenuBackground(){

		if(getLayoutInflater().getFactory() != null){
			return;
		}
		getLayoutInflater().setFactory( new Factory() {
            
			public View onCreateView(String name, Context context,
					AttributeSet attrs) {
                if ( name.equalsIgnoreCase( "com.android.internal.view.menu.IconMenuItemView" ) ) {
                    
                    try { // Ask our inflater to create the view
                        LayoutInflater f = getLayoutInflater();
                        final View view = f.createView( name, null, attrs );
                        new Handler().post( new Runnable() {
                            public void run () {
                                view.setBackgroundColor(Color.WHITE);//设置背景色
                            }
                        } );
                        return view;
                    }
                    catch ( InflateException e ) {}
                    catch ( ClassNotFoundException e ) {}
                }
                return null;
			}
        });
    }
	
	
	public void showWaitDialog( String message, int style, int layoutResID, 
			int imageID, int messageID ){
		
		mShowWaitDlg = true;
		if( mProcessDlg == null ){
			mProcessDlg = YTProcessDialog.makeProcessDialog(YTBaseActivity.this, style, layoutResID, imageID, messageID);
		}
		mProcessDlg.setMessage(message);
		mProcessDlg.setOnKeyListener(new OnKeyListener(){

			@Override
			public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
				
				hideWaitDialog();
				return true;
			}
			
		});
		mProcessDlg.show();
		
	}
	

	@Override
	protected void onDestroy() {
		if(mPopupWindow != null){
			mPopupWindow.dismiss();
			mPopupWindow = null;
		}
		if(mProgressDialog != null){
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
		super.onDestroy();
		
		YTBaseApplication.remove(this);
		hideWaitDialog();
		super.onDestroy();		
	}
	
	
	public YTBaseApplication getApp(){
		
		return (YTBaseApplication)getApplication();
		
	}
	
	public void setCurTaskID(long id){
		
		mCurTaskID = id;
		
	}
	
	public long getCurTaskID(){
		
		return mCurTaskID;
		
	}
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			
			if( mShowWaitDlg ){
				
				return true;
			}
			
			if( isExit() ){ 
				
				alertExit();
				
			}
			else {
				
				onBackAciton();
				
			}
			
		}
		
		return super.onKeyDown(keyCode, event);
		
	}
	
	public void alertExit() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getResources().getString(R.string.warming_tips));
		builder.setMessage(getResources().getString(R.string.really_exit));
		builder.setIcon(R.drawable.msg_dlg_warning);
		builder.setPositiveButton(getResources().getString(R.string.dialog_exit_btn_txt), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
				finish();
				getApp().dealloc();
				
				android.os.Process.killProcess(android.os.Process.myPid());
				
			}
		});
		builder.setNegativeButton(getResources().getString(R.string.dialog_cancle_btn_txt), null);
		builder.create().show();

		
	}
	
	public abstract void    onBackAciton();
	
	public abstract boolean isExit();
	
	public abstract void    taskSuccess(Object... param);
	
	public abstract void    taskFailed(Object... param);
	
	public abstract void    taskProcessing(Object... param);

}
