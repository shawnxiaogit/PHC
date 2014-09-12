package com.vsi.page;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.LocationListener;
import com.mobilercn.base.YTBaseActivity;
import com.mobilercn.base.YTBaseApplication;
import com.mobilercn.base.YTBaseService;
import com.mobilercn.sop.bt.SOPBTCallHelper;
import com.mobilercn.sop.bt.SOPBluetoothService;
import com.mobilercn.task.JJTask;
import com.mobilercn.util.CellInfoManager;
import com.mobilercn.util.CellLocationManager;
import com.mobilercn.util.WifiInfoManager;
import com.mobilercn.util.YTLocationEngine;
import com.mobilercn.util.YTNetUtil;
import com.mobilercn.util.YTStringHelper;
import com.mobilercn.widget.D2EListAdapter;
import com.mobilercn.widget.D2EListAdapterItam;
import com.vsi.phc.R;
import com.vsi.config.D2EConfigures;
import com.vsi.config.PHCConfig;
import com.vsi.data.AreaItem;

import android.widget.ImageButton;
import android.widget.BaseAdapter;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.view.View.OnTouchListener;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 监测信息录入界面
 */
public class PHCInfoPage extends YTBaseActivity {
	
	//add by shawn 2012-12-17 Begin
	//使用百度地图api 定位功能需要的监听器
	//在onCreate()的时候注册此监听，在onDestroy()的时候remove掉
	LocationListener mLocationListener=null;
	//End

	private static final String READER_PREFS_NAME = "READERID";
	private static final String BT_MAC_KEY = "BTMAC";
	private static final String READER_KEY = "READER";

	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	public static String EXTRA_READER_ID = "reader_id";

	private static final int REQUESR_DEVICE = 2;

	/**
	 * 爱委会公司id
	 */
	private String mOrgID;
	/**
	 * 区Id
	 */
	private String mAreaID;

	/**
	 * 我的区Id
	 */
	private String mAreaItemId;
	/**
	 * 监测人
	 */
	private String mPerson;
	/**
	 * 获取位置引擎
	 */
	private YTLocationEngine locationEngine;
	/**
	 * 请求打开蓝牙参数
	 */
	private static final int REQUEST_ENABLE_BT = 1;

	AutoCompleteTextView mAutoText;
	/**
	 * 区下拉框图片按钮
	 */
	private ImageButton ibtn_dropDown;
	/**
	 * 下拉弹出框
	 */
	private PopupWindow pop;
	/**
	 * 下拉框数据适配器
	 */
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

	/**
	 * 是否显示mAreaListView
	 */
	private boolean isShowList = false;
	private ListView mAreaListView;
	/**
	 * 区名称列表
	 */
	private List<String> areas;
	private List<AreaItem> mAreaItems = new ArrayList<AreaItem>();
	/**
	 * 检测时间输入框
	 */
	private EditText mTimeEdit;
	
	private EditText mDateEdit;
	/**
	 * 今日天气输入框
	 */
	private EditText mWeathyEdit;
	/**
	 * 今日温度输入框
	 */
	private EditText mTempEdit;
	/**
	 * 监测单位输入框
	 */
	private EditText mCompEdit;
	/**
	 * 监测人输入框
	 */
	private EditText mPersonEdit;

