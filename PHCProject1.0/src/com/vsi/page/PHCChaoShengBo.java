package com.vsi.page;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobilercn.base.YTBaseActivity;
import com.mobilercn.base.YTBaseApplication;
import com.mobilercn.base.YTBaseService;
import com.mobilercn.sop.bt.SOPBTCallHelper;
import com.mobilercn.sop.bt.SOPBluetoothService;
import com.mobilercn.task.JJTask;
import com.mobilercn.util.YTNetUtil;
import com.mobilercn.util.YTStringHelper;
import com.mobilercn.widget.YTReportModel;
import com.mobilercn.widget.YTReportView;
import com.vsi.config.D2EConfigures;
import com.vsi.config.PHCConfig;
import com.vsi.data.CaoItem;
import com.vsi.data.MouseReportItem;
import com.vsi.phc.R;

/**
 * ���ϵͳ������ģ���½���
 * 
 */
public class PHCChaoShengBo extends YTBaseActivity {
	/**
	 * �������ô���
	 */
	private int mMouseComeNum;
	/**
	 * ��ע
	 */
	EditText edit_d2e_remark;
	/**
	 * D2E������
	 */
	MouseReportItem reportItem=null;
	
	/**
	 * ����ķ�����Ϣ
	 */
	private String mSaveResponse;
	
	/**
	 * �������������
	 */
	private static final int REQUEST_ENABLE_BT = 1;

	private static final String READER_PREFS_NAME = "READERID";
	private static final String BT_MAC_KEY = "BTMAC";
	private static final String READER_KEY = "READER";

	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	public static String EXTRA_READER_ID = "reader_id";

	private static final int REQUESR_DEVICE = 2;


	/**
	 * �ֵ����������
	 */
	private EditText edit_street;
	/**
	 * �������������
	 */
	private AutoCompleteTextView et_place_type;
	/**
	 * �������������
	 */
	private EditText edit_mouse_palce_name;
	/**
	 * ����(�������ô���)
	 */
	private TextView tv_cao_sum;
	/**
	 * ����ͼ��
	 */
	private ImageView iv_cao_battery;
	
	private ImageView iv_cao_cover;
	/**
	 * ����������
	 */
	//private YTReportView reportview_chao;
	/**
	 * ��ʼ����ť
	 */
	private Button btn_cao_init;
	/**
	 * ɨ�谴ť
	 */
	private Button btn_cao_scann;

	// ������������
	/**
	 * �ֵ�����
	 */
	private String strStreet = "-";
	/**
	 * ��������
	 */
	private String strPlaceType = "-";
	/**
	 * ��������
	 */
	private String strPlaceName = "-";
	/**
	 * ����
	 */
	private String strMouseSum = "-";
	
	private String mOrgID;
	private String mAreaID;
	private String mType;
	private String mPerson;
	
	
	//kris
	String mPlaceType;
	long mInitTime;//222222s
	ImageButton mButtonRight, mButtonLeft;

	/**
	 * �Ƿ��ǵ�һ���ϵ�
	 */
	private boolean isFirstElectric=false;
	
	/**
	 * ��������������
	 */
	ArrayAdapter adapterPlaceType;
	/**
	 * ��������������ť
	 */
	private ImageButton ibtn_dropDown_place;
	
