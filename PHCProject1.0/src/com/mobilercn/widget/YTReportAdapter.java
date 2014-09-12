package com.mobilercn.widget;

public interface YTReportAdapter {
	
	//表格列
	public int getColumns();
	
	//多少行
	public int getRows();
	
	//指定元素
	public Object getField(int row, int column);
	
	//指定列中的最大宽
	public float getMaxWidthAtColumn(int column);
	
	//指定行中的最大高度
	public float getMaxHeight();

	//指定行中的最大高度
	public float getMaxWidth();

}
