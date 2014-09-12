package com.vsi.page;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mobilercn.base.YTBaseActivity;
import com.mobilercn.base.YTBaseApplication;
import com.vsi.phc.R;
import com.vsi.phc.R.color;
import com.vsi.config.D2EConfigures;
import com.vsi.config.PHCConfig;
import com.vsi.data.MouseReportItem;

/**
 *蟑螂报表数据录入界面 
 */
public class PHCKrockroachPage extends YTBaseActivity {
	private String mOrgID;
	private String mAreaID;
	private String mType;
	private String mPerson;
	/**
	 * 蟑螂监测场所类型输入框
	 */
	private AutoCompleteTextView et_cockroach_place_type;
	/**
	 * 蟑螂监测场所名称输入框
	 */
	private EditText edit_sticky_cockroach_place_name;
	/**
	 * 蟑螂监测场所类型弹出框
	 */
	private PopupWindow cockroachPop;
	private boolean isShowCockroachPop=false;
	/**
	 * 蟑螂监测下拉列表
	 */
	private ListView cockroachListView;
	/**
	 * 蟑螂监测场所类型数据列表
	 */
	private List<String> cockroachPlaceTypes;
	/**
	 * 蟑螂监测场所类型数据适配器
	 */
	private CockRoachPlaceTypePopupAdapter cockroachPlaceTypePopupAdapter;
	/**
	 * 蟑螂监测备注
	 */
	
