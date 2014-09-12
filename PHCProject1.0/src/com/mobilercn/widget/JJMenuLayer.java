/*****************************************************
 * 项目名称         T68 Tag Reader
 * 包名称           com.mobilercn.layout
 * 文件名称         JJMenuLayer.java
 * 编写者           wu.s.w
 *                 www.mobilercn.com
 * 编写日期          2011-12-10
 * Version         1.0
 * 
 * 用于首页功能菜单图标滑选效果
 * 
 *****************************************************/
package com.mobilercn.widget;

import java.util.Timer;

import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class JJMenuLayer extends FrameLayout {
	private static final int SLIDE_NONE    = 0;
	private static final int SLIDE_BEGIN   = 1;
	private static final int SLIDE_MOVING  = 2;
	private static final int SLIDE_FINISED = 3;
	
	private static final int TOPDIS = 0;
	
	public  static final int TOP    = 0;
	public  static final int LEFT   = 1;
	public  static final int RIGHT  = 2;
	private static final int CENTER = 3;
	public  static final int DOWN   = 4;
	private static final int NONE   = 5;
	
	private OnMenuSelection mMenuSelectionListener;
	
	private ImageView[] indexs = new ImageView[5];
	private int from = NONE;
	
	//source id
	private int mTop;
	private int mTopSelection;
	
	private int mLeft;
	private int mLeftSelection;
	
	private int mRight;
	private int mRightSelection;
	
	private int mCenter;
	private int mCenterSelection;
	
	private int mDown;
	private int mDownSelection;
	
	//view
	private ImageView mTopMenu;
	private ImageView mCenterMenu;
	private ImageView mLeftMenu;
	private ImageView mRightMenu;
	private ImageView mDownMenu;
	
	private int mSlideStates;
	
	
	private int[] mCenterPosition = new int[4];
	private int[] mTopPosition    = new int[4];
	private int[] mLeftPosition   = new int[4];
	private int[] mRightPosition  = new int[4];
	private int[] mDownPosition   = new int[4];
	
	

	public JJMenuLayer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public JJMenuLayer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public JJMenuLayer(Context context) {
		super(context);
	}

	public void initPositions(){
		
		indexs[CENTER] = mCenterMenu;
		indexs[TOP] = mTopMenu;
		indexs[LEFT] = mLeftMenu;
		indexs[RIGHT] = mRightMenu;
		indexs[DOWN] = mDownMenu;
		
		
		mCenterMenu.layout(mCenterPosition[0], mCenterPosition[1], mCenterPosition[2], mCenterPosition[3]);
		mTopMenu.layout(mTopPosition[0], mTopPosition[1], mTopPosition[2], mTopPosition[3]);
		mLeftMenu.layout(mLeftPosition[0], mLeftPosition[1], mLeftPosition[2], mLeftPosition[3]);
		mDownMenu.layout(mDownPosition[0], mDownPosition[1], mDownPosition[2], mDownPosition[3]);	
		mRightMenu.layout(mRightPosition[0], mRightPosition[1], mRightPosition[2], mRightPosition[3]);
		resetIcon();	
		
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if(null != indexs[CENTER]){
			
			ImageView view = indexs[CENTER];
						
			int lt = (right - left - view.getMeasuredWidth() ) >> 1;
			int tp = (bottom - top - view.getMeasuredHeight()) >> 1;
			int rt = lt + view.getMeasuredWidth();
			int bm = tp + view.getMeasuredHeight();
			mCenterPosition[0] = lt;
			mCenterPosition[1] = tp;
			mCenterPosition[2] = rt;
			mCenterPosition[3] = bm;
			view.layout(mCenterPosition[0], mCenterPosition[1], mCenterPosition[2], mCenterPosition[3]);
		}
	
		if(null != indexs[TOP]){
			ImageView view = indexs[TOP];
			int lt = (right - left - view.getMeasuredWidth() ) >> 1;
			int tp = TOPDIS;
			int rt = lt + view.getMeasuredWidth();
			int bm = tp + view.getMeasuredHeight();
			mTopPosition[0] = lt;
			mTopPosition[1] = tp;
			mTopPosition[2] = rt;
			mTopPosition[3] = bm;
			view.layout(mTopPosition[0], mTopPosition[1], mTopPosition[2], mTopPosition[3]);
		}
		
		if(null != indexs[LEFT]){
			ImageView view = indexs[LEFT];
			int lt = left;
			int tp = (bottom - top - view.getMeasuredHeight()) >> 1;
			int rt = lt + view.getMeasuredWidth();
			int bm = tp + view.getMeasuredHeight();
			mLeftPosition[0] = lt;
			mLeftPosition[1] = tp;
			mLeftPosition[2] = rt;
			mLeftPosition[3] = bm;
			view.layout(mLeftPosition[0], mLeftPosition[1], mLeftPosition[2], mLeftPosition[3]);
			
		}
		
		if(null != indexs[RIGHT]){
			ImageView view = indexs[RIGHT];
			int lt = right - view.getMeasuredWidth();
			int tp = (bottom - top - view.getMeasuredHeight()) >> 1;
			int rt = right;
			int bm = tp + view.getMeasuredHeight();
			mRightPosition[0] = lt;
			mRightPosition[1] = tp;
			mRightPosition[2] = rt;
			mRightPosition[3] = bm;
			view.layout(mRightPosition[0], mRightPosition[1], mRightPosition[2], mRightPosition[3]);
		}
		
		if(null != indexs[DOWN]){
			ImageView view = indexs[DOWN];
			int lt = (right - left - view.getMeasuredWidth() ) >> 1;
			int bm = bottom - TOPDIS;
			int rt = lt + view.getMeasuredWidth();
			int tp = bm - view.getMeasuredHeight();
			mDownPosition[0] = lt;
			mDownPosition[1] = tp;
			mDownPosition[2] = rt;
			mDownPosition[3] = bm;
			view.layout(mDownPosition[0], mDownPosition[1], mDownPosition[2], mDownPosition[3]);	
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();			
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			resetIcon();
			if(inRect(x, y, mTopPosition)){
				from = TOP;
				changeIcon(TOP);
				mSlideStates = SLIDE_BEGIN;
			}
			else if(inRect(x, y, mLeftPosition)){
				from = LEFT;
				changeIcon(LEFT);
				mSlideStates = SLIDE_BEGIN;
			}
			else if(inRect(x, y, mRightPosition)){
				from = RIGHT;
				changeIcon(RIGHT);
				mSlideStates = SLIDE_BEGIN;
			}
			else if(inRect(x,y,mDownPosition)){
				from = DOWN;
				changeIcon(DOWN);
				mSlideStates = SLIDE_BEGIN;
			}
			
		}
		else if(event.getAction() == MotionEvent.ACTION_UP){			
			if(inRect(x, y, mCenterPosition)  && mSlideStates == SLIDE_BEGIN){
				int direct = MotionTask.MOTION_DOWN;
				if(from == LEFT){
					direct = MotionTask.MOTION_RIGHT;
				}
				else if(from == RIGHT){
					direct = MotionTask.MOTION_RIGHT;
				}
				else if(from == DOWN){
					direct = MotionTask.MOTION_UP;
				}
				startMoving(direct);
			}
			else {
				mSlideStates = SLIDE_NONE;
				resetIcon();
			}
		}
		else if(event.getAction() == MotionEvent.ACTION_MOVE){
			
		}
				
		return true;
	}
	
	private void changeIcon(int pos){
		
		ImageView fromview = indexs[pos];
		Integer tag = (Integer)fromview.getTag();
		if(tag.intValue() == OnMenuSelection.MENU_TOP){
			mTopMenu.setImageResource(mTopSelection);
		}
		else if(tag.intValue() == OnMenuSelection.MENU_LEFT){
			mLeftMenu.setImageResource(mLeftSelection);	
		}
		else if(tag.intValue() == OnMenuSelection.MENU_RIGHT){
			mRightMenu.setImageResource(mRightSelection);	
		}
		else if(tag.intValue() == OnMenuSelection.MENU_NONE){
			mCenterMenu.setImageResource(mCenterSelection);
		}
		else if(tag.intValue() == OnMenuSelection.MENU_BOTTOM){
			mDownMenu.setImageResource(mDownSelection);
		}
	}
	
	private void resetIcon(){
		mTopMenu.setImageResource(mTop);
		mLeftMenu.setImageResource(mLeft);	
		mRightMenu.setImageResource(mRight);
		mCenterMenu.setImageResource(mCenter);
		mDownMenu.setImageResource(mDown);
	}
	
	private boolean inRect(float x, float y, int[] pos){
		if(x >= pos[0] && x <= pos[2]
				&& y >= pos[1] && y <= pos[3]){
			return true;
			
		}		
		return false;
	}
	
	private void exchangeMenu(){
		
		if(from == NONE){
			return;
		}
		ImageView fromview = indexs[from];
		ImageView toview   = indexs[CENTER];
		
		indexs[from]   = toview;
		indexs[CENTER] = fromview;
		int tl = 0;
		int tt = 0;
		int tr = 0;
		int tb = 0;
		
		if(from == TOP){
			tl = mTopPosition[0];
			tt = mTopPosition[1];
			tr = mTopPosition[2];
			tb = mTopPosition[3];
		}
		else if(from == LEFT){
			tl = mLeftPosition[0];
			tt = mLeftPosition[1];
			tr = mLeftPosition[2];
			tb = mLeftPosition[3];			
		}
		else if(from == RIGHT){
			tl = mRightPosition[0];
			tt = mRightPosition[1];
			tr = mRightPosition[2];
			tb = mRightPosition[3];
		}
		else if(from == DOWN){
			tl = mDownPosition[0];
			tt = mDownPosition[1];
			tr = mDownPosition[2];
			tb = mDownPosition[3];			
		}
		
		toview.layout(tl, tt, tr, tb);
		fromview.layout(mCenterPosition[0], mCenterPosition[1], mCenterPosition[2], mCenterPosition[3]);
		Integer tmp = (Integer)fromview.getTag();
		mMenuSelectionListener.menuSelection(tmp.intValue());
		from = NONE;
	}
	
	//  public method
	public void setSourceId(int top, int topselection, int left, int leftselection, 
			int right, int rightselection, int center, int centerselection,
			int down, int downselection){
		mTop             = top;
		mTopSelection    = topselection;
		
		mLeft            = left;
		mLeftSelection   = leftselection;
		
		mRight           = right;
		mRightSelection  = rightselection;
		
		mCenter          = center;
		mCenterSelection = centerselection;
		
		mDown            = down;
		mDownSelection   = downselection;
		
		//create all imageviews
		if(null == mCenterMenu){
			mCenterMenu = new ImageView(getContext());
			mCenterMenu.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
			mCenterMenu.setImageResource(mCenter);
			mCenterMenu.setTag(OnMenuSelection.MENU_NONE);
			addView(mCenterMenu);
			indexs[CENTER] = mCenterMenu;
		}
		
		if(null == mTopMenu){
			mTopMenu = new ImageView(getContext());
			mTopMenu.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
			mTopMenu.setImageResource(mTop);
			mTopMenu.setTag(OnMenuSelection.MENU_TOP);
			addView(mTopMenu);
			indexs[TOP] = mTopMenu;
		}

		if(null == mLeftMenu){
			mLeftMenu = new ImageView(getContext());
			mLeftMenu.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
			mLeftMenu.setImageResource(mLeft);
			mLeftMenu.setTag(OnMenuSelection.MENU_LEFT);
			addView(mLeftMenu);
			indexs[LEFT] = mLeftMenu;
		}

		if(null == mRightMenu){
			mRightMenu = new ImageView(getContext());
			mRightMenu.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
			mRightMenu.setImageResource(mRight);
			mRightMenu.setTag(OnMenuSelection.MENU_RIGHT);
			addView(mRightMenu);
			indexs[RIGHT] = mRightMenu;
		}
		
		if(null == mDownMenu){
			mDownMenu = new ImageView(getContext());
			mDownMenu.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
			mDownMenu.setImageResource(mDown);
			mDownMenu.setTag(OnMenuSelection.MENU_BOTTOM);
			addView(mDownMenu);
			indexs[DOWN] = mDownMenu;
		}
		
	}
	
	private final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
            
            case SLIDE_MOVING:{
            	ImageView view = indexs[from];
            	int l = view.getLeft() + msg.arg1;
            	int t = view.getTop() + msg.arg2;
            	int r = view.getRight() + msg.arg1;
            	int b = view.getBottom() + msg.arg2;
            	
            	view.bringToFront();
            	view.layout(l, t, r, b);
            }break;
            
            case SLIDE_FINISED:{
            	mSlideStates = SLIDE_NONE;
            	stopMoving();
            	exchangeMenu();
            }break;
            
            }
        }
	};
	
	public void setMenuListener(OnMenuSelection menuSelection){
		mMenuSelectionListener = menuSelection;
	}
	
	private Timer mMotionTimer;
	
	private void startMoving(int motionType){
    	if(null != mMotionTimer){
    		mMotionTimer.cancel();
    		mMotionTimer = null;
    	}

    	mMotionTimer = new Timer();
    	MotionTask task = new MotionTask(motionType);
    	mMotionTimer.schedule(task, 0L, 5L);

	}
	
	private void stopMoving(){
    	if(null != mMotionTimer){
    		mMotionTimer.cancel();
    		mMotionTimer = null;
    	}		
	}
	
	private class MotionTask extends TimerTask {
		public static final int MOTION_DOWN  = 0;
		public static final int MOTION_RIGHT = 1;
		public static final int MOTION_UP    = 3;
		
		public static final int DRATION = 50;
		
		private double mStep;
		private double mTotalStep;
		private double mTotalX;
		private double mTotalY;
		
		private ImageView mView;
		
		private int mLeft;
		private int mTop;
		private int mStart;
		
		public MotionTask(int motionType){
			
			mStart = 0;
			mView   = indexs[from];
			mLeft   = mView.getLeft();
			mTop    = mView.getTop();

			mTotalX    = mCenterPosition[0] - mLeft;
			mTotalY    = mCenterPosition[1] - mTop;
			mTotalStep = Math.hypot(mTotalX, mTotalY);
			mStep = mTotalStep / DRATION;
		}
		
		@Override
		public void run() {
			mStart += mStep;
			int x = (int)((mTotalX * mStep) / mTotalStep);
			int y = (int)((mTotalY * mStep) / mTotalStep);			
			if(mStart >= mTotalStep){
                mHandler.obtainMessage(SLIDE_FINISED, -1, -1, null).sendToTarget();
			}
			else {
                mHandler.obtainMessage(SLIDE_MOVING, x, y, null).sendToTarget();
			}
		}
	}
	
}
