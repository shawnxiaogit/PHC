package com.vsi.data;

import java.util.ArrayList;
import java.util.List;

import com.vsi.config.D2EConfigures;

import android.content.Context;
import android.util.Log;

/**
 * 监测信息项
 */
public class MouseReportItem {
	//kris
	private int     mCoverTimes;
	private boolean mCoverState;
	
	public int getCoverTimes() {
		return mCoverTimes;
	}
	public void setCoverTimes(int coverTimes) {
		mCoverTimes = coverTimes;
	}

	public boolean getCoverState() {
		return mCoverState;
	}
	public void setCoverState(boolean coverStates) {
		mCoverState = coverStates;
	}

	
	
	/**
	 * 报表项监测方法类型
	 */
	private String strMonitorMethodType;
	
	
	
	/**
	 * 报表类型
	 */
	private String strReportType;
	/**
	 * 爱委会公司
	 */
	private String strOrgID;
	/**
	 * 监测方法
	 */
	private String monitorMethod;
	/**
	 * 一条报表的备注
	 */
	private String mTotalMemo;
	
	private Context mContext;
	/**
	 * 街道名称
	 */
	private String mStreetName;
	/**
	 * 场所名称
	 */
	private String mPlaceName;
	/**
	 * 场所类型
	 */
	private String mPlaceType;
	//监测相关信息
	/**
	 * 区名称
	 */
	private String strAreaName;
	/**
	 * 区Id
	 */
	private String strAreaID;
	
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
	
	
	//老鼠监测报表相关信息
	/**
	 * 检查米数
	 */
	private String strMouseMeter;
	/**
	 * 鼠洞
	 */
	private String strRatHole;
	/**
	 * 鼠道
	 */
	private String strRatDrains;
	/**
	 * 鼠粪
	 */
	private String strFecal;
	/**
	 * 鼠咬痕
	 */
	private String strRatBiteMark;
	/**
	 * 鼠尸
	 */
	private String strRatCorpse;
	/**
	 * 鼠迹数
	 */
	private String strMouseTrace;
	/**
	 * 鼠密度(鼠迹数/100m)
	 */
	private String strMouseDensity;
	/**
	 * 外环境(目测法)备注
	 */
	private String strOutMemo;
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
	/**
	 * 室内(粘鼠板法)备注
	 */
	private String strInMemo;
	
	
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
	
	
	
	//超声波报表相关
	/**
	 * 超声波获取的老鼠数量
	 */
	private String strMouseGetNum;
	
	/**
	 * 标签号
	 */
	private String strTagNum;
	/**
	 * 时间
	 */
	private String mTime;
	/**
	 * 遮挡或者老鼠来访明细
	 */
	private String mKeep;
	
	/**
	 * 缺电
	 */
	private boolean mLess;
	
	
	
	/**
	 * 遮挡相关信息列表
	 */
	private List<CaoItem> mCaoList;
	/**
	 * 遮挡数量
	 */
	private int mCoverNum=0;
	
	/**
	 * 鼠类来访信息列表
	 */
	private List<CaoItem> mMouseComeList;
	
	
	
	/**
	 * 构造函数
	 * @param id
	 * 标识
	 * @param title
	 * 标题
	 */
	public MouseReportItem(String street,String place){
		this.mStreetName=street;
		this.mPlaceName=place;
		mCaoList=new ArrayList<CaoItem>();
		mMouseComeList=new ArrayList<CaoItem>();
	}
	public MouseReportItem(Context context){
		this.mContext=context;
		mCaoList=new ArrayList<CaoItem>();
		mMouseComeList=new ArrayList<CaoItem>();
	}
	
	/**
	 * 信息列表项
	 */
	public List<MouseReportItem> monitorItems=new ArrayList<MouseReportItem>();
	/**
	 * 添加项
	 * @param item
	 * 项元素
	 */
	public void addItem(MouseReportItem item){
		monitorItems.add(item);
	}
	
	public void setLess(boolean less){
		
		mLess = less;
		
	}
	public boolean getLess(){
		return mLess;
	}
	
	public boolean isLess(){
		
		return mLess;
		
	}
	
