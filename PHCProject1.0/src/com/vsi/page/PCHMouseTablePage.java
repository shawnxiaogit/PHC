package com.vsi.page;

import java.io.InputStream;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.mobilercn.base.YTBaseActivity;
import com.mobilercn.base.YTBaseApplication;
import com.mobilercn.base.YTBaseService;
import com.mobilercn.task.JJTask;
import com.mobilercn.util.YTNetUtil;
import com.mobilercn.util.YTStringHelper;
import com.mobilercn.widget.YTReportModel;
import com.mobilercn.widget.YTReportView;
import com.vsi.phc.R;
import com.vsi.config.D2EConfigures;
import com.vsi.config.PHCConfig;
import com.vsi.data.CaoItem;
import com.vsi.data.MouseReportItem;

/**
 * 鼠监测报表界面
 * 
 */
public class PCHMouseTablePage extends YTBaseActivity {
	ImageButton mButtonRight, mButtonLeft;
	YTReportView report;
	private static int GET_REPORT = 1;
	private String mOrgID;
	private String mAreaID;
	private String mType;
	private String mPerson;
	private String strMouseNum;
	private String tagId;
	private boolean isGetMouseNum = false;
	private String mStreet;
	private String mPlace;
	/**
	 * d2e的报表
	 */
	private Button ib_d2e;
	/**
	 * 其他报表
	 */
	private Button ib_other;
	/**
	 * 返回按钮
	 */
	private Button btn_back;
	/**
	 * 新增按钮
	 */
	private Button newinput;
	/**
	 * 提交按钮
	 */
	private Button btn_submit;
	/**
	 * D2E报表容器
	 */
	private LinearLayout panel_d2e;
	/**
	 * d2e报表
	 */
	YTReportView report_d2e;
	/**
	 * d2e报表前一个按钮
	 */
	ImageButton btn_pre_d2e;
	/**
	 * d2e报表后一个按钮
	 */
	ImageButton btn_next_d2e;

	/**
	 * 普通报表容器
	 */
	private LinearLayout panel_common_report;
	
