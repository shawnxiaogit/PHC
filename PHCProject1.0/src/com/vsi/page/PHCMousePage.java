package com.vsi.page;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


import com.mobilercn.base.YTBaseActivity;
import com.mobilercn.base.YTBaseApplication;
import com.mobilercn.base.YTBaseService;
import com.mobilercn.sop.bt.SOPBTCallHelper;
import com.mobilercn.sop.bt.SOPBluetoothService;
import com.mobilercn.task.JJTask;
import com.mobilercn.util.YTNetUtil;
import com.mobilercn.widget.D2EListAdapter;
import com.mobilercn.widget.D2EListAdapterItam;
import com.vsi.page.PHCInfoPage.PopupAdapter;
import com.vsi.page.PHCInfoPage.PopupAdapter.Holder;
import com.vsi.phc.R;
import com.vsi.phc.R.color;
import com.vsi.config.D2EConfigures;
import com.vsi.config.PHCConfig;
import com.vsi.data.MouseReportItem;

/**
 * ����������Ϣ¼�����
 * 
 */
public class PHCMousePage extends YTBaseActivity {
	/**
	 * ������
	 */
	MouseReportItem mouseReportItem = null;
	
	/**
	 * ����
	 */
	int array = R.array.mouse_monitor_method;
	/**
	 * ѡ�еļ�ⷨ
	 */
	private int intCurrent;
	/**
	 * �⻷����ⷨ��������ֵ
	 */
	private int intOut = 0;
	/**
	 * ���ڼ�ⷨ����ֵ
	 */
	private int intIn = 1;
	/**
	 * ��������ⷨ����ֵ
	 */
	private int intCao = 2;
	/**
	 * ��ǰ�������͵�����ֵ
	 */
	private int intCurrentPlace = 0;

	/**
	 * �ֵ����������
	 */
	private EditText mStreetEdit;

	/**
	 * ���ⳡ�����ͱ༭��
	 */
	private AutoCompleteTextView et_place_type;
	/**
	 * ���ⳡ����������ͼƬ��ť
	 */
	private ImageButton ibtn_dropDown_place;

	/**
	 * ������������
	 */
	private RelativeLayout panel_palce_name;
	/**
	 * �������������
	 */
	private EditText edit_mouse_palce_name;
	/**
	 * ��
	 */
	private EditText edit_rat_hole;
	/**
	 * ���
	 */
	private EditText edit_rat_drains;
	/**
	 * ���
	 */
	private EditText edit_rat_dung;
	/**
	 * ��ҧ��
	 */
	private EditText edit_rat_bite_mark;
	/**
	 * ��ʬ
	 */
	private EditText edit_rat_corpse;
	/**
	 * �⻷����ע
	 */
	private EditText edit_out_remark;
	/**
	 * ���ڱ�ע
	 */
	private EditText edit_in_remark;
	/**
	 * �⻷��(Ŀ�ⷨ)����
	 */
	private LinearLayout panelOut;
	/**
	 * ����(ճ���)��
	 */
	private LinearLayout panelIn;

	/**
	 * ��ⷽ���༭��
	 */
	private AutoCompleteTextView et_monitor_method;

	private D2EListAdapter mDropAdapter;

