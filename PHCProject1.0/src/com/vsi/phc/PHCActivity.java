package com.vsi.phc;

import com.mobilercn.widget.YTReportModel;
import com.mobilercn.widget.YTReportView;
import com.vsi.page.PHCLoginPage;
import com.vsi.phc.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;


/**
 *进入程序动画界面 
 */
public class PHCActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.public_logo_page);
        
        Window window = getWindow();
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.public_title_bar);
		View view = this.findViewById(R.id.logo);
		
		Animation animation = (Animation)AnimationUtils.loadAnimation(PHCActivity.this, R.anim.wave_scale);
		animation.setDuration(200); 
		view.startAnimation(animation);	
		animation.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {}

			public void onAnimationRepeat(Animation animation) {}

			public void onAnimationEnd(Animation animation) {
														
				Intent intent = new Intent(PHCActivity.this, PHCLoginPage.class);
				PHCActivity.this.startActivity(intent);
				finish();
				
			}
			
		});
		
    }
}