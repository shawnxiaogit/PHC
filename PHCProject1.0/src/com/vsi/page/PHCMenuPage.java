package com.vsi.page;

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobilercn.base.YTBaseActivity;
import com.mobilercn.base.YTBaseApplication;
import com.mobilercn.base.YTBaseService;
import com.mobilercn.task.JJTask;
import com.mobilercn.util.YTNetUtil;
import com.mobilercn.util.YTStringHelper;
import com.mobilercn.widget.JJLinkLayer;
import com.mobilercn.widget.JJMenuLayer;
import com.mobilercn.widget.OnMenuSelection;
import com.vsi.config.D2EConfigures;
import com.vsi.config.PHCConfig;
import com.vsi.data.AreaItem;
import com.vsi.phc.R;

import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * ���˵�����
 *
 */
public class PHCMenuPage extends YTBaseActivity implements OnMenuSelection {
	
	
	private JJLinkLayer mDownLink;
	private JJLinkLayer mLeftLink;
	private JJLinkLayer mRightLink;
	private JJLinkLayer mUpLink;
	private String mOrgID;
	
	private JJMenuLayer mMenuLayer;
	/**
	 * ���ұ�׼ͼƬ��ť
	 */
	private ImageButton  ib_state_standard;
	/**
	 * �Ƿ�����һ�η���
	 */
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.phc_menu_page);
        Window window = getWindow();
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.public_title_bar);
        
        Intent intent=getIntent();
        mOrgID=intent.getStringExtra("OrgID");
        
        ib_state_standard=(ImageButton) this.findViewById(R.id.ib_state_standard);
        ib_state_standard.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(PHCMenuPage.this,PHCStateStandardActivity.class);
				PHCMenuPage.this.startActivity(intent);
				overridePendingTransition(R.anim.fade, R.anim.hold);
			}
		});
        
        
        
        //menu icon
        mMenuLayer = (JJMenuLayer)this.findViewById(R.id.layout_menus);
        mMenuLayer.setSourceId(R.drawable.mouse_no_select, R.drawable.mouse_select, 
        		R.drawable.cockroach_no_select, R.drawable.cockroach_select,
        		R.drawable.fly_no_select, R.drawable.fly_select, 
        		R.drawable.pco_select, R.drawable.pco_select,
        		R.drawable.mosquito_no_select, R.drawable.mosquito_select);
        mMenuLayer.setMenuListener(this);
        
        //animation
        mDownLink  = (JJLinkLayer)findViewById(R.id.link_up);
        mDownLink.setMaskBitmap(R.drawable.mask_vertical);
        mDownLink.setSourceBitmap(R.drawable.link_down);
        mDownLink.setDuration(1200f);
        mDownLink.setDirection(JJLinkLayer.DIRECTION_DOWN);
        
        mLeftLink  = (JJLinkLayer)findViewById(R.id.link_left);
        mLeftLink.setMaskBitmap(R.drawable.mask_horizontal);
        mLeftLink.setSourceBitmap(R.drawable.link_right);
        mLeftLink.setDuration(1200f);
        mLeftLink.setDirection(JJLinkLayer.DIRECTION_RIGHT);

        mRightLink  = (JJLinkLayer)findViewById(R.id.link_right);
        mRightLink.setMaskBitmap(R.drawable.mask_horizontal);
        mRightLink.setSourceBitmap(R.drawable.link_left);
        mRightLink.setDuration(1200f);
        mRightLink.setDirection(JJLinkLayer.DIRECTION_LEFT);
        
        mUpLink  = (JJLinkLayer)findViewById(R.id.link_dowm);
        mUpLink.setMaskBitmap(R.drawable.mask_vertical);
        mUpLink.setSourceBitmap(R.drawable.link_up);
        mUpLink.setDuration(1200f);
        mUpLink.setDirection(JJLinkLayer.DIRECTION_UP);        

    }
    
    
	private void startAnimation(){
		mDownLink.startAnimation(false);
		mLeftLink.startAnimation(false);
		mRightLink.startAnimation(false);
		mUpLink.startAnimation(false);
    }
    
    private void stopAnimation(){
		mDownLink.stopAnimation();
		mLeftLink.stopAnimation();
		mRightLink.stopAnimation();
		mUpLink.stopAnimation();
    }
    
    protected void onPause(){
    	super.onPause();
    	stopAnimation();
    }
    
    protected void onResume(){
    	super.onResume();
    	startAnimation();
    }


	@Override
	public void menuSelection(int index) {
		String tag=" menuSelection--->";

		switch(index){
		//�����������
		case OnMenuSelection.MENU_TOP:{	
			if(D2EConfigures.TEST){
				Log.e(tag, "link_up");
			}
			PHCConfig.g_infoType = PHCConfig.TYPE_NORMAL;
			PHCConfig.SURVEY_TYPE= PHCConfig.TYPE_MOUSE;
			//shawn
	        //������б�û�б��棬����������
	        if(!(PHCConfig.areaItems.size()>0)){
	        	String[] params=new String[]{
	    				"FunType", "getStreetList", //��ȡ��id�ӿ�
	    				"OrgID",mOrgID//��ί��id
	    		};
	    		YTStringHelper.array2string(params);
	    		long id = YTBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-main/webservices/get/awh.html", params, PHCMenuPage.this.getClass().getName(),PHCConfig.TASK_GET_AREA);		        
	            setCurTaskID(id);
	            showWaitDialog(getResources().getString(R.string.handling_wait), R.style.dialog, R.layout.progress_dialog, R.id.wait_image, R.id.wait_message);
	        }else{
	        	Intent intent = new Intent(PHCMenuPage.this, PHCInfoPage.class);
				intent.putExtra("OrgID", mOrgID);
				PHCMenuPage.this.startActivity(intent);overridePendingTransition(R.anim.fade, R.anim.hold);
				overridePendingTransition(R.anim.fade, R.anim.hold);
	        }
			break;
		}
		//��������ü��
		case OnMenuSelection.MENU_BOTTOM:{
			if(D2EConfigures.TEST){
				Log.e(tag, "link_dowm");
			}
			PHCConfig.g_infoType = PHCConfig.TYPE_NORMAL;
			PHCConfig.SURVEY_TYPE= PHCConfig.TYPE_MOSQUITO;
			//shawn
	        //������б�û�б��棬����������
	        if(!(PHCConfig.areaItems.size()>0)){
	        	String[] params=new String[]{
	    				"FunType", "getStreetList", //��ȡ��id�ӿ�
	    				"OrgID",mOrgID//��ί��id
	    		};
	    		YTStringHelper.array2string(params);
	    		long id = YTBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-main/webservices/get/awh.html", params, PHCMenuPage.this.getClass().getName(),PHCConfig.TASK_GET_AREA);		        
	            setCurTaskID(id);
	            showWaitDialog(getResources().getString(R.string.handling_wait), R.style.dialog, R.layout.progress_dialog, R.id.wait_image, R.id.wait_message);
	        }else{
	        	Intent intent = new Intent(PHCMenuPage.this, PHCInfoPage.class);
				intent.putExtra("OrgID", mOrgID);
				PHCMenuPage.this.startActivity(intent);
				overridePendingTransition(R.anim.fade, R.anim.hold);
	        }
			break;
		}
		//��ߵ��������
		case OnMenuSelection.MENU_LEFT:{
			if(D2EConfigures.TEST){
				Log.e(tag, "link_left");
			}
			PHCConfig.g_infoType = PHCConfig.TYPE_NORMAL;	
			PHCConfig.SURVEY_TYPE= PHCConfig.TYPE_KOCKROACH;
			//shawn
	        //������б�û�б��棬����������
	        if(!(PHCConfig.areaItems.size()>0)){
	        	String[] params=new String[]{
	    				"FunType", "getStreetList", //��ȡ��id�ӿ�
	    				"OrgID",mOrgID//��ί��id
	    		};
	    		YTStringHelper.array2string(params);
	    		long id = YTBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-main/webservices/get/awh.html", params, PHCMenuPage.this.getClass().getName(),PHCConfig.TASK_GET_AREA);		        
	            setCurTaskID(id);
	            showWaitDialog(getResources().getString(R.string.handling_wait), R.style.dialog, R.layout.progress_dialog, R.id.wait_image, R.id.wait_message);
	        }else{
	        	Intent intent = new Intent(PHCMenuPage.this, PHCInfoPage.class);
				intent.putExtra("OrgID", mOrgID);
				PHCMenuPage.this.startActivity(intent);
				overridePendingTransition(R.anim.fade, R.anim.hold);
	        }
			
			break;
		}
		//�ұߵ���Ӭ���
		case OnMenuSelection.MENU_RIGHT:{
			if(D2EConfigures.TEST){
				Log.e(tag, "link_right");
			}
			PHCConfig.g_infoType = PHCConfig.TYPE_NORMAL;
			PHCConfig.SURVEY_TYPE= PHCConfig.TYPE_FLY;
			//shawn
	        //������б�û�б��棬����������
	        if(!(PHCConfig.areaItems.size()>0)){
	        	String[] params=new String[]{
	    				"FunType", "getStreetList", //��ȡ��id�ӿ�
	    				"OrgID",mOrgID//��ί��id
	    		};
	    		YTStringHelper.array2string(params);
	    		long id = YTBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-main/webservices/get/awh.html", params, PHCMenuPage.this.getClass().getName(),PHCConfig.TASK_GET_AREA);		        
	            setCurTaskID(id);
	            showWaitDialog(getResources().getString(R.string.handling_wait), R.style.dialog, R.layout.progress_dialog, R.id.wait_image, R.id.wait_message);
	        }else{
				Intent intent = new Intent(PHCMenuPage.this, PHCInfoPage.class);
				intent.putExtra("OrgID", mOrgID);
				PHCMenuPage.this.startActivity(intent);
				overridePendingTransition(R.anim.fade, R.anim.hold);
	        }
			break;
		}
		}
		
		mMenuLayer.initPositions();
	}


	@Override
	public void onBackAciton() {
		
	}


	@Override
	public boolean isExit() {
		return true;
	}


	@Override
	public void taskSuccess(Object... param) {
		if(D2EConfigures.TEST){
			Log.e("taskSuccess=========>", "taskSuccess()");
		}
		int values = ((Integer)param[0]).intValue();
		if(values == YTBaseService.HTTP_SERVICE_INT){
		    JJTask task = (JJTask)param[1];
		    if(task.getTaskId() == PHCConfig.TASK_GET_AREA){
		    	try{
					InputStream ins = task.getInputStream();
					byte[] bytes = YTNetUtil.readByByte(ins, -1);
					
					String tmp = new String(bytes, "UTF-8");
		            ins.close();
		            if(D2EConfigures.TEST){
		            	Log.e("OUT 20xxxxx >>>>>> ", tmp);
		            }
		            if(tmp!=null&&tmp.length()>0){
		            JSONArray j=null;
		            try{
		            	j=new JSONArray(tmp);
		            }catch(Exception e){
		            	e.printStackTrace();
		            }
		            if(D2EConfigures.TEST){
		            	Log.e("------", "-------------");
		            	Log.e("j-------->", ""+j);
		            }
		            JSONObject j2=null;
		            for(int i=0;i<j.length();i++){
		            	j2=new JSONObject(j.getString(i));
		            	AreaItem item=new AreaItem(j2.getString("Name"));
		            	item.setStrAreaID(j2.getString("ID"));
		            	item.setStrOrgID(j2.getString("OrgID"));
		            	item.setmAreaLongitude(j2.getString("Longitude"));
		            	item.setmAreaLatitude(j2.getString("Latitude"));
		            	item.setmAreaCreateTime("CreateTime");
		            	item.setmAreaDeleteTime("DeleteTime");
		            	
		            	PHCConfig.areaItems.add(item);
		            }
		            
		          //ֻ�л�ȡ���������ڽ����¸�����
		            Intent intent = new Intent(PHCMenuPage.this, PHCInfoPage.class);
					intent.putExtra("OrgID", mOrgID);
					PHCMenuPage.this.startActivity(intent);  
		            
		    	}else{
		    		getApp().showMessage(getResources().getString(R.string.data_failed));
		    	}
		    	}
		    	catch(Exception e){}
		    }
		}
		
		hideWaitDialog();
	}

	
	@Override
	public void taskFailed(Object... param) {
		if(D2EConfigures.TEST){
			Log.e("taskFailed=========>", "taskFailed()");
		}
		hideWaitDialog();
		hideWaitDialog();
		int values = ((Integer)param[0]).intValue();
		if(values == YTBaseService.HTTP_SERVICE_INT){
			((YTBaseApplication)getApplication()).showMessage(getResources().getString(R.string.net_failed));
		}
	}


	@Override
	public void taskProcessing(Object... param) {
		
	}

}