/*****************************************************
 * ��Ŀ����         T68 Tag Reader
 * ������           com.mobilercn.app
 * �ļ�����         OnMenuSelection.java
 * ��д��           wu.s.w
 *                 www.mobilercn.com
 * ��д����          2011-12-19
 * Version         1.0
 * 
 * 
 *****************************************************/
package com.mobilercn.widget;

public interface OnMenuSelection {
	
    public static final int MENU_TOP    = 0;
    public static final int MENU_LEFT   = 1;
    public static final int MENU_RIGHT  = 2;
    public static final int MENU_BOTTOM = 3;
    public static final int MENU_NONE   = 4;

    public abstract void menuSelection(int index);
}
