package com.vsi.data;

import java.util.ArrayList;
import java.util.List;

import com.vsi.config.D2EConfigures;

import android.content.Context;
import android.util.Log;

/**
 * �����Ϣ��
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
	 * �������ⷽ������
	 */
	private String strMonitorMethodType;
	
	
	
	/**
	 * ��������
	 */
	private String strReportType;
	/**
	 * ��ί�ṫ˾
	 */
	private String strOrgID;
	/**
	 * ��ⷽ��
	 */
	private String monitorMethod;
	/**
	 * һ������ı�ע
	 */
	private String mTotalMemo;
	
	private Context mContext;
	/**
	 * �ֵ�����
	 */
	private String mStreetName;
	/**
	 * ��������
	 */
	private String mPlaceName;
	/**
	 * ��������
	 */
	private String mPlaceType;
	//��������Ϣ
	/**
	 * ������
	 */
	private String strAreaName;
	/**
	 * ��Id
	 */
	private String strAreaID;
	
	/**
	 * ���ʱ��
	 */
	private String strSurveyTime;
	/**
	 * ��������
	 */
	private String strWeather;
	/**
	 * �����¶�
	 */
	private String strTempter;
	/**
	 * ��ⵥλ
	 */
	private String strCompany;
	/**
	 * �����
	 */
	private String strPerson;
	
	
	//�����ⱨ�������Ϣ
	/**
	 * �������
	 */
	private String strMouseMeter;
	/**
	 * ��
	 */
	private String strRatHole;
	/**
	 * ���
	 */
	private String strRatDrains;
	/**
	 * ���
	 */
	private String strFecal;
	/**
	 * ��ҧ��
	 */
	private String strRatBiteMark;
	/**
	 * ��ʬ
	 */
	private String strRatCorpse;
	/**
	 * ����
	 */
	private String strMouseTrace;
	/**
	 * ���ܶ�(����/100m)
	 */
	private String strMouseDensity;
	/**
	 * �⻷��(Ŀ�ⷨ)��ע
	 */
	private String strOutMemo;
	/**
	 * ճ�����(������)
	 */
	private String strMouseSunInit;
	/**
	 * ճ�����(������)
	 */
	private String strMouseSun;
	/**
	 * ����ֻ��
	 */
	private String strMouseCatchNum;
	/**
	 * ���ܶ�(ճ��ֻ��/��)
	 */
	private String strMouseDesitySun;
	/**
	 * ����(ճ��巨)��ע
	 */
	private String strInMemo;
	
	
	//�ü�ⱨ�������Ϣ
	/**
	 * ��ˮ����(��ˮ��)��
	 */
	private String strMosquPond;
	/**
	 * ������
	 */
	private String strMosquPosPond;
	/**
	 * �ܶ�(������/�л�ˮ��)
	 */
	private String strMosquDensPond;
	/**
	 * �ɼ�����
	 */
	private String strMosquCollSpoo;
	/**
	 * ��������
	 */
	private String strMosquPosSpoo;
	/**
	 * �ܶ�(��������/������)
	 */
	private String strMosquDensSpoo;
	
	
	
	//����ⱨ�������Ϣ
	/**
	 * ճ�ֽ��
	 */
	private String strKrockroachStickyPaperNumber;
	/**
	 * ����ճ�ֽ��
	 */
	private String strKrockroachStickyPaperPosNumber;
	/**
	 * ճ�ֻ��
	 */
	private String strKrockroachStickyNumber;
	/**
	 * �ܶ�(ճ��ֻ��/��)
	 */
	private String strKrockroachDensity;
	
	
	
	//Ӭ��ⱨ�������Ϣ
	/**
	 * ��Ұ��
	 */
	private String strFlyFieldNumber;
	/**
	 * ��Ӭֻ��
	 */
	private String strFlyMatureNumber;
	/**
	 * �ܶ�(��Ӭֻ��/��Ұ)
	 */
	private String strFlyMatureFieldDensity;
	/**
	 * ճӬ����
	 */
	private String strFlyStickBarNumber;
	/**
	 * ��Ӭ��
	 */
	private String strFlyCatchNumber;
	/**
	 * �ܶ�(ճ��Ӭ��/��)
	 */
	private String strFlyStickyCatchDesity;
	
	
	
	//�������������
	/**
	 * ��������ȡ����������
	 */
	private String strMouseGetNum;
	
	/**
	 * ��ǩ��
	 */
	private String strTagNum;
	/**
	 * ʱ��
	 */
	private String mTime;
	/**
	 * �ڵ���������������ϸ
	 */
	private String mKeep;
	
	/**
	 * ȱ��
	 */
	private boolean mLess;
	
	
	
	/**
	 * �ڵ������Ϣ�б�
	 */
	private List<CaoItem> mCaoList;
	/**
	 * �ڵ�����
	 */
	private int mCoverNum=0;
	
	/**
	 * ����������Ϣ�б�
	 */
	private List<CaoItem> mMouseComeList;
	
	
	
	/**
	 * ���캯��
	 * @param id
	 * ��ʶ
	 * @param title
	 * ����
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
	 * ��Ϣ�б���
	 */
	public List<MouseReportItem> monitorItems=new ArrayList<MouseReportItem>();
	/**
	 * �����
	 * @param item
	 * ��Ԫ��
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
	 * ��ӡ���������Ϣ
	 */
	public void printReprot(){
		if(D2EConfigures.TEST){
		Log.e("ReportMessage----------->", "�ֵ����ƣ�"+mStreetName+" , "
				+"�������ͣ�"+mPlaceType+" , "
				+"���������"+strMouseMeter+" , "
				+"��:"+strRatHole+" , "
				+"�����"+strRatDrains+" , "
				+"��ࣺ"+strFecal+","
				+"��ҧ�ۣ�"+strRatBiteMark+" , "
				+"��ʬ��"+strRatCorpse+","
				+"������"+strMouseTrace+" , "
				+"���ܶ�(����/100m)��"+strMouseDensity+" , "
				+"��ע��"+strOutMemo+" , "
				+"����ճ�������"+strMouseSunInit+" , "
				+"����ճ�������"+strMouseSun+" , "
				+"����ֻ��:"+strMouseCatchNum+" , "
				+"�ܶ�(ճ������/��):"+strMouseDesitySun+" , "
				+"��ע:"+mTotalMemo);
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
