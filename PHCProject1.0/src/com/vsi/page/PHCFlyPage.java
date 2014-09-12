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
 *Ӭ�������¼����� 
 */
public class PHCFlyPage extends YTBaseActivity {
	/**
	 * ����(Ŀ�ⷨ)����
	 */
	private LinearLayout panel_fly_out;
	/**
	 * ����(ճӬ����)
	 */
	private LinearLayout panel_fly_in;
	/**
	 * ������������
	 */
	private RelativeLayout panel_fly_palce_name;
	/**
	 * Ӭ��ⳡ�����������
	 */
	private EditText edit_fly_palce_name;
	/**
	 * Ӭ�������������
	 */
	private AutoCompleteTextView et_fly_place_type;
	/**
	 * Ӭ��������������ť
	 */
	private ImageButton ibtn_dropDown_fly_place;
	/**
	 * Ӭ�������͵�������
	 */
	private PopupWindow flyPlacePop;
	/**
	 * Ӭ�������͵����б���ͼ
	 */
	private ListView mFlyPlaceListView;
	/**
	 * �Ƿ�Ҫ��ʾӬ�������͵�����
	 */
	private boolean isShowFlyPlace=false;
	/**
	 * Ӭ���������б�
	 */
	private List<String> mFlyPlaceList;
	/**
	 * Ӭ������������������
	 */
	private FlyPlacePopAdapter flyPlacePopAdapter;
	/**
	 * Ӭ��ⷽ�������
	 */
	private AutoCompleteTextView et_fly_method;
	/**
	 * Ӭ��ⷽ��������ť
	 */
	private ImageButton ibtn_dropDown_fly_method;
	/**
	 * Ӭ��ⷽ����������
	 */
	private PopupWindow flyPop;
	/**
	 * Ӭ��ⵯ�����б���ͼ
	 */
	private ListView mFlyListView;
	/**
	 * �Ƿ���ʾӬ��ⵯ������
	 */
	private boolean isShowFlyPop=false;
	/**
	 * Ӭ��ⷽ���б�
	 */
	private List<String> flyMethod;
	/**
	 * Ӭ��ⷽ������������
	 */
	private FlyPopAdapter flyPopAdapter;
	/**
	 * �ӵ����������
	 */
	private EditText mStreetEdit;
	/**
	 * ��Ұ��
	 */
	private EditText mFieldNumberEdit;
	/**
	 * ��Ӭֻ��
	 */
	private EditText mMatureFlyNumberEdit;
	/**
	 * �ܶ�(��Ӭ��/��Ұ)
	 */
	private EditText mMatureflyFieldDensityEdit;
	/**
	 *ճӬ���� 
	 */
	private EditText mStickyflyBarNumberEdit;
	/**
	 *��Ӭ��
	 */
	private EditText mCatchFlyNumberEdit;
	/**
	 * �ܶ�(ճ��Ӭ��/��)
	 */
	private EditText mStickCatchFlyNumberDensityEdit;
	
	/**
	 * ����ע��
	 */
	private EditText edit_out_remark;
	/**
	 * ����ע��
	 */
	private EditText edit_in_remark;
	
	private String mOrgID;
	private String mAreaID;
	private String mType;
	private String mPerson;
	
	/**
	 * Ӭ�������(Ŀ�ⷨ)
	 */
	private int intMethodOut=0;
	/**
	 * Ӭ�������(ճӬ����)
	 */
	private int intMethodIn=1;
	/**
	 * ��ǰӬ��ⷽ��
	 */
	private int mCurrentFlyMethod;
	