	private EditText edit_cockroach_remark;
	/**
	 * 场所类型下拉图片按钮
	 */
	private ImageButton ibtn_dropDown_cockroach_place;
	/**
	 * 街道名称输入框
	 */
	private EditText mStreetEdit;
	/**
	 * 粘蟑纸数
	 */
	private EditText mStickyKrockroachPaperNumberEdit;
	/**
	 * 阳性粘蟑纸数
	 */
	private EditText mPosStickyKrockroachPapeNumberEdit;
	/**
	 * 粘蟑只数
	 */
	private EditText mStickyKrockroachNumber;
	/**
	 * 密度(粘捕只数/张)
	 */
	/**
	 * 当前场所类型整型值
	 */
	private int mCurrentPlaceInt;
	private EditText mKrockroachDensityEdit;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.phc_input_krockroach_page);
        Window window = getWindow();
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.public_title_bar);
                
        mStreetEdit = (EditText)findViewById(R.id.edit_street);
        mStickyKrockroachPaperNumberEdit = (EditText)findViewById(R.id.edit_sticky_krockroach_paper_number);
        mPosStickyKrockroachPapeNumberEdit = (EditText)findViewById(R.id.edit_pos_sticky_krockroach_paper_number);
        mStickyKrockroachNumber = (EditText)findViewById(R.id.edit_sticky_krockroach_number);
        mKrockroachDensityEdit = (EditText)findViewById(R.id.edit_krockroach_density);
        et_cockroach_place_type=(AutoCompleteTextView) this.findViewById(R.id.et_cockroach_place_type);
        edit_sticky_cockroach_place_name=(EditText) this.findViewById(R.id.edit_sticky_cockroach_place_name);
        edit_cockroach_remark=(EditText) this.findViewById(R.id.edit_cockroach_remark);
        ibtn_dropDown_cockroach_place=(ImageButton) this.findViewById(R.id.ibtn_dropDown_cockroach_place);
        
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.cockroach_monitor_place, R.layout.simple_dropdown_item_1line);
        et_cockroach_place_type.setAdapter(adapter);
        et_cockroach_place_type.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
        			long id) {
        		mCurrentPlaceInt=position;
        		if(D2EConfigures.TEST){
        			Log.e("mCurrentPlaceInt------------>", ""+mCurrentPlaceInt);
        		}
        	}
		});
        ibtn_dropDown_cockroach_place.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(et_cockroach_place_type!=null&&et_cockroach_place_type.length()>0){
					et_cockroach_place_type.setText("");
				}
				
				et_cockroach_place_type.showDropDown();
			}
		});
        
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        mOrgID=bundle.getString("OrgID");
        mAreaID=bundle.getString("AreaID");
        mType=bundle.getString("Type");
        mPerson=bundle.getString("Person");
        
        if (D2EConfigures.TEST) {
			Log.e("AreaID----------->", "" + bundle.getString("AreaID"));
			Log.e("OrgID----------->", "" + bundle.getString("OrgID"));
			Log.e("Type----------->", "" + bundle.getString("Type"));
			Log.e("mPerson----------->", "" + bundle.getString("Person"));
		}
        
        
        //启动界面的时候就启动算密度的线程
        handler.postDelayed(calculateThread, 1000);
        
        Button btnback = (Button)findViewById(R.id.btn_back);
        btnback.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				AlertDialog.Builder builder = new AlertDialog.Builder(PHCKrockroachPage.this);
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

        Button btnsave = (Button)findViewById(R.id.btn_save);
        btnsave.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				try{
					String mStreet=mStreetEdit.getText().toString();
					String mPlaceType=et_cockroach_place_type.getText().toString();
					String mPlace=edit_sticky_cockroach_place_name.getText().toString();
					String mPaperNum=mStickyKrockroachPaperNumberEdit.getText().toString();
					String mPositivePaperNum=mPosStickyKrockroachPapeNumberEdit.getText().toString();
					String mRoachNum=mStickyKrockroachNumber.getText().toString();
					String mRoachDensity=mKrockroachDensityEdit.getText().toString();
					String mMemo=edit_cockroach_remark.getText().toString();
					//add by shawn 2012-9-21 Begin
					//界面信息录入完整性判断
					if(mStreet == null || !(mStreet.length() > 0)){
						((YTBaseApplication) getApplication())
						.showMessage(getResources().getString(R.string.cockroach_street_less));
						return;
					}
					if(mPlaceType == null || !(mPlaceType.length() > 0)){
						((YTBaseApplication) getApplication())
						.showMessage(getResources().getString(R.string.cockroach_place_type_less));
						return;
					}
					if(mPlace == null || !(mPlace.length() > 0)){
						((YTBaseApplication) getApplication())
						.showMessage(getResources().getString(R.string.cockroach_place_less));
						return;
					}
					if(mPaperNum == null || !(mPaperNum.length() > 0)){
						((YTBaseApplication) getApplication())
						.showMessage(getResources().getString(R.string.cockroach_paper_num_less));
						return;
					}
					if(mPositivePaperNum == null || !(mPositivePaperNum.length() > 0)){
						((YTBaseApplication) getApplication())
						.showMessage(getResources().getString(R.string.cockroach_pos_paper_num_less));
						return;
					}
					if(mRoachNum == null || !(mRoachNum.length() > 0)){
						((YTBaseApplication) getApplication())
						.showMessage(getResources().getString(R.string.cockroach_catch_num_less));
						return;
					}
					
					//End
					
					
					
					MouseReportItem mouseReportItem=null;
					if(mStreet!=null&&mStreet.length()>0
							&&mPlace!=null&&mPlace.length()>0){
						mouseReportItem=new MouseReportItem(mStreet,mPlace);
					}
					
					if(mType!=null&&mType.length()>0){
						mouseReportItem.setStrReportType(mType);
					}else{
						mouseReportItem.setStrReportType("N/A");
					}
					if(mPerson!=null&&mPerson.length()>0){
						mouseReportItem.setStrPerson(mPerson);
					}else{
						mouseReportItem.setStrPerson("N/A");
					}
					
					mouseReportItem.setStrAreaID(mAreaID);
					
					if(mPlaceType!=null&&mPlaceType.length()>0){
						mouseReportItem.setmPlaceType(mPlaceType);
					}else{
						mouseReportItem.setmPlaceType("N/A");
					}
					
					
					if(mPaperNum!=null&&mPaperNum.length()>0){
						mouseReportItem.setStrKrockroachStickyPaperNumber(mPaperNum);
					}else{
						mouseReportItem.setStrKrockroachStickyPaperNumber("-");
					}
					if(mPositivePaperNum!=null&&mPositivePaperNum.length()>0){
						mouseReportItem.setStrKrockroachStickyPaperPosNumber(mPositivePaperNum);
					}else{
						mouseReportItem.setStrKrockroachStickyPaperPosNumber("-");
					}
					if(mRoachNum!=null&&mRoachNum.length()>0){
						mouseReportItem.setStrKrockroachStickyNumber(mRoachNum);
					}else{
						mouseReportItem.setStrKrockroachStickyNumber("-");
					}
					if(mRoachDensity!=null&&mRoachDensity.length()>0){
						mouseReportItem.setStrKrockroachDensity(mRoachDensity);
					}else{
						mouseReportItem.setStrKrockroachDensity("-");
					}
					if(mMemo!=null&&mMemo.length()>0){
						mouseReportItem.setmTotalMemo(mMemo);
					}else{
						mouseReportItem.setmTotalMemo("-");
					}
					PHCConfig.cockRoachReportList.add(mouseReportItem);
					PHCKrockroachPage.this.finish();
					
					if(mouseReportItem!=null){
						mouseReportItem.printReprot();
					}
				}catch(Exception e){
					
				}finally{
					
				}
			}
        	
        });

        Button btnreset = (Button)findViewById(R.id.btn_reset);
        btnreset.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
		        
		        mStickyKrockroachNumber.setText("");
		        et_cockroach_place_type.setText("");
		        edit_sticky_cockroach_place_name.setText("");
		        edit_cockroach_remark.setText("");
		        mStreetEdit.setText("");
		        mStickyKrockroachPaperNumberEdit.setText("");
		        mPosStickyKrockroachPapeNumberEdit.setText("");
		        mPosStickyKrockroachPapeNumberEdit.setText("");
		        mKrockroachDensityEdit.setText("");
			}
        	
        });

    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	handler.removeCallbacks(calculateThread);
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	handler.removeCallbacks(calculateThread);
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
		
	}

	@Override
	public void taskFailed(Object... param) {
		
	}

	@Override
	public void taskProcessing(Object... param) {
		
	}
	
	
	
	Handler handler=new Handler();
	Runnable calculateThread=new Runnable(){
		int intPaperNum=-1;
		int intPositivePaperNum=-1;
		int intRoachNum=-1;
		@Override
		public void run() {
			intPaperNum=-1;
			intPositivePaperNum=-1;
			intRoachNum=-1;
			double density=0.0;

			String mPaperNum=mStickyKrockroachPaperNumberEdit.getText().toString();//粘
			String mPositivePaperNum=mPosStickyKrockroachPapeNumberEdit.getText().toString();//阳性
			String mRoachNum=mStickyKrockroachNumber.getText().toString();//
			

			
			if(mPaperNum!=null&&mPaperNum.length()>0){
				try{
					intPaperNum=Integer.valueOf(mPaperNum);					
				}
				catch(Exception e){}				
			}
			if(mPositivePaperNum!=null&&mPositivePaperNum.length()>0){
				try{
					intPositivePaperNum=Integer.valueOf(mPositivePaperNum);					
				}
				catch(Exception e){}
			}
			
			
			if(mRoachNum!=null&&mRoachNum.length()>0){
				try{
					intRoachNum=Integer.valueOf(mRoachNum);					
				}
				catch(Exception e){}
				
			}
			
			if(intPaperNum < intPositivePaperNum){
				getApp().showMessage(getResources().getString(R.string.sun_no_lagrge_prompt));
				mPosStickyKrockroachPapeNumberEdit.setText("");
				mStickyKrockroachNumber.setText("");
				intPositivePaperNum = -1;
				intRoachNum = -1;
			}
			
			if(intPositivePaperNum >= 0 && intRoachNum >= 0 ){
				//modify by shawn 2012-10-10 Begin
				//密度算法修改，不四舍五入，直接去除尾数
				float i=intRoachNum;
				float j=intPositivePaperNum;
				mKrockroachDensityEdit.setText(com.mobilercn.util.NumberUtils.getPrecentPoint(i, j, 2));
				//End
			}
			handler.postDelayed(calculateThread, 1000);
		}
		
	};
	
	/**
	 *蟑螂监测数据适配器 
	 *
	 */
	class CockRoachPlaceTypePopupAdapter extends BaseAdapter{
    	private LayoutInflater layoutInflater;
    	private Context context ;
    	public CockRoachPlaceTypePopupAdapter(){
    		
    	}
    	public CockRoachPlaceTypePopupAdapter(Context context){
    		this.context=context;
    	}
		@Override
		public int getCount() {
			return cockroachPlaceTypes.size();
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			Holder holder;
			final String area=cockroachPlaceTypes.get(position);
			if(convertView==null){
				layoutInflater=LayoutInflater.from(context);
				convertView=layoutInflater.inflate(R.layout.area_item, null);
				holder=new Holder();
				holder.tv=(TextView) convertView.findViewById(R.id.tv_detailAreaName);
				convertView.setTag(holder);
			}else{
				holder=(Holder) convertView.getTag();
			}
			if(holder!=null){
				convertView.setId(position);
				holder.setId(position);
				holder.tv.setText(area);
				holder.tv.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View arg0, MotionEvent arg1) {
						cockroachPop.dismiss();
						isShowCockroachPop=false;
						et_cockroach_place_type.setText(area);
						return true;
					}
				});
			}
			return convertView;
		}
    	class Holder{
    		TextView tv;
    		void setId(int position){
    			tv.setId(position);
    		}
    	}
    }

}
