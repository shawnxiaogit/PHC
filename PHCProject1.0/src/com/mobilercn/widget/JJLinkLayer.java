/*****************************************************
 * 项目名称         T68 Tag Reader
 * 包名称           com.mobilercn.layout
 * 文件名称         JJLinkLayer.java
 * 编写者           wu.s.w
 *                 www.mobilercn.com
 * 编写日期          2011-12-11
 * Version         1.0
 * 
 * 用于首页功能菜单指示线动画
 * 
 *****************************************************/
package com.mobilercn.widget;

import java.util.Timer;
import java.util.TimerTask;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.view.View;

public class JJLinkLayer extends View {
	
	public static final int DIRECTION_NONE  = 0;
    public static final int DIRECTION_DOWN  = 1;
    public static final int DIRECTION_LEFT  = 2;
    public static final int DIRECTION_RIGHT = 3;
    public static final int DIRECTION_UP    = 4;

    private final long  FRAME_DURATION = 100L;
    private final float ANIMI_DURATION = 2000F;

    /**
     * 方向
     */
    private int    mDirection;
    /**
     * 间隔时间
     */
    private float  mDuration;
    
    /**
     * 当前位置
     */
	private float  mCurrentPos;	
	/**
	 * 当前窗口
	 */
    private float  mCurrentFrame;
    
    /**
     * 资源图片
     */
    private Bitmap mSrcBmp;
    /**
     * 任务图片
     */
    private Bitmap mMaskBmp;
    
    /**
     * 开始位置
     */
    private float  mStartPos;
    /**
     * 窗体总大小
     */
    private float  mTotalFrames;
    /**
     *移动步数 
     */
    private float  mMoveStep;
    
    /**
     * 定时器
     */
    private Timer  mTimer;

	
	public JJLinkLayer(Context context) {
		super(context);
		init();
	}
	
    public JJLinkLayer(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
    
	public JJLinkLayer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
    public void init(){
		mDirection    = DIRECTION_NONE;
		mDuration     = 0F;
		mCurrentPos   = 0F;
		mCurrentFrame = 0F;
		mSrcBmp       = null;
		mMaskBmp      = null;
		mStartPos     = 0F;
		mTotalFrames  = 0F;
		mMoveStep     = 0F;    	
    }
    
    public void setDirection(int direction){
    	if(direction >= DIRECTION_NONE && direction <= DIRECTION_UP){
    		mDirection = direction;
    	}
    }
    
    public void setDuration(float duration){
    	if(duration > 0){
    		mDuration = duration;
    	}
    	else {
    		mDuration = ANIMI_DURATION;
    	}
    	
    	mTotalFrames = mDuration / FRAME_DURATION;
    }
    
    public void setMaskBitmap(int sourceId){
    	mMaskBmp = BitmapFactory.decodeResource(getResources(), sourceId);
    }
    
    public void setSourceBitmap(int sourceId){
    	mSrcBmp = BitmapFactory.decodeResource(getResources(), sourceId);
    }
    
    public void stopAnimation(){
    	if(null != mTimer){
    		mTimer.cancel();
    		mTimer = null;
    	}
    	mCurrentPos   = 0f;
    	mCurrentFrame = 0f;
    	postInvalidate();
    }

    public void startAnimation(boolean runOnce){
    	if(mMaskBmp == null && mSrcBmp == null){
    		return;
    	}
    	//stop old timer
    	if(null != mTimer){
    		mTimer.cancel();
    		mTimer = null;
    	}
    	mCurrentPos   = 0f;
    	mCurrentFrame = 0f;
    	
    	switch(mDirection){
    	
	    	case DIRECTION_DOWN:{
	    		mStartPos = mSrcBmp.getHeight() - mMaskBmp.getHeight(); //< 0
	    		mMoveStep = ( mMaskBmp.getHeight() - mSrcBmp.getHeight()) / mTotalFrames;
	    		mCurrentPos = mStartPos;
	    	}break;
	    	
	    	case DIRECTION_RIGHT:{
	    		mStartPos = mSrcBmp.getWidth() - mMaskBmp.getWidth(); //< 0
	    		mMoveStep = ( mMaskBmp.getWidth() - mSrcBmp.getWidth()) / mTotalFrames;
	    		mCurrentPos = mStartPos;	    		
	    	}break;
	
	    	case DIRECTION_LEFT:{
	    		mStartPos = 0;//- (mSrcBmp.getWidth() - mMaskBmp.getWidth()); //< 0
	    		mMoveStep = - (( mMaskBmp.getWidth() - mSrcBmp.getWidth()) / mTotalFrames);
	    		mCurrentPos = mStartPos;	    			    		
	    	}break;
    	
	    	case DIRECTION_UP:{
	    		mStartPos = 0;//mSrcBmp.getHeight() - mMaskBmp.getHeight(); //< 0
	    		mMoveStep = -(( mMaskBmp.getHeight() - mSrcBmp.getHeight()) / mTotalFrames);
	    		mCurrentPos = mStartPos;	    			    			    		
	    	}break;
    	}
    	
    	//create new timer
    	mTimer = new Timer();
    	JJMaskTimeTask task = new JJMaskTimeTask(runOnce);
    	mTimer.schedule(task, 0L, FRAME_DURATION);
    }

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if(mSrcBmp != null){
			setMeasuredDimension(mSrcBmp.getWidth(), mSrcBmp.getHeight());			
		}
	}
	
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(mSrcBmp == null || mMaskBmp == null){
			return;
		}
				
