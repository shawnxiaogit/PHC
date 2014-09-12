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
 * ��������¼�����
 * @author ShawnXiao
 *
 */
public class PHCMosquitoPage extends YTBaseActivity {
	private String mOrgID;
	private String mAreaID;
	private String mType;
	private String mPerson;
	/**
	 * ������������
	 */
	private RelativeLayout panel_mosqito_palce_name;
	/**
	 * ��λ�������⻷��
	 */
	private LinearLayout panel_mosquito_out;
	/**
	 * ����������ȴ�����ˮ��
	 */
	private LinearLayout panel_mosquito_in;
	/**
	 * ��������
	 */
	private EditText edit_mosqito_palce_name;
	/**
	 * �ü�ⳡ�����ͱ༭��
	 */
	private AutoCompleteTextView et_mosquito_place_type;
	/**
	 * �ü�ⳡ������������ť
	 */
	private ImageButton ibtn_dropDown_mosquito_place;
	/**
	 * �ü�ⳡ�����͵�������
	 */
	private PopupWindow mosiquitoPlaceTypePop;
	/**
	 * �Ƿ�Ҫ��ʾ�ü�ⳡ�����͵�������
	 */
	private boolean isShowMosquitoPlaceTypePop;
	/**
	 * �ü�ⳡ�������б���ͼ
	 */
	private ListView mMosquitoPlaceTypeListView;
	/**
	 * �ü�ⳡ������������
	 */
	private MosquitoPlaceTypeAdapter mosquitoPlaceTypeAdapter;
	/**
	 * �ü�ⳡ�������б�
	 */
	private static List<String> mosquitoPlaceTypeList;
	/**
	 * �ü��ص����ͱ༭��
	 */
	private AutoCompleteTextView et_mosquito_monitor_place;
	/**
	 * �ü��ص�������ť
	 */
	private ImageButton ibtn_dropDown_mosquito_monitor_place;
	/**
	 * �ü��ص����͵�����
	 */
	private PopupWindow mosquitoMonitorPlacePop;
	/**
	 * �ü��ص㵯�����б���ͼ
	 */
	private ListView mosquitoMonitorPlaceListView;
	/**
	 * �ü��ص�����������
	 */
	private MosqitoMonitorPlaceAdapter mosiqitoMonitorPlaceAdapter;
	/**
	 * �Ƿ���ʾ�ü��ص㵯������
	 */
	private boolean isShowMosquitoMonitorPlace=false;
	/**
	 * �ü��ص��б�
	 */
	private static List<String> mosquitoMonitorPlaceList;
	/**
	 * �ü��ص���
	 */
	/**
	 * �ֵ����������
	 */
	private EditText mStreeEdit;
	/**
	 * ��ˮ����(��ˮ��)��
	 */
	private EditText mPondEdit;
	/**
	 * �����������
	 */
	private EditText mPosPondEdit;
	/**
	 * ���ܶ������
	 */
	private EditText mDensPondEdit;
	/**
	 * �ɼ�����
	 */
	private EditText mCollSpooEdit;
	/**
	 * ��������
	 */
	private EditText mPosSpooEdit;
	/**
	 * �ܶ�(��������/������)
	 */
	private EditText mDensiSpooEdit;
	
	/**
	 * �⻷����ע
	 */
	private EditText edit_out_remark;
	/**
	 * ����ˮ�屸ע
	 */
	private EditText edit_in_remark;
	/**
	 * ��λ�������⻷����ⷽ��
	 */
	private int intMethodOut=0;
	/**
	 * ����������ȴ�����ˮ���ⷽ��
	 */
	private int intMethodRiver=1;
	/**
	 * ��ǰ��ⷽ��
	 */
	private int mCurrentMosqitoMonitorMethod;
	
	
	/**
	 * ��λ�������⻷��������������������������
	 */
	ArrayAdapter adapterPlaceOut;
	/**
	 * ����������ȴ�����ˮ�峡����������������������
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
        		//����Ҫ�ж�ѡ��������ּ�ⷽ��
        		
        		if(mCurrentMosqitoMonitorMethod==intMethodOut){
        			//1�����ѡ����ǵ�λ�������⻷����ⷽ������ѵ�λ�������⻷���Ľ�����ʾ����������������
        			panel_mosquito_out.setVisibility(View.VISIBLE);
            		panel_mosquito_in.setVisibility(View.GONE);
            		//2������Ӧ����������������Ϊ��λ�������⻷��
            		et_mosquito_place_type.setAdapter(adapterPlaceOut);
            		//3�����������������ı�������
            		panel_mosqito_palce_name.setBackgroundResource(R.drawable.public_line_mid);
        			
        		}
        		
        		if(mCurrentMosqitoMonitorMethod==intMethodRiver){
        			//1�����ѡ����ǳ���������ȴ�����ˮ���ⷽ������ѳ���������ȴ�����ˮ��Ľ�����ʾ����������������
        			panel_mosquito_out.setVisibility(View.GONE);
            		panel_mosquito_in.setVisibility(View.VISIBLE);
        			//2������Ӧ����������������Ϊ��������
            		et_mosquito_place_type.setAdapter(adapterPlaceRiver);
            		//3�����������������ı�������
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
        
        
        //���������ʱ��Ͱ��ż����ܶ��߳�
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
					//����ֵ����ƺͳ������Ʋ�Ϊ�գ����½�һ��������
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
					//������Ϣ¼���������ж�
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
					//����Ҫ��������һ�ּ�ⷽ��
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
					//modify by shawn �����ܶ�ֻҪ������λ������λ����������
					//����888/78687=0.011285218651111366   ȡֵΪ�� 1.12%
					float i=intPosPond;
					float j=intPond;
					mDensPondEdit.setText(com.mobilercn.util.NumberUtils.getPrecent(i, j, 2));
					//End
				}
				
			}
			
			if(et_mosquito_monitor_place.getText().toString().equalsIgnoreCase("ˮ��")){
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
					//modify by shawn �����ܶ�ֻҪ������λ������λ����������
					//����888/78687=0.011285218651111366   ȡֵΪ�� 1.12%
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
	 *���ⳡ�����ͺ����������������� 
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
	 *���ⳡ�����ͺ����������������� 
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
