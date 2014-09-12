package com.mobilercn.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SaveLoginParams {
	private static final String PREF_NAME_N="myPHC_N";
	private static final String PREF_NAME_P="myPHC_P";
	//保存登录用户名
	public static void saveParamsName(Context context,String username){
		SharedPreferences sp=
				context.getSharedPreferences(PREF_NAME_N, Activity.MODE_PRIVATE);
		sp.edit()
		.putString("una", username)
		.commit();
	} 
	//保存登录密码
	public static void saveParamsPwd(Context context,String password){
		SharedPreferences sp=
				context.getSharedPreferences(PREF_NAME_P, Activity.MODE_PRIVATE);
		sp.edit()
		.putString("pwd", password)
		.commit();
	}
	//获取登录用户名
	public static String[] getParamsName(Context context){
		SharedPreferences sp=
				context.getSharedPreferences(PREF_NAME_N, Activity.MODE_PRIVATE);
		String loginParams[]=new String[1];
		loginParams[0]=sp.getString("una", "");
		return loginParams;
	} 
	//获取登录密码
	public static String[] getParamsPwd(Context context){
		SharedPreferences sp=
				context.getSharedPreferences(PREF_NAME_P, Activity.MODE_PRIVATE);
		String loginParams[]=new String[1];
		loginParams[0]=sp.getString("pwd", "");
		return loginParams;
	}
	//删除登录的用户名
	public static void deletParamsName(Context context){
		SharedPreferences sp=
				context.getSharedPreferences(PREF_NAME_N, Activity.MODE_PRIVATE);
		sp.edit()
		.remove("una")
		.commit();
	}
	//删除登录的密码
	public static void deletParamsPwd(Context context){
		SharedPreferences sp=
				context.getSharedPreferences(PREF_NAME_P, Activity.MODE_PRIVATE);
		sp.edit()
		.remove("pwd")
		.commit();
	}
}
