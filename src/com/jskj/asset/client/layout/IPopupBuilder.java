/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import java.util.HashMap;

/**
 *
 * @author 305027939
 */
/*
 IPopupBuilder需要以下的参数，请完善那些接口方法即可
 getType：目前就2个，一个是点鼠标左键弹出的日期的popup>>TYPE_DATE_CLICK,一个是点回车弹出的popup>>TYPE_POPUP_TEXT
 getWebServiceURI:webservice URL接口。这个很重要，就是弹出popup，将要访问哪个接口。在服务端定义这个接口的时候，记得返回的bean，
 一定有2个参数，一个是 （int）count，另一个是（List）result。
 getConditionSQL:这里的SQL语句注意是数据库的字段名，不是entity名。 
 displayColumns：popup弹出来后，显示的内容
 setBindedMap:点击popup返回的值，自己把object的值类型强制转化成自己需要。和你在regiter的时候，传入的URL返回bean有关。
 */
public interface IPopupBuilder {

    public static final int TYPE_DATE_CLICK = 1;
    public static final int TYPE_POPUP_TEXT = 2;
    public static final int TYPE_POPUP_TABLE = 3;

    public final String ICON_DATE = "/com/jskj/asset/client/resources/date.gif";
    public final String ICON_POPUP_TEXT = "/com/jskj/asset/client/resources/search.gif";
    public final String ICON_POPUP_TABLE = "/com/jskj/asset/client/resources/tablePopup.gif";

    public int getType();

    public String getWebServiceURI();

    public String getConditionSQL();

    public String[][] displayColumns();

    public void setBindedMap(HashMap bindedMap);

}
