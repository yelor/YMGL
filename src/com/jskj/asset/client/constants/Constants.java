/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.constants;

import java.awt.Font;

/**
 *
 * @author woderchen
 */
public class Constants {

    /*HTTP conifg*/
    public static String HTTP = "http://127.0.0.1:8084";
    public static String APPID = "/AssetsSys/service/";
    public static int OK_DELETE = 100;
    public static int HTTP_NOT_FOUND = 404;
    /*字体设置*/
    public final static Font GLOBAL_FONT = new Font("SimSun", Font.PLAIN, 12);
    public final static Font TREE_FONT = new Font("SimSun", Font.PLAIN, 18);
    /*Global config*/
    public static String WINTITLE = "疫苗资产管理系统";
    public static int LOGIN_WIDTH = 395;
    public static int LOGIN_HEIGHT = 270;

    /*左边树*/
    public final static String VIEW_TABLES_TREE = "com.jskj.asset.client.panel.LeftPanel";
    
    /*登陆后窗口显示大小*/
    public static int MAINFRAME_WIDTH = 1000;
    public static int MAINFRAME_HEIGHT = 700;

    /*附件上传控制*/
    public static String[] ATTACHMENT_PLUS = new String[]{"jar"};
    public static String[] ATTACHMENT_ICONS = new String[]{"ico", "jpg", "gif", "bmp"};

    /*后台功能配置*/
    /*plus*/
    public static String PLUS_EXEC = "Main-Class";
}
