/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.constants;

import java.awt.Font;
import javax.swing.ImageIcon;

/**
 *
 * @author woderchen
 */
public class Constants {

    public final static String VERSION = "v1.00";
    public final static ImageIcon logoIcon = new ImageIcon(Constants.class.getResource("/com/jskj/asset/client/resources/icon.png"));

    /*HTTP conifg*/
    public static String HTTP = "";
    public static String APPID = "/AssetsSys/service/";
    public static String SERVICE_IP="";
    public static String SERVICE_PORT="";
    public static int OK_DELETE = 100;
    public static int HTTP_NOT_FOUND = 404;
    /*字体设置*/
    public final static Font GLOBAL_FONT = new Font("微软雅黑", Font.PLAIN, 14 );
    public final static Font TREE_FONT = new Font("微软雅黑", Font.PLAIN, 18 );
    public final static Font FONT_12 = new Font("微软雅黑", Font.PLAIN, 12 );
    
    /*Global config*/
    public static String WINTITLE = "广安疾控资产管理系统";
    public static int LOGIN_WIDTH = 360;
    public static int LOGIN_HEIGHT = 274;

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
    
    /*用于登陆设置*/
    public static void setHTTPHost(String ip,String port){
       HTTP = "http://"+ip+":"+port;
       SERVICE_IP = ip;
       SERVICE_PORT = port;
    }
}