	TextWatcher mWatcher = new TextWatcher() {

		// @Override
		public void afterTextChanged(Editable arg0) {

		}

		// @Override
		public void beforeTextChanged(CharSequence str, int arg1, int arg2,
				int arg3) {

		}

		// @Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			mDropAdapter.getFilter().filter(s);
			mDropAdapter.notifyDataSetInvalidated();
		}
	};
	ArrayAdapter adapterPlace;
	ArrayAdapter adapterPlaceOut;
	ArrayAdapter adapterPlaceIn;
	ArrayAdapter adapterPlaceCao;
	/**
	 * ��ⷽ��������ť
	 */
	private ImageButton ibtn_dropDown_method;
	/**
	 * ������������
	 */
	private EditText mMeterEdit;
	/**
	 * ���������
	 */
	private EditText mTracEdit;
	/**
	 * ���ܶ�(����/100m)�����
	 */
	private EditText mDensityEdit;
	/**
	 * ����ճ����������
	 */
	private EditText mSumInitEdit;
	/**
	 * ����ճ����������
	 */
	private EditText mSumEdit;
	/**
	 * ����ֻ�������
	 */
	private EditText mCatchEdit;
	/**
	 * ���ܶ������(ճ������/��)
	 */
	private EditText mDensitySumEdit;
	/**
	 * ���ر���Ľ����
	 */

	/**
	 * ����
	 */
	private String mouseTrace;
	/**
	 * �ܶ�
	 */
	private String strDensitySun;

	public static int RESPONSE_REPORT = 2;

	private String mOrgID;
	private String mAreaID;
	private String mType;
	private String mPerson;

	/**
	 * ɨ������������
	 */
	private RelativeLayout menu_mounsenumber_layout;
	/**
	 * �������������
	 */
	private static final int REQUEST_ENABLE_BT = 1;

	public static final String READER_PREFS_NAME = "READERID";
	public static final String BT_MAC_KEY = "BTMAC";
	public static final String READER_KEY = "READER";

	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	public static String EXTRA_READER_ID = "reader_id";

	private static final int REQUESR_DEVICE = 2;
	
	/**
	 * ����������
	 */
	private LinearLayout panel_cao;

	Bundle bundle;
	
	/**
	 * ��ͨ��������
	 */
	private LinearLayout panel_common_report;
	
	
	private boolean isShwoDeviceList = false;
	
	

	/**
	 * ���󱨱�����¼�����
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.phc_input_mouse_page);
		Window window = getWindow();
		window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.public_title_bar);

		et_monitor_method = (AutoCompleteTextView) this
				.findViewById(R.id.et_monitor_method);
		ibtn_dropDown_method = (ImageButton) this
				.findViewById(R.id.ibtn_dropDown_method);
		mStreetEdit = (EditText) findViewById(R.id.edit_street);
		et_place_type = (AutoCompleteTextView) this
				.findViewById(R.id.et_place_type);
		ibtn_dropDown_place = (ImageButton) this
				.findViewById(R.id.ibtn_dropDown_place);
		edit_mouse_palce_name = (EditText) this
				.findViewById(R.id.edit_mouse_palce_name);
		mMeterEdit = (EditText) findViewById(R.id.edit_meter);
		edit_rat_hole = (EditText) this.findViewById(R.id.edit_rat_hole);
		edit_rat_drains = (EditText) this.findViewById(R.id.edit_rat_drains);
		edit_rat_dung = (EditText) this.findViewById(R.id.edit_rat_dung);
		edit_rat_bite_mark = (EditText) this
				.findViewById(R.id.edit_rat_bite_mark);
		edit_rat_corpse = (EditText) this.findViewById(R.id.edit_rat_corpse);
		mTracEdit = (EditText) findViewById(R.id.edit_trace);
		mDensityEdit = (EditText) findViewById(R.id.edit_density);
		mSumInitEdit = (EditText) findViewById(R.id.edit_sum_init);
		mSumEdit = (EditText) findViewById(R.id.edit_sum);
		mCatchEdit = (EditText) findViewById(R.id.edit_catch);
		mDensitySumEdit = (EditText) findViewById(R.id.edit_density_sum);
		panelOut = (LinearLayout) this.findViewById(R.id.panel_out);
		panelIn = (LinearLayout) this.findViewById(R.id.panel_in);
		panel_palce_name = (RelativeLayout) this
				.findViewById(R.id.panel_palce_name);
		edit_out_remark = (EditText) this.findViewById(R.id.edit_out_remark);
		edit_in_remark = (EditText) this.findViewById(R.id.edit_in_remark);

		menu_mounsenumber_layout = (RelativeLayout) this
				.findViewById(R.id.menu_mounsenumber_layout);
		panel_cao=(LinearLayout) this.findViewById(R.id.panel_cao);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		mOrgID = bundle.getString("OrgID");
		mAreaID = bundle.getString("AreaID");
		mType = bundle.getString("Type");
		mPerson = bundle.getString("Person");
		if (D2EConfigures.TEST) {
			Log.e("AreaID----------->", "" + bundle.getString("AreaID"));
			Log.e("OrgID----------->", "" + bundle.getString("OrgID"));
			Log.e("Type----------->", "" + bundle.getString("Type"));
			Log.e("mPerson----------->", "" + bundle.getString("Person"));
		}

		adapterPlaceOut = ArrayAdapter.createFromResource(PHCMousePage.this,
				R.array.mouse_monitor_place_out,
				R.layout.simple_dropdown_item_1line);
		adapterPlaceIn = ArrayAdapter.createFromResource(PHCMousePage.this,
				R.array.mouse_monitor_place_in,
				R.layout.simple_dropdown_item_1line);
		adapterPlaceCao = ArrayAdapter.createFromResource(PHCMousePage.this,
				R.array.mouse_cao_place,
				R.layout.simple_dropdown_item_1line);
		mDropAdapter = new D2EListAdapter(this);
		ArrayAdapter adapter = ArrayAdapter.createFromResource(this, array,
				R.layout.simple_dropdown_item_1line);
		et_monitor_method.setAdapter(adapter);
		et_monitor_method.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				intCurrent = position;
				if (D2EConfigures.TEST) {
					Log.e("intCurrent-------------->", "" + intCurrent);
				}
				if (intCurrent == intOut) {
					handler.postDelayed(calculateThread, 1000);
					// 1�����⻷���Ľ���չʾ��Ϊ�ɿ����������ںͳ������Ľ����Ϊ���ɼ�
					panelOut.setVisibility(View.VISIBLE);
					panelIn.setVisibility(View.GONE);
					menu_mounsenumber_layout.setVisibility(View.GONE);
					// 2������ص����������������������µ�����(�����������������һ���µģ���Ȼ�����)
					et_place_type.setAdapter(adapterPlaceOut);
					// 3���ѳ������Ƶ����������޸�Ϊ��
					panel_palce_name
							.setBackgroundResource(R.drawable.public_line_mid);
				} else if (intCurrent == intIn) {
					handler.postDelayed(calculateThread, 1000);
					// 1�������ڵĽ���չʾ��Ϊ�ɿ��������⻷���ͳ������Ľ����Ϊ���ɼ�
					panelIn.setVisibility(View.VISIBLE);
					panelOut.setVisibility(View.GONE);
					menu_mounsenumber_layout.setVisibility(View.GONE);
					// 2������ص����������������������µ�����(�����������������һ���µģ���Ȼ�����)
					et_place_type.setAdapter(adapterPlaceIn);
					// 3���ѳ������Ƶ����������޸�Ϊ��
					panel_palce_name
							.setBackgroundResource(R.drawable.public_line_mid);
				} else if (intCurrent == intCao) {
					handler.removeCallbacks(calculateThread);
					// 1���������򿪣�����Reader����
					BluetoothAdapter bluetoothAdapter = BluetoothAdapter
							.getDefaultAdapter();
					if (bluetoothAdapter == null) {
						((YTBaseApplication) getApplication())
								.showMessage(getResources().getString(R.string.no_support_blue));
					}
					if (!bluetoothAdapter.isEnabled()) {
						Intent enableBtIntent = new Intent(
								BluetoothAdapter.ACTION_REQUEST_ENABLE);
						startActivityForResult(enableBtIntent,
								REQUEST_ENABLE_BT);
					} else {
						initServices();						
					}
				}

			}

		});
		ibtn_dropDown_method.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if(et_monitor_method!=null&&et_monitor_method.length()>0){
					et_monitor_method.setText("");
				}
				if(et_place_type!=null&&et_place_type.length()>0){
					et_place_type.setText("");
				}
				et_monitor_method.showDropDown();
			}
		});

		et_place_type.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				if (D2EConfigures.TEST) {
					Log.e("et_place_type---------------->",
							"et_place_type.OnItemClick()");
				}
				intCurrentPlace = position;

			}

		});

		ibtn_dropDown_place.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (D2EConfigures.TEST) {
					Log.e("ibtn_dropDown_place---------------->",
							"ibtn_dropDown_place.OnClick()");
				}
				if (et_place_type.getAdapter() == null) {
					getApp().showMessage(getResources().getString(R.string.mouse_page_method_less));
				}
				if(et_place_type!=null&&et_place_type.length()>0){
					et_place_type.setText("");
				}
				
				et_place_type.showDropDown();
			}
		});

		Button btnback = (Button) findViewById(R.id.btn_back);
		btnback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				AlertDialog.Builder builder = new AlertDialog.Builder(PHCMousePage.this);
				builder.setTitle(getResources().getString(R.string.warming_tips));
				builder.setMessage(getResources().getString(R.string.data_no_submit));
				builder.setIcon(R.drawable.msg_dlg_warning);
				builder.setPositiveButton(getResources().getString(R.string.dialog_ensure_btn_txt), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
						finish();
						
					}
				});
				builder.setNegativeButton(getResources().getString(R.string.dialog_back_btn_txt), null);
				builder.create().show();

			}

		});

		// 1�������ﴴ��һ���±���������������Ϣ
		// 2�����뱨���������
		Button btnsave = (Button) findViewById(R.id.btn_save);
		btnsave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {

					String mMethod = et_monitor_method.getText().toString();
					// ����ֵ����ƺͳ������Ʋ�Ϊ�գ����½�һ��������
					String mStreet = mStreetEdit.getText().toString();
					String mPlace = edit_mouse_palce_name.getText().toString();
					String mPlaceType = et_place_type.getText().toString();
					String mMeter = mMeterEdit.getText().toString();
					String mRatHole = edit_rat_hole.getText().toString();
					String mRatDrains = edit_rat_drains.getText().toString();
					String mFecal = edit_rat_dung.getText().toString();
					String mRatBiteMark = edit_rat_bite_mark.getText()
							.toString();
					String mRatCorpse = edit_rat_corpse.getText().toString();

					String mMouseDensity = mDensityEdit.getText().toString();
					String mOutMemo = edit_out_remark.getText().toString();
					String mMouseSunInit = mSumInitEdit.getText().toString();
					String mMouseSun = mSumEdit.getText().toString();
					String mMouseCatchNum = mCatchEdit.getText().toString();
					String mMouseDesitySun = mDensitySumEdit.getText()
							.toString();
					String mInMemo = edit_in_remark.getText().toString();
					String strMouseDensity = null;
					
					//�жϼ�ⷽ���Ƿ���д
					if(mMethod == null || !(mMethod.length() > 0)){
						((YTBaseApplication) getApplication())
						.showMessage(getResources().getString(R.string.mouse_page_method_less));
						return;
					}
					//�жϽֵ������Ƿ���д
					if(mStreet==null||!(mStreet.length()>0)){
						getApp().showMessage(getResources().getString(R.string.mouse_page_street_less));
						return;
					}
					//�жϳ��������Ƿ���д
					if(mPlaceType==null||!(mPlaceType.length()>0)){
						getApp().showMessage(getResources().getString(R.string.mouse_page_palce_type_less));
						return;
					}
					//�жϳ��������Ƿ���д
					if(mPlace==null||!(mPlace.length()>0)){
						getApp().showMessage(getResources().getString(R.string.mouse_page_palce_less));
						return;
					}
					//�ж�ѡ��������ּ�ⷽ��
					//1��������⻷��Ŀ�ⷨ,����Ӧ�Ľ�����Ϣ����Ϊ��
					if(intCurrent == intOut){
						if(mMeter==null||!(mMeter.length()>0)){
							getApp().showMessage(getResources().getString(R.string.mouse_page_meters_less));
							return;
						}
						if(mRatHole==null||!(mRatHole.length()>0)){
							getApp().showMessage(getResources().getString(R.string.mouse_page_rat_hole_less));
							return;
						}
						if(mRatDrains==null||!(mRatDrains.length()>0)){
							getApp().showMessage(getResources().getString(R.string.mouse_page_rat_drains_less));
							return;
						}
						if(mFecal==null||!(mFecal.length()>0)){
							getApp().showMessage(getResources().getString(R.string.mouse_page_rat_fecal_less));
							return;
						}
						if(mRatCorpse==null||!(mRatCorpse.length()>0)){
							getApp().showMessage(getResources().getString(R.string.mouse_page_rat_corpse_less));
							return;
						}
						if(mRatBiteMark==null||!(mRatBiteMark.length()>0)){
							getApp().showMessage(getResources().getString(R.string.mouse_page_rat_bitmark_less));
							return;
						}
						
					}else if(intCurrent == intIn){
						//2�����������ճ��巨������Ӧ�Ľ�����Ϣ����Ϊ��
						if(mMouseSunInit==null||!(mMouseSunInit.length()>0)){
							getApp().showMessage(getResources().getString(R.string.mouse_page_sun_init_less));
							return;
						}
						if(mMouseSun==null||!(mMouseSun.length()>0)){
							getApp().showMessage(getResources().getString(R.string.mouse_page_sun_less));
							return;
						}
						if(mMouseCatchNum==null||!(mMouseCatchNum.length()>0)){
							getApp().showMessage(getResources().getString(R.string.mouse_page_catch_num_less));
							return;
						}
						
					}

					
					if (mStreet != null && mStreet.length() > 0
							&& mPlace != null && mPlace.length() > 0) {
						mouseReportItem = new MouseReportItem(mStreet, mPlace);
					}
					
					//���ñ����������
					if(intCurrent == intOut){
						mouseReportItem.setStrMonitorMethodType(YTBaseApplication.MOUSE_METHOD_OUT);
					}else if(intCurrent == intIn){
						mouseReportItem.setStrMonitorMethodType(YTBaseApplication.MOUSE_METHOD_IN);
					}else if(intCurrent==intCao){
						mouseReportItem.setStrMonitorMethodType(YTBaseApplication.MOUSE_METHOD_CAO);
					}
					
					
					mouseReportItem.setStrTagNum("-");
					mouseReportItem.setStrMouseGetNum("-");
					if (mType != null && mType.length() > 0) {
						mouseReportItem.setStrReportType(mType);
					} else {
						mouseReportItem.setStrReportType("-");
					}
					if (mPerson != null && mPerson.length() > 0) {
						mouseReportItem.setStrPerson(mPerson);
					} else {
						mouseReportItem.setStrPerson("-");
					}
					if (mAreaID != null && mAreaID.length() > 0) {
						mouseReportItem.setStrAreaID(mAreaID);
					} else {
						mouseReportItem.setStrAreaID("-");
					}

					if (mMethod != null && mMethod.length() > 0) {
						mouseReportItem.setMonitorMethod(mMethod);
					} else {
						mouseReportItem.setMonitorMethod("-");
					}
					if (mPlaceType != null && mPlaceType.length() > 0) {
						mouseReportItem.setmPlaceType(mPlaceType);
					} else {
						mouseReportItem.setmPlaceType("-");
					}
					if (mMeter != null && mMeter.length() > 0) {
						mouseReportItem.setStrMouseMeter(mMeter);
					} else {
						mouseReportItem.setStrMouseMeter("-");
					}
					if (mRatHole != null && mRatHole.length() > 0) {
						mouseReportItem.setStrRatHole(mRatHole);
					} else {
						mouseReportItem.setStrRatHole("-");
					}
					if (mRatDrains != null && mRatDrains.length() > 0) {
						mouseReportItem.setStrRatDrains(mRatDrains);
					} else {
						mouseReportItem.setStrRatDrains("-");
					}

					if (mFecal != null && mFecal.length() > 0) {
						mouseReportItem.setStrFecal(mFecal);
					} else {
						mouseReportItem.setStrFecal("-");
					}
					if (mRatBiteMark != null && mRatBiteMark.length() > 0) {
						mouseReportItem.setStrRatBiteMark(mRatBiteMark);
					} else {
						mouseReportItem.setStrRatBiteMark("-");
					}
					if (mRatCorpse != null && mRatCorpse.length() > 0) {
						mouseReportItem.setStrRatCorpse(mRatCorpse);
					} else {
						mouseReportItem.setStrRatCorpse("-");
					}
					if (mouseTrace != null && mouseTrace.length() > 0) {
						if (mouseTrace.equalsIgnoreCase("0")) {
							mouseReportItem.setStrMouseTrace("-");
						} else {
							mouseReportItem.setStrMouseTrace(mouseTrace);
						}
					} else {
						mouseReportItem.setStrMouseTrace("-");
					}
					if (mMouseDensity != null && mMouseDensity.length() > 0) {
						if (mMouseDensity.equalsIgnoreCase("NaN%")) {
							mouseReportItem.setStrMouseDensity("-");
						} else {
							mouseReportItem.setStrMouseDensity(mMouseDensity);
						}
					} else {
						mouseReportItem.setStrMouseDensity("-");
					}
					if (mOutMemo != null && mOutMemo.length() > 0) {
						mouseReportItem.setStrOutMemo(mOutMemo);
					} else {
						mouseReportItem.setStrOutMemo("-");
					}
					if (mMouseSunInit != null && mMouseSunInit.length() > 0) {
						mouseReportItem.setStrMouseSunInit(mMouseSunInit);
					} else {
						mouseReportItem.setStrMouseSunInit("-");
					}
					if (mMouseSun != null && mMouseSun.length() > 0) {
						mouseReportItem.setStrMouseSun(mMouseSun);
					} else {
						mouseReportItem.setStrMouseSun("-");
					}
					if (mMouseCatchNum != null && mMouseCatchNum.length() > 0) {
						mouseReportItem.setStrMouseCatchNum(mMouseCatchNum);
					} else {
						mouseReportItem.setStrMouseCatchNum("-");
					}
					if (mMouseDesitySun != null && mMouseDesitySun.length() > 0) {
						if (mMouseDesitySun.equalsIgnoreCase("0.00")) {
							mouseReportItem.setStrMouseDesitySun("-");
						} else {
							mouseReportItem.setStrMouseDesitySun(mMouseDesitySun);
						}
					} else {
						mouseReportItem.setStrMouseDesitySun("-");
					}
					if (mInMemo != null && mInMemo.length() > 0) {
						mouseReportItem.setStrInMemo(mInMemo);
					} else {
						mouseReportItem.setStrInMemo("-");
					}
					
					
					if (intCurrent == intOut) {
						if(mOutMemo!=null&&mOutMemo.length()>0){
							mouseReportItem.setmTotalMemo(mOutMemo);
						}else{
							mouseReportItem.setmTotalMemo("-");
						}
						
					}
					if (intCurrent == intIn) {
						if(mInMemo!=null&&mInMemo.length()>0){
							mouseReportItem.setmTotalMemo(mInMemo);
						}else{
							mouseReportItem.setmTotalMemo("-");
						}
						
					}
					
					if (intCurrent == intCao) {
						mouseReportItem.setmTotalMemo("-");
					}
					
					PHCConfig.mouseReportList.add(mouseReportItem);
					PHCMousePage.this.finish();

					
					
					if (D2EConfigures.TEST) {
						Log.e("density---------->", "" + strMouseDensity);
					}
					if (mouseReportItem != null) {
						mouseReportItem.printReprot();
					}
					
					
				} catch (Exception e) {

				} finally {
					
				}
			}

		});

		Button btnreset = (Button) findViewById(R.id.btn_reset);
		btnreset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				mStreetEdit.setText("");
				et_place_type.setText("");
				edit_mouse_palce_name.setText("");
				mDensityEdit.setText("");
				mDensitySumEdit.setText("");

				if(intCurrent == intOut){
					mMeterEdit.setText("");
					edit_rat_hole.setText("");
					edit_rat_drains.setText("");
					edit_rat_dung.setText("");
					edit_rat_bite_mark.setText("");
					edit_rat_corpse.setText("");
					edit_out_remark.setText("");
					
				}
				//�����"����(ճ��巨)"
				if(intCurrent == intIn){
					mSumInitEdit.setText("");
					mSumEdit.setText("");
					mCatchEdit.setText("");
					edit_in_remark.setText("");
				}

			}

		});

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void initServices() {
		
		if(!YTBaseApplication.mIsConnected){
			YTBaseApplication.mIsTryConnect = true;
			
			// �ж��Ƿ񱾵ر�����������
			SharedPreferences settings = getSharedPreferences(READER_PREFS_NAME, 0);
			SOPBluetoothService.g_btMacAddress = settings.getString(BT_MAC_KEY,
					null);
			SOPBluetoothService.g_readerID = settings.getString(READER_KEY, null);
			if (SOPBluetoothService.g_btMacAddress == null
					|| SOPBluetoothService.g_readerID == null) {
				
				Intent serverIntent = new Intent(PHCMousePage.this,D2EDeviceListActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString("OrgID", mOrgID);
				bundle.putString("AreaID", mAreaID);
				bundle.putString("Type", mType);
				bundle.putString("Person", mPerson);
				serverIntent.putExtras(bundle);
				startActivity(serverIntent);
				PHCMousePage.this.finish();
				
			}

			else {
				enterMainActivity();
			}
		}
		else {
			Intent intentCao=new Intent(PHCMousePage.this,PHCChaoShengBo.class);
			Bundle bundle=new Bundle();
			bundle.putString("OrgID", mOrgID);
			bundle.putString("AreaID", mAreaID);
			bundle.putString("Type", mType);
			bundle.putString("Person", mPerson);
			intentCao.putExtras(bundle);
			PHCMousePage.this.startActivity(intentCao);
			PHCMousePage.this.finish();
		}
		
	}

	private void enterMainActivity() {
		showProcessDialog("");
		((YTBaseApplication) getApplication()).startJJService();			
	}

	// ������ͣ��ʱ��ֹͣ�����ܶ��߳�
	@Override
	protected void onPause() {
		super.onPause();
		handler.removeCallbacks(calculateThread);
	}

	// �������ٵ�ʱ��ֹͣ�����ܶ��߳�
	@Override
	protected void onDestroy() {
		super.onDestroy();
		handler.removeCallbacks(calculateThread);
	}

	class CalCulateListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			handler.postDelayed(calculateThread, 1000);
		}

	}

	class CancleCalCulateListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			handler.removeCallbacks(calculateThread);
		}

	}


	@Override
	public void onBackAciton() {

	}

	@Override
	public boolean isExit() {
		return false;
	}
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch(requestCode){
		
		case REQUEST_ENABLE_BT:{
			if(resultCode == Activity.RESULT_OK){
				initServices();
			}
			else {
				getApp().showMessage(getResources().getString(R.string.blue_start_faield));
				finish();
			}
		}break;
		
		}
	}
	

	@Override
	public void taskSuccess(Object... param) {
		try {
			int values = ((Integer) param[0]).intValue();
			if (values == YTBaseService.BT_SERVICE_INT) {
				String response = (String) param[2];

				
				if (response.toLowerCase().equals(
						SOPBluetoothService.STATE_FAILED.toLowerCase())) {
					((YTBaseApplication) getApplication())
							.showMessage(getResources().getString(R.string.tag_read_faield));
					dismissProcessDialog();
					return;
				}
				

			}
			if (values == YTBaseService.HTTP_SERVICE_INT) {
				JJTask task = (JJTask) param[1];
				if (task.getTaskId() == PHCConfig.TASK_INSERT_STREE) {

					try {
						InputStream ins = task.getInputStream();
						byte[] bytes = YTNetUtil.readByByte(ins, -1);

						String tmp = new String(bytes, "UTF-8");
						ins.close();
						if(D2EConfigures.TEST){
							Log.e("OUT 20sss >>>>>> ", tmp);
						}
					}

					catch (Exception e) {
					}
				}
			}

		} catch (Exception ex) {

		}

		finally {
			hideWaitDialog();
		}

	}

	@Override
	public void taskFailed(Object... param) {
		hideWaitDialog();
		dismissProcessDialog();

		
		int values = ((Integer) param[0]).intValue();
		if (values == YTBaseService.BT_SERVICE_INT) {
			int state = ((Integer) param[1]).intValue();
			if (state == SOPBluetoothService.BT_DISCONNECT) {
				if(D2EConfigures.TEST){
					Log.e("BT_DISCONNECT==========>", "BT_DISCONNECT");
				}
				if(YTBaseApplication.mIsTryConnect){
					
					YTBaseApplication.mIsTryConnect = true;
					YTBaseApplication.g_Service_runState = false;
					((YTBaseApplication) getApplication()).stopJJService();
					if(!isShwoDeviceList){
						isShwoDeviceList = true;
						Intent serverIntent = new Intent(PHCMousePage.this,D2EDeviceListActivity.class);
						Bundle bundle=new Bundle();
						if(D2EConfigures.TEST){
							Log.e("mOrgID==========>", ""+mOrgID);
						}
						bundle.putString("OrgID", mOrgID);
						bundle.putString("AreaID", mAreaID);
						bundle.putString("Type", mType);
						bundle.putString("Person", mPerson);
						serverIntent.putExtras(bundle);
//						startActivity(serverIntent);
						PHCMousePage.this.finish();
						startActivityForResult(serverIntent, REQUESR_DEVICE);											
					}
				}
				else {
					((YTBaseApplication) getApplication()).showMessage(getResources().getString(R.string.reader_no_start));
				}
			}else if(state == SOPBluetoothService.BT_CONNECT){
								
				if(YTBaseApplication.mIsTryConnect){
					YTBaseApplication.mIsTryConnect = false;
					YTBaseApplication.mIsConnected = true;
					Intent intentCao=new Intent(PHCMousePage.this,PHCChaoShengBo.class);
					Bundle bundle=new Bundle();
					bundle.putString("OrgID", mOrgID);
					bundle.putString("AreaID", mAreaID);
					bundle.putString("Type", mType);
					bundle.putString("Person", mPerson);
					intentCao.putExtras(bundle);
					PHCMousePage.this.startActivity(intentCao);
					PHCMousePage.this.finish();
				}
			}

		}
		if (values == YTBaseService.HTTP_SERVICE_INT) {
			((YTBaseApplication) getApplication()).showMessage(getResources()
					.getString(R.string.net_failed));
		}
	}

	@Override
	public void taskProcessing(Object... param) {
		// TODO Auto-generated method stub

	}

	// *

	Handler handler = new Handler();
	Runnable calculateThread = new Runnable() {
		String sum = null;
		int meter = -1;
		int ratHole = -1;
		int ratDrains = -1;
		int fecal = -1;
		int ratBiteMark = -1;
		int ratCorpse = -1;
		int mouseSunInit = -1;
		int mouseSun = -1;
		int mouseCatchNumber = -1;

		@Override
		public void run() {
			
		    meter = -1;
			ratHole = -1;
			ratDrains = -1;
			fecal = -1;
			ratBiteMark = -1;
			ratCorpse = -1;
			mouseSunInit = -1;
			mouseSun = -1;
			mouseCatchNumber = -1;
			DecimalFormat df = new DecimalFormat("0.00");

			if(D2EConfigures.TEST){
				Log.e("Thread------>", "Thread.run()");
			}
			String mMeter = mMeterEdit.getText().toString();
			String mRatHole = edit_rat_hole.getText().toString();
			String mRatDrains = edit_rat_drains.getText().toString();
			String mFecal = edit_rat_dung.getText().toString();
			String mRatBiteMark = edit_rat_bite_mark.getText().toString();
			String mRatCorpse = edit_rat_corpse.getText().toString();

			String mMouseSunInit = mSumInitEdit.getText().toString();
			String mMouseSun = mSumEdit.getText().toString();
			String mMouseCatchNum = mCatchEdit.getText().toString();

			if (mMeter != null && mMeter.length() > 0) {
				try{
					meter = Integer.valueOf(mMeter);
				}
				catch(Exception e){
					meter = -1;
				}
				
			}
			if (mRatHole != null && mRatHole.length() > 0) {
				try{
					ratHole = Integer.valueOf(mRatHole);
				}
				catch(Exception e){}
				
			}
			if (mRatDrains != null && mRatDrains.length() > 0) {
				try{
					ratDrains = Integer.valueOf(mRatDrains);
				}
				catch(Exception e){}
				
			}
			if (mFecal != null && mFecal.length() > 0) {
				try{
					fecal = Integer.valueOf(mFecal);
				}
				catch(Exception e){}
				
			}
			if (mRatBiteMark != null && mRatBiteMark.length() > 0) {
				try{
					ratBiteMark = Integer.valueOf(mRatBiteMark);
				}
				catch(Exception e){}
				
			}
			if (mRatCorpse != null && mRatCorpse.length() > 0) {
				try{
					ratCorpse = Integer.valueOf(mRatCorpse);
				}
				catch(Exception e){}
				
			}

			if (mMouseSunInit != null && mMouseSunInit.length() > 0) {
				try{
					mouseSunInit = Integer.valueOf(mMouseSunInit);
				}
				catch(Exception e){mouseSunInit = -1;}
				
			}
			if (mMouseSun != null && mMouseSun.length() > 0) {
				try{
					mouseSun = Integer.valueOf(mMouseSun);
				}
				catch(Exception e){mouseSun = -1;}
				
				if(mouseSun > mouseSunInit){
					getApp().showMessage(getResources().getString(R.string.sun_no_large_banshu));
					mouseSun = -1;
					mSumEdit.setText("");
				}
				
			}
			if (mMouseCatchNum != null && mMouseCatchNum.length() > 0) {
				try{
					mouseCatchNumber = Integer.valueOf(mMouseCatchNum);
				}
				catch(Exception e){}
				
			}
			
			// ��֤��ĸ��Ϊ0
			if (mouseSunInit > 0 && mouseSun > 0 ) {
				//modify by shawn �����ܶ�ֻҪ������λ������λ����������
				//����888/78687=0.011285218651111366   ȡֵΪ�� 1.12%
				float i=mouseSun;
				float j=mouseSunInit;
				mDensitySumEdit.setText(com.mobilercn.util.NumberUtils.getPrecent(i, j, 2));
				//End
			}
			else {
				mDensitySumEdit.setText("");
			}



			int total = (ratHole + ratDrains + fecal + ratBiteMark + ratCorpse);

			if(total > 0){
				mouseTrace = String.valueOf(total);				
			}
			else {
				mouseTrace = "-";
			}
			
			if(total >= 0 && meter > 0){
				// �Ѹ����͵�����ת��Ϊ������λ��Ч����ֵ
				//modify by shawn �����ܶ�ֻҪ������λ������λ����������
				//����888/78687=0.011285218651111366   ȡֵΪ�� 1.12%
				float i=total;
				float j=meter;
				mDensityEdit.setText(com.mobilercn.util.NumberUtils.getPrecent(i, j, 2));
				//End
			}
			else {
				mDensityEdit.setText("");
			}
			if (D2EConfigures.TEST) {
				Log.e("mouseTrace------>", "" + mouseTrace);
				Log.e("density------>", "" + sum);
			}
			handler.postDelayed(calculateThread, 1000);
		}

	};
}
