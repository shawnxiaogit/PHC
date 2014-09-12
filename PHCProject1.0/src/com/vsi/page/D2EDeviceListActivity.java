package com.vsi.page;

import java.util.Set;

import com.mobilercn.base.YTBaseActivity;
import com.mobilercn.base.YTBaseApplication;
import com.mobilercn.base.YTBaseService;
import com.mobilercn.sop.bt.SOPBluetoothService;



import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.vsi.config.D2EConfigures;
import com.vsi.phc.R;


public class D2EDeviceListActivity extends YTBaseActivity {
	private String mOrgID;
	private String mAreaID;
	private String mType;
	private String mPerson;

    // Return Intent extra
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    public static String EXTRA_READER_ID      = "reader_id";
    
    public static String PREFIX = "PCOBT";

    // Member fields
    private BluetoothAdapter     mBtAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    private String               mBtAddress;

    Button mQuertBtn;
    
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.t68_bt_list);
        setTitle(getResources().getString(R.string.set_up_reader));

        setResult(Activity.RESULT_CANCELED);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		mOrgID = bundle.getString("OrgID");
		mAreaID = bundle.getString("AreaID");
		mType = bundle.getString("Type");
		mPerson = bundle.getString("Person");


        mQuertBtn = (Button) findViewById(R.id.bt_query_btn);
        mQuertBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                doDiscovery();
            }
        });
        
        Button bt_enter_btn = (Button) findViewById(R.id.bt_enter_btn);
        bt_enter_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	backAciton();
            }
        });

        
        
        Button okButton = (Button)findViewById(R.id.bt_select_btn);
        okButton.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
                mBtAdapter.cancelDiscovery();

                
                TextView et = (TextView)findViewById(R.id.read_id);
                String id = et.getText().toString().trim();
                
                if(id == null || id.length() == 0){
                	getApp().showMessage(getResources().getString(R.string.chose_reader));
                	return;
                }
                
                if(mBtAddress == null || mBtAddress.length() == 0){
                	getApp().showMessage(getResources().getString(R.string.chose_reader));
                	return;                	
                }
                
    			SOPBluetoothService.g_btMacAddress = mBtAddress;//data.getExtras().getString(EXTRA_DEVICE_ADDRESS);
    			SOPBluetoothService.g_readerID     = id;//data.getExtras().getString(EXTRA_READER_ID);			
                
    			SharedPreferences settings = getSharedPreferences(PHCMousePage.READER_PREFS_NAME, 0); 
    			Editor editor = settings.edit();
    			editor.putString(PHCMousePage.BT_MAC_KEY, SOPBluetoothService.g_btMacAddress);
    			editor.putString(PHCMousePage.READER_KEY, SOPBluetoothService.g_readerID);
    			editor.commit();
                
    			showProcessDialog("");
    			((YTBaseApplication) getApplication()).startJJService();			

        	}
        });

        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.t68_bt_item);
        mNewDevicesArrayAdapter    = new ArrayAdapter<String>(this, R.layout.t68_bt_item);

        // Find and set up the ListView for paired devices
        ListView listView = (ListView) findViewById(R.id.devices);
        listView.setAdapter(mNewDevicesArrayAdapter);
        listView.setItemsCanFocus(false);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(mDeviceClickListener);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
            	if(device.getName().toLowerCase().indexOf(PREFIX.toLowerCase()) >= 0){
                    mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                    mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());            		
            	}
            }
        } 

    }
    

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
    }

    /**
     * Start device discover with the BluetoothAdapter
     */
    private void doDiscovery() {

        // Indicate scanning in the title
        setProgressBarIndeterminateVisibility(true);
        setTitle(getResources().getString(R.string.title_searching));
        
        mNewDevicesArrayAdapter.clear();
        int cnt = mPairedDevicesArrayAdapter.getCount();
        for(int i = 0; i < cnt; i ++){
        	mNewDevicesArrayAdapter.add(mPairedDevicesArrayAdapter.getItem(i));
        }

        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }

        mBtAdapter.startDiscovery();
    }

    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            String info = ((TextView)v).getText().toString();
            mBtAddress = info.substring(info.length() - 17);
            
            int index = info.indexOf("PCOBT-");
            TextView et = (TextView)findViewById(R.id.read_id);
            try{
            	et.setText(info.substring(index + 6, index + 10));
            }
            catch(Exception ex){
            	et.setText("");
            }
            
        }
    };

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(device!=null){
                	if (device.getBondState() != BluetoothDevice.BOND_BONDED && device.getName().toLowerCase().indexOf(PREFIX.toLowerCase()) >= 0) {
                        mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                    }
                }
            }
        }
    };

    

	@Override
	public void taskSuccess(Object... param) {
		
	}

	@Override
	public void taskFailed(Object... param) {
		if(D2EConfigures.TEST){
			Log.e("D2EDeviceListActivity------------->", "taskFailed()");
		}
		hideWaitDialog();
		dismissProcessDialog();

		int values = ((Integer) param[0]).intValue();
		if (values == YTBaseService.BT_SERVICE_INT) {
			int state = ((Integer) param[1]).intValue();

			if (state == SOPBluetoothService.BT_DISCONNECT) {
				if(YTBaseApplication.mIsTryConnect){
					
					YTBaseApplication.mIsTryConnect = true;
					YTBaseApplication.g_Service_runState = false;
					((YTBaseApplication) getApplication()).stopJJService();
				}
				
				
			}else if(state == SOPBluetoothService.BT_CONNECT){

				if(YTBaseApplication.mIsTryConnect){
					YTBaseApplication.mIsTryConnect = false;
					YTBaseApplication.mIsConnected = true;
					Intent intentCao=new Intent(D2EDeviceListActivity.this,PHCChaoShengBo.class);
					Bundle bundle=new Bundle();
					bundle.putString("OrgID", mOrgID);
					bundle.putString("AreaID", mAreaID);
					bundle.putString("Type", mType);
					bundle.putString("Person", mPerson);
					intentCao.putExtras(bundle);
					D2EDeviceListActivity.this.startActivity(intentCao);
					finish();
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
		
	}

	public void backAciton() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
		finish();
	}

	@Override
	public boolean isExit() {
		return false;
	}

	@Override
	public void onBackAciton() {
		
	}

}
