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
import android.widget.AdapterView.OnItemClickListener;
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

import com.mobilercn.base.YTBaseActivity;
import com.mobilercn.base.YTBaseApplication;
import com.vsi.phc.R;
import com.vsi.phc.R.color;
import com.vsi.config.D2EConfigures;
import com.vsi.config.PHCConfig;
import com.vsi.data.MouseReportItem;

/**
 * 蚊类数据录入界面
 * @author ShawnXiao
 *
 */
public class PHCMosquitoPage extends YTBaseActivity {
	private String mOrgID;
	private String mAreaID;
	private String mType;
	private String mPerson;
	/**
	 * 场所名称容器
	 */
	private RelativeLayout panel_mosqito_palce_name;
	/**
	 * 单位居民区外环境
	 */
	private LinearLayout panel_mosquito_out;
	/**
	 * 池塘或河流等大中型水体
	 */
	private LinearLayout panel_mosquito_in;
	/**
	 * 场所名称
	 */
	private EditText edit_mosqito_palce_name;
	/**
	 * 蚊监测场所类型编辑框
	 */
	private AutoCompleteTextView et_mosquito_place_type;
	/**
	 * 蚊监测场所类型下拉按钮
	 */
	private ImageButton ibtn_dropDown_mosquito_place;
	/**
	 * 蚊监测场所类型弹出窗体
	 */
	private PopupWindow mosiquitoPlaceTypePop;
	/**
	 * 是否要显示蚊监测场所类型弹出窗体
	 */
	private boolean isShowMosquitoPlaceTypePop;
	/**
	 * 蚊监测场所类型列表视图
	 */
	private ListView mMosquitoPlaceTypeListView;
	/**
	 * 蚊监测场所类型适配器
	 */
	private MosquitoPlaceTypeAdapter mosquitoPlaceTypeAdapter;
	/**
	 * 蚊监测场所类型列表
	 */
	private static List<String> mosquitoPlaceTypeList;
	/**
	 * 蚊监测地点类型编辑框
	 */
	private AutoCompleteTextView et_mosquito_monitor_place;
	/**
	 * 蚊监测地点下拉按钮
	 */
	private ImageButton ibtn_dropDown_mosquito_monitor_place;
	/**
	 * 蚊监测地点类型弹出窗
	 */
	private PopupWindow mosquitoMonitorPlacePop;
	/**
	 * 蚊监测地点弹出窗列表视图
	 */
	private ListView mosquitoMonitorPlaceListView;
	/**
	 * 蚊监测地点数据适配器
	 */
	private MosqitoMonitorPlaceAdapter mosiqitoMonitorPlaceAdapter;
	/**
	 * 是否显示蚊监测地点弹出窗体
	 */
	private boolean isShowMosquitoMonitorPlace=false;
	/**
	 * 蚊监测地点列表
	 */
	private static List<String> mosquitoMonitorPlaceList;
	/**
	 * 蚊监测地点累
	 */
	/**
	 * 街道名称输入框
	 */
	private EditText mStreeEdit;
	/**
	 * 积水容器(下水道)数
	 */
	private EditText mPondEdit;
	/**
	 * 阳性数输入框
	 */
	private EditText mPosPondEdit;
	/**
	 * 鼠密度输入框
	 */
	private EditText mDensPondEdit;
	/**
	 * 采集勺数
	 */
	private EditText mCollSpooEdit;
	/**
	 * 阳性勺数
	 */
	private EditText mPosSpooEdit;
	/**
	 * 密度(阳性勺数/总勺数)
	 */
	private EditText mDensiSpooEdit;
	
	/**
	 * 外环境备注
	 */
	private EditText edit_out_remark;
	/**
	 * 大型水体备注
	 */
	private EditText edit_in_remark;
	/**
	 * 单位居民区外环境监测方法
	 */
	private int intMethodOut=0;
	/**
	 * 池塘或河流等大中型水体监测方法
	 */
	private int intMethodRiver=1;
	/**
	 * 当前监测方法
	 */
	private int mCurrentMosqitoMonitorMethod;
	
	
	/**
	 * 单位居民区外环境场所类型下拉框数据适配器
	 */
	ArrayAdapter adapterPlaceOut;
	/**
	 * 池塘或河流等大中型水体场所类型下拉框数据适配器
	 */
	ArrayAdapter adapterPlaceRiver;
	
