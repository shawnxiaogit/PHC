package com.vsi.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

/**
 * �����Ϣ��
 */
public class MonitorItem {
	
	private Context mContext;
	
	private String mId;
	private String mTitle;
	
	//��������Ϣ
	/**
	 * ������
	 */
	private String strAreaName;
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
	
	/**
	 * �ֵ�����
	 */
	private String strStreet;
	
	
	
	//�����ⱨ�������Ϣ
	/**
	 * �������
	 */
	private String strMouseMeter;
	/**
	 * ����
	 */
	private String strMouseTrace;
	/**
	 * ���ܶ�(����/100m)
	 */
	private String strMouseDensity;
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
	
	/**
	 * ���캯��
	 * @param id
	 * ��ʶ
	 * @param title
	 * ����
	 */
	public MonitorItem(String id,String title){
		this.mId=id;
		this.mTitle=title;
	}
	public MonitorItem(Context context){
		this.mContext=context;
	}
	/**
	 * ��Ϣ�б���
	 */
	public List<MonitorItem> monitorItems=new ArrayList<MonitorItem>();
	/**
	 * �����
	 * @param item
	 * ��Ԫ��
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
