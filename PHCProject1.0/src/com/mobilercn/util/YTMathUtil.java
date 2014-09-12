package com.mobilercn.util;

import android.content.res.Resources;
import android.util.TypedValue;

public class YTMathUtil {

	
	public static float dipToPixels(float dip, Resources resource) {
		
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, resource.getDisplayMetrics());
		return px;
		
	}

}
