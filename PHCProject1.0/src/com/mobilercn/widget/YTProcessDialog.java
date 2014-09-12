package com.mobilercn.widget;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.widget.TextView;

public class YTProcessDialog extends Dialog {
	
	private Context                mContext = null;
	private static YTProcessDialog mSelf    = null; 
	private int                    mImageID ;
	private int                    mMessageID;
	
	public YTProcessDialog(Context context) {
		super(context);
		
		mContext = context;
	}

	public YTProcessDialog(Context context, int theme) {
		super(context, theme);
		
		mContext = context;
		
	}

	protected YTProcessDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		
		mContext = context;
		
	}
	
	public static YTProcessDialog makeProcessDialog(Context context, int style, int layoutResID, 
			int imageID, int messageID){
		
		mSelf = new YTProcessDialog( context, style );
		mSelf.setContentView( layoutResID );		
		mSelf.mImageID   = imageID;
		mSelf.mMessageID = messageID;
		return mSelf;
		
	}

	public void onWindowFocusChanged(boolean hasFocus){
		
		if( mSelf == null ){
			return;
		}
		
		
		final ImageView  imageView  = ( ImageView ) mSelf.findViewById(mImageID);
		if(null != imageView){
			
			AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground(); 
			animationDrawable.start();
						
		}
		
	}
	

	public void setMessage(String message){
		
		if( mSelf == null ){
			return;
		}
		TextView text = ( TextView ) mSelf.findViewById(mMessageID);
		if( text != null ){
			text.setText(message);
		}
		
	}
}
