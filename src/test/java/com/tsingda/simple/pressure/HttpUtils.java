package com.tsingda.simple.pressure;

import java.io.IOException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpUtils {
    
    public static String post(String url, List<NameValuePair> parameters) throws ClientProtocolException, IOException{
        HttpPost post = new HttpPost(url);
        post.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        CloseableHttpClient client = clientBuilder.build();
        ResponseHandler<String> handler = new BasicResponseHandler();
        String response = client.execute(post, handler);
        client.close();
        return response;
    }
    
    public static String get(String uri) throws ClientProtocolException, IOException{
        HttpGet get = new HttpGet(uri);
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        CloseableHttpClient client = clientBuilder.build();
        ResponseHandler<String> handler = new BasicResponseHandler();
        String response = client.execute(get, handler);
        client.close();
        return response;
    }
}
