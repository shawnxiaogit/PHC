package com.vsi.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

/**
 * 监测信息项
 */
public class MonitorItem {
	
	private Context mContext;
	
	private String mId;
	private String mTitle;
	
	//监测相关信息
	/**
	 * 区名称
	 */
	private String strAreaName;
	/**
	 * 监测时间
	 */
	private String strSurveyTime;
	/**
	 * 今日天气
	 */
	private String strWeather;
	/**
	 * 今日温度
	 */
	private String strTempter;
	/**
	 * 监测单位
	 */
	private String strCompany;
	/**
	 * 监测人
	 */
	private String strPerson;
	
	/**
	 * 街道名称
	 */
	private String strStreet;
	
	
	
	//老鼠监测报表相关信息
	/**
	 * 检查米数
	 */
	private String strMouseMeter;
	/**
	 * 鼠迹数
	 */
	private String strMouseTrace;
	/**
	 * 鼠密度(鼠迹数/100m)
	 */
	private String strMouseDensity;
	/**
	 * 粘鼠板数(布放数)
	 */
	private String strMouseSunInit;
	/**
	 * 粘鼠板数(阳性数)
	 */
	private String strMouseSun;
	/**
	 * 捕鼠只数
	 */
	private String strMouseCatchNum;
	/**
	 * 鼠密度(粘捕只数/张)
	 */
	private String strMouseDesitySun;
	
	
	//蚊监测报表相关信息
	/**
	 * 积水容器(下水道)数
	 */
	private String strMosquPond;
	/**
	 * 阳性数
	 */
	private String strMosquPosPond;
	/**
	 * 密度(阳性数/有积水数)
	 */
	private String strMosquDensPond;
	/**
	 * 采集勺数
	 */
	private String strMosquCollSpoo;
	/**
	 * 阳性勺数
	 */
	private String strMosquPosSpoo;
	/**
	 * 密度(阳性勺数/总勺数)
	 */
	private String strMosquDensSpoo;
	
	
	
	//蟑螂监测报表相关信息
	/**
	 * 粘蟑纸数
	 */
	private String strKrockroachStickyPaperNumber;
	/**
	 * 阳性粘蟑纸数
	 */
	private String strKrockroachStickyPaperPosNumber;
	/**
	 * 粘蟑只数
	 */
	private String strKrockroachStickyNumber;
	/**
	 * 密度(粘捕只数/张)
	 */
	private String strKrockroachDensity;
	
	
	
	//蝇监测报表相关信息
	/**
	 * 视野数
	 */
	private String strFlyFieldNumber;
	/**
	 * 成蝇只数
	 */
	private String strFlyMatureNumber;
	/**
	 * 密度(成蝇只数/视野)
	 */
	private String strFlyMatureFieldDensity;
	/**
	 * 粘蝇条数
	 */
	private String strFlyStickBarNumber;
	/**
	 * 捕蝇数
	 */
	private String strFlyCatchNumber;
	/**
	 * 密度(粘捕蝇数/条)
	 */
	private String strFlyStickyCatchDesity;
	
