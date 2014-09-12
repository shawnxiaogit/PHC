package com.vsi.config;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.mobilercn.widget.YTReportModel;
import com.vsi.data.AreaItem;
import com.vsi.data.MouseReportItem;

/**
 *应用程序的一些静态常量
 */
public class PHCConfig {
	
//监测类型===============================
	/**
	 * 鼠监测类型
	 */
	public static final int TYPE_MOUSE  = 0x01;
	/**
	 * 正常监测类型
	 */
	public static final int TYPE_NORMAL = 0x02;
	/**
	 * 蚊监测类型
	 */
	public static final int TYPE_MOSQUITO=0x03;
	/**
	 * 蟑螂监测类型
	 */
	public static final int TYPE_KOCKROACH=0x04;
	/**
	 * 蝇监测类型
	 */
	public static final int TYPE_FLY=0x05;
	
	/**
	 * 信息类型
	 */
	public static int g_infoType = TYPE_NORMAL;
	
	public static int SURVEY_TYPE=TYPE_NORMAL;
//-----------------------------------------------
//接口相关任务类型=================================================
	/**
	 * 登录任务
	 */
	public static final int TASK_LOGIN = 1;
	/**
	 * 插入区接口任务
	 */
	public static final int TASK_INSERT_STREE=2;
	/**
	 * 取区任务
	 */
	public static final int TASK_GET_AREA=3;
	/**
	 * 提交报表任务
	 */
	public static final int TASK_SUBMIT_REPORT=4;
	/**
	 * 鼠盒第一次上电任务
	 */
	public static final int TASK_FIRST_ELECTRIC=5;
	
	
	/**
	 * 获取上电数据任务
	 */
	public static final int TASK_GET_ELECTRIC=6;
	
	//报表项
//=================================================================
	public static YTReportModel g_ReportModel = null;
	public static YTReportModel g_d2eReportModel=null;
	/**
	 * 老鼠报表项
	 */
	public static MouseReportItem mouseReportItems=null;
	/**
	 * 老鼠报表项列表
	 */
	public static List<MouseReportItem> mouseReportList=new ArrayList<MouseReportItem>();
	/**
	 * 超声波报表项列表
	 */
	public static List<MouseReportItem> mouseCaoReportList=new ArrayList<MouseReportItem>();
	
	/**
	 * 蟑螂报表项列表
	 */
	public static List<MouseReportItem> cockRoachReportList=new ArrayList<MouseReportItem>();
	/**
	 * 蝇报表项列表
	 */
	public static List<MouseReportItem> flyReportList=new ArrayList<MouseReportItem>();
	/**
	 * 蚊子报表项
	 */
	public static List<MouseReportItem> mosquitoReportList=new ArrayList<MouseReportItem>();
	
	/**
	 * 区列表
	 */
	public static List<AreaItem> areaItems=new ArrayList<AreaItem>();
	public static boolean ContainsArea(AreaItem item){
		if(item!=null){
			for(int i=0;i<areaItems.size();i++){
				if(D2EConfigures.TEST){
					Log.e("存在的区-------》", "名字为:"+(areaItems.get(i).getStrAreaName()));
					Log.e("现在的区********》", "经度为："+(item.getStrAreaName()));
					Log.e("存在的区-------》", "经度为："+(areaItems.get(i).getmAreaLongitude())+"纬度为："+(areaItems.get(i).getmAreaLatitude()));
					Log.e("现在的区********》", "经度为："+(item.getmAreaLongitude())+"纬度为："+(item.getmAreaLatitude()));
				}
				if(areaItems.get(i).getStrAreaName().equalsIgnoreCase(item.getStrAreaName())){
					return true;
				}
			}
		}
		return false;
	}
	
}
