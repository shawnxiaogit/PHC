/*****************************************************
 * 项目名称         T68 Tag Reader
 * 包名称           com.mobilercn.app
 * 文件名称         OnMenuSelection.java
 * 编写者           wu.s.w
 *                 www.mobilercn.com
 * 编写日期          2011-12-19
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
