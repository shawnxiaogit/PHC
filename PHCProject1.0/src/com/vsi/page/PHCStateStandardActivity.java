package com.vsi.page;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import com.mobilercn.util.YTNetUtil;
import com.vsi.phc.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

/**
 * 国家标准显示文档界面
 *
 */
public class PHCStateStandardActivity extends Activity{
	
	private TextView mTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phc_state_standar);
		Window window = getWindow();
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.public_title_bar);
        mTextView=(TextView) this.findViewById(R.id.phc_state_standard);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		byte[] bytes = null;
		try {
			InputStream is=this.getAssets().open("insect_pest_state_standard.txt");
			bytes = YTNetUtil.readByByte(is, -1);
			if(bytes != null && bytes.length > 0){
				mTextView.setText(new String(bytes, "UTF-8"));				
			}
			
		} catch (IOException e) {
			if(bytes != null && bytes.length > 0){
				mTextView.setText(new String(bytes));				
			}
		}

	}
	
}	