	/**
	 * 经度
	 */
	private String mLogitude;
	/**
	 * 纬度
	 */
	private String mLatitude;
	Bundle bundle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.phc_info_page);
		Window window = getWindow();
		window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.public_title_bar);
		
		
		
		//add by shawn 2012-12-17
		//使用百度api，定位
		YTBaseApplication app=(YTBaseApplication)this.getApplication();
		if(app.mBMapMan==null){
			app.mBMapMan=new BMapManager(getApplication());
			app.mBMapMan.init(app.mStrKey, new YTBaseApplication.MyGeneralListener());
		}
		app.mBMapMan.start();
		//注册定位事件
		mLocationListener=new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				if(location!=null){
					NumberFormat mFormat=new DecimalFormat(".0000000");
					mLogitude=mFormat.format(location.getLongitude());
					mLatitude=mFormat.format(location.getLatitude());
				}
			}
		};
		
		//End
		

		mDropAdapter = new D2EListAdapter(this);
		for (AreaItem item : PHCConfig.areaItems) {
			mDropAdapter.addAreaItem(item);
			item.getStrAreaID();
		}

		mAutoText = (AutoCompleteTextView) findViewById(R.id.et_areaName);
		mAutoText.setThreshold(1);
		mAutoText.addTextChangedListener(mWatcher);
		mAutoText.setAdapter(mDropAdapter);
		mAutoText.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {

				D2EListAdapterItam item = (D2EListAdapterItam) mDropAdapter
						.getItem(position);

				mAutoText.setText(item.getTitle());
				// 获取选择的区id,用个静态变量来存好了
				mAreaID = item.getId();
				// 如果选择了一个区，则经纬度由这里获得
				// 1、首先把获取经纬度的线程关了
				// 2、这里获得经纬度
				mLogitude = item.getmAreaLongitude();
				mLatitude = item.getmAreaLatitude();
				if (D2EConfigures.TEST) {
					Log.e("mLogitude--------------->", "" + mLogitude);
					Log.e("mLatitude--------------->", "" + mLatitude);
				}
			}

		});

		// kris

		ibtn_dropDown = (ImageButton) this.findViewById(R.id.ibtn_dropDown);
		mTimeEdit = (EditText) findViewById(R.id.input_time);
		mDateEdit = (EditText) findViewById(R.id.input_date);
		mWeathyEdit = (EditText) findViewById(R.id.input_weathy);
		mTempEdit = (EditText) findViewById(R.id.input_temp);
		mCompEdit = (EditText) findViewById(R.id.input_comp);
		mPersonEdit = (EditText) findViewById(R.id.input_person);
		
		//kris 格式化日期时间
		SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd|HH:mm");
		String tmp = dates.format(new Date());
		try{
			int index = tmp.indexOf("|");
			if(mTimeEdit != null){
				mTimeEdit.setText(tmp.substring(index + 1));
			}	
			
			if(mDateEdit != null){
				mDateEdit.setText(tmp.subSequence(0, index));
			}
		}
		catch(Exception e){}


		ibtn_dropDown.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {

				if (mAutoText.getText() != null
						&& mAutoText.getText().length() > 0) {
					mAutoText.setText("");
				}
				mAutoText.showDropDown();
				mAutoText.setThreshold(1);
			}
		});


		// add by kris

		Intent intent = getIntent();
		mOrgID = intent.getStringExtra("OrgID");

		bundle = new Bundle();
		bundle.putString("OrgID", mOrgID);


		if (D2EConfigures.TEST) {
			Log.e("mOrgID----->", mOrgID);
			Log.e("mLogitude--------------->", "" + mLogitude);
			Log.e("mLatitude--------------->", "" + mLatitude);
		}

		// 添加普通监测数据，即通过填表方式来采集数据
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.menu_mosquito_layout);
		rl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String strAreaName = mAutoText.getText().toString();
				String strComp = mCompEdit.getText().toString();
				String strPerson = mPersonEdit.getText().toString();

				// 判断是否输入选择了区
				if (strAreaName == null || !(strAreaName.length() > 0)) {
					getApp().showMessage("请输入或选择区");
					return;
				}
				// 判断是否输入了监测单位
				if (strComp == null || !(strComp.length() > 0)) {
					getApp().showMessage("请填写监测单位");
					return;
				}
				// 判断是否输入了监测人
				if (strPerson == null || !(strPerson.length() > 0)) {
					getApp().showMessage("请填写监测人");
					return;
				}
				// //判断是否获取了经纬度
				if (mLogitude == null || !(mLogitude.length() > 0)
						|| mLatitude == null || !(mLatitude.length() > 0)) {
					getApp().showMessage("未获取到经纬度，请稍后...");
					return;
				}

				if (mPersonEdit.getText().toString() != null
						&& mPersonEdit.getText().toString().length() > 0) {
					mPerson = mPersonEdit.getText().toString();
				} else {
					mPerson = "-";
				}
				bundle.putString("Person", mPerson);
				AreaItem item = new AreaItem(strAreaName);
				if (!PHCConfig.ContainsArea(item)) {
					if (mLogitude != null && mLogitude.length() > 0
							&& mLatitude != null && mLatitude.length() > 0) {

						if (strAreaName != null && strAreaName.length() > 0) {
							String[] params = new String[] { "FunType",
									"insertStreet", // 插入区接口名称
									"OrgID", mOrgID,// 爱委会id
									"Name", strAreaName,// 区的名字
									"Longitude", mLogitude,// 经度
									"Latitude", mLatitude // 纬度
							};
							YTStringHelper.array2string(params);
							long id = YTBaseService
									.addMutilpartHttpTask(
											"http://www.ohpest.com/ohpest-main/webservices/get/awh.html",
											params, PHCInfoPage.this.getClass()
													.getName(),
											PHCConfig.TASK_INSERT_STREE);
							setCurTaskID(id);
							showWaitDialog("正在处理,请稍等....", R.style.dialog,
									R.layout.progress_dialog, R.id.wait_image,
									R.id.wait_message);
							return;
						} else {
							getApp().showMessage("请输入或选择区");
							return;
						}
					} else {
						getApp().showMessage("没有获取到经纬度，请到室外或者网络好点的地方获取一次");
						return;
					}
				}
				// 这里要判断进入那个表
				// 鼠监测则进入鼠的报表
				if (PHCConfig.SURVEY_TYPE == PHCConfig.TYPE_MOUSE) {
					Intent intent = new Intent(PHCInfoPage.this,
							PCHMouseTablePage.class);
					// 这里还要采集界面的信息传递过去
					bundle.putString("AreaID", mAreaID);
					bundle.putString("Type", "Rat");
					intent.putExtras(bundle);
					PHCInfoPage.this.startActivity(intent);
					PHCInfoPage.this.finish();
					overridePendingTransition(R.anim.fade, R.anim.hold);
				}
				// 蚊监测
				else if (PHCConfig.SURVEY_TYPE == PHCConfig.TYPE_MOSQUITO) {
					Intent intent = new Intent(PHCInfoPage.this,
							PCHMosquitoTablePage.class);
					bundle.putString("AreaID", mAreaID);
					bundle.putString("Type", "Mosquito");
					intent.putExtras(bundle);
					PHCInfoPage.this.startActivity(intent);
					PHCInfoPage.this.finish();
					overridePendingTransition(R.anim.fade, R.anim.hold);
				}
				// 蟑螂监测
				else if (PHCConfig.SURVEY_TYPE == PHCConfig.TYPE_KOCKROACH) {
					Intent intent = new Intent(PHCInfoPage.this,
							PCHKockroachTablePage.class);
					bundle.putString("AreaID", mAreaID);
					bundle.putString("Type", "Roach");
					intent.putExtras(bundle);
					PHCInfoPage.this.startActivity(intent);
					PHCInfoPage.this.finish();
					overridePendingTransition(R.anim.fade, R.anim.hold);
				}
				// 蝇监测
				else if (PHCConfig.SURVEY_TYPE == PHCConfig.TYPE_FLY) {
					Intent intent = new Intent(PHCInfoPage.this,
							PCHFlyTablePage.class);
					bundle.putString("AreaID", mAreaID);
					bundle.putString("Type", "Fly");
					intent.putExtras(bundle);
					PHCInfoPage.this.startActivity(intent);
					PHCInfoPage.this.finish();
					overridePendingTransition(R.anim.fade, R.anim.hold);
				}
			}
		});
	}

	// 界面暂停的时候停止获取经纬度线程
	@Override
	protected void onPause() {
		YTBaseApplication app=(YTBaseApplication) this.getApplication();
		//移除监听器
		app.mBMapMan.getLocationManager().removeUpdates(mLocationListener);
		app.mBMapMan.stop();
		super.onPause();
	}
	
	//页面重新载入的时候，重新注册监听器
	@Override
	protected void onResume() {
		YTBaseApplication app=(YTBaseApplication) this.getApplication();
		//注册监听器
		app.mBMapMan.getLocationManager().requestLocationUpdates(mLocationListener);
		app.mBMapMan.start();
		super.onResume();
	}

	private void initServices() {
		// 判断是否本地保存蓝牙设置
		SharedPreferences settings = getSharedPreferences(READER_PREFS_NAME, 0);
		SOPBluetoothService.g_btMacAddress = settings.getString(BT_MAC_KEY,
				null);
		SOPBluetoothService.g_readerID = settings.getString(READER_KEY, null);
		if (SOPBluetoothService.g_btMacAddress == null
				|| SOPBluetoothService.g_readerID == null) {
			Intent serverIntent = new Intent(this, D2EDeviceListActivity.class);
			startActivityForResult(serverIntent, REQUESR_DEVICE);
		} else {
			enterMainActivity();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {

		case REQUEST_ENABLE_BT: {
			if (resultCode == Activity.RESULT_OK) {
				initServices();
			} else {
				((YTBaseApplication) getApplication()).showMessage(getResources().getString(R.string.blue_start_faield));
			}
		}
			break;

		case REQUESR_DEVICE: {
			if (resultCode == Activity.RESULT_CANCELED) {
				return;
			}

			SOPBluetoothService.g_btMacAddress = data.getExtras().getString(
					EXTRA_DEVICE_ADDRESS);
			SOPBluetoothService.g_readerID = data.getExtras().getString(
					EXTRA_READER_ID);

			SharedPreferences settings = getSharedPreferences(
					READER_PREFS_NAME, 0);
			Editor editor = settings.edit();
			editor.putString(BT_MAC_KEY, SOPBluetoothService.g_btMacAddress);
			editor.putString(READER_KEY, SOPBluetoothService.g_readerID);
			editor.commit();

			enterMainActivity();

		}
			break;

		}
	}

	private void enterMainActivity() {
		((YTBaseApplication) getApplication()).startJJService();

		if (SOPBluetoothService.getService() == null
				|| !SOPBluetoothService.getService().mIsRun) {
			((YTBaseApplication) getApplication())
					.showMessage(getResources().getString(R.string.blue_tooth_conn_prompt));
			return;
		}

		showProcessDialog(getResources().getString(R.string.tag_reading));
		SOPBluetoothService.getService().doTask(SOPBTCallHelper.READ_TAG,
				PHCInfoPage.class.getName(),
				SOPBTCallHelper.getInitOrder(SOPBluetoothService.g_readerID));

	}


	/**
	 * 自定义下拉框 区名称列表数据适配器
	 * 
	 */
	class PopupAdapter extends BaseAdapter {
		private LayoutInflater layoutInflater;
		private Context context;

		public PopupAdapter() {

		}

		public PopupAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return mAreaItems.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			Holder holder;
			final AreaItem item = mAreaItems.get(position);
			if (convertView == null) {
				layoutInflater = LayoutInflater.from(context);
				convertView = layoutInflater.inflate(R.layout.area_item, null);
				holder = new Holder();
				holder.tv = (TextView) convertView
						.findViewById(R.id.tv_detailAreaName);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			if (holder != null) {
				convertView.setId(position);
				holder.setId(position);
				holder.tv.setText(item.getStrAreaName());
				holder.tv.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View arg0, MotionEvent arg1) {
						pop.dismiss();
						isShowList = false;
						mAutoText.setText(item.getStrAreaName());
						mAreaItemId = item.getStrAreaID();
						return true;
					}
				});
			}
			return convertView;
		}

		class Holder {
			TextView tv;

			void setId(int position) {
				tv.setId(position);
			}
		}
	}

	@Override
	public void onBackAciton() {

	}

	@Override
	public boolean isExit() {
		return false;
	}

	@Override
	public void taskSuccess(Object... param) {
		if (D2EConfigures.TEST) {
			Log.e("taskSuccess=========>", "taskSuccess()");
		}
		try {
			int values = ((Integer) param[0]).intValue();
			if (values == YTBaseService.BT_SERVICE_INT) {
				String response = (String) param[2];
				if(D2EConfigures.TEST){
					Log.i("response=------>", response);
				}
				// 标签号
				String tagID = response.substring(14, 22);
				// 老鼠数量
				String mouseNum = response.substring(22, 30);
				int intMouseNum = Integer.parseInt(mouseNum, 16);
				String strMouseNum = String.valueOf(intMouseNum);
				Log.e("老鼠数量为：--------->", "" + intMouseNum);
				if (response.toLowerCase().equals(
						SOPBluetoothService.STATE_FAILED.toLowerCase())) {
					((YTBaseApplication) getApplication())
							.showMessage("标签读取失败，请重扫描！");
					dismissProcessDialog();
					return;
				}
				// 跳转到报表画面，并插入一项报表
				Intent intent = new Intent(PHCInfoPage.this,
						PCHMouseTablePage.class);
				if (mPersonEdit.getText().toString() != null
						&& mPersonEdit.getText().toString().length() > 0) {
					mPerson = mPersonEdit.getText().toString();
				} else {
					mPerson = "N/A";
				}
				bundle.putString("Person", mPerson);
				bundle.putString("Type", "Rat");
				bundle.putString("MouseNum", strMouseNum);
				bundle.putString("tagId", tagID);
				bundle.putBoolean("isGetMouseNum", true);
				intent.putExtras(bundle);
				PHCInfoPage.this.startActivity(intent);
				finish();
			}
			if (values == YTBaseService.HTTP_SERVICE_INT) {
				JJTask task = (JJTask) param[1];
				if (task.getTaskId() == PHCConfig.TASK_INSERT_STREE) {

					try {
						InputStream ins = task.getInputStream();
						byte[] bytes = YTNetUtil.readByByte(ins, -1);

						String tmp = new String(bytes, "UTF-8");
						ins.close();
						if (D2EConfigures.TEST) {
							Log.e("OUT 20sss >>>>>> ", tmp);
							JSONObject jo=new JSONObject(tmp);
							Log.e("jo============>", ""+jo);
						}
						String id = null;
						// add by shawn 2012-9-20 Begin
						// 进入下个界面的前提是获取到了经纬度，否则提示"获取经纬度失败，请稍后..."
						if (mLogitude != null && mLogitude.length() > 0
								&& mLatitude != null && mLatitude.length() > 0) {
							JSONObject jo = new JSONObject(tmp);
							id = jo.getString("ID");
							mAreaID = id;
							bundle.putString("AreaID", mAreaID);
							String name = jo.getString("Name");
							AreaItem item = new AreaItem(id, name);
							item.setmAreaLongitude(mLogitude);
							item.setmAreaLatitude(mLatitude);
							PHCConfig.areaItems.add(item);

							// 插入成功则进入下个界面
							if (PHCConfig.SURVEY_TYPE == PHCConfig.TYPE_MOUSE) {
								Intent intent = new Intent(PHCInfoPage.this,
										PCHMouseTablePage.class);
								bundle.putString("Type", "Rat");
								intent.putExtras(bundle);
								PHCInfoPage.this.startActivity(intent);
								PHCInfoPage.this.finish();
								overridePendingTransition(R.anim.fade,
										R.anim.hold);
							}
							// 蚊监测
							else if (PHCConfig.SURVEY_TYPE == PHCConfig.TYPE_MOSQUITO) {
								Intent intent = new Intent(PHCInfoPage.this,
										PCHMosquitoTablePage.class);
								bundle.putString("Type", "Mosquito");
								intent.putExtras(bundle);
								PHCInfoPage.this.startActivity(intent);
								PHCInfoPage.this.finish();
								overridePendingTransition(R.anim.fade,
										R.anim.hold);
							}
							// 蟑螂监测
							else if (PHCConfig.SURVEY_TYPE == PHCConfig.TYPE_KOCKROACH) {
								Intent intent = new Intent(PHCInfoPage.this,
										PCHKockroachTablePage.class);
								bundle.putString("Type", "Roach");
								intent.putExtras(bundle);
								PHCInfoPage.this.startActivity(intent);
								PHCInfoPage.this.finish();
								overridePendingTransition(R.anim.fade,
										R.anim.hold);
							}
							// 蝇监测
							else if (PHCConfig.SURVEY_TYPE == PHCConfig.TYPE_FLY) {
								Intent intent = new Intent(PHCInfoPage.this,
										PCHFlyTablePage.class);
								bundle.putString("Type", "Fly");
								intent.putExtras(bundle);
								PHCInfoPage.this.startActivity(intent);
								PHCInfoPage.this.finish();
								overridePendingTransition(R.anim.fade,
										R.anim.hold);
							}
						} else {
							((YTBaseApplication) getApplication())
									.showMessage(getResources()
											.getString(
													R.string.get_longitude_latitude_faield));
						}
						// End
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
		if (D2EConfigures.TEST) {
			Log.e("taskFailed=========>", "taskFailed()");
		}
		hideWaitDialog();
		int values = ((Integer) param[0]).intValue();
		if (values == YTBaseService.BT_SERVICE_INT) {
			int state = ((Integer) param[1]).intValue();
			if (state == SOPBluetoothService.BT_DISCONNECT) {
			}

		}
		if (values == YTBaseService.HTTP_SERVICE_INT) {
			((YTBaseApplication) getApplication()).showMessage(getResources()
					.getString(R.string.net_failed));
		}

	}

	@Override
	public void taskProcessing(Object... param) {

	}


}