	/**
	 * Tab容器
	 */
	private LinearLayout panel_tabs;
	
	
	private boolean isD2E = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.phc_table_input_mouse);
		Window window = getWindow();
		window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.public_title_bar);

		// 初始化控件
		initView();

		// 获得界面传递过来的数据
		getDataFromActivity();

		// 建立默认的报表
		setUpDefaultReport();

		// 如果获取到了老鼠数量，则插入报表,列表添加一项，报表数据添加一栏
		getMouseReportCao();

		// 为控件设置监听器
		setUpViewListener();

		// 控制报表按钮的显示
		controlReportBtnDisplay();

	}

	/**
	 * 控制报表按钮的显示
	 */
	private void controlReportBtnDisplay() {
		if (PHCConfig.g_ReportModel != null) {
			if (PHCConfig.g_ReportModel.getColumns() > 1) {
				mButtonRight.setVisibility(View.INVISIBLE);
			}
			if (D2EConfigures.TEST) {
				Log.e("PHCConfig.g_ReportModel.getRows()-------------->", ""
						+ PHCConfig.g_ReportModel.getRows());
				Log.e("PHCConfig.g_ReportModel.getColumns()-------------->", ""
						+ PHCConfig.g_ReportModel.getColumns());
			}
		}
	}

	/**
	 * 报表左移监听器
	 * 
	 */
	private class BtnLeftListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			int tmp = report.scrollLeft(false);
			if (tmp <= 0) {
				mButtonLeft.setVisibility(View.INVISIBLE);
			} else {
				mButtonLeft.setVisibility(View.VISIBLE);
			}
			mButtonRight.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * 报表右移监听器
	 * 
	 */
	private class BtnRightListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int tmp = report.scrollLeft(true);
			if (tmp >= PHCConfig.g_ReportModel.getRows() - 1) {
				mButtonRight.setVisibility(View.INVISIBLE);
			} else {
				mButtonRight.setVisibility(View.VISIBLE);
			}
			mButtonLeft.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * 新增报表项监听器
	 * 
	 */
	private class NewReportListener implements OnClickListener {

		@Override
		public void onClick(View v) {

			Intent intent = new Intent(PCHMouseTablePage.this,
					PHCMousePage.class);
			Bundle bundle = new Bundle();
			bundle.putString("OrgID", mOrgID);
			bundle.putString("AreaID", mAreaID);
			bundle.putString("Type", mType);
			bundle.putString("Person", mPerson);
			intent.putExtras(bundle);
			PCHMouseTablePage.this.startActivityForResult(intent, GET_REPORT);
		}

	}

	/**
	 * 返回按钮监听器
	 * 
	 * @author ShawnXiao
	 * 
	 */
	private class BtnBackListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			//判断报表是否为空,
			if(PHCConfig.mouseReportList!=null&&PHCConfig.mouseReportList.size()>0){
				AlertDialog.Builder builder = new AlertDialog.Builder(PCHMouseTablePage.this);
				builder.setTitle(getResources().getString(R.string.warming_tips));
				builder.setMessage(getResources().getString(R.string.data_no_submit));
				builder.setIcon(R.drawable.msg_dlg_warning);
				builder.setPositiveButton(getResources().getString(R.string.dialog_ensure_btn_txt), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
						finish();
						PHCConfig.mouseReportList.clear();
						PHCConfig.mouseCaoReportList.clear();
					}
				});
				builder.setNegativeButton(getResources().getString(R.string.dialog_cancle_btn_txt), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
				builder.create().show();					

				return;
			}
			if(PHCConfig.mouseCaoReportList!=null&&PHCConfig.mouseCaoReportList.size()>0){
				AlertDialog.Builder builder = new AlertDialog.Builder(PCHMouseTablePage.this);
				builder.setTitle(getResources().getString(R.string.warming_tips));
				builder.setMessage(getResources().getString(R.string.data_no_submit));
				builder.setIcon(R.drawable.msg_dlg_warning);
				builder.setPositiveButton(getResources().getString(R.string.dialog_ensure_btn_txt), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
						finish();
						PHCConfig.mouseReportList.clear();
						PHCConfig.mouseCaoReportList.clear();
					}
				});
				builder.setNegativeButton(getResources().getString(R.string.dialog_cancle_btn_txt), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
				builder.create().show();					
				return;
			}
			finish();
			PHCConfig.mouseReportList.clear();
			PHCConfig.mouseCaoReportList.clear();
		}

	}

	/**
	 * 提交报表监听器
	 * 
	 * @author ShawnXiao
	 * 
	 */
	private class BtnSubmitListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			
			//这里要判断数据是否为空，否则自己提交会保参数错误
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			// 对报表列表循环遍历取出
			for (MouseReportItem item : PHCConfig.mouseReportList) {
				sb.append("{");
				sb.append("\"").append("Type").append("\":").append("\"")
						.append(item.getStrReportType()).append("\",");
				sb.append("\"").append("Person").append("\":").append("\"")
						.append(item.getStrPerson()).append("\",");
				sb.append("\"").append("StreetID").append("\":").append("\"")
						.append(item.getStrAreaID()).append("\",");
				sb.append("\"").append("Name").append("\":").append("\"")
						.append(item.getmStreetName()).append("\",");
				sb.append("\"").append("Report").append("\":");
				sb.append("[");
				sb.append("{");
				sb.append("\"").append("SiteType").append("\":").append("\"")
						.append(item.getmPlaceType()).append("\",");
				sb.append("\"").append("Name").append("\":").append("\"")
						.append(item.getmPlaceName()).append("\",");
				sb.append("\"").append("TagNum").append("\":").append("\"")
						.append(item.getStrTagNum()).append("\",");
				sb.append("\"").append("InspectionMeters").append("\":")
						.append("\"").append(item.getStrMouseMeter())
						.append("\",");
				sb.append("\"").append("RatHole").append("\":").append("\"")
						.append(item.getStrRatHole()).append("\",");
				sb.append("\"").append("RatDrains").append("\":").append("\"")
						.append(item.getStrRatDrains()).append("\",");
				sb.append("\"").append("Fecal").append("\":").append("\"")
						.append(item.getStrFecal()).append("\",");
				sb.append("\"").append("RatBiteMark").append("\":")
						.append("\"").append(item.getStrRatBiteMark())
						.append("\",");
				sb.append("\"").append("RatCorpse").append("\":").append("\"")
						.append(item.getStrRatCorpse()).append("\",");
				sb.append("\"").append("MouseTrace").append("\":").append("\"")
						.append(item.getStrMouseTrace()).append("\",");
				sb.append("\"").append("MouseDensity").append("\":")
						.append("\"").append(item.getStrMouseDensity())
						.append("\",");
				sb.append("\"").append("MouseGBNum").append("\":").append("\"")
						.append(item.getStrMouseSunInit()).append("\",");
				sb.append("\"").append("MousePositiveGBNum").append("\":")
						.append("\"").append(item.getStrMouseSun())
						.append("\",");
				sb.append("\"").append("RatsNum").append("\":").append("\"")
						.append(item.getStrMouseCatchNum()).append("\",");
				sb.append("\"").append("MouseDensitySun").append("\":")
						.append("\"").append(item.getStrMouseDesitySun())
						.append("\",");
				sb.append("\"").append("Memo").append("\":").append("\"")
						.append(item.getmTotalMemo()).append("\"");
				sb.append("}");
				sb.append("]");
				sb.append("}");
				sb.append(",");
			}
			sb.deleteCharAt(sb.toString().length() - 1);
			sb.append("]");
			if (D2EConfigures.TEST) {
				Log.e("Data---------->", "" + sb.toString());
			}
			
			
			StringBuffer sbCao = new StringBuffer();
			sbCao.append("[");
			//超声波数据
			for(MouseReportItem caoReportItem:PHCConfig.mouseCaoReportList){
				StringBuffer sbTmp=new StringBuffer();
				sbTmp.append("[");
				if(caoReportItem.getmCaoList()!=null&&caoReportItem.getmCaoList().size()>0){
					for(CaoItem item:caoReportItem.getmCaoList()){						
						if(item!=null){
						sbTmp.append("\"").append(item.getStrKeep()).append("|").append(item.getStrTime()).append("\",");
						}else{
							sbTmp.append("\"").append("-").append("\",");
						}
					}
				}else{
					sbTmp.append("\"").append("-").append("\",");
				}
				
				sbTmp.deleteCharAt(sbTmp.length()-1);
				sbTmp.append("]");
				
				
				StringBuffer sbCoverNum=new StringBuffer();
				sbCoverNum.append("[");
				if(caoReportItem.getmMouseComeList()!=null&&caoReportItem.getmMouseComeList().size()>0){
				for(CaoItem item:caoReportItem.getmMouseComeList()){
					if(item!=null){
						sbCoverNum.append("\"").append(item.getStrKeep()).append("|").append(item.getStrTime()).append("\",");//append(item.getStrKeep()).
					}else{
						sbCoverNum.append("\"").append("-").append("\",");
					}
					
				}
				}else{
					sbCoverNum.append("\"").append("-").append("\",");
				}
				sbCoverNum.deleteCharAt(sbCoverNum.length()-1);
				sbCoverNum.append("]");
				
				
				sbCao.append("{");
				sbCao.append("\"").append("Type").append("\":").append("\"")
						.append(caoReportItem.getStrReportType()).append("\",");
				sbCao.append("\"").append("Person").append("\":").append("\"")
						.append(caoReportItem.getStrPerson()).append("\",");
				sbCao.append("\"").append("StreetID").append("\":").append("\"")
						.append(caoReportItem.getStrAreaID()).append("\",");
				sbCao.append("\"").append("Name").append("\":").append("\"")
						.append(caoReportItem.getmStreetName()).append("\",");
				sbCao.append("\"").append("Report").append("\":");
				sbCao.append("[");
				sbCao.append("{");
				sbCao.append("\"").append("SiteType").append("\":").append("\"")
						.append(caoReportItem.getmPlaceType()).append("\",");
				sbCao.append("\"").append("Name").append("\":").append("\"")
						.append(caoReportItem.getmPlaceName()).append("\",");
				sbCao.append("\"").append("TagNum").append("\":").append("\"")
						.append(caoReportItem.getStrTagNum()).append("\",");
				sbCao.append("\"").append("InspectionMeters").append("\":")
						.append("\"").append(caoReportItem.getStrMouseMeter())
						.append("\",");
				sbCao.append("\"").append("RatHole").append("\":").append("\"")
						.append(caoReportItem.getStrRatHole()).append("\",");
				sbCao.append("\"").append("RatDrains").append("\":").append("\"")
						.append(caoReportItem.getStrRatDrains()).append("\",");
				sbCao.append("\"").append("Fecal").append("\":").append("\"")
						.append(caoReportItem.getStrFecal()).append("\",");
				sbCao.append("\"").append("RatBiteMark").append("\":")
						.append("\"").append(caoReportItem.getStrRatBiteMark())
						.append("\",");
				sbCao.append("\"").append("RatCorpse").append("\":").append("\"")
						.append(caoReportItem.getStrRatCorpse()).append("\",");
				sbCao.append("\"").append("MouseTrace").append("\":").append("\"")
						.append(caoReportItem.getStrMouseTrace()).append("\",");
				sbCao.append("\"").append("MouseDensity").append("\":")
						.append("\"").append(caoReportItem.getStrMouseDensity())
						.append("\",");
				sbCao.append("\"").append("MouseGBNum").append("\":").append("\"")
						.append(caoReportItem.getStrMouseSunInit()).append("\",");
				sbCao.append("\"").append("MousePositiveGBNum").append("\":")
						.append("\"").append(caoReportItem.getStrMouseSun())
						.append("\",");
				sbCao.append("\"").append("RatsNum").append("\":").append("\"")
						.append(caoReportItem.getStrMouseCatchNum()).append("\",");
				sbCao.append("\"").append("MouseDensitySun").append("\":")
						.append("\"").append(caoReportItem.getStrMouseDesitySun())
						.append("\",");
				sbCao.append("\"").append("UlWave").append("\":").append("\"")
						.append(caoReportItem.getStrMouseGetNum()).append("\",");
				sbCao.append("\"").append("Shielding").append("\":").append("\"")
				.append(caoReportItem.getmCoverNum()).append("\",");
				sbCao.append("\"").append("ShieldingMemo").append("\":");
				sbCao.append(sbTmp.toString());
				sbCao.append(",");
				//遮挡明细表
				sbCao.append("\"").append("UlWaveMemo").append("\":");
				sbCao.append(sbCoverNum.toString());
				sbCao.append(",");
				
				
				sbCao.append("\"").append("Memo").append("\":").append("\"")
						.append(caoReportItem.getmTotalMemo()).append("\"");
				sbCao.append("}");
				sbCao.append("]");
				sbCao.append("}");
				sbCao.append(",");
				  
			}
			sbCao.deleteCharAt(sbCao.toString().length() - 1);
			sbCao.append("]");
			if (D2EConfigures.TEST) {
				Log.e("DataCao---------->", "" + sbCao.toString());
			}
			
			
			//报表数据要整合到一起去
			
			if(D2EConfigures.TEST){
				Log.e("sb.length()---------------->", ""+sb.length());
				Log.e("sbCao.length()---------------->", ""+sbCao.length());
			}
			
			StringBuffer data=new StringBuffer();
			if(sb.length()>2){
				sb.deleteCharAt(sb.length()-1);
				data.append(sb.toString());
				data.append(",");
			}else{
				data.append("[");
			}
			if(sbCao.length()>2){
				sbCao.deleteCharAt(0);
				data.append(sbCao.toString());
			}else{
				data.deleteCharAt(data.length()-1);
				data.append("]");
			}
			
			if(D2EConfigures.TEST){
				Log.e("dataComplex=============>", data.toString());
			}
			
			
			
			// 提交报表接口参数
			String[] params = new String[] { 
					"FunType", "insertSOPReport",
					"OrgID", mOrgID, 
					"Data", data.toString() };
			YTStringHelper.array2string(params);
			long id = YTBaseService
					.addMutilpartHttpTask(
							"http://www.ohpest.com/ohpest-main/webservices/get/awh.html",
							params,
							PCHMouseTablePage.this.getClass().getName(),
							PHCConfig.TASK_SUBMIT_REPORT);
			setCurTaskID(id);
			showWaitDialog(getResources().getString(R.string.handling_wait), R.style.dialog,
					R.layout.progress_dialog, R.id.wait_image,
					R.id.wait_message);
		}

	}

	/**
	 * 切换卡按钮D2E监听器
	 */
	private class D2EReportListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			isD2E = true;
			ib_other.setBackgroundResource(R.drawable.tab_normal);
			ib_d2e.setBackgroundResource(R.drawable.tab);
			panel_d2e.setVisibility(View.VISIBLE);
			panel_common_report.setVisibility(View.GONE);