	/**
	 * 打印报表相关信息
	 */
	public void printReprot(){
		if(D2EConfigures.TEST){
		Log.e("ReportMessage----------->", "街道名称："+mStreetName+" , "
				+"场所类型："+mPlaceType+" , "
				+"检查米数："+strMouseMeter+" , "
				+"鼠洞:"+strRatHole+" , "
				+"鼠道："+strRatDrains+" , "
				+"鼠粪："+strFecal+","
				+"鼠咬痕："+strRatBiteMark+" , "
				+"鼠尸："+strRatCorpse+","
				+"鼠迹数："+strMouseTrace+" , "
				+"鼠密度(鼠迹数/100m)："+strMouseDensity+" , "
				+"备注："+strOutMemo+" , "
				+"布放粘鼠板数："+strMouseSunInit+" , "
				+"阳性粘鼠板数："+strMouseSun+" , "
				+"捕鼠只数:"+strMouseCatchNum+" , "
				+"密度(粘捕鼠数/张):"+strMouseDesitySun+" , "
				+"备注:"+mTotalMemo);
		}
	}
	
	
	
	
	
		
	public String getStrTagNum() {
		return strTagNum;
	}
	public void setStrTagNum(String strTagNum) {
		this.strTagNum = strTagNum;
	}
	public String getStrMouseGetNum() {
		return strMouseGetNum;
	}
	public void setStrMouseGetNum(String strMouseGetNum) {
		this.strMouseGetNum = strMouseGetNum;
	}
	public String getStrReportType() {
		return strReportType;
	}
	public void setStrReportType(String strReportType) {
		this.strReportType = strReportType;
	}
	public String getStrOrgID() {
		return strOrgID;
	}
	public void setStrOrgID(String strOrgID) {
		this.strOrgID = strOrgID;
	}
	public String getStrAreaID() {
		return strAreaID;
	}
	public void setStrAreaID(String strAreaID) {
		this.strAreaID = strAreaID;
	}
	public String getmTotalMemo() {
		return mTotalMemo;
	}
	public void setmTotalMemo(String mTotalMemo) {
		this.mTotalMemo = mTotalMemo;
	}
	public String getMonitorMethod() {
		return monitorMethod;
	}
	public void setMonitorMethod(String monitorMethod) {
		this.monitorMethod = monitorMethod;
	}
	public String getStrOutMemo() {
		return strOutMemo;
	}
	public void setStrOutMemo(String strOutMemo) {
		this.strOutMemo = strOutMemo;
	}
	public String getStrInMemo() {
		return strInMemo;
	}
	public void setStrInMemo(String strInMemo) {
		this.strInMemo = strInMemo;
	}
	public String getmPlaceType() {
		return mPlaceType;
	}
	public String getStrPerson() {
		return strPerson;
	}
	public void setStrPerson(String strPerson) {
		this.strPerson = strPerson;
	}
	public String getStrRatHole() {
		return strRatHole;
	}
	public void setStrRatHole(String strRatHole) {
		this.strRatHole = strRatHole;
	}
	public String getStrRatDrains() {
		return strRatDrains;
	}
	public void setStrRatDrains(String strRatDrains) {
		this.strRatDrains = strRatDrains;
	}
	public String getStrFecal() {
		return strFecal;
	}
	public void setStrFecal(String strFecal) {
		this.strFecal = strFecal;
	}
	public String getStrRatBiteMark() {
		return strRatBiteMark;
	}
	public void setStrRatBiteMark(String strRatBiteMark) {
		this.strRatBiteMark = strRatBiteMark;
	}
	public String getStrRatCorpse() {
		return strRatCorpse;
	}
	public void setStrRatCorpse(String strRatCorpse) {
		this.strRatCorpse = strRatCorpse;
	}
	public void setmPlaceType(String mPlaceType) {
		this.mPlaceType = mPlaceType;
	}
	public String getmStreetName() {
		return mStreetName;
	}
	public void setmStreetName(String mStreetName) {
		this.mStreetName = mStreetName;
	}
	public String getmPlaceName() {
		return mPlaceName;
	}
	public void setmPlaceName(String mPlaceName) {
		this.mPlaceName = mPlaceName;
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
	public String getmTime() {
		return mTime;
	}
	public void setmTime(String mTime) {
		this.mTime = mTime;
	}
	public String getmKeep() {
		return mKeep;
	}
	public void setmKeep(String mKeep) {
		this.mKeep = mKeep;
	}
	public List<CaoItem> getmCaoList() {
		return mCaoList;
	}
	public void setmCaoList(List<CaoItem> mCaoList) {
		this.mCaoList = mCaoList;
	}
	public String getStrMonitorMethodType() {
		return strMonitorMethodType;
	}
	public void setStrMonitorMethodType(String strMonitorMethodType) {
		this.strMonitorMethodType = strMonitorMethodType;
	}
	public int getmCoverNum() {
		return mCoverNum;
	}
	public void setmCoverNum(int mCoverNum) {
		this.mCoverNum = mCoverNum;
	}
	public List<CaoItem> getmMouseComeList() {
		return mMouseComeList;
	}
	public void setmMouseComeList(List<CaoItem> mMouseComeList) {
		this.mMouseComeList = mMouseComeList;
	}
	
	
}
