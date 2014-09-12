package com.vsi.data;

public class CaoItem {
	
	public static final int TYPE_MOUSE = 1;
	public static final int TYPE_COVER = 2;
	
	private String strTime;
	private String strKeep;
	
	private int    mType;
	
	
	
	public CaoItem(int type) {
		mType = type;
	}
	
	public int getType(){
		
		return mType;
		
	}
	
	public String getStrTime() {
		return strTime;
	}
	public void setStrTime(String strTime) {
		this.strTime = strTime;
	}
	public String getStrKeep() {
		return strKeep;
	}
	public void setStrKeep(String strKeep) {
		this.strKeep = strKeep;
	}
	
}
