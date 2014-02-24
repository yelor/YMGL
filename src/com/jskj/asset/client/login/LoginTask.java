/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.login;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.UserSessionEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.BaseTreePane;
import com.jskj.asset.client.layout.ws.ComResponse;
import com.jskj.asset.client.util.BeanFactory;
import com.jskj.asset.client.util.UnicodeConverter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author 305027939
 */
public abstract class LoginTask extends Task<Object, Void> {

    private final String URI = Constants.HTTP + Constants.APPID + "login";
    private HashMap map;
    private boolean logined;

    public LoginTask(HashMap map, boolean logined) {
        super(Application.getInstance(AssetClientApp.class));
        this.map = map;
        this.logined = logined;
    }

    @Override
    protected Object doInBackground() throws Exception {
        try {
            RestTemplate restTemplate = (RestTemplate) BeanFactory.instance().createBean(RestTemplate.class);

            if (logined) {
                ComResponse<String> com = restTemplate.getForObject(java.net.URI.create(Constants.HTTP + Constants.APPID + "logout"), ComResponse.class);
                if (com.getResponseStatus() != ComResponse.STATUS_OK) {
                    return new Exception("logout failed. ");
                } else {
                    BaseTreePane.disTabCount.clear();
                }
            }

            Object userNameObj = map.get("userName");;
            Object passwdObj = map.get("userPassword");

            HttpComponentsClientHttpRequestFactory httpRequestFactory = (HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory();
            DefaultHttpClient httpClient = (DefaultHttpClient) httpRequestFactory.getHttpClient();
            String unicodeStr = UnicodeConverter.toEncodedUnicode(userNameObj.toString(), false);
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(unicodeStr, passwdObj.toString());
            httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, credentials);

            UserSessionEntity session = restTemplate.getForObject(java.net.URI.create(URI), UserSessionEntity.class);

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
