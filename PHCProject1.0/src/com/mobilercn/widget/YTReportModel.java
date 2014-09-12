package com.mobilercn.widget;

import java.util.ArrayList;
import java.util.List;

import com.mobilercn.util.YTMathUtil;
import com.mobilercn.util.YTStringHelper;

import android.content.Context;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.style.StyleSpan;
import android.util.Log;

/**
 *报表一项的模型 
 */
public class YTReportModel implements YTReportAdapter {
	
	//modify by shawn 2012-8-30
	
	private static final int HORIZONTAL = 8;
	private static final int VERTICAL   = 4; 
	
	/**
	 * 表字符串数组
	 */
	private String[]       mHeader = null;
	private String[]       mLeft   = null;
	/**
	 * 行内容列表
	 */
	private List<String[]> mItems  = new ArrayList<String[]>();
	
	/**
	 * 列宽
	 */
	private float[]        mWidthOfColumn;
	private float[]        mWidthOfRow;
	
	/**
	 * 总宽度
	 */
	private float          mTotalWidth;
	/**
	 * 列数
	 */
	private int            mColumns;
	
	/**
	 * 画笔
	 */
	private Paint          mPaint;
	
	
	/**
	 * 上下文
	 */
	private Context        mContext;
	
	
	private float          mMaxHeaderWidth;
	
	public YTReportModel(Paint paint, Context context){
		
		mPaint         = paint;
		mContext       = context;
		mColumns       = 0;
		mTotalWidth    = 0f;
		mWidthOfColumn = null;
		mWidthOfRow    = null;
		
		mMaxHeaderWidth = -1;
		
	}
	/**
	 * 清空所有信息
	 */
	public void clear(){
		
		mColumns       = 0;
		mTotalWidth    = 0f;
		mWidthOfColumn = null;
		mLeft = null;
		mHeader = null;
		mItems.clear();
		
	}
	
	// +-----------------------------------------------------------
	// |                取当前行包含的列数
	// +-----------------------------------------------------------
	/**
	 * 获取当前行保航列数
	 * @param row
	 * 当前行
	 * @return
	 * 整型 列数
	 */
	public int getColumnOfRow(int row){
		
		if(row < 0 || row >= mItems.size()){
			return 0;
		}
		
		String[] tmp = mItems.get(row);		
		return tmp.length;
		
	}
	
	public float getMaxWidthOfRows(int index){
		if(index < 0 || index > mItems.size()){
			return 0;
		}
		return mWidthOfRow[index];
	}
	
	private void calculateWidthOfRow(String[] rows){
		
		
		
		int cnt = rows.length;
		float len = 0;
		
		if(cnt == 1){
			len = getMaxWidth();
		}
		else {
			for(int i = 0; i < cnt; i ++){
				
				len = Math.max(len, mPaint.measureText(rows[i]) + YTMathUtil.dipToPixels(HORIZONTAL, mContext.getResources()) * 2);
				
			}			
		}
		
		
		if(mWidthOfRow == null){
			mWidthOfRow = new float[1];
			mWidthOfRow[0] = len;
		}
		else {
			float[] tmp = new float[mWidthOfRow.length + 1];
			System.arraycopy(mWidthOfRow, 0, tmp, 0, mWidthOfRow.length);
			tmp[mWidthOfRow.length] = len;
			mWidthOfRow = null;
			mWidthOfRow = tmp;
		}

	}
	
