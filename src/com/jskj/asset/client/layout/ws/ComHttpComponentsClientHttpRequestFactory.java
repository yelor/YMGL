/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout.ws;

import com.jskj.asset.client.constants.Constants;
import java.net.URI;
import java.nio.charset.Charset;
import org.apache.http.HttpHost;
import org.apache.http.client.AuthCache;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

/**
 *
 * @author 305027939
 */
public class ComHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {

    
    public ComHttpComponentsClientHttpRequestFactory(HttpClient httpClient) {
        super(httpClient);
    }

//    @Override
//    protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri) {
//        return createHttpContext();
//    }
//
//    private HttpContext createHttpContext() {
//        HttpHost host = new HttpHost(Constants.SERVICE_PORT,Integer.parseInt(Constants.SERVICE_PORT));
//// Create AuthCache instance       
//        AuthCache authCache = new BasicAuthCache();
//// Generate BASIC scheme object and add it to the local auth cache        
//        BasicScheme basicAuth = new BasicScheme(Charset.forName("UTF-8"));
//        authCache.put(host, basicAuth);
//        // Add AuthCache to the execution context       
//        BasicHttpContext localcontext = new BasicHttpContext();
//        localcontext.setAttribute(ClientContext.AUTH_CACHE, authCache);
//        return localcontext;
//    }
}
