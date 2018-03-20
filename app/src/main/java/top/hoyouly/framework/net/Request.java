package com.example.hoyouly.myframework.net;

import com.example.hoyouly.myframework.net.inter.ICallback;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by hoyouly on 15/5/3.
 * 请求的实体类
 */
public class Request {

    public enum RequestMethod {//请求方法
        GET, POST, DELETE, PUT;
    }

    public RequestMethod method;

    public String url;
    public String postContent;
    public final static String ENCODING = "UTF-8";
    public Map<String, String> header;
    public HttpEntity entity;
    public ICallback callback;

    public Request(String url, RequestMethod method) {
        this.url = url;
        this.method = method;
    }

    public void setCallback(ICallback callback) {
        this.callback = callback;
    }

    public void execute() {
        RequestTask task = new RequestTask(this);
        task.execute();
    }

    /**
     * 通过表单形式设置 entity
     *
     * @param postContent
     */
    public void setEntity(String postContent) {
        try {
            entity = new StringEntity(postContent, ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    /**
     * 通过字符串形式设置entity
     *
     * @param forms
     */
    public void setEntity(ArrayList<NameValuePair> forms) {
        try {
            entity = new UrlEncodedFormEntity(forms, ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    /**
     * 通过字节数组进行设置entity
     *
     * @param bytes
     */
    public void setEntity(byte[] bytes) {
        entity = new ByteArrayEntity(bytes);
    }
}