	/**
	 * 测量列宽	
	 * @param rows
	 * 字符串数组 行内容
	 */
	private void calculateWidthOfColumn(String[] rows){
		
		float total = 0;
		if(mColumns < rows.length){
			
			mColumns = rows.length;
			
			if(mWidthOfColumn != null){
				float[] tmp = new float[mColumns];
				System.arraycopy(mWidthOfColumn, 0, tmp, 0, mWidthOfColumn.length);
				mWidthOfColumn = null;
				mWidthOfColumn = tmp;				
			} 
			else {
				mWidthOfColumn = new float[mColumns];
			}
			
			total = 0;
			for(int i = 0; i < mColumns; i ++ ){
				mWidthOfColumn[i] = Math.max(mWidthOfColumn[i], mPaint.measureText(rows[i]) + YTMathUtil.dipToPixels(HORIZONTAL, mContext.getResources()) * 2);
				total += mWidthOfColumn[i];
			}		
		}
		else {
			mColumns = rows.length;
			total = 0;
			for(int i = 0; i < mColumns; i ++ ){						
				mWidthOfColumn[i] = Math.max(mWidthOfColumn[i], mPaint.measureText(rows[i]) + YTMathUtil.dipToPixels(HORIZONTAL, mContext.getResources()) * 2);
				total += mWidthOfColumn[i];
			}					
		}
		
		mTotalWidth = Math.max(mTotalWidth, total);
			
	}
	/**
	 * 设置表头内容
	 * @param header
	 * 字符串数组表头内容
	 */
	public void setHeader(String[] header){
		if(header == null || header.length == 0){
			return;
		}
		mHeader=header;
		calculateWidthOfColumn(header);
		
	}
	/**
	 * 获取表头
	 * @return
	 * 字符串数组表头内容
	 */
	public String[] getHeader(){
		
		return mHeader;
		
	}
	
	public float getMaxHeaderWidth(){
		
		if(mMaxHeaderWidth > 0){
			return mMaxHeaderWidth;
		}
		
		int cnt = mHeader.length;
		for(int i = 0; i < cnt; i ++){
			
			mMaxHeaderWidth = Math.max(mMaxHeaderWidth, mPaint.measureText(mHeader[i]) + YTMathUtil.dipToPixels(HORIZONTAL, mContext.getResources()) * 2);
			
		}
		
		return mMaxHeaderWidth;
		
	}
	
	/**
	 * 判断是否有表头
	 * @return
	 * 浮点型
	 */
	public boolean hasHeader(){
		
		return mHeader != null;
		
	}
	/**
	 * 给定行中添加一条信息
	 * @param row
	 * 行信息
	 * @param index
	 * 指定行
	 */
	public void addItemAt(String[] row, int index){
		
		
		if(row == null || row.length == 0){
			return;
		}
		
		if(index < 0 || index > mItems.size() ){
			return;
		}
		
		if(row.length > 1){
			calculateWidthOfColumn(row);			
			
		}
		calculateWidthOfRow(row);
		
		mItems.add(index, row);
	}
	/**
	 * 报表添加一行信息
	 * @param row
	 */
	public void addItem(String[] row){
		
		addItemAt(row, mItems.size());
	}
	/**
	 * 获取总宽度
	 * @return
	 * 浮点型总宽度
	 */
	public float getTotalWidth(){
		
		return mTotalWidth;
		
	}
	/**
	 * 设置总宽度
	 * @param f
	 * 浮点型宽度
	 */
	public void setTotalWidth(float f){
		
		mTotalWidth = f;
		
	}
	
	/**
	 * 获取列数
	 */
	@Override
	public int getColumns() {
		return mColumns;
	}
	/**
	 * 获取行数
	 */
	@Override
	public int getRows() {
		return mItems.size();
	}
	/**
	 * 获取指定行和列的对象
	 * @param row
	 * 指定行
	 * @param column
	 * 指定列
	 */
	@Override
	public Object getField(int row, int column) {
		
		
		if(row < 0 || row > mItems.size()){
			return null;
		}
		
				
		String[] tmp = mItems.get(row);
		
		
		if(column < 0 || column >= tmp.length){
			return null;
		}
		
		
		return tmp[column];
	}
	/**
	 * 获取指定列的最大宽度
	 * @param column 
	 * 整型指定列
	 */
	@Override
	public float getMaxWidthAtColumn(int column) {
		
		if(column < 0 || column >= getColumns()){
			return 0;
		}
		
		return mWidthOfColumn[column];
	}
	
	public void setMaxWidthAtColumn(int column, float f){
		
		if(column < 0 || column >= getColumns()){
			return ;
		}
		
		mWidthOfColumn[column] = f;

		
	}
	/**
	 * 获取最大高度
	 */
	@Override
	public float getMaxHeight() {
		return mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top + YTMathUtil.dipToPixels(VERTICAL, mContext.getResources()) * 3;
	}
	
	/**
	 * 获取最大宽度
	 */
	@Override
	public float getMaxWidth() {
		return YTMathUtil.dipToPixels(40, mContext.getResources());
	}
	
	
	
}
