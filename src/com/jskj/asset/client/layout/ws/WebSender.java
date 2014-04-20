/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout.ws;

import java.net.URI;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author 305027939
 */
public class WebSender extends RestTemplate {

    private static final Log log = LogFactory.getLog(WebSender.class);

    public WebSender(ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
    }

    public <T extends Object> T getForObject(String url, Class<T> responseType) throws RestClientException {
        try {
            T obj = super.getForObject(url, responseType);
            return obj;
        } catch (Exception e) {
            throw new RestClientException("" + e.getLocalizedMessage());
        }

    }

    public <T extends Object> T postForObject(String url, Object request, Class<T> responseType) throws RestClientException {
        try {
            T obj = super.postForObject(url, request, responseType);;
            return obj;
        } catch (Exception e) {
            throw new RestClientException("" + e.getLocalizedMessage());
        }

    }

    public URI postForLocation(String url, Object request) throws RestClientException {
        try {
            URI uri = super.postForLocation(url, request);
            return uri;
        } catch (Exception e) {
            throw new RestClientException("" + e.getLocalizedMessage());
        }

    }

    public <T extends Object> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType) throws RestClientException {
        try {
            ResponseEntity<T> res = super.exchange(url, method, requestEntity, responseType);
            return res;
        } catch (Exception e) {
            throw new RestClientException("" + e.getLocalizedMessage());
        }
    }

}
