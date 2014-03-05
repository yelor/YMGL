/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.bean;

import com.jskj.asset.client.bean.entity.Appparam;
import com.jskj.asset.client.layout.ws.CommFindEntity;
import com.jskj.asset.client.panel.user.ParamFindTask;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.jdesktop.application.Task;

/**
 *
 * @author 305027939
 */
public class ParamSession {

    private static ParamSession seesion;
    private static List<Appparam> currentPageData;
    private static HashMap<String, List<String>> paramMap;

    private ParamSession() {
        paramMap = new HashMap<String, List<String>>();
    }

    public static ParamSession getInstance() {
        if (seesion == null) {
            seesion = new ParamSession();

        }
        return seesion;
    }

    public Task buildParamSession(Task afterTask) {
        return new RefreshTask(afterTask);
    }

    public List<String> getChildNameByParentName(String parentParamName) {
        if (paramMap != null) {
            Set<String> keySet = paramMap.keySet();
            for (String key : keySet) {
                if (parentParamName.equalsIgnoreCase(key)) {
                    return paramMap.get(key);
                }
            }
        }
        List temp = new ArrayList();
        temp.add("[ERROR:NO "+parentParamName+"]");
        return temp;
    }

    public String[] getParamNamesByType(String type) {
        List<String> result = new ArrayList<String>();
        if (currentPageData != null) {
            for (Appparam param : currentPageData) {
                if (type.equalsIgnoreCase(param.getAppparamType())) {
                    result.add(param.getAppparamName());
                }
            }
        }
        String[] temp = null;
        if (result.size() > 0) {
            temp = new String[result.size()];
            result.toArray(temp);
        }else{
           temp = new String[]{"[ERROR:NO "+type+"]"};
        }
        return temp;
    }

    public List<Appparam> getParamsByType(String type) {
        List<Appparam> result = new ArrayList<Appparam>();
        if (currentPageData != null) {
            for (Appparam param : currentPageData) {
                if (type.equalsIgnoreCase(param.getAppparamType())) {
                    result.add(param);
                }
            }
        }
        return result;
    }

    private static class RefreshTask extends ParamFindTask {

        private Task afterTask;
        RefreshTask(Task afterTask) {
            super(1, 100000, "appparam/", "");
            this.afterTask = afterTask;
        }

        @Override
        public void responseResult(CommFindEntity<Appparam> response) {
            System.out.println("CommFindEntity<Appparam> count:" + response.getCount());
            //存下所有的数据
            currentPageData = response.getResult();

            List<Appparam> pdata = new ArrayList<Appparam>();

            //找到所有的父节点
            for (Appparam param : currentPageData) {
                if (param.getAppparamPid() == null || param.getAppparamPid() <= 0) {
                    pdata.add(param);
                    //System.out.println("found parent param:" + param.getAppparamName());
                }
            }

            //找到所有父节点的子节点
            for (Appparam pparam : pdata) {
                List childParam = new ArrayList();
                paramMap.put(pparam.getAppparamName(), childParam);
                for (Appparam param : currentPageData) {
                    if (pparam.getAppparamId() == param.getAppparamPid()) {
                        //找到子节点
                        childParam.add(param.getAppparamName());
                    }

                }

            }

            afterTask.execute();
        }
    }

}
