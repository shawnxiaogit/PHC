package com.vsi.page;

import java.io.InputStream;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

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
import com.vsi.data.MouseReportItem;
/**
 *�ü�� ���ݱ������
 *
 */
public class PCHMosquitoTablePage extends YTBaseActivity {
	ImageButton mButtonRight, mButtonLeft;
	YTReportView report;
	private String mOrgID;
	private String mAreaID;
	private String mType;
	private String mPerson;
	private Button btnsubmit;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.phc_table_input);
        Window window = getWindow();
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.public_title_bar);
        
        
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(D2EConfigures.TEST){
        	Log.e("AreaID----------->", ""+bundle.getString("AreaID"));
        	Log.e("OrgID----------->", ""+bundle.getString("OrgID"));
        	Log.e("Type----------->", ""+bundle.getString("Type"));
        	Log.e("Person----------->", ""+bundle.getString("Person"));
        }
        mOrgID=bundle.getString("OrgID");
        mAreaID=bundle.getString("AreaID");
        mType=bundle.getString("Type");
        mPerson=bundle.getString("Person");
        
        //������ť
        Button btn = (Button)findViewById(R.id.newinput);
        btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(PCHMosquitoTablePage.this, PHCMosquitoPage.class);
				Bundle bundle=new Bundle();
				bundle.putString("OrgID", mOrgID);
				bundle.putString("AreaID", mAreaID);
				bundle.putString("Type", mType);
				bundle.putString("Person", mPerson);
				intent.putExtras(bundle);
				PCHMosquitoTablePage.this.startActivity(intent);

			}
        	
        });
        //���ذ�ť
        Button btnback = (Button)findViewById(R.id.btn_back);
        btnback.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				if(PHCConfig.mosquitoReportList != null && PHCConfig.mosquitoReportList.size() > 0){
					AlertDialog.Builder builder = new AlertDialog.Builder(PCHMosquitoTablePage.this);
					builder.setTitle(getResources().getString(R.string.warming_tips));
					builder.setMessage(getResources().getString(R.string.data_no_submit));
					builder.setIcon(R.drawable.msg_dlg_warning);
					builder.setPositiveButton(getResources().getString(R.string.dialog_ensure_btn_txt), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							
							finish();
							PHCConfig.mosquitoReportList.clear();
						}
					});
					builder.setNegativeButton(getResources().getString(R.string.dialog_cancle_btn_txt), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							
						}
					});
					builder.create().show();					
				}
				else {
					finish();
				}
				


			}
        	
        });
        //�ύ��ť
        btnsubmit = (Button)findViewById(R.id.btn_submit);
        btnsubmit.setEnabled(false);
        btnsubmit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				StringBuffer sb=new StringBuffer();
				sb.append("[");
				for(MouseReportItem item:PHCConfig.mosquitoReportList){
					sb.append("{");
					sb.append("\"").append("Type").append("\":").append("\"").append(item.getStrReportType()).append("\",");
					sb.append("\"").append("Person").append("\":").append("\"").append(item.getStrPerson()).append("\",");
					sb.append("\"").append("StreetID").append("\":").append("\"").append(item.getStrAreaID()).append("\",");
					sb.append("\"").append("Name").append("\":").append("\"").append(item.getmStreetName()).append("\",");
					sb.append("\"").append("Report").append("\":");
					sb.append("[");
					sb.append("{");
					sb.append("\"").append("SiteType").append("\":").append("\"").append(item.getmPlaceType()).append("\",");
					sb.append("\"").append("Name").append("\":").append("\"").append(item.getmPlaceName()).append("\",");
					sb.append("\"").append("TagNum").append("\":").append("\"").append("null").append("\",");
					sb.append("\"").append("MosquitoPond").append("\":").append("\"").append(item.getStrMosquPond()).append("\",");
					sb.append("\"").append("MosquitoPositivePond").append("\":").append("\"").append(item.getStrMosquPosPond()).append("\",");
					sb.append("\"").append("MosquitoDensityPond").append("\":").append("\"").append(item.getStrMosquDensPond()).append("\",");
					sb.append("\"").append("LadleNum").append("\":").append("\"").append(item.getStrMosquCollSpoo()).append("\",");
					sb.append("\"").append("PositiveLadleNum").append("\":").append("\"").append(item.getStrMosquPosSpoo()).append("\",");
					sb.append("\"").append("MosquitoDensitySpoon").append("\":").append("\"").append(item.getStrMosquDensSpoo()).append("\",");
					sb.append("\"").append("Memo").append("\":").append("\"").append(item.getmTotalMemo()).append("\"");
					sb.append("}");
					sb.append("]");
					sb.append("}");
					sb.append(",");
				}
				sb.deleteCharAt(sb.toString().length() - 1);
				sb.append("]");
				
				String[] params=new String[]{
						"FunType", "insertSOPReport", 
						"OrgID",mOrgID,
						"Data",sb.toString()						
				};
				if(D2EConfigures.TEST){
					YTStringHelper.array2string(params);
				}
				long id = YTBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-main/webservices/get/awh.html", params, PCHMosquitoTablePage.this.getClass().getName(),PHCConfig.TASK_SUBMIT_REPORT);		        
		        setCurTaskID(id);
				showWaitDialog(getResources().getString(R.string.handling_wait), R.style.dialog, R.layout.progress_dialog, R.id.wait_image, R.id.wait_message);
			}
        	
        });
        
        
        
       report = (YTReportView)findViewById(R.id.reportview);
        
        
        if(PHCConfig.g_ReportModel == null){
            PHCConfig.g_ReportModel = new YTReportModel(report.getPaint(), this);        	
        }
        PHCConfig.g_ReportModel.clear();
        //�����ܶȼ��Ҫ�ɼ�7������
        PHCConfig.g_ReportModel.setHeader(new String[]{
        		getResources().getString(R.string.mouse_street_name), 
        		getResources().getString(R.string.mosquito_pond_number),
        		getResources().getString(R.string.mosquito_pos_pond_number), 
        		getResources().getString(R.string.mosquito_density_pond),
        		getResources().getString(R.string.mosquito_coll_spoo_number), 
        		getResources().getString(R.string.mosquito_pos_spoo_number), 
        		getResources().getString(R.string.mosquito_density_spoo)});
        PHCConfig.g_ReportModel.addItem(new String[]{"  ", "  ", "  ", "  ", "  ", "  ", "  "});
        
        report.setModel(PHCConfig.g_ReportModel);
        
        mButtonLeft = (ImageButton) findViewById(R.id.btn_pre);

		mButtonRight = (ImageButton) findViewById(R.id.btn_next);

		mButtonLeft.setVisibility(View.INVISIBLE);
		mButtonLeft.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				int tmp = report.scrollLeft(false);
				if (tmp <= 0) {
					mButtonLeft.setVisibility(View.INVISIBLE);
				} else {
					mButtonLeft.setVisibility(View.VISIBLE);
				}
				mButtonRight.setVisibility(View.VISIBLE);
			}
		});
		mButtonRight.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				int tmp = report.scrollLeft(true);
				if (tmp >= PHCConfig.g_ReportModel.getRows() - 1) {
					mButtonRight.setVisibility(View.INVISIBLE);
				} else {
					mButtonRight.setVisibility(View.VISIBLE);
				}
				mButtonLeft.setVisibility(View.VISIBLE);
			}
		});
		isShowReprotButton();

    }
    
    /**
     * �Ƿ���ʾ���������л���ť
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
    
    @Override
    protected void onResume() {
    	super.onResume();
    	if(PHCConfig.mosquitoReportList != null && PHCConfig.mosquitoReportList.size()>0){
    		if(PHCConfig.g_ReportModel == null){
    			PHCConfig.g_ReportModel = new YTReportModel(report.getPaint(), this);  
    		}
    		PHCConfig.g_ReportModel.clear();
    		 PHCConfig.g_ReportModel.setHeader(new String[]{
    	        		getResources().getString(R.string.mouse_street_name), 
    	        		getResources().getString(R.string.mosquito_pond_number),
    	        		getResources().getString(R.string.mosquito_pos_pond_number), 
    	        		getResources().getString(R.string.mosquito_density_pond),
    	        		getResources().getString(R.string.mosquito_coll_spoo_number), 
    	        		getResources().getString(R.string.mosquito_pos_spoo_number), 
    	        		getResources().getString(R.string.mosquito_density_spoo)});
    		for(MouseReportItem item:PHCConfig.mosquitoReportList){
    			PHCConfig.g_ReportModel.addItem(new String[]{
    					item.getmStreetName(),
    					item.getStrMosquPond(),
    					item.getStrMosquPosPond(),
    					item.getStrMosquDensPond(),
    					item.getStrMosquCollSpoo(),
    					item.getStrMosquPosSpoo(),
    					item.getStrMosquDensSpoo()
    			});
    			report.setModel(PHCConfig.g_ReportModel);
    		}
    		
    		btnsubmit.setEnabled(true);
    	}
    	isShowReprotButton();
    	report.invalidate();
    	
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
		try{
			int values = ((Integer)param[0]).intValue();
			if(values == YTBaseService.HTTP_SERVICE_INT){
			    JJTask task = (JJTask)param[1];
			    if(task.getTaskId() == PHCConfig.TASK_SUBMIT_REPORT){
			    	InputStream ins = task.getInputStream();
					byte[] bytes = YTNetUtil.readByByte(ins, -1);
					
					String tmp = new String(bytes, "UTF-8");
		            ins.close();
		            if(D2EConfigures.TEST){
		            	Log.e("OUT 20 >>>>>> ", tmp);
		            }
		            //���ؽ�����
		            hideWaitDialog();
		            JSONObject j=new JSONObject(tmp);
		            String strSucc=null;
		            if(j.has("success")){
		            	strSucc=j.getString("success");
		            }
		            //�ύ�ɹ���ʱ�򣬲Űѱ����������
		            PHCConfig.g_ReportModel.clear();
		            PHCConfig.mosquitoReportList.clear();
		            getApp().showMessage(strSucc);
		            PCHMosquitoTablePage.this.finish();
			    }		
			}
		}catch(Exception e){}
	}

	@Override
	public void taskFailed(Object... param) {
		
	}

	@Override
	public void taskProcessing(Object... param) {
		
	}

}