	/**
	 * Ӭ�������(Ŀ�ⷨ)������������������������
	 */
	ArrayAdapter adapterPlaceOut;
	/**
	 * Ӭ�������(ճӬ����)������������������������
	 */
	ArrayAdapter adapterPlaceIn;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.phc_input_fly_page);
        Window window = getWindow();
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.public_title_bar);
                
        et_fly_method=(AutoCompleteTextView) this.findViewById(R.id.et_fly_method);
        ibtn_dropDown_fly_method=(ImageButton) this.findViewById(R.id.ibtn_dropDown_fly_method);
        mStreetEdit = (EditText)findViewById(R.id.edit_street);
        et_fly_place_type=(AutoCompleteTextView) this.findViewById(R.id.et_fly_place_type);
        panel_fly_palce_name=(RelativeLayout) this.findViewById(R.id.panel_fly_palce_name);
        edit_fly_palce_name=(EditText) this.findViewById(R.id.edit_fly_palce_name);
        ibtn_dropDown_fly_place=(ImageButton) this.findViewById(R.id.ibtn_dropDown_fly_place);
        panel_fly_out=(LinearLayout) this.findViewById(R.id.panel_fly_out);
        panel_fly_in=(LinearLayout) this.findViewById(R.id.panel_fly_in);
        mFieldNumberEdit = (EditText)findViewById(R.id.edit_field_number);
        mMatureFlyNumberEdit = (EditText)findViewById(R.id.edit_mature_fly_number);
        mMatureflyFieldDensityEdit = (EditText)findViewById(R.id.edit_maturefly_field_density);
        mStickyflyBarNumberEdit = (EditText)findViewById(R.id.edit_stickyfly_bar_number);
        mCatchFlyNumberEdit = (EditText)findViewById(R.id.edit_catch_fly_number);
        mStickCatchFlyNumberDensityEdit = (EditText)findViewById(R.id.edit_stick_catch_fly_number_density);
        
        edit_out_remark=(EditText) this.findViewById(R.id.edit_out_remark);
        edit_in_remark=(EditText) this.findViewById(R.id.edit_in_remark);
        
        adapterPlaceOut=ArrayAdapter.createFromResource(this, R.array.fly_place_out, R.layout.simple_dropdown_item_1line);
        adapterPlaceIn=ArrayAdapter.createFromResource(this, R.array.fly_place_in, R.layout.simple_dropdown_item_1line);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.fly_monitor_method, R.layout.simple_dropdown_item_1line);
        et_fly_method.setAdapter(adapter);
        et_fly_method.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				mCurrentFlyMethod=position;
				//����Ҫ�ж�ѡ��������ּ�ⷽ��
				if(mCurrentFlyMethod==intMethodOut){
					//1�����������(Ŀ�ⷨ)���������(Ŀ�ⷨ)������ʾ������(ճӬ����)��������
					panel_fly_out.setVisibility(View.VISIBLE);
					panel_fly_in.setVisibility(View.GONE);
					//2���ѳ������͵���������������Ϊ����(Ŀ�ⷨ)������������
					et_fly_place_type.setAdapter(adapterPlaceOut);
					
					//3���ѳ������Ƶı�������
					panel_fly_palce_name.setBackgroundResource(R.drawable.public_line_mid);
				}
				
				//1�����������(ճӬ����)���������(ճӬ����)������ʾ������(ճӬ����)��������
				//2���ѳ������͵���������������Ϊ����(ճӬ����)������������
				if(mCurrentFlyMethod==intMethodIn){
					//1�����������(Ŀ�ⷨ)���������(Ŀ�ⷨ)������ʾ������(ճӬ����)��������
					panel_fly_out.setVisibility(View.GONE);
					panel_fly_in.setVisibility(View.VISIBLE);
					//2���ѳ������͵���������������Ϊ����(Ŀ�ⷨ)������������
					et_fly_place_type.setAdapter(adapterPlaceIn);
					//3���ѳ������Ƶı�������
					panel_fly_palce_name.setBackgroundResource(R.drawable.public_line_mid);
				}
			}
        	
		});
        ibtn_dropDown_fly_method.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(et_fly_place_type!=null&&et_fly_place_type.length()>0){
					et_fly_place_type.setText("");
				}
				
				//kris
				if(et_fly_method!=null&&et_fly_method.length()>0){
					et_fly_method.setText("");
				}
				et_fly_method.showDropDown();
			}
		});
        ibtn_dropDown_fly_place.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(et_fly_place_type.getAdapter()==null){
					getApp().showMessage(getResources().getString(R.string.select_mosquito_monitor_method));
				}
				if(et_fly_place_type!=null&&et_fly_place_type.length()>0){
					et_fly_place_type.setText("");
				}
				et_fly_place_type.showDropDown();
			}
		});
      
        //���������ʱ����������ܶȵ��߳�
        handler.postDelayed(calculateThread, 1000);
        
        
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        mOrgID=bundle.getString("OrgID");
        mAreaID=bundle.getString("AreaID");
        mType=bundle.getString("Type");
        mPerson=bundle.getString("Person");
        
        
        if(D2EConfigures.TEST){
        	Log.e("PHCFlyPage============>", "PHCFlyPage");
        	Log.e("AreaID----------->", ""+bundle.getString("AreaID"));
        	Log.e("OrgID----------->", ""+bundle.getString("OrgID"));
        	Log.e("Type----------->", ""+bundle.getString("Type"));
        	Log.e("Person----------->", ""+bundle.getString("Person"));
        	Log.e("PHCFlyPage============>", "PHCFlyPage");
        }
        
        
        Button btnback = (Button)findViewById(R.id.btn_back);
        btnback.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				AlertDialog.Builder builder = new AlertDialog.Builder(PHCFlyPage.this);
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
					String mMethod=et_fly_method.getText().toString();
					String mPlaceType=et_fly_place_type.getText().toString();
					String mPlace=edit_fly_palce_name.getText().toString();
					
					String mFiledNumber=mFieldNumberEdit.getText().toString();
					String mMatureFlyNum=mMatureFlyNumberEdit.getText().toString();
					String mMatureFlyFieldDensity=mMatureflyFieldDensityEdit.getText().toString();
					String mOutMemo=edit_out_remark.getText().toString();
					
					String mStickyFlyBarNum=mStickyflyBarNumberEdit.getText().toString();
					String mCatchFlyNum=mCatchFlyNumberEdit.getText().toString();
					String mStickCatchFlyNumDensity=mStickCatchFlyNumberDensityEdit.getText().toString();
					String mInMemo=edit_in_remark.getText().toString();
					
					//add by shawn 2012-9-21 Begin
					//������Ϣ¼���������ж�
					if(mMethod == null || !(mMethod.length() > 0)){
						((YTBaseApplication) getApplication())
						.showMessage(getResources().getString(R.string.fly_method_less));
						return;
					}
					if(mStreet == null || !(mStreet.length() > 0)){
						((YTBaseApplication) getApplication())
						.showMessage(getResources().getString(R.string.fly_street_less));
						return;
					}
					if(mPlaceType == null || !(mPlaceType.length() > 0)){
						((YTBaseApplication) getApplication())
						.showMessage(getResources().getString(R.string.fly_place_type_less));
						return;
					}
					
					if(mPlace == null || !(mPlace.length() > 0)){
						((YTBaseApplication) getApplication())
						.showMessage(getResources().getString(R.string.fly_place_less));
						return;
					}
					//����Ҫ�ж�ѡ��������ּ�ⷽ��
					if(mCurrentFlyMethod==intMethodOut){
						if(mFiledNumber == null || !(mFiledNumber.length() > 0)){
							((YTBaseApplication) getApplication())
							.showMessage(getResources().getString(R.string.fly_field_num_less));
							return;
						}
						if(mMatureFlyNum == null || !(mMatureFlyNum.length() > 0)){
							((YTBaseApplication) getApplication())
							.showMessage(getResources().getString(R.string.fly_mature_num_less));
							return;
						}
					}
					if(mCurrentFlyMethod==intMethodIn){
						if(mStickyFlyBarNum == null || !(mStickyFlyBarNum.length() > 0)){
							((YTBaseApplication) getApplication())
							.showMessage(getResources().getString(R.string.fly_sticky_bar_num_less));
							return;
						}
						if(mCatchFlyNum == null || !(mCatchFlyNum.length() > 0)){
							((YTBaseApplication) getApplication())
							.showMessage(getResources().getString(R.string.fly_catch_num_less));
							return;
						}
					}
					//End
					
					
					MouseReportItem mosquitoReportItem=null;
					if(mStreet!=null&&mStreet.length()>0
							&&mPlace!=null&&mPlace.length()>0){
						mosquitoReportItem=new MouseReportItem(mStreet,mPlace);
					}
					
					if(mType!=null&&mType.length()>0){
						mosquitoReportItem.setStrReportType(mType);
					}else{
						mosquitoReportItem.setStrReportType("-");
					}
					if(mPerson!=null&&mPerson.length()>0){
						mosquitoReportItem.setStrPerson(mPerson);
					}else{
						mosquitoReportItem.setStrPerson("-");
					}
					if(mAreaID!=null&&mAreaID.length()>0){
						mosquitoReportItem.setStrAreaID(mAreaID);
					}else{
						mosquitoReportItem.setStrAreaID("-");
					}
					
					if(mMethod!=null&&mMethod.length()>0){
						mosquitoReportItem.setMonitorMethod(mMethod);
					}else{
						mosquitoReportItem.setMonitorMethod("-");
					}
					if(mPlaceType!=null&&mPlaceType.length()>0){
						mosquitoReportItem.setmPlaceType(mPlaceType);
					}else{
						mosquitoReportItem.setmPlaceType("-");
					}
					
					
					if(mFiledNumber!=null&&mFiledNumber.length()>0){
						mosquitoReportItem.setStrFlyFieldNumber(mFiledNumber);
					}else{
						mosquitoReportItem.setStrFlyFieldNumber("-");
					}
					if(mMatureFlyNum!=null&&mMatureFlyNum.length()>0){
						mosquitoReportItem.setStrFlyMatureNumber(mMatureFlyNum);
					}else{
						mosquitoReportItem.setStrFlyMatureNumber("-");
					}
					if(mMatureFlyFieldDensity!=null&&mMatureFlyFieldDensity.length()>0){
						if(mMatureFlyFieldDensity.equalsIgnoreCase("0.00")){
							mosquitoReportItem.setStrFlyMatureFieldDensity("-");
						}else{
							mosquitoReportItem.setStrFlyMatureFieldDensity(mMatureFlyFieldDensity);
						}
					}else{
						mosquitoReportItem.setStrFlyMatureFieldDensity("-");
					}
					if(mOutMemo!=null&&mOutMemo.length()>0){
						mosquitoReportItem.setStrOutMemo(mOutMemo);
					}else{
						mosquitoReportItem.setStrOutMemo("-");
					}
					
					
					if(mStickyFlyBarNum!=null&&mStickyFlyBarNum.length()>0){
						mosquitoReportItem.setStrFlyStickBarNumber(mStickyFlyBarNum);
					}else{
						mosquitoReportItem.setStrFlyStickBarNumber("-");
					}
					if(mCatchFlyNum!=null&&mCatchFlyNum.length()>0){
						mosquitoReportItem.setStrFlyCatchNumber(mCatchFlyNum);
					}else{
						mosquitoReportItem.setStrFlyCatchNumber("-");
					}
					if(mStickCatchFlyNumDensity!=null&&mStickCatchFlyNumDensity.length()>0){
						if(mStickCatchFlyNumDensity.equalsIgnoreCase("0.00")){
							mosquitoReportItem.setStrFlyStickyCatchDesity("-");
						}else{
							mosquitoReportItem.setStrFlyStickyCatchDesity(mStickCatchFlyNumDensity);
						}
					}else{
						mosquitoReportItem.setStrFlyStickyCatchDesity("-");
					}
					if(mInMemo!=null&&mInMemo.length()>0){
						mosquitoReportItem.setStrInMemo(mInMemo);
					}else{
						mosquitoReportItem.setStrInMemo("-");
					}
					
					
					if(mCurrentFlyMethod==intMethodOut){
						if(mOutMemo!=null&&mOutMemo.length()>0){
							mosquitoReportItem.setmTotalMemo(mOutMemo);
						}else{
							mosquitoReportItem.setmTotalMemo("-");
						}
						
					}
					if(mCurrentFlyMethod==intMethodIn){
						if(mInMemo!=null&&mInMemo.length()>0){
							mosquitoReportItem.setmTotalMemo(mInMemo);
						}else{
							mosquitoReportItem.setmTotalMemo("-");
						}
						
					}
					
					PHCConfig.flyReportList.add(mosquitoReportItem);
			        PHCFlyPage.this.finish();
				}catch(Exception e){
					
				}finally{
					
				}
			}
        	
        });

        Button btnreset = (Button)findViewById(R.id.btn_reset);
        btnreset.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
		        
		        edit_fly_palce_name.setText("");
		        et_fly_place_type.setText("");
		        et_fly_method.setText("");
		        mStreetEdit.setText("");
		        mFieldNumberEdit.setText("");
		        mMatureFlyNumberEdit.setText("");
		        mMatureflyFieldDensityEdit.setText("");
		        mStickyflyBarNumberEdit.setText("");
		        mCatchFlyNumberEdit.setText("");
		        mStickCatchFlyNumberDensityEdit.setText("");
		        edit_out_remark.setText("");
		        edit_in_remark.setText("");

			}
        	
        });

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
		int intFieldNum=-1;
		int intMatureFlyNum=-1;
		int intStickyFlyBauNum=-1;
		int intCatchFlyNum=-1;
		@Override
		public void run() {
			
			intFieldNum=-1;
			intMatureFlyNum=-1;
			intStickyFlyBauNum=-1;
			intCatchFlyNum=-1;
			double densityField=0.0;
			double densityCatch=0.0;

			
			String mFiledNumber=mFieldNumberEdit.getText().toString();
			String mMatureFlyNum=mMatureFlyNumberEdit.getText().toString();
			String mStickyFlyBarNum=mStickyflyBarNumberEdit.getText().toString();
			String mCatchFlyNum=mCatchFlyNumberEdit.getText().toString();
			
			if(mFiledNumber!=null&&mFiledNumber.length()>0){
				try{
					intFieldNum=Integer.valueOf(mFiledNumber);
				}
				catch(Exception e){}				
			}
			if(mMatureFlyNum!=null&&mMatureFlyNum.length()>0){
				try{
					intMatureFlyNum=Integer.valueOf(mMatureFlyNum);
				}
				catch(Exception e){}				
			}
			if(mStickyFlyBarNum!=null&&mStickyFlyBarNum.length()>0){
				try{
					intStickyFlyBauNum=Integer.valueOf(mStickyFlyBarNum);
				}
				catch(Exception e){}				
			}
			if(mCatchFlyNum!=null&&mCatchFlyNum.length()>0){
				try{
					intCatchFlyNum=Integer.valueOf(mCatchFlyNum);
				}
				catch(Exception e){}				
			}
			
			DecimalFormat df=new DecimalFormat("0.00");
			
			if(intFieldNum > 0 && intMatureFlyNum > 0 ){
				//modify by shawn 2012-10-10 Begin
				//�ܶ��㷨�޸ģ����������룬ֱ��ȥ��β��
				float i=intMatureFlyNum;
				float j=intFieldNum;
				mMatureflyFieldDensityEdit.setText(com.mobilercn.util.NumberUtils.getPrecentPoint(i, j, 2));
				//End
			}
			if(intStickyFlyBauNum > 0 && intCatchFlyNum > 0){
				//modify by shawn 2012-10-10 Begin
				//�ܶ��㷨�޸ģ����������룬ֱ��ȥ��β��
				float i=intCatchFlyNum;
				float j=intStickyFlyBauNum;
				mStickCatchFlyNumberDensityEdit.setText(com.mobilercn.util.NumberUtils.getPrecentPoint(i, j, 2));
				//End
			}
						
			handler.postDelayed(calculateThread, 1000);
			
		}
		
	};
	
	
	/**
	 *Ӭ��ⷽ�������������������� 
	 *
	 */
    class FlyPopAdapter extends BaseAdapter{
    	private LayoutInflater layoutInflater;
    	private Context context ;
    	public FlyPopAdapter(){
    		
    	}
    	public FlyPopAdapter(Context context){
    		this.context=context;
    	}
		@Override
		public int getCount() {
			return flyMethod.size();
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
			final String area=flyMethod.get(position);
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
						flyPop.dismiss();
						isShowFlyPop=false;
						et_fly_method.setText(area);
						et_fly_method.setTextColor(color.et_editableColor);
						panel_fly_palce_name.setBackgroundResource(R.drawable.public_line_mid);
						String mMethod=et_fly_method.getText().toString();
				        if(mMethod.equalsIgnoreCase(getResources().getString(R.string.fly_method_out))){
				        	panel_fly_out.setVisibility(View.VISIBLE);
				        	panel_fly_in.setVisibility(View.GONE);
				        }else if(mMethod.equalsIgnoreCase(getResources().getString(R.string.fly_method_in))){
				        	panel_fly_out.setVisibility(View.GONE);
				        	panel_fly_in.setVisibility(View.VISIBLE);
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
	 *Ӭ��ⳡ�����͵���������������
	 *
	 */
    class FlyPlacePopAdapter extends BaseAdapter{
    	private LayoutInflater layoutInflater;
    	private Context context ;
    	public FlyPlacePopAdapter(){
    		
    	}
    	public FlyPlacePopAdapter(Context context){
    		this.context=context;
    	}
		@Override
		public int getCount() {
			return mFlyPlaceList.size();
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
			final String area=mFlyPlaceList.get(position);
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
						flyPlacePop.dismiss();
						isShowFlyPlace=false;
						et_fly_place_type.setText(area);
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
