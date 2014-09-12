package com.mobilercn.widget;


import com.vsi.config.D2EConfigures;
import com.vsi.phc.R;
import com.mobilercn.util.YTMathUtil;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class YTReportView extends View {
	
	static final String TAG = YTReportView.class.getSimpleName();

	private static final float DEFAULT_STEP   = 10;
	private static final float DEFAULT_SCROLL = 5;
	
	private float mPaddingTop  = 1;
	private float mPaddingLeft = 1;
	
	private boolean mIsTouch = false;
	private float mLastX = 0;
	private float mLastY = 0;
	private float mScrollOffsetVertial  = 0;
	private float mScrollOffsetHornizal = 0;
	
	private Paint mPaint;
	
	private int   mBackgroundColor = Color.WHITE; 
	private int   mTextColor       = Color.BLACK;

	private YTReportModel    mModel;
	private YTReportRenderer mRenderer;
	
	private int mWidth;
	private int mHeight;
	
	private boolean isRecal;//根据屏幕大小再计算下表格的宽度
	
	private int mIndex;
		
	
	public YTReportView(Context context){
		super(context);
		init();
	}
	
	public YTReportView(Context context, AttributeSet attrs){
		super(context, attrs);
		init();
	}
	
	public YTReportView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public int scrollLeft(boolean flag){
		
		if(flag){
			if(mIndex < mModel.getRows() - 1){
				mIndex ++;
				mScrollOffsetHornizal -= mWidth - mModel.getMaxHeaderWidth() + mPaddingLeft ;
			}
		}
		else {
			if(mIndex > 0){
				mIndex --;
				mScrollOffsetHornizal += mWidth - mModel.getMaxHeaderWidth() +  mPaddingLeft;	
			}
		}
		invalidate();
		return mIndex;
	}

	private void init(){
		
		isRecal   = false;
		mPaint    = new Paint();
		mPaint.setTextSize(22.0f);
		Typeface font = Typeface.create(Typeface.DEFAULT_BOLD, Typeface.NORMAL);
		mPaint.setTypeface(font);
		mRenderer = new YTReportRenderer();
		mIndex = 0;
	}
	
	private int getColor(int id){
		
		return getResources().getColor(id);
		
	}
	
	public Paint getPaint(){
		
		return mPaint;
		
	}
	
	public void setModel(YTReportModel model){
		
		mModel = model;
		
	}
	
	protected void onDraw(Canvas canvas){
		if(D2EConfigures.TEST){
			Log.e(TAG, "执行【 onDraw】");
		}
		if(mModel == null || mModel.getRows() == 0){
			return;
		}
				
		float tmp = 0;
		
		if(mWidth != getWidth()){
			mWidth = getWidth();
		}
		
		if(mHeight != getHeight()){
			mHeight = getHeight();
		}
		
		if(mModel.getTotalWidth() < mWidth ){
			tmp = mWidth / mModel.getColumns();
		}

		if(!isRecal){
			float total = 0;
			for(int i = 0; i < mModel.getColumns(); i ++){
				float f = mModel.getMaxWidthAtColumn(i) > tmp ? mModel.getMaxWidthAtColumn(i) : tmp;
				total += f;
				mModel.setMaxWidthAtColumn(i, f);
			}			
			mModel.setTotalWidth(total);
		}
		
		mRenderer.onDraw(canvas);
		
	}
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			
			mIsTouch = true;
			mLastX = 0;
			mLastY = 0;
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_UP){
			mIsTouch = false;
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_MOVE){
			
			if(!mIsTouch){
				return false;
			}
			
			if(Math.abs(event.getY() - mLastY) > YTMathUtil.dipToPixels(DEFAULT_SCROLL, getResources())){
				if(event.getY() > mLastY){
					mScrollOffsetVertial += YTMathUtil.dipToPixels(DEFAULT_STEP, getResources());
					if(mScrollOffsetVertial > 0){
						mScrollOffsetVertial = 0;
					}
				}
				else {
					if(mModel.getMaxHeight() * (mModel.getHeader().length) > mHeight){
						mScrollOffsetVertial -= YTMathUtil.dipToPixels(DEFAULT_STEP, getResources());
						if(mHeight - mModel.getMaxHeight() * (mModel.getHeader().length + 1) - mPaddingLeft * (mModel.getHeader().length + 1) > mScrollOffsetVertial){
							mScrollOffsetVertial = mHeight - mModel.getMaxHeight() * (mModel.getHeader().length + 1) - mPaddingLeft * (mModel.getHeader().length + 1);
						}
					}
				}				
			}

			
			mLastX = event.getX();
			mLastY = event.getY();
			invalidate();
			return true;
		}		
		
		return super.onTouchEvent(event);
	}



	// +-----------------------------------------------------------
	// |                YTReportRenderer
	// +-----------------------------------------------------------
	class YTReportRenderer {
		
		private YTCellRenderer mCellBackgroundRenderer = new YTCellRenderer(){

			@Override
			void onDrawCell(RectF cellRect, String text, int color, boolean center) {
				
				mCanvas.save();
				
				mCanvas.clipRect(cellRect);
								
				drawCells(mCanvas, cellRect, text, color, center);
								
				mCanvas.restore();
				
			}			
			
			
		};
	
		private void drawCells(Canvas canvas, RectF rect, String text, int color, boolean center){
						
			mPaint.setStyle(Style.FILL);
			mPaint.setColor(mBackgroundColor);
			canvas.drawRect(rect, mPaint);
			
			mPaint.setColor(color);
			canvas.drawRect(rect, mPaint);
			
			mPaint.setColor(mTextColor);
			if(center){
				mPaint.setTextAlign(Align.CENTER);				
			}
			else {
				mPaint.setTextAlign(Align.LEFT);
			}
			
			float fontHeight = mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top; 
			
			float textBaseY = rect.top + (rect.bottom - rect.top) - 
					
					((rect.bottom - rect.top) - fontHeight) / 2 - 
					
					mPaint.getFontMetrics().bottom; 
			if(center){
				canvas.drawText(text, rect.left + (rect.right - rect.left) / 2, 
						textBaseY, 
						mPaint);
				
			}
			else {
				canvas.drawText(text, rect.left + 4 , 
						textBaseY, 
						mPaint);				
			}
		}
		
        private void onDraw(Canvas canvas){
			int headerlen = 0;
			if(mModel.getHeader() != null){
				headerlen = mModel.getHeader().length;	
			}
					
			float headerH = 0;
			if(mModel.getHeader() != null){
			}
			
			
			float x = 0f;
			float y = 0f;
			int rows = mModel.getRows();
			
			for(int i = headerlen - 1; i >= 0; i --){
				y = i * (mModel.getMaxHeight() + mPaddingTop) + headerH + mScrollOffsetVertial;
				x = mScrollOffsetHornizal + mModel.getMaxHeaderWidth();

				for(int j = 0; j < rows; j ++){
					
					float width  =  (mWidth - mModel.getMaxHeaderWidth());//mModel.getMaxWidthOfRows(j) ;						
					float height = mModel.getMaxHeight();
					RectF rect = new RectF();
					rect.set(x, y, x + width, y + height);
										
					mCellBackgroundRenderer.setCanvas(canvas);
					String tmp = mModel.getField(j, i) != null ? (String)mModel.getField(j, i) : "-";
					mCellBackgroundRenderer.onDrawCell(rect, tmp, j % 2 == 0 ? getColor(R.color.frontColor1) : getColor(R.color.frontColor1), false );
					x += width + mPaddingLeft ;
					
				}

			}
			
			
			//绘制第一列
			float tmpy = mModel.getMaxHeight();
			
			int   cnt = 0;
			for(int i = 0; i < headerlen ; i ++){				
									
				tmpy = i * (mModel.getMaxHeight() + mPaddingTop) + mScrollOffsetVertial;
				RectF rect = new RectF();
				rect.set(0, tmpy,  mModel.getMaxHeaderWidth() - mPaddingLeft, tmpy + mModel.getMaxHeight());
									
				mCellBackgroundRenderer.setCanvas(canvas);
				
				mCellBackgroundRenderer.onDrawCell(rect, 
						(mModel.getHeader() != null && mModel.getHeader()[i] != null) ? mModel.getHeader()[i] : cnt > 0 ? "" + cnt : "", 
								getColor(R.color.frontColor1), false);					
				
				if(mModel.getColumnOfRow(i) == 1 && i != 0){
					cnt = 0;
				}
				else {
					cnt ++;
				}
				
				tmpy += mModel.getMaxHeight();
			}

		}
	}
	
	// +-----------------------------------------------------------
	// |                YTCellRenderer
	// +-----------------------------------------------------------
	abstract class YTCellRenderer {
		
		Canvas mCanvas;
		
		public void setCanvas(Canvas canvas){
			mCanvas = canvas;
		}
		
		abstract void onDrawCell(RectF cellRect, String text, int color, boolean center);
	}

	
}
