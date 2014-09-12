package com.mobilercn.sop.bt;

import android.util.Log;

import com.mobilercn.util.JJHexHelper;



public class SOPBTCallHelper {
	
	public static final int READ_TAG     = 1;//
	public static final int TAG_LOCATION = 2;
	public static final int TAG_RETURN   = 3;
	public static final int TAG_CHANGE   = 4;
	public static final int TAG_CHECKIN  = 5;//员工签到 
	public static final int TAG_GET_EMPLOEE_NUM=6;//获取员工卡
	
	public static final int STATE_RESPONSE = -1;
	
	public static final String PREFIX = "BB";
	public static final String SUFFIX = "EE";
	public static final String ORDER  = "7E";


	//---------------------读取命令-------------------------
	public static byte[] getInitOrder(String readerID){
		
		StringBuffer sb = new StringBuffer(PREFIX);
		
		sb.append(ORDER);
		
		sb.append("0601").append(readerID).append("00");
		
		sb.append(SUFFIX);
				
		return JJHexHelper.decode(sb.toString());
	}

}