		Paint paint = new Paint();
		paint.setFilterBitmap(false);
		paint.setStyle(Style.FILL);
		paint.setStyle(Style.STROKE);
		paint.setShader(null);
		
		float w = mSrcBmp.getWidth();
		float h = mSrcBmp.getHeight();
		
		float xp = 0F;
		float yp = 0F;
		
		switch(mDirection){
		
    	case DIRECTION_DOWN:{
    		xp = 0f;
    		yp = mCurrentPos;  
    	}break;
    	
    	case DIRECTION_RIGHT:{
    		xp = mCurrentPos;//(float) (mCurrentPos * (Math.sqrt(2) / 2));
    		yp = 0f;  
    	}break;
    	
    	case DIRECTION_LEFT:{
    		xp = mCurrentPos;//(float) (mCurrentPos * (Math.sqrt(2) / 2));
    		yp = 0f;      		
    	}break;
    	
    	case DIRECTION_UP:{
    		xp = 0f;//(float) (mCurrentPos * (Math.sqrt(2) / 2));
    		yp = mCurrentPos;      		
    	}break;
		}
		
		//System.out.println("3");

		int saveState = canvas.saveLayer(0, 0, w, h, null, Canvas.ALL_SAVE_FLAG);
				
		canvas.translate(xp, yp);
		canvas.drawBitmap(mMaskBmp, 0F, 0F, paint);
		canvas.translate(-xp, -yp);
			
		PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(Mode.SRC_IN);
		paint.setXfermode(porterDuffXfermode);
		canvas.drawBitmap(mSrcBmp, 0F, 0F, paint);
		paint.setXfermode(null);
		
		canvas.restoreToCount(saveState);
	}

	private class JJMaskTimeTask extends TimerTask {
		
		private boolean mRunOnce;
		
		public JJMaskTimeTask(boolean runOnce){
			super();
			mRunOnce = runOnce;
		}

		@Override
		public void run() {
			if(mCurrentFrame <= mTotalFrames){
				mCurrentPos += mMoveStep;				
				mCurrentFrame += 1F;
				if(mCurrentPos > 0){
					mCurrentPos = mStartPos;
				}
			}
			else {//finish a cycle
				if(mRunOnce){
					stopAnimation();
				}
				else {
					mCurrentPos   = mStartPos;
					mCurrentFrame = 0f;
				}
			}
			postInvalidate();
		}
		
	}

}