	/**
	 * �Ƿ�ȱ��
	 */
	private boolean mIsLessElec=false;
	
	
	//����
	private boolean isSave = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.phc_chao_sheng_bo);
		Window window = getWindow();
		window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.public_title_bar);
		// ��ʼ������
		initPage();
		//��ȡ����
		getDataFromView();
		// Ϊ��ť���ü���
		setUpBtnListener();
		
	}
	
	private void getDataFromView(){
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

		
	}

	/**
	 * ��ʼ������
	 */
	private void initPage() {
		edit_street = (EditText) this.findViewById(R.id.edit_street);
		et_place_type = (AutoCompleteTextView) this
				.findViewById(R.id.et_place_type);
		edit_mouse_palce_name = (EditText) this
				.findViewById(R.id.edit_mouse_palce_name);
		tv_cao_sum = (TextView) this.findViewById(R.id.tv_cao_sum);
		iv_cao_battery = (ImageView) this.findViewById(R.id.iv_cao_battery);
		
		iv_cao_cover = (ImageView) this.findViewById(R.id.iv_cao_cover_state);
		
		btn_cao_init = (Button) this.findViewById(R.id.btn_cao_init);
		ibtn_dropDown_place=(ImageButton) this.findViewById(R.id.ibtn_dropDown_place);
		btn_cao_scann = (Button) this.findViewById(R.id.btn_cao_scann);
		
		btn_cao_scann.setEnabled(true);
		
		adapterPlaceType = ArrayAdapter.createFromResource(PHCChaoShengBo.this,
				R.array.mouse_cao_place,
				R.layout.simple_dropdown_item_1line);
		edit_d2e_remark=(EditText) this.findViewById(R.id.edit_d2e_remark);
		
		Button btn_save = (Button)findViewById(R.id.btn_cao_save);
		btn_save.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(isSave){
					getApp().showMessage("����ɹ�!");
					isSave = false;
					//add by shawn 2012-10-9 Begin
					//��ӱ�ע
					if(reportItem!=null){
					if(edit_d2e_remark.getText()!=null&&edit_d2e_remark.getText().length()>0){
						reportItem.setmTotalMemo(edit_d2e_remark.getText().toString());
					}else{
						reportItem.setmTotalMemo("-");
					}
					}
					
					
					finish();
				}
				else {
					getApp().showMessage("����ɨ�裡");
				}
			}
			
		});
		
		
	}
	
	//�ж��Ƿ��·��ؼ��������ˣ����жϱ�ע�Ƿ�Ϊ�գ���Ϊ����ѱ�ע������
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
				//add by shawn 2012-10-9 Begin
				//��ӱ�ע
				if(reportItem!=null){
				if(edit_d2e_remark.getText()!=null&&edit_d2e_remark.getText().length()>0){
					reportItem.setmTotalMemo(edit_d2e_remark.getText().toString());
				}else{
					reportItem.setmTotalMemo("-");
				}
				}
				//End
			}
			return super.onKeyDown(keyCode, event);
		}
	

	/**
	 * Ϊ��ť���ü�����
	 */
	private void setUpBtnListener() {
		btn_cao_init.setOnClickListener(new myInitListener());
		btn_cao_scann.setOnClickListener(new myScannerListener());
		et_place_type.setAdapter(adapterPlaceType);
		ibtn_dropDown_place.setOnClickListener(new DropDownListener());
	}
	/**
	 * �������������������������
	 */
	private class DropDownListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(et_place_type!=null&&et_place_type.length()>0){
				et_place_type.setText("");
			}
			
			et_place_type.showDropDown();
		}
		
	}

	/**
	 * ��ʼ��������
	 */
	private class myInitListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			isFirstElectric=true;
			//�ϵ��ʱ��ˢ��У���ȡ��ǩ�źͷ�����ͬ��ʱ������
			enterMainActivity();
			
		}

	}

	/**
	 * ɨ�������
	 */
	private class myScannerListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			isFirstElectric=false;
			// ����ֵ����ƺͳ������Ʋ�Ϊ�գ����½�һ��������
			strStreet = edit_street.getText().toString();
			mPlaceType = et_place_type.getText().toString();
			strPlaceName = edit_mouse_palce_name.getText().toString();
			//�жϼ�ⷽ���Ƿ���д
			if(strStreet == null || !(strStreet.length() > 0)){
				((YTBaseApplication) getApplication())
				.showMessage(getResources().getString(R.string.cao_shengbo_street_less));
				return;
			}
			if(mPlaceType == null || !(mPlaceType.length() > 0)){
				((YTBaseApplication) getApplication())
				.showMessage(getResources().getString(R.string.cao_shengbo_place_type_less));
				return;
			}
			if(strPlaceName == null || !(strPlaceName.length() > 0)){
				((YTBaseApplication) getApplication())
				.showMessage(getResources().getString(R.string.cao_shengbo_place_name_less));
				return;
			}
			
			enterMainActivity();
		}

	}


	@Override
	public void onBackAciton() {

	}

	/**
	 * ��ȡ��������
	 */
	private void enterMainActivity() {

		showProcessDialog("��ǩ��ȡ��...");
		SOPBluetoothService.getService().doTask(SOPBTCallHelper.READ_TAG,
				PHCChaoShengBo.class.getName(),
				SOPBTCallHelper.getInitOrder(SOPBluetoothService.g_readerID));

	}

	private void initServices() {
		// �ж��Ƿ񱾵ر�����������
		SharedPreferences settings = getSharedPreferences(READER_PREFS_NAME, 0);
		SOPBluetoothService.g_btMacAddress = settings.getString(BT_MAC_KEY,
				null);
		SOPBluetoothService.g_readerID = settings.getString(READER_KEY, null);
		if (SOPBluetoothService.g_btMacAddress == null
				|| SOPBluetoothService.g_readerID == null) {
			Intent serverIntent = new Intent(this, D2EDeviceListActivity.class);
			startActivityForResult(serverIntent, REQUESR_DEVICE);
		}
		((YTBaseApplication) getApplication()).startJJService();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {

		case REQUEST_ENABLE_BT: {
			if (resultCode == Activity.RESULT_OK) {
				initServices();
			} else {
				getApp().showMessage(getResources().getString(R.string.blue_start_faield));
				finish();
			}
		}
			break;

		case REQUESR_DEVICE: {
			if (resultCode == Activity.RESULT_CANCELED) {
				// return;
				finish();
				getApp().dealloc();
				android.os.Process.killProcess(android.os.Process.myPid());
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

		}
			break;

		}
	}

	@Override
	public boolean isExit() {
		return false;
	}
	
	
	
	//�������ݣ�����ʾ����
	/**
	 * �������ݲ��ڱ�������ʾ
	 * @param response
	 * ��ȡ�ķ�������
	 * @param time
	 * �ϵ�ʱ��
	 */
	/**
	 * �������ݲ��ڱ�������ʾ
	 * @param response
	 * ������ȡ���������
	 * @param time
	 * �ϵ�ʱ��
	 * @param cumulativeTime
	 * ��������ʼ��ǰ���ۼ�ʱ��
	 * @param cycle
	 * Ӳ���ۼ�ʱ������
	 */
	private void getData(String response,Date time,String cumulativeTime,String cycle){
		isSave = true;
		SimpleDateFormat formater1=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat formater=new SimpleDateFormat("MM/dd HH:mm");
		if(D2EConfigures.TEST){
			Log.e("getData------------->", "getData()");
			Log.e("Reponse------------->", ""+response);
			Log.e("time------------->", ""+formater1.format(time));
			Log.e("cumulativeTime========>", ""+cumulativeTime);
			Log.e("cycle=========>", ""+cycle);
		}
		
		
		
		reportItem = new MouseReportItem(strStreet, strPlaceName);
		reportItem.setStrMonitorMethodType(YTBaseApplication.MOUSE_METHOD_CAO);
		reportItem.setStrTagNum("-");
		reportItem.setStrMouseGetNum("-");
		if (mType != null && mType.length() > 0) {
			reportItem.setStrReportType(mType);
		} else {
			reportItem.setStrReportType("-");
		}
		if (mPerson != null && mPerson.length() > 0) {
			reportItem.setStrPerson(mPerson);
		} else {
			reportItem.setStrPerson("-");
		}
		if (mAreaID != null && mAreaID.length() > 0) {
			reportItem.setStrAreaID(mAreaID);
		} else {
			reportItem.setStrAreaID("-");
		}

		if (mPlaceType != null && mPlaceType.length() > 0) {
			reportItem.setmPlaceType(mPlaceType);
		} else {
			reportItem.setmPlaceType("-");
		}
//		response="55aa 5c 0c 6630003a 7e06480000001e7e0629020509087e0649020509087e0627020509087e0637020509087e0647020509087e064f020509087e062f02050a087e064f020509077e062f020609087e0625030509087e062503060908";
		
		String tmp = response.substring(16, response.length());
		String numb = response.substring(6,8);
		String tag  = response.substring(8, 16);
		reportItem.setStrTagNum(tag);
		
		int total = 0;
		try{
			total = Integer.parseInt(numb, 16);
		}
		catch(Exception e){}
		/**
		 * �ڵ�����
		 */
		int mouseComNum=0;
		
		
		boolean coverState = false;
		
		for(int i = 0; i < total; i ++){
			String sub  = tmp.substring(0, 14);//7e........
			String code = sub.substring(4,6);
								
			if(code.equalsIgnoreCase("27")){//ȱ��
				mIsLessElec=true;
				reportItem.setLess(true);
			}
			else if(code.equalsIgnoreCase("4F")){//�ڵ�
				
				Calendar calendar=Calendar.getInstance();
				calendar.set(time.getYear(), time.getMonth(), time.getDate(), time.getHours(), time.getMinutes(), time.getSeconds());
				
				mouseComNum=mouseComNum+1;
				//7e 06 29 0205 09 08
				int d = 0;
				int h = 0;
				int m = 0;
				try{
					d = Integer.parseInt(sub.substring(6, 10), 16);
					
				}
				catch(Exception ex){}

				try{
					h = Integer.parseInt(sub.substring(10, 12), 16);
				}
				catch(Exception ex){}

				try{
					m = Integer.parseInt(sub.substring(12, 14), 16);
				}
				catch(Exception ex){}

				Date date=setTime(calendar, d, h, m);
				
				CaoItem item=new CaoItem(CaoItem.TYPE_COVER);
				item.setStrTime(formater.format(date) + "");
				item.setStrKeep(getResources().getString(R.string.txt_zhedang));        //�ڵ�
				reportItem.getmCaoList().add(item);
				
				reportItem.setmCoverNum(mouseComNum);
				
				coverState = true;
			}
			else if(code.equalsIgnoreCase("2F")){//ȡ���ڵ�
				
				Calendar calendar=Calendar.getInstance();
				calendar.set(time.getYear(), time.getMonth(), time.getDate(), time.getHours(), time.getMinutes(), time.getSeconds());
				int d = 0;
				int h = 0;
				int m = 0;
				try{
					d = Integer.parseInt(sub.substring(6, 10), 16);
				}
				catch(Exception ex){}

				try{
					h = Integer.parseInt(sub.substring(10, 12), 16);
				}
				catch(Exception ex){}

				try{
					m = Integer.parseInt(sub.substring(12, 14), 16);
				}
				catch(Exception ex){}

				Date date=setTime(calendar, d, h, m);
				
				CaoItem item=new CaoItem(CaoItem.TYPE_COVER);
				item.setStrTime(formater.format(date) + "");
				item.setStrKeep(getResources().getString(R.string.txt_yichu)); //�ڵ�
				reportItem.getmCaoList().add(item);	
				
				coverState = false;
			}
			else if(code.equalsIgnoreCase("25")){//����
				Calendar calendar=Calendar.getInstance();
				calendar.set(time.getYear(), time.getMonth(), time.getDate(), time.getHours(), time.getMinutes(), time.getSeconds());
				mMouseComeNum++;
				int d = 0;
				int h = 0;
				int m = 0;
				try{
					d = Integer.parseInt(sub.substring(6, 10), 16);
				}
				catch(Exception ex){}

				try{
					h = Integer.parseInt(sub.substring(10, 12), 16);
				}
				catch(Exception ex){}

				try{
					m = Integer.parseInt(sub.substring(12, 14), 16);
				}
				catch(Exception ex){}

				
				CaoItem item=new CaoItem(CaoItem.TYPE_MOUSE);

				Date date =setTime(calendar, d, h, m);
				item.setStrTime(formater.format(date) + "");
				item.setStrKeep(getResources().getString(R.string.txt_mouse_come));        
				reportItem.getmMouseComeList().add(item);
			}
			tmp = tmp.substring(14);
		}
		
		//�������֮���ж��Ƿ�ȱ��
		if(mIsLessElec){
			iv_cao_battery.setBackgroundResource(R.drawable.less);
			AnimationDrawable animationDrawable = (AnimationDrawable) iv_cao_battery.getBackground(); 
			animationDrawable.start();

		}else{
			iv_cao_battery.setBackgroundResource(R.drawable.no_less);
			AnimationDrawable animationDrawable = (AnimationDrawable) iv_cao_battery.getBackground(); 
			animationDrawable.start();

		}
		
		
		if(coverState){
			iv_cao_cover.setBackgroundResource(R.drawable.cover);
			AnimationDrawable animationDrawable1 = (AnimationDrawable) iv_cao_cover.getBackground(); 
			animationDrawable1.start();

		}
		else {
			iv_cao_cover.setBackgroundResource(R.drawable.nocover);
			AnimationDrawable animationDrawable1 = (AnimationDrawable) iv_cao_cover.getBackground(); 
			animationDrawable1.start();

		}
		
		//modify by shawn 2012-12-14 Begin
		//�������ô����Ǳ����ۼӵ�����
		tv_cao_sum.setText(String.valueOf(mMouseComeNum));
		reportItem.setStrMouseGetNum(String.valueOf(mMouseComeNum));
		
		//End
		reportItem.setCoverState(coverState);
		reportItem.setCoverTimes(mouseComNum);
		
		TextView tv_sums = (TextView)findViewById(R.id.iv_cao_cover_sum);
		tv_sums.setText(mouseComNum + "");
		
		PHCConfig.mouseCaoReportList.add(reportItem);
		
	}
	
	

	@Override
	public void taskSuccess(Object... param) {
		
		dismissProcessDialog();
		
		if (D2EConfigures.TEST) {
			Log.e("taskSuccess=========>", "taskSuccess()");
		}
		try {
			int values = ((Integer) param[0]).intValue();
			if (values == YTBaseService.BT_SERVICE_INT) {
				String response = (String) param[2];
				
				if(response == null || response.length() == 0){
					return;
				}
				if(D2EConfigures.TEST){
					Log.e("taskSuccess=========>", "taskSuccess()�� " + response);
				}
				mSaveResponse=response;
				mSaveResponse="55aa5c0c6630003a7e06480000001e7e0649020009087e0629020509087e0649020509087e0627020509087e0637020509087e0647020509087e064f020509087e062f02050a087e064f020509077e062f020609087e0625030509087e062503060908";
				if(D2EConfigures.TEST){
//					response="55aa5c0c6630003a7e06480000001e7e0629020509087e0649020509087e0627020509087e0637020509087e0647020509087e064f020509087e062f02050a087e064f020509077e062f020609087e0625030509087e062503060908";
					Log.e("response=======>", ""+(response));
					Log.e("isFirstElectric========>", ""+(isFirstElectric));
					Log.e("mSaveResponse===========>", ""+mSaveResponse);
				}
				//����ǳ�ʼ������ͷ�����ͬ������
				if(isFirstElectric){
					//�������ݻ�ȡ�����豸���������ڵ��ۼ�ʱ��
					String time=getAccumuTime(mSaveResponse);
					
					String[] params = new String[]{
			        		"FunType", "setPowerOn", 
			        		"TagNum",response.substring(8, 16),
			        		"DayMarkings",time
			        		};
			        YTStringHelper.array2string(params);
			        long id = YTBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-main/webservices/get/awh.html", params, PHCChaoShengBo.this.getClass().getName(),PHCConfig.TASK_FIRST_ELECTRIC);		        
			        setCurTaskID(id);
					showWaitDialog(getResources().getString(R.string.handling_wait), R.style.dialog, R.layout.progress_dialog, R.id.wait_image, R.id.wait_message);
					return;
				}else{
					
					//��ȡʱ��
					String[] params =new String[]{
							"FunType","getPowerOn",
							"TagNum",response.substring(8, 16)
						};
						
					YTStringHelper.array2string(params);
			        long id = YTBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-main/webservices/get/awh.html", params, PHCChaoShengBo.this.getClass().getName(),PHCConfig.TASK_GET_ELECTRIC);		        
			        setCurTaskID(id);
					showWaitDialog(getResources().getString(R.string.handling_wait), R.style.dialog, R.layout.progress_dialog, R.id.wait_image, R.id.wait_message);
				}
			}
			if (values == YTBaseService.HTTP_SERVICE_INT) {
				JJTask task = (JJTask) param[1];
				String response=null;
				try{
					InputStream is=task.getInputStream();
					byte[] bytes=YTNetUtil.readByByte(is, -1);
					response=new String(bytes,"UTF-8");
					
					
					JSONObject jo=new JSONObject(response);
					if(D2EConfigures.TEST){
						Log.e("HTTP_SERVICE_INT========>", ""+(YTBaseService.HTTP_SERVICE_INT));
						Log.e("response=========>", ""+response);
						Log.e("jo========>", ""+jo);
					}
					String error=null;
					if(jo.has("error")){
						error=jo.getString("error");
						getApp().showMessage(error);
						return;
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				
				if (task.getTaskId() == PHCConfig.TASK_INSERT_STREE) {
					
					try {
						InputStream ins = task.getInputStream();
						byte[] bytes = YTNetUtil.readByByte(ins, -1);

						String tmp = new String(bytes, "UTF-8");
						ins.close();

						Log.e("OUT 20sss >>>>>> ", tmp);
					}

					catch (Exception e) {
					}
					
				}
				//��ȡ�ϵ�����
				else if(  task.getTaskId() == PHCConfig.TASK_GET_ELECTRIC){
					try {
						if(D2EConfigures.TEST){
							Log.e("TASK_GET_ELECTRIC------>", "TASK_GET_ELECTRIC");
							Log.e("response===========>", response);
						}
						
						btn_cao_scann.setEnabled(false);
//						InputStream ins = task.getInputStream();
//						byte[] bytes = YTNetUtil.readByByte(ins, -1);
//						if(D2EConfigures.TEST){
//							Log.e("bytes======>", ""+bytes.length);
//						}
//
//						String tmp =null;
//						try{
//							tmp= new String(bytes, "UTF-8");
//						}catch(Exception e){
//							tmp=new String(bytes);
//						}
//						ins.close();
						
						if(D2EConfigures.TEST){
							Log.e("TASK_GET_ELECTRIC������", ""+response);
						}
						String strTime=null;
						String strCumulativeTime=null;
						String strCycle=null;
						if(response!=null&&response.length()>0){
							
							if(D2EConfigures.TEST){
								Log.e("=============", "=================");
								Log.e("OUT 20sss �ϵ緵������>>>>>> ", response);
								Log.e("���������--------------��", ""+mSaveResponse);
							}
							
							Date mDate = null;
							try{
								SimpleDateFormat formatter2 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
								JSONObject jo=new JSONObject(response);
								
								if(jsonHasStringContent(jo,"PowerOnTime")){
									strTime=jo.getString("PowerOnTime");
								}
								if(jsonHasStringContent(jo,"DayMarkings")){
									strCumulativeTime=jo.getString("DayMarkings");
								}
								if(jsonHasStringContent(jo,"Num")){
									strCycle=jo.getString("Num");
								}
								mDate=formatter2.parse(strTime);
								
							}
							catch(Exception ex){
								ex.printStackTrace();
								mDate = new Date();
							}
							
							
							getData(mSaveResponse,mDate,strCumulativeTime,strCycle);
						}else{
							Date date=new Date();
							getData(mSaveResponse,date,strCumulativeTime,strCycle);
						}
					}

					catch (Exception e) {
						e.printStackTrace();
					}
				}
				//��һ���ϵ�
				else if(task.getTaskId() == PHCConfig.TASK_FIRST_ELECTRIC){
					if(D2EConfigures.TEST){
						Log.e("TASK_FIRST_ELECTRIC", "TASK_FIRST_ELECTRIC");
						Log.e("response============>", response);
					}
					if(response!=null&&response.length()>0){
						
					}
					try {
					
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
	
	
	private boolean jsonHasStringContent(JSONObject jo, String str)
			throws JSONException {

		if (jo.has(str)) {
			if (jo.getString(str) != null && jo.getString(str).length() > 0) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * �������ݻ�ȡ�����豸���������ڵ��ۼ�ʱ��
	 * @param mSaveResponse2
	 * @return
	 */
	private String getAccumuTime(String response) {
		if(D2EConfigures.TEST){
			Log.e("getAccumuTime", "getAccumuTime");
			Log.e("response=======>", ""+response);
		}
		HashMap<String,String> mMap=new HashMap<String,String>();
		//�ۼ�ʱ���
		String result=null;
		//���ݳ���
		String lon=response.substring(6, 8);
		
		//�������ݵ�����
		int mKey=0;
		
		//��������Ϣ
		String data=response.substring(16, response.length());
		
		if(D2EConfigures.TEST){
			Log.e("���ݳ���:", ""+lon);
			Log.e("���ݳ���:", ""+(Integer.parseInt(lon,16)));
			Log.e("��������Ϣ", ""+data);
		}
		int total=0;
		try{
			total=Integer.parseInt(lon,16);
		}catch(Exception e){}
		for(int i=0;i<total;i++){
			//����������Ϣ
			String dataTmp=data.substring(0, 14);//7e...
			//����������
			String code=data.substring(4, 6);
			if(D2EConfigures.TEST){
				Log.e("����������Ϣ:", dataTmp);
				Log.e("����������:", code);
			}
			//�ۼ�ʱ�䣬�п����ж����ȥ���һ��
			if(code.equalsIgnoreCase("49")){
				if(D2EConfigures.TEST){
					Log.e("��49���ݣ�", dataTmp);
				}
				int d=0;
				int h=0;
				int m=0;
				try{
					d=Integer.parseInt((dataTmp.substring(6, 10)),16);
					h=Integer.parseInt((dataTmp.substring(10, 12)),16);
					m=Integer.parseInt((dataTmp.substring(12, 14)),16);
				}catch(Exception  e){
					e.printStackTrace();
				}
				
				
				mKey=i;
				String index=String.valueOf(i);
				String diff= d+"-"+h+"-"+m;
				if(D2EConfigures.TEST){
					Log.e("ʱ�䣺", "d:"+d+" h:"+h+" m:"+m);
					Log.e("diff=======>", ""+diff);
					Log.e("index", index);
				}
				
				mMap.put(index, diff);
			}
			data=data.substring(14);
		}
		String key=String.valueOf(mKey);
		if(D2EConfigures.TEST){
			Log.e("mKey", ""+key);
		}
		result=mMap.get(key);
		return result;
	}

	private Date setTime(Calendar calendar,int d,int h,int m){
//		Date date=calendar.getTime();
		calendar.add(Calendar.DATE, d);
		calendar.add(Calendar.HOUR, h);
		calendar.add(Calendar.MINUTE, m);
		return calendar.getTime();
	}
	

	@Override
	public void taskFailed(Object... param) {
		hideWaitDialog();
		dismissProcessDialog();
		int values = ((Integer) param[0]).intValue();
		if (values == YTBaseService.BT_SERVICE_INT) {
			int state = ((Integer) param[1]).intValue();
			if (state == SOPBluetoothService.BT_DISCONNECT) {
				
			}
			else if(state == SOPBluetoothService.BT_CONNECT){
				
			}
			else {
				((YTBaseApplication) getApplication()).showMessage(getResources()
						.getString(R.string.bt_failed));				
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