	private boolean isSave;
	
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.phc_input_mosquito_page);
        Window window = getWindow();
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.public_title_bar);
                
        
        et_mosquito_monitor_place=(AutoCompleteTextView) this.findViewById(R.id.et_mosquito_monitor_place);
        ibtn_dropDown_mosquito_monitor_place=(ImageButton) this.findViewById(R.id.ibtn_dropDown_mosquito_monitor_place);
        mStreeEdit = (EditText)findViewById(R.id.edit_street);
        et_mosquito_place_type=(AutoCompleteTextView) this.findViewById(R.id.et_mosquito_place_type);
        ibtn_dropDown_mosquito_place=(ImageButton) this.findViewById(R.id.ibtn_dropDown_mosquito_place);
        edit_mosqito_palce_name=(EditText) this.findViewById(R.id.edit_mosqito_palce_name);
        panel_mosquito_out=(LinearLayout) this.findViewById(R.id.panel_mosquito_out);
        panel_mosquito_in=(LinearLayout) this.findViewById(R.id.panel_mosquito_in);
        panel_mosqito_palce_name=(RelativeLayout) this.findViewById(R.id.panel_mosqito_palce_name);
        mPondEdit = (EditText)findViewById(R.id.edit_pond_num);
        mPosPondEdit = (EditText)findViewById(R.id.edit_pos_pond);
        mDensPondEdit = (EditText)findViewById(R.id.edit_desi_pond);
        mCollSpooEdit = (EditText)findViewById(R.id.edit_coll_spoo);
        mPosSpooEdit = (EditText)findViewById(R.id.edit_pos_spoo);
        mDensiSpooEdit = (EditText)findViewById(R.id.edit_densi_spoo);
        edit_out_remark=(EditText) this.findViewById(R.id.edit_out_remark);
        edit_in_remark=(EditText) this.findViewById(R.id.edit_in_remark);
        
        adapterPlaceOut=ArrayAdapter.createFromResource(PHCMosquitoPage.this, R.array.mosquito_place_out, R.layout.simple_dropdown_item_1line);
        adapterPlaceRiver=ArrayAdapter.createFromResource(PHCMosquitoPage.this, R.array.mosquito_place_river,R.layout.simple_dropdown_item_1line);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.mosquito_monitor_method, R.layout.simple_dropdown_item_1line);
        et_mosquito_monitor_place.setAdapter(adapter);
        et_mosquito_monitor_place.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
        			long id) {
        		mCurrentMosqitoMonitorMethod=position;
        		//这里要判断选择的是那种监测方法
        		
        		if(mCurrentMosqitoMonitorMethod==intMethodOut){
        			//1、如果选择的是单位居民区外环境监测方法，则把单位居民区外环境的界面显示，把其他界面隐藏
        			panel_mosquito_out.setVisibility(View.VISIBLE);
            		panel_mosquito_in.setVisibility(View.GONE);
            		//2、把相应的适配器数据设置为单位居民区外环境
            		et_mosquito_place_type.setAdapter(adapterPlaceOut);
            		//3、将场所名称容器的背景换下
            		panel_mosqito_palce_name.setBackgroundResource(R.drawable.public_line_mid);
        			
        		}
        		
        		if(mCurrentMosqitoMonitorMethod==intMethodRiver){
        			//1、如果选择的是池塘或河流等大中型水体监测方法，则把池塘或河流等大中型水体的界面显示，把其他界面隐藏
        			panel_mosquito_out.setVisibility(View.GONE);
            		panel_mosquito_in.setVisibility(View.VISIBLE);
        			//2、把相应的适配器数据设置为池塘河流
            		et_mosquito_place_type.setAdapter(adapterPlaceRiver);
            		//3、将场所名称容器的背景换下
            		panel_mosqito_palce_name.setBackgroundResource(R.drawable.public_line_mid);
        		}
        		
        	}
		});
        ibtn_dropDown_mosquito_monitor_place.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(et_mosquito_monitor_place!=null&&et_mosquito_monitor_place.length()>0){
					et_mosquito_monitor_place.setText("");
				}
				
				//kris
				if(et_mosquito_place_type!=null&&et_mosquito_place_type.length()>0){
					et_mosquito_place_type.setText("");
				}
				et_mosquito_monitor_place.showDropDown();
			}
		});
        
        ibtn_dropDown_mosquito_place.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(et_mosquito_place_type.getAdapter()==null){
					getApp().showMessage(getResources().getString(R.string.select_one_monitor_place));
				}
				if(et_mosquito_place_type!=null&&et_mosquito_place_type.length()>0){
					et_mosquito_place_type.setText("");
				}
				et_mosquito_place_type.showDropDown();
				
			}
		});
        
        
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        mOrgID=bundle.getString("OrgID");
        mAreaID=bundle.getString("AreaID");
        mType=bundle.getString("Type");
        mPerson=bundle.getString("Person");
        
        
        if(D2EConfigures.TEST){
        	Log.e("PHCMosquitoPage============>", "PHCMosquitoPage");
        	Log.e("AreaID----------->", ""+bundle.getString("AreaID"));
        	Log.e("OrgID----------->", ""+bundle.getString("OrgID"));
        	Log.e("Type----------->", ""+bundle.getString("Type"));
        	Log.e("Person----------->", ""+bundle.getString("Person"));
        	Log.e("PHCMosquitoPage============>", "PHCMosquitoPage");
        }
        
        
        //启动界面的时候就安排计算密度线程
        handler.postDelayed(calculateThread, 1000);
        
        
        Button btnback = (Button)findViewById(R.id.btn_back);
        btnback.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				
				
				AlertDialog.Builder builder = new AlertDialog.Builder(PHCMosquitoPage.this);
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
					String mMethod=et_mosquito_monitor_place.getText().toString();
					//如果街道名称和场所名称不为空，则新建一个报表项
					String mStreet=mStreeEdit.getText().toString();
					String mPlace=edit_mosqito_palce_name.getText().toString();
					String mPlaceType=et_mosquito_place_type.getText().toString();
					
					
					String mPond=mPondEdit.getText().toString();
					String mPosPond=mPosPondEdit.getText().toString();
					String mDensPond=mDensPondEdit.getText().toString();
					String mMemoOut=edit_out_remark.getText().toString();
					
					String mCollSpoo=mCollSpooEdit.getText().toString();
					String mPosSpoo=mPosSpooEdit.getText().toString();
					String mDensiSpoo=mDensiSpooEdit.getText().toString();
					String mMemoIn=edit_in_remark.getText().toString();
					
					
					//add by shawn 2012-9-21 Begin
					//界面信息录入完整性判断
					if(mMethod == null || !(mMethod.length() > 0)){
						((YTBaseApplication) getApplication())
						.showMessage(getResources().getString(R.string.mosquito_method_less));
						return;
					}
					if(mStreet == null || !(mStreet.length() > 0)){
						((YTBaseApplication) getApplication())
						.showMessage(getResources().getString(R.string.mosquito_street_less));
						return;
					}
					if(mPlaceType == null || !(mPlaceType.length() > 0)){
						((YTBaseApplication) getApplication())
						.showMessage(getResources().getString(R.string.mosquito_place_type_less));
						return;
					}
					if(mPlace == null || !(mPlace.length() > 0)){
						((YTBaseApplication) getApplication())
						.showMessage(getResources().getString(R.string.mosquito_place_less));
						return;
					}
					//这里要区分是哪一种监测方法
					if(mCurrentMosqitoMonitorMethod==intMethodOut){
						if(mPond == null || !(mPond.length() > 0)){
							((YTBaseApplication) getApplication())
							.showMessage(getResources().getString(R.string.mosquito_pond_less));
							return;
						}
						if(mPosPond == null || !(mPosPond.length() > 0)){
							((YTBaseApplication) getApplication())
							.showMessage(getResources().getString(R.string.mosquito_pos_pond_less));
							return;
						}
					}
					if(mCurrentMosqitoMonitorMethod==intMethodRiver){
						if(mCollSpoo == null || !(mCollSpoo.length() > 0)){
							((YTBaseApplication) getApplication())
							.showMessage(getResources().getString(R.string.mosquito_coll_spoo_less));
							return;
						}
						if(mPosSpoo == null || !(mPosSpoo.length() > 0)){
							((YTBaseApplication) getApplication())
							.showMessage(getResources().getString(R.string.mosquito_pos_spoo_less));
							return;
						}

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
						mouseReportItem.setStrReportType("-");
					}
					if(mPerson!=null&&mPerson.length()>0){
						mouseReportItem.setStrPerson(mPerson);
					}else{
						mouseReportItem.setStrPerson("-");
					}
					if(mAreaID!=null&&mAreaID.length()>0){
						mouseReportItem.setStrAreaID(mAreaID);
					}else{
						mouseReportItem.setStrAreaID("-");
					}
					
					
					if(mMethod!=null&&mMethod.length()>0){
						mouseReportItem.setMonitorMethod(mMethod);
					}else{
						mouseReportItem.setMonitorMethod("-");
					}
					if(mPlaceType!=null&&mPlaceType.length()>0){
						mouseReportItem.setmPlaceType(mPlaceType);
					}else{
						mouseReportItem.setmPlaceType("-");
					}
					
					
					if(mPond!=null&&mPond.length()>0){
						mouseReportItem.setStrMosquPond(mPond);
					}else{
						mouseReportItem.setStrMosquPond("-");
					}
					if(mPosPond!=null&&mPosPond.length()>0){
						mouseReportItem.setStrMosquPosPond(mPosPond);
					}else{
						mouseReportItem.setStrMosquPosPond("-");
					}
					if(mDensPond!=null&&mDensPond.length()>0){
						if(mDensPond.equalsIgnoreCase("0.00")){
							mouseReportItem.setStrMosquDensPond("-");
						}else{
							mouseReportItem.setStrMosquDensPond(mDensPond);
						}
						
					}else{
						mouseReportItem.setStrMosquDensPond("-");
					}
					if(mMemoOut!=null&&mMemoOut.length()>0){
						mouseReportItem.setStrOutMemo(mPond);
					}else{
						mouseReportItem.setStrOutMemo("-");
					}
					
					
					
					if(mCollSpoo!=null&&mCollSpoo.length()>0){
						mouseReportItem.setStrMosquCollSpoo(mCollSpoo);
					}else{
						mouseReportItem.setStrMosquCollSpoo("-");
					}
					if(mPosSpoo!=null&&mPosSpoo.length()>0){
						mouseReportItem.setStrMosquPosSpoo(mPosSpoo);
					}else{
						mouseReportItem.setStrMosquPosSpoo("-");
					}
					if(mDensiSpoo!=null&&mDensiSpoo.length()>0){
						if(mDensiSpoo.equalsIgnoreCase("0.00")){
							mouseReportItem.setStrMosquDensSpoo("-");
						}else{
							mouseReportItem.setStrMosquDensSpoo(mDensiSpoo);
						}
					}else{
						mouseReportItem.setStrMosquDensSpoo("-");
					}
					if(mMemoIn!=null&&mMemoIn.length()>0){
						mouseReportItem.setStrInMemo(mPond);
					}else{
						mouseReportItem.setStrInMemo("-");
					}
					
					if(mCurrentMosqitoMonitorMethod==intMethodOut){
						if(mMemoOut!=null&&mMemoOut.length()>0){
							mouseReportItem.setmTotalMemo(mMemoOut);
						}else{
							mouseReportItem.setmTotalMemo("-");
						}
						
					}
					if(mCurrentMosqitoMonitorMethod==intMethodRiver){
						if(mMemoIn!=null&&mMemoIn.length()>0){
							mouseReportItem.setmTotalMemo(mMemoIn);
						}else{
							mouseReportItem.setmTotalMemo("-");
						}
						
					}
					
					PHCConfig.mosquitoReportList.add(mouseReportItem);
					PHCMosquitoPage.this.finish();
					
					if(mouseReportItem!=null){
						mouseReportItem.printReprot();
					}
					isSave = true;
				}catch(Exception e){
					
				}finally{
					
				}
								
			}
        	
        });

        Button btnreset = (Button)findViewById(R.id.btn_reset);
        btnreset.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				edit_mosqito_palce_name.setText("");
				et_mosquito_place_type.setText("");
				et_mosquito_monitor_place.setText("");
				mStreeEdit.setText("");
				mPosSpooEdit.setText("");
				mPondEdit.setText("");
				mPosPondEdit.setText("");
				mDensPondEdit.setText("");
				mCollSpooEdit.setText("");
				mPosSpooEdit.setText("");
				mDensiSpooEdit.setText("");
				edit_out_remark.setText("");
				edit_in_remark.setText("");
			}
        	
        });

    }
    
    @Override
    protected void onPause() {
    	super.onPause();
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
		int intPond=-1;
		int intPosPond=-1;
		int intCollSpoo=-1;
		int intPosSpoo=-1;
		
		@Override
		public void run() {
			intPond=-1;
			intPosPond=-1;
			intCollSpoo=-1;
			intPosSpoo=-1;
			
			String mPond    =mPondEdit.getText().toString();
			String mPosPond =mPosPondEdit.getText().toString();
			
			String mCollSpoo=mCollSpooEdit.getText().toString();
			String mPosSpoo =mPosSpooEdit.getText().toString();
			
			double densityPond=0.0;
			double densitySpoo=0.0;
			DecimalFormat df=new DecimalFormat("0.00");
			
			if(et_mosquito_monitor_place.getText().toString().equalsIgnoreCase(getResources().getString(R.string.txt_outside_envi))){
				if(mPond!=null&&mPond.length() > 0){
					try{
					    intPond=Integer.valueOf(mPond);
					}
					catch(Exception e){}
				}
				
				if(mPosPond!=null&&mPosPond.length() > 0){
					try{
						intPosPond=Integer.valueOf(mPosPond);
					}
					catch(Exception e){}
					
					if(intPosPond > intPond){
						intPond=-1;
						intPosPond=-1;
						getApp().showMessage(getResources().getString(R.string.sum_no_lagre_jishui));
						mPosPondEdit.setText("");
					}
				}
				
				if(intPond > 0 && intPond >= intPosPond && intPosPond > 0){
					//modify by shawn 由于密度只要保留两位，不进位，后面舍弃
					//例如888/78687=0.011285218651111366   取值为： 1.12%
					float i=intPosPond;
					float j=intPond;
					mDensPondEdit.setText(com.mobilercn.util.NumberUtils.getPrecent(i, j, 2));
					//End
				}
				
			}
			
			if(et_mosquito_monitor_place.getText().toString().equalsIgnoreCase("水体")){
				if(mCollSpoo!=null&&mCollSpoo.length()>0){
					try{
						intCollSpoo=Integer.valueOf(mCollSpoo);						
					}
					catch(Exception ex){}
				}
				if(mPosSpoo!=null&&mPosSpoo.length()>0){
					try{
						intPosSpoo=Integer.valueOf(mPosSpoo);						
					}
					catch(Exception ex){}
					if(intCollSpoo < intPosSpoo){
						getApp().showMessage(getResources().getString(R.string.sum_no_large_caijishu));
						mPosSpooEdit.setText("");
						intCollSpoo=-1;
						intPosSpoo=-1;
					}
				}
				if(intPosSpoo > 0 && intCollSpoo > 0 && intPosSpoo <= intCollSpoo){
					//modify by shawn 由于密度只要保留两位，不进位，后面舍弃
					//例如888/78687=0.011285218651111366   取值为： 1.12%
					float i=intPosSpoo;
					float j=intCollSpoo;
					mDensiSpooEdit.setText(com.mobilercn.util.NumberUtils.getPrecent(i, j, 2));
					//End
				}

			}
			
			handler.postDelayed(calculateThread, 1000);
			
		}
		
	};
	
	/**
	 *鼠监测场所类型和下拉框数据适配器 
	 *
	 */
    class MosqitoMonitorPlaceAdapter extends BaseAdapter{
    	private LayoutInflater layoutInflater;
    	private Context context ;
    	public MosqitoMonitorPlaceAdapter(){
    		
    	}
    	public MosqitoMonitorPlaceAdapter(Context context){
    		this.context=context;
    	}
		@Override
		public int getCount() {
			return mosquitoMonitorPlaceList.size();
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
			final String area=mosquitoMonitorPlaceList.get(position);
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
						mosquitoMonitorPlacePop.dismiss();
						isShowMosquitoMonitorPlace=false;
						et_mosquito_monitor_place.setText(area);
						panel_mosqito_palce_name.setBackgroundResource(R.drawable.public_line_mid);
						String mMethod=et_mosquito_monitor_place.getText().toString();
				        if(mMethod.equalsIgnoreCase(getResources().getString(R.string.unit_out_envir))){
				        	panel_mosquito_out.setVisibility(View.VISIBLE);
				        	panel_mosquito_in.setVisibility(View.GONE);
				        }else if(mMethod.equalsIgnoreCase(getResources().getString(R.string.chitang_shuiti))){
				        	panel_mosquito_in.setVisibility(View.VISIBLE);
				        	panel_mosquito_out.setVisibility(View.GONE);
				        }
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
    
    /**
	 *鼠监测场所类型和下拉框数据适配器 
	 *
	 */
    class MosquitoPlaceTypeAdapter extends BaseAdapter{
    	private LayoutInflater layoutInflater;
    	private Context context ;
    	public MosquitoPlaceTypeAdapter(){
    		
    	}
    	public MosquitoPlaceTypeAdapter(Context context){
    		this.context=context;
    	}
		@Override
		public int getCount() {
			return mosquitoPlaceTypeList.size();
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
			final String area=mosquitoPlaceTypeList.get(position);
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
						mosiquitoPlaceTypePop.dismiss();
						isShowMosquitoPlaceTypePop=false;
						et_mosquito_place_type.setText(area);
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
