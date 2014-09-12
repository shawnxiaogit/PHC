package com.vsi.page;

import java.io.InputStream;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobilercn.base.YTBaseActivity;
import com.mobilercn.base.YTBaseApplication;
import com.mobilercn.base.YTBaseService;
import com.mobilercn.task.JJTask;
import com.mobilercn.util.SaveLoginParams;
import com.mobilercn.util.YTNetUtil;
import com.mobilercn.util.YTStringHelper;
import com.vsi.phc.R;
import com.vsi.config.D2EConfigures;
import com.vsi.config.PHCConfig;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

/**
 * ��¼����
 * @author ShawnXiao
 *
 */
public class PHCLoginPage extends YTBaseActivity {
	/**
	 * �û��������
	 */
	private EditText login_edit_account;
	/**
	 * ���������
	 */
	private EditText login_edit_pwd;
	/**
	 * �����û�������
	 */
	private CheckBox login_cb_name;
	/**
	 *����������� 
	 */
	private CheckBox login_cb_pwd;
	/**
	 * �û���
	 */
	private String username;
	/**
	 * ����
	 */
	private String password;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.public_login_page);
        Window window = getWindow();
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.public_title_bar);
        
        login_edit_account=(EditText) this.findViewById(R.id.login_edit_account);
    	login_edit_pwd=(EditText) this.findViewById(R.id.login_edit_pwd);
    	login_cb_name=(CheckBox) this.findViewById(R.id.login_cb_name);
    	login_cb_pwd=(CheckBox) this.findViewById(R.id.login_cb_pwd);
    	login_cb_name.setChecked(false);
    	login_cb_pwd.setChecked(false);
    	
    	//����û�������Ϊ��ѡ��״̬����ɾ���ļ��б�����û���
    	login_cb_name.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(!login_cb_name.isChecked()){
					SaveLoginParams.deletParamsName(PHCLoginPage.this);
					login_cb_name.setChecked(false);
				}
			}
		});
    	//����������Ϊ��ѡ��״̬����ɾ���ļ��б��������
    	login_cb_pwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(!login_cb_pwd.isChecked()){
					SaveLoginParams.deletParamsPwd(PHCLoginPage.this);
					login_cb_pwd.setChecked(false);
				}
			}
		});
    	
    	//���뱣���ڱ��ص��û���������
    	//���뱣����û���
        String loginParamsN[]=SaveLoginParams.getParamsName(PHCLoginPage.this);
        //���뱣�������
        String loginParamsP[]=SaveLoginParams.getParamsPwd(PHCLoginPage.this);
        {
        	if(loginParamsN[0]!=null&&loginParamsN[0].length()!=0){
        		login_cb_name.setChecked(true);
        	}
        	if(loginParamsP[0]!=null&&loginParamsP[0].length()!=0){
        		login_cb_pwd.setChecked(true);
        	}
        	login_edit_account.setText((String)loginParamsN[0]);
        	login_edit_pwd.setText((String)loginParamsP[0]);
        }
    	
    	Button submit = (Button)findViewById(R.id.login_submit_btn);
        //1����һ���ӿڣ���¼ ���ύ��ʱ����õ�¼��֤�ӿ�
        submit.setOnClickListener(new OnClickListener(){
        	
			public void onClick(View arg0) {
				//��ȡ�û���
				username=login_edit_account.getText().toString();
				//��ȡ����
				password=login_edit_pwd.getText().toString();
				//����û��������벻Ϊ�գ���������֤�û���������
				if(username!=null&&username.length()>0
						&&password!=null&&password.length()>0){
			        String[] params = new String[]{
			        		"FunType", "Login", 
			        		"Username",username, 
			        		"Password",password};
			        YTStringHelper.array2string(params);
			        long id = YTBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-main/webservices/get/awh.html", params, PHCLoginPage.this.getClass().getName(),PHCConfig.TASK_LOGIN);
			        setCurTaskID(id);
					showWaitDialog(getResources().getString(R.string.handling_wait), R.style.dialog, R.layout.progress_dialog, R.id.wait_image, R.id.wait_message);
				}else{
				//������ʾ�����û���������
					((YTBaseApplication)getApplication()).showMessage(getResources().getString(R.string.input_account_pwd));
				}
			}
        	
        });

    }
    
    @Override
    protected void OnInit() {
    	super.OnInit();
    	
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
		try{
			int values = ((Integer)param[0]).intValue();
			if(values == YTBaseService.HTTP_SERVICE_INT){
			    JJTask task = (JJTask)param[1];
			    if(task.getTaskId() == PHCConfig.TASK_LOGIN){
			    	try{
						InputStream ins = task.getInputStream();
						byte[] bytes = YTNetUtil.readByByte(ins, -1);
						
						String tmp = new String(bytes, "UTF-8");
			            ins.close();
			            if(D2EConfigures.TEST){
			            	Log.e("OUT 20 >>>>>> ", tmp);
			            }
			            //������¼��������
			            if(tmp!=null&&tmp.length()>0){
			            JSONArray j=null;
			            try{
			            	j=new JSONArray(tmp);
			            }catch(Exception e){
			            	JSONObject j2=new JSONObject(tmp);
			            	String error=j2.getString("error");
			            	if(error!=null&&error.length()>0){
			            		getApp().showMessage(error);	
			            	}
			            	e.printStackTrace();
			            }
			            JSONObject json=null;
			            for(int i=0;i<j.length();i++){
			            	if(D2EConfigures.TEST){
			            		Log.e("j----->", j.getString(i));
			            	}
			            	json=new JSONObject(j.getString(i));
			            }
			            
			            String strUserName=json.getString("Username");
			            String strPassWord=json.getString("Password");
			            String strOrgId=json.getString("OrgID");
			            //����û��������벻Ϊ��,�򱣴��û���
			            if(strUserName!=null&&strUserName.length()>0
			            		&&strPassWord!=null&&strPassWord.length()>0){
			            	//����û�������Ϊѡ�У��򱣴��û���
			            	if(login_cb_name.isChecked()){
			            		SaveLoginParams.saveParamsName(PHCLoginPage.this, username);
			            	}			            	
			            	//����������Ϊѡ�У��򱣴�����
			            	if(login_cb_pwd.isChecked()){
			            		SaveLoginParams.saveParamsPwd(PHCLoginPage.this, password);
			            	}
			            }
			            
						Intent intent = new Intent(PHCLoginPage.this, PHCMenuPage.class);
						intent.putExtra("OrgID", strOrgId);
					    PHCLoginPage.this.startActivity(intent);
					    overridePendingTransition(R.anim.fade, R.anim.hold);
						finish();

			    	}else{
			    		getApp().showMessage(getResources().getString(R.string.data_failed));
			    	}
			            
			    	}
			    	catch(Exception e){}
			    }
			}
		}
		catch(Exception ex){
						
		}
		finally{
		    hideWaitDialog();
		}
		
	}

	@Override
	public void taskFailed(Object... param) {
		if(D2EConfigures.TEST){
			Log.e("taskFailed=========>", "taskFailed()");
		}
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
