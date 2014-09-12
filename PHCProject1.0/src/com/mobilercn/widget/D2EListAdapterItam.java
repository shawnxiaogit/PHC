package com.mobilercn.widget;

import java.util.ArrayList;


import com.mobilercn.util.YTStringHelper;

public class D2EListAdapterItam {
	private String mTitle;
	private String mId;
	private String mPinYin;
	private String mTagNum;
	
	//绑定设备信息
	public String mType     = "";
	public String mState    = "";
	public String mStateInt = "";
	public String mMemo     = "";
	
	
	
	/**
	 * 区经度
	 */
	private String mAreaLongitude;
	/**
	 * 区纬度
	 */
	private String mAreaLatitude;
	
	//
	public boolean mChecked = false;
		
	public D2EListAdapterItam(String title, String id){
		mTitle = title;
		mId    = id;
		
		try{
			mPinYin = YTStringHelper.cn2FirstSpell(title);
		}catch(Exception ex){}
	}
	
	public final String getTitle(){
		return mTitle;
	}		
	
	public final String getId(){
		return mId;
	}
	
	public final String getPinYin(){
		return mPinYin;
	}
		
	public void setTagNum(String num){
		mTagNum = num;
	}
	
	public String getTagNum(){
		return mTagNum;
	}

	public String getmAreaLongitude() {
		return mAreaLongitude;
	}

	public void setmAreaLongitude(String mAreaLongitude) {
		this.mAreaLongitude = mAreaLongitude;
	}

	public String getmAreaLatitude() {
		return mAreaLatitude;
	}

	public void setmAreaLatitude(String mAreaLatitude) {
		this.mAreaLatitude = mAreaLatitude;
	}
	
	
}