//			 报表展示数据为D2E,即，报表类型是D2E的才显示，显示表头为：街道名、D2E、电量
//			 1、对报表项列表遍历取出
			if (PHCConfig.mouseCaoReportList != null
					&& PHCConfig.mouseCaoReportList.size() > 0) {
				for (MouseReportItem item : PHCConfig.mouseCaoReportList) {
					PHCConfig.g_d2eReportModel.clear();
					if (item.getStrMonitorMethodType().equalsIgnoreCase(
							YTBaseApplication.MOUSE_METHOD_CAO)) {
						PHCConfig.g_d2eReportModel.setHeader(new String[] {
								getResources().getString(R.string.mouse_street_name),
								getResources().getString(R.string.equip_id_num),
								getResources().getString(R.string.mouse_cao_num),
								getResources().getString(R.string.chao_sheng_bo_electric), 
								getResources().getString(R.string.chao_sheng_bo_cover_sum), 
								getResources().getString(R.string.chao_sheng_bo_cover_state),
								"  ",
								"  ",
								"  ",
								"  "
								});
						PHCConfig.g_d2eReportModel.addItem(new String[] {
								item.getmStreetName(),
								item.getStrTagNum(),
								item.getStrMouseGetNum(), 
								item.getLess()?getResources().getString(R.string.less_power):getResources().getString(R.string.power_enough),
							    item.getCoverTimes() + "",
								item.getCoverState() ? getResources().getString(R.string.bing_covered): getResources().getString(R.string.no_covering),
								"  ",
								"  ",
								"  ",
								"  "
								});
						report_d2e.setModel(PHCConfig.g_d2eReportModel);
						
					}

				}
			}
			else {
				PHCConfig.g_d2eReportModel.clear();
				PHCConfig.g_d2eReportModel.setHeader(new String[] {
						getResources().getString(R.string.mouse_street_name),
						getResources().getString(R.string.equip_id_num),
						getResources().getString(R.string.mouse_cao_num),
						getResources().getString(R.string.chao_sheng_bo_electric),
						getResources().getString(R.string.mouse_cao_time), 
						getResources().getString(R.string.chao_sheng_bo_cover_state),
						"  ",
						"  ",
						"  ",
						"  "
						});
				
				PHCConfig.g_d2eReportModel.addItem(new String[] {
						"  ",
						"  ",
						"  ", 
						"  ",
						"  " ,
						"  ",
						"  ",
						"  ",
						"  ",
						"  "
						});
				report_d2e.setModel(PHCConfig.g_d2eReportModel);

				
			}

			report_d2e.invalidate();
			// 2、判断是否有D2E的报表项,有则显示

		}

	}

	/**
	 * 切换卡为其他报表的监听器
	 */
	private class OtherReoprtListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			isD2E = false;
			panel_d2e.setVisibility(View.GONE);
			panel_common_report.setVisibility(View.VISIBLE);
			ib_other.setBackgroundResource(R.drawable.tab);
			ib_d2e.setBackgroundResource(R.drawable.tab_normal);
			// 如果报表类型不是D2E的，则显示的信息，即老鼠报表数据
			// 报表展示数据为D2E,即，报表类型是D2E的才显示，显示表头为：街道名、D2E、电量
			// 1、对报表项列表遍历取出
			if (PHCConfig.mouseReportList != null
					&& PHCConfig.mouseReportList.size() > 0) {
				PHCConfig.g_ReportModel.clear();
				for (MouseReportItem item : PHCConfig.mouseReportList) {
					if (D2EConfigures.TEST) {
						Log.e("!item.getStrMonitorMethodType().equalsIgnoreCase(YTBaseApplication.MOUSE_METHOD_CAO)========>",
								""
										+ (!item.getStrMonitorMethodType()
												.equalsIgnoreCase(
														YTBaseApplication.MOUSE_METHOD_CAO)));
					}
					if (!item.getStrMonitorMethodType().equalsIgnoreCase(
							YTBaseApplication.MOUSE_METHOD_CAO)) {
						PHCConfig.g_ReportModel.setHeader(new String[] {
								getResources().getString(R.string.mouse_street_name), 
								getResources().getString(R.string.mouse_check_meters),
								getResources().getString(R.string.mouse_henji_shu), 
								getResources().getString(R.string.mouse_density_total),
								getResources().getString(R.string.mouse_bar_put_number), 
								getResources().getString(R.string.mouse_sum_number), 
								getResources().getString(R.string.mouse_catch_number), 
								getResources().getString(R.string.mouse_density_total),
								"  ",
								"  ",
								"  "
								});
						PHCConfig.g_ReportModel.addItem(new String[] {
								item.getmStreetName(),
								item.getStrMouseMeter(),
								item.getStrMouseTrace(),
								item.getStrMouseDensity(),
								item.getStrMouseSunInit(),
								item.getStrMouseSun(),
								item.getStrMouseCatchNum(),
								item.getStrMouseDesitySun(),
								"  ",
								"  ",
								"  "
								});
						report.setModel(PHCConfig.g_ReportModel);
					}

				}
			}
			else {
				PHCConfig.g_ReportModel.clear();
				PHCConfig.g_ReportModel.setHeader(new String[] {
						getResources().getString(R.string.mouse_street_name), 
						getResources().getString(R.string.mouse_check_meters),
						getResources().getString(R.string.mouse_henji_shu),
						getResources().getString(R.string.mouse_density_total),
						getResources().getString(R.string.mouse_bar_put_number), 
						getResources().getString(R.string.mouse_sum_number), 
						getResources().getString(R.string.mouse_catch_number), 
						getResources().getString(R.string.mouse_density_total),
						"  ",
						"  ",
						"  "
						});
				PHCConfig.g_ReportModel.addItem(new String[] {
						"  ",
						"  ",
						"  ",
						"  ",
						"  ",
						"  ",
						"  ",
						"  ",
						"  ",
						"  ",
						"  "
						});
				report.setModel(PHCConfig.g_ReportModel);
				
			}
			report.invalidate();
		}

	}

	/**
	 * 为功能按钮设置监听器
	 */
	private void setUpViewListener() {
		ib_d2e.setOnClickListener(new D2EReportListener());
		ib_other.setOnClickListener(new OtherReoprtListener());
		mButtonLeft.setOnClickListener(new BtnLeftListener());
		mButtonRight.setOnClickListener(new BtnRightListener());
		newinput.setOnClickListener(new NewReportListener());
		btn_back.setOnClickListener(new BtnBackListener());
		btn_submit.setOnClickListener(new BtnSubmitListener());

		btn_pre_d2e.setOnClickListener(new BtnPreD2EListener());
		btn_next_d2e.setOnClickListener(new BtnNexD2EListener());

	}

	/**
	 * d2e报表后一项监听器
	 * 
	 * @author ShawnXiao
	 * 
	 */
	private class BtnNexD2EListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int tmp = report_d2e.scrollLeft(true);
			if (tmp >= PHCConfig.g_d2eReportModel.getRows() - 1) {
				btn_next_d2e.setVisibility(View.INVISIBLE);
			} else {
				btn_next_d2e.setVisibility(View.VISIBLE);
			}
			btn_pre_d2e.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * d2e报表前一项监听器
	 * 
	 * @author ShawnXiao
	 * 
	 */
	private class BtnPreD2EListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			int tmp = report_d2e.scrollLeft(false);
			if (tmp <= 0) {
				btn_pre_d2e.setVisibility(View.INVISIBLE);
			} else {
				btn_pre_d2e.setVisibility(View.VISIBLE);
			}
			btn_next_d2e.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * 获取超声波模块的数据
	 */
	private void getMouseReportCao() {
		MouseReportItem mouseReportItem = null;
		if (isGetMouseNum) {
			mouseReportItem = new MouseReportItem(mStreet, mPlace);
			mouseReportItem.setStrReportType(mType);
			mouseReportItem.setStrPerson(mPerson);
			mouseReportItem.setStrAreaID(mAreaID);
			mouseReportItem.setStrMouseMeter("-");
			mouseReportItem.setStrRatHole("-");
			mouseReportItem.setStrRatDrains("-");
			mouseReportItem.setStrFecal("-");
			mouseReportItem.setStrRatBiteMark("-");
			mouseReportItem.setStrRatCorpse("-");
			mouseReportItem.setStrMouseTrace("-");
			mouseReportItem.setStrMouseDensity("-");
			mouseReportItem.setStrMouseSunInit("-");
			mouseReportItem.setStrMouseSun("-");
			mouseReportItem.setStrMouseCatchNum("-");
			mouseReportItem.setStrMouseDesitySun("-");
			mouseReportItem.setStrMouseGetNum(strMouseNum);
			mouseReportItem.setmTotalMemo("-");
			if (tagId != null && tagId.length() > 0) {
				mouseReportItem.setStrTagNum(tagId);
			} else {
				mouseReportItem.setStrTagNum("-");
			}

			PHCConfig.mouseReportList.add(mouseReportItem);

		}
	}

	/**
	 * 获取从界面传递过来的信息
	 */
	private void getDataFromActivity() {
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		mOrgID = bundle.getString("OrgID");
		mAreaID = bundle.getString("AreaID");
		mType = bundle.getString("Type");
		mPerson = bundle.getString("Person");
		strMouseNum = bundle.getString("MouseNum");
		tagId = bundle.getString("tagId");
		isGetMouseNum = bundle.getBoolean("isGetMouseNum");
		mStreet = bundle.getString("Street");
		mPlace = bundle.getString("Place");

		if (D2EConfigures.TEST) {
			Log.e("mPerson------------->", mPerson);
		}
		if (D2EConfigures.TEST) {
			Log.e("AreaID----------->", "" + bundle.getString("AreaID"));
			Log.e("OrgID----------->", "" + bundle.getString("OrgID"));
			Log.e("Type----------->", "" + bundle.getString("Type"));
			Log.e("mPerson----------->", "" + bundle.getString("Person"));
			Log.e("strMouseNum----------->", "" + bundle.getString("MouseNum"));
			Log.e("tagId----------->", "" + bundle.getString("tagId"));
			Log.e("isGetMouseNum----------->",
					"" + bundle.getBoolean("isGetMouseNum"));
		}
	}


	/**
	 * 初始化控件，通过id找到控件
	 */
	private void initView() {
		report = (YTReportView) findViewById(R.id.reportview);
		ib_d2e = (Button) this.findViewById(R.id.ib_d2e);
		ib_other = (Button) this.findViewById(R.id.ib_other);

		mButtonLeft = (ImageButton) findViewById(R.id.btn_pre);
		mButtonRight = (ImageButton) findViewById(R.id.btn_next);
		
		newinput = (Button) findViewById(R.id.newinput);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_submit = (Button) findViewById(R.id.btn_submit);

		panel_common_report = (LinearLayout) this
				.findViewById(R.id.panel_common_report);
		panel_d2e = (LinearLayout) this.findViewById(R.id.panel_d2e);
		report_d2e = (YTReportView) this.findViewById(R.id.reportview_d2e);
		
		
		btn_pre_d2e = (ImageButton) this.findViewById(R.id.btn_pre_d2e);
		btn_next_d2e = (ImageButton) this.findViewById(R.id.btn_next_d2e);
		
		panel_tabs=(LinearLayout) this.findViewById(R.id.panel_tabs);
		panel_tabs.setVisibility(View.VISIBLE);
		
		isShowReprotButton();
		isShowD2EReportButton();
	}
	
	/**
     * 是否显示普通报表左右切换按钮
     */
    private void isShowReprotButton(){
    	if (PHCConfig.g_ReportModel != null) {
			if (PHCConfig.g_ReportModel.getRows() > 1) {
				mButtonRight.setVisibility(View.VISIBLE);
			}else{
				mButtonRight.setVisibility(View.INVISIBLE);
			}
		}
    }
    /**
     * 是否显示D2E报表左右切换按钮
     */
    private void isShowD2EReportButton(){
    	if (PHCConfig.g_d2eReportModel != null) {
			if (PHCConfig.g_d2eReportModel.getRows() > 1) {
				btn_next_d2e.setVisibility(View.VISIBLE);
			}else{
				btn_next_d2e.setVisibility(View.INVISIBLE);
			}
		}
    }
	

	/**
	 * 为报表简历默认的表内容
	 */
	private void setUpDefaultReport() {
		if (PHCConfig.g_ReportModel == null) {
			PHCConfig.g_ReportModel = new YTReportModel(report.getPaint(), this);
		}
		if (PHCConfig.g_d2eReportModel == null) {
			PHCConfig.g_d2eReportModel = new YTReportModel(
					report_d2e.getPaint(), this);
		}
		panel_d2e.setVisibility(View.GONE);
		panel_common_report.setVisibility(View.VISIBLE);
		PHCConfig.g_ReportModel.clear();
		PHCConfig.g_d2eReportModel.clear();
		// 鼠类监测要采集8个数据，其中鼠迹处数(为总数)包括：鼠洞、鼠道、鼠咬痕、鼠尸
		// PHCConfig.g_ReportModel.setHeader(new String[]{"场所类型","场所名称","检查米数",
		// "鼠洞","鼠道","鼠粪","鼠咬痕","鼠尸", "密度(鼠迹数/100m)", "布放粘鼠板数", "阳性粘鼠板数",
		// "捕鼠只数", "密度(阳性板数/布放数)","备注"});
		PHCConfig.g_ReportModel.setHeader(new String[] {
				getResources().getString(R.string.mouse_street_name), 
				getResources().getString(R.string.mouse_check_meters),
				getResources().getString(R.string.mouse_henji_shu),
				getResources().getString(R.string.mouse_density_total),
				getResources().getString(R.string.mouse_bar_put_number), 
				getResources().getString(R.string.mouse_sum_number), 
				getResources().getString(R.string.mouse_catch_number), 
				getResources().getString(R.string.mouse_density_total),
				"  ",
				"  ",
				"  "
				});
		PHCConfig.g_ReportModel.addItem(new String[] {
				"  ",
				"  ", 
				"  ",
				"  ", 
				"  ", 
				"  ", 
				"  ",
				"  ",
				"  ",
				"  ",
				"  "
				
		});

		report.setModel(PHCConfig.g_ReportModel);
		report.invalidate();

	}

	@Override
	public void onBackAciton() {

	}

	@Override
	protected void onResume() {
		super.onResume();
		
		if((PHCConfig.mouseReportList!=null&&PHCConfig.mouseReportList.size()>0) || (PHCConfig.mouseCaoReportList!=null&&PHCConfig.mouseCaoReportList.size()>0)){
			btn_submit.setEnabled(true);
		}
		
		
		if (D2EConfigures.TEST) {
			Log.e("PCHMouseTablePage------>", "onResume()");
		}
		if (PHCConfig.mouseCaoReportList != null
				&& PHCConfig.mouseCaoReportList.size() > 0) {
			if (PHCConfig.g_d2eReportModel != null) {
				PHCConfig.g_d2eReportModel.clear();
				for (MouseReportItem item : PHCConfig.mouseCaoReportList) {
					if (item.getStrMonitorMethodType().equalsIgnoreCase(
							YTBaseApplication.MOUSE_METHOD_CAO)) {
						PHCConfig.g_d2eReportModel.setHeader(new String[] {
								getResources().getString(R.string.mouse_street_name),
								getResources().getString(R.string.equip_id_num),
								getResources().getString(R.string.mouse_cao_num),
								getResources().getString(R.string.chao_sheng_bo_electric),
								getResources().getString(R.string.mouse_cao_time), 
								getResources().getString(R.string.chao_sheng_bo_cover_state),
								"  ",
								"  ",
								"  ",
								"  "
								});
						PHCConfig.g_d2eReportModel.addItem(new String[] {
								item.getmStreetName(),
								item.getStrTagNum(),
								item.getStrMouseGetNum(), 
								item.getLess()?getResources().getString(R.string.less_power):getResources().getString(R.string.power_enough),
							    item.getCoverTimes() + "",
								item.getCoverState() ? getResources().getString(R.string.bing_covered): getResources().getString(R.string.no_covering),
								"  ",
								"  ",
								"  ",
								"  "
								});
						report_d2e.setModel(PHCConfig.g_d2eReportModel);

					}
				}
			}
			//设置为可见
			report_d2e.setVisibility(View.VISIBLE);
		}
		isShowD2EReportButton();
		report_d2e.invalidate();

		if (PHCConfig.mouseReportList != null
				&& PHCConfig.mouseReportList.size() > 0) {
			if (PHCConfig.g_ReportModel != null) {
				PHCConfig.g_ReportModel.clear();

				for (MouseReportItem item : PHCConfig.mouseReportList) {
					if (D2EConfigures.TEST) {
						Log.e("item.getStrMonitorMethodType()============>", ""
								+ (item.getStrMonitorMethodType()));
						Log.e("item.getStrMonitorMethodType().equalsIgnoreCase(YTBaseApplication.MOUSE_METHOD_CAO)============>",
								""
										+ (item.getStrMonitorMethodType()
												.equalsIgnoreCase(YTBaseApplication.MOUSE_METHOD_CAO)));
					}
					if (!item.getStrMonitorMethodType().equalsIgnoreCase(
							YTBaseApplication.MOUSE_METHOD_CAO)) {
						PHCConfig.g_ReportModel.setHeader(new String[] {
								getResources().getString(R.string.mouse_street_name), 
								getResources().getString(R.string.mouse_check_meters),
								getResources().getString(R.string.mouse_henji_shu),
								getResources().getString(R.string.mouse_density_total),
								getResources().getString(R.string.mouse_bar_put_number), 
								getResources().getString(R.string.mouse_sum_number), 
								getResources().getString(R.string.mouse_catch_number), 
								getResources().getString(R.string.mouse_density_total),
								"  ",
								"  ",
								"  "
								});
						PHCConfig.g_ReportModel.addItem(new String[] {
								item.getmStreetName(),
								item.getStrMouseMeter(),
								item.getStrMouseTrace(),
								item.getStrMouseDensity(),
								item.getStrMouseSunInit(),
								item.getStrMouseSun(),
								item.getStrMouseCatchNum(),
								item.getStrMouseDesitySun() ,
								"  ",
								"  ",
								"  "
						});
						report.setModel(PHCConfig.g_ReportModel);
					}
				}
			}
			report.setVisibility(View.VISIBLE);
		}
		isShowReprotButton();
		report.invalidate();
	}

	@Override
	public boolean isExit() {
		return false;
	}

	@Override
	public void taskSuccess(Object... param) {
		try {
			int values = ((Integer) param[0]).intValue();
			if (values == YTBaseService.HTTP_SERVICE_INT) {
				JJTask task = (JJTask) param[1];
				if (task.getTaskId() == PHCConfig.TASK_SUBMIT_REPORT) {
					InputStream ins = task.getInputStream();
					byte[] bytes = YTNetUtil.readByByte(ins, -1);

					String tmp = new String(bytes, "UTF-8");
					ins.close();
					if (D2EConfigures.TEST) {
						Log.e("OUT 20 >>>>>> ", tmp);
					}
					// 隐藏进度条
					hideWaitDialog();

					JSONObject j = new JSONObject(tmp);
					String strSucc = null;
					String strFail = null;
					if (j.has("success")) {
						strSucc = j.getString("success");
						((YTBaseApplication) getApplication())
								.showMessage(strSucc);
						// 提交成功的时候把本地报表和报表列表清除
						PHCConfig.g_ReportModel.clear();
						PHCConfig.mouseReportList.clear();
						//把报表数据清理下
						PHCConfig.g_d2eReportModel.clear();
						PHCConfig.mouseCaoReportList.clear();
						getApp().showMessage(strSucc);
						PCHMouseTablePage.this.finish();
					} else if (j.has("error")) {
						strFail = j.getString("error");
						((YTBaseApplication) getApplication())
								.showMessage(strFail);
						return;
					}

				}
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void taskFailed(Object... param) {
		if (D2EConfigures.TEST) {
			Log.e("taskFailed=========>", "taskFailed()");
		}
		hideWaitDialog();
		int values = ((Integer) param[0]).intValue();
		if (values == YTBaseService.HTTP_SERVICE_INT) {
			((YTBaseApplication) getApplication()).showMessage(getResources()
					.getString(R.string.net_failed));
		}
	}

	@Override
	public void taskProcessing(Object... param) {

	}

}