	/**
	 * 构造函数
	 * @param id
	 * 标识
	 * @param title
	 * 标题
	 */
	public MonitorItem(String id,String title){
		this.mId=id;
		this.mTitle=title;
	}
	public MonitorItem(Context context){
		this.mContext=context;
	}
	/**
	 * 信息列表项
	 */
	public List<MonitorItem> monitorItems=new ArrayList<MonitorItem>();
	/**
	 * 添加项
	 * @param item
	 * 项元素
	 */
	public void addItem(MonitorItem item){
		monitorItems.add(item);
	}
	
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	public String getmTitle() {
		return mTitle;
	}
	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public String getStrAreaName() {
		return strAreaName;
	}
	public void setStrAreaName(String strAreaName) {
		this.strAreaName = strAreaName;
	}
	public String getStrSurveyTime() {
		return strSurveyTime;
	}
	public void setStrSurveyTime(String strSurveyTime) {
		this.strSurveyTime = strSurveyTime;
	}
	public String getStrWeather() {
		return strWeather;
	}
	public void setStrWeather(String strWeather) {
		this.strWeather = strWeather;
	}
	public String getStrTempter() {
		return strTempter;
	}
	public void setStrTempter(String strTempter) {
		this.strTempter = strTempter;
	}
	public String getStrCompany() {
		return strCompany;
	}
	public void setStrCompany(String strCompany) {
		this.strCompany = strCompany;
	}
	public String getStrPersion() {
		return strPerson;
	}
	public void setStrPersion(String strPerson) {
		this.strPerson = strPerson;
	}
	public String getStrStreet() {
		return strStreet;
	}
	public void setStrStreet(String strStreet) {
		this.strStreet = strStreet;
	}
	public String getStrMouseMeter() {
		return strMouseMeter;
	}
	public void setStrMouseMeter(String strMouseMeter) {
		this.strMouseMeter = strMouseMeter;
	}
	public String getStrMouseTrace() {
		return strMouseTrace;
	}
	public void setStrMouseTrace(String strMouseTrace) {
		this.strMouseTrace = strMouseTrace;
	}
	public String getStrMouseDensity() {
		return strMouseDensity;
	}
	public void setStrMouseDensity(String strMouseDensity) {
		this.strMouseDensity = strMouseDensity;
	}
	public String getStrMouseSunInit() {
		return strMouseSunInit;
	}
	public void setStrMouseSunInit(String strMouseSunInit) {
		this.strMouseSunInit = strMouseSunInit;
	}
	public String getStrMouseSun() {
		return strMouseSun;
	}
	public void setStrMouseSun(String strMouseSun) {
		this.strMouseSun = strMouseSun;
	}
	public String getStrMouseCatchNum() {
		return strMouseCatchNum;
	}
	public void setStrMouseCatchNum(String strMouseCatchNum) {
		this.strMouseCatchNum = strMouseCatchNum;
	}
	public String getStrMouseDesitySun() {
		return strMouseDesitySun;
	}
	public void setStrMouseDesitySun(String strMouseDesitySun) {
		this.strMouseDesitySun = strMouseDesitySun;
	}
	public String getStrMosquPond() {
		return strMosquPond;
	}
	public void setStrMosquPond(String strMosquPond) {
		this.strMosquPond = strMosquPond;
	}
	public String getStrMosquPosPond() {
		return strMosquPosPond;
	}
	public void setStrMosquPosPond(String strMosquPosPond) {
		this.strMosquPosPond = strMosquPosPond;
	}
	public String getStrMosquDensPond() {
		return strMosquDensPond;
	}
	public void setStrMosquDensPond(String strMosquDensPond) {
		this.strMosquDensPond = strMosquDensPond;
	}
	public String getStrMosquCollSpoo() {
		return strMosquCollSpoo;
	}
	public void setStrMosquCollSpoo(String strMosquCollSpoo) {
		this.strMosquCollSpoo = strMosquCollSpoo;
	}
	public String getStrMosquPosSpoo() {
		return strMosquPosSpoo;
	}
	public void setStrMosquPosSpoo(String strMosquPosSpoo) {
		this.strMosquPosSpoo = strMosquPosSpoo;
	}
	public String getStrMosquDensSpoo() {
		return strMosquDensSpoo;
	}
	public void setStrMosquDensSpoo(String strMosquDensSpoo) {
		this.strMosquDensSpoo = strMosquDensSpoo;
	}
	public String getStrKrockroachStickyPaperNumber() {
		return strKrockroachStickyPaperNumber;
	}
	public void setStrKrockroachStickyPaperNumber(
			String strKrockroachStickyPaperNumber) {
		this.strKrockroachStickyPaperNumber = strKrockroachStickyPaperNumber;
	}
	public String getStrKrockroachStickyPaperPosNumber() {
		return strKrockroachStickyPaperPosNumber;
	}
	public void setStrKrockroachStickyPaperPosNumber(
			String strKrockroachStickyPaperPosNumber) {
		this.strKrockroachStickyPaperPosNumber = strKrockroachStickyPaperPosNumber;
	}
	public String getStrKrockroachStickyNumber() {
		return strKrockroachStickyNumber;
	}
	public void setStrKrockroachStickyNumber(String strKrockroachStickyNumber) {
		this.strKrockroachStickyNumber = strKrockroachStickyNumber;
	}
	public String getStrKrockroachDensity() {
		return strKrockroachDensity;
	}
	public void setStrKrockroachDensity(String strKrockroachDensity) {
		this.strKrockroachDensity = strKrockroachDensity;
	}
	public String getStrFlyFieldNumber() {
		return strFlyFieldNumber;
	}
	public void setStrFlyFieldNumber(String strFlyFieldNumber) {
		this.strFlyFieldNumber = strFlyFieldNumber;
	}
	public String getStrFlyMatureNumber() {
		return strFlyMatureNumber;
	}
	public void setStrFlyMatureNumber(String strFlyMatureNumber) {
		this.strFlyMatureNumber = strFlyMatureNumber;
	}
	public String getStrFlyMatureFieldDensity() {
		return strFlyMatureFieldDensity;
	}
	public void setStrFlyMatureFieldDensity(String strFlyMatureFieldDensity) {
		this.strFlyMatureFieldDensity = strFlyMatureFieldDensity;
	}
	public String getStrFlyStickBarNumber() {
		return strFlyStickBarNumber;
	}
	public void setStrFlyStickBarNumber(String strFlyStickBarNumber) {
		this.strFlyStickBarNumber = strFlyStickBarNumber;
	}
	public String getStrFlyCatchNumber() {
		return strFlyCatchNumber;
	}
	public void setStrFlyCatchNumber(String strFlyCatchNumber) {
		this.strFlyCatchNumber = strFlyCatchNumber;
	}
	public String getStrFlyStickyCatchDesity() {
		return strFlyStickyCatchDesity;
	}
	public void setStrFlyStickyCatchDesity(String strFlyStickyCatchDesity) {
		this.strFlyStickyCatchDesity = strFlyStickyCatchDesity;
	}
}
