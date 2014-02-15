/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.login;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.UserSessionEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.util.BeanFactory;
import java.util.HashMap;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author 305027939
 */
public abstract class LoginTask extends Task<Object, Void> {

    private final String URI = Constants.HTTP + Constants.APPID + "login";
    private HashMap map;

    public LoginTask(HashMap map) {
        super(Application.getInstance(AssetClientApp.class));
        this.map = map;
    }

    @Override
    protected Object doInBackground() throws Exception {
        try {
            RestTemplate restTemplate = (RestTemplate) BeanFactory.instance().createBean(RestTemplate.class);

            Object userNameObj = map.get("userName");
            Object passwdObj = map.get("userPassword");
            /*add http header*/
            HttpComponentsClientHttpRequestFactory httpRequestFactory = (HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory();
            DefaultHttpClient httpClient = (DefaultHttpClient) httpRequestFactory.getHttpClient();
            httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(userNameObj.toString(), passwdObj.toString()));

            UserSessionEntity session = restTemplate.getForObject(java.net.URI.create(URI),UserSessionEntity.class);
            return session;
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
    }

    @Override
    protected void succeeded(Object object) {
        onSucceeded(object);
    }

    public abstract void onSucceeded(Object object);

}
