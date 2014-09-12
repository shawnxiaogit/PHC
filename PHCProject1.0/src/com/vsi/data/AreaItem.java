package com.vsi.data;

/**
 * 区列表项数据
 * @author ShawnXiao
 *
 */
public class AreaItem {
	/**
	 * 爱委会id
	 */
	private String strOrgID;
	/**
	 * 区名字
	 */
	private String strAreaName;
	/**
	 * 区id
	 */
	private String strAreaID;
	/**
	 * 区经度
	 */
	private String mAreaLongitude;
	/**
	 * 区纬度
	 */
	private String mAreaLatitude;
	/**
	 * 区删除时间
	 */
	private String mAreaDeleteTime;
	/**
	 * 区创建时间
	 */
	private String mAreaCreateTime;
	
	
	public AreaItem(){
		
	}
	
	public AreaItem(String name){
		this.strAreaName=name;
	}
	public AreaItem(String id,String name){
		this.strAreaID=id;
		this.strAreaName=name;
	}
	
	
	public String getStrOrgID() {
		return strOrgID;
	}
	public void setStrOrgID(String strOrgID) {
		this.strOrgID = strOrgID;
	}
	public String getStrAreaName() {
		return strAreaName;
	}
	public void setStrAreaName(String strAreaName) {
		this.strAreaName = strAreaName;
	}
	public String getStrAreaID() {
		return strAreaID;
	}
	public void setStrAreaID(String strAreaID) {
		this.strAreaID = strAreaID;
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
	public String getmAreaDeleteTime() {
		return mAreaDeleteTime;
	}
	public void setmAreaDeleteTime(String mAreaDeleteTime) {
		this.mAreaDeleteTime = mAreaDeleteTime;
	}
	public String getmAreaCreateTime() {
		return mAreaCreateTime;
	}
	public void setmAreaCreateTime(String mAreaCreateTime) {
		this.mAreaCreateTime = mAreaCreateTime;
	}
	
}
