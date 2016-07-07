package com.tsingda.simple.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class HttpUtil {

    public static final String DEFAULT_CHARSET = "UTF-8";

    public static String post(String url, List<NameValuePair> parameters, String charset)
            throws ClientProtocolException, IOException {
        HttpPost post = new HttpPost(url);
        // post.setHeader("Content-Type","application/json");
        post.setEntity(new UrlEncodedFormEntity(parameters, charset));
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        CloseableHttpClient client = clientBuilder.build();
        BasicResponseHandler handler = new BasicResponseHandler();
        String response = client.execute(post, handler);
        client.close();
        return response;
    }

    public static String postFile(String url, File file, String fileDesc, Map<String, String> parameters)
            throws ClientProtocolException, IOException {
        return postFile(url, file, fileDesc, parameters, DEFAULT_CHARSET);
    }

    /**
     * 传文件
     *@param url 请求地址
     *@param file 文件
     *@param fileDesc 文件说明（文件请求参数名称）
     *@param parameters key-value请求参数
     *@param charset 字符编码
     *@return 请求结果
     *@throws ClientProtocolException 请求协议异常
     *@throws IOException IO异常
     */
    public static String postFile(String url, File file, String fileDesc, Map<String, String> parameters, String charset)
            throws ClientProtocolException, IOException {
        HttpPost post = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.forName(charset));
        builder.addBinaryBody(fileDesc, file);
        if (parameters != null && parameters.size() > 0) {
            Set<String> keySet = parameters.keySet();
            for (String key : keySet) {
                builder.addTextBody(key, parameters.get(key),
                        ContentType.create("text/plain", Charset.forName(charset)));
            }
        }
        HttpEntity entity = builder.build();
        post.setEntity(entity);
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        CloseableHttpClient client = clientBuilder.build();
        BasicResponseHandler handler = new BasicResponseHandler();
        String response = client.execute(post, handler);
        client.close();
        return response;
    }

    public static String post(String url, List<NameValuePair> parameters) throws ClientProtocolException, IOException {
        return post(url, parameters, DEFAULT_CHARSET);
    }

    public static String post(String url, Map<String, Object> postParam) throws ClientProtocolException, IOException {
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();

        for (Map.Entry<String, Object> entry : postParam.entrySet()) {
            parameters.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
        }

        return post(url, parameters, DEFAULT_CHARSET);
    }

    public static String get(String url) throws ClientProtocolException, IOException {
        HttpGet get = new HttpGet(url);
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        CloseableHttpClient client = clientBuilder.build();
        BasicResponseHandler handler = new BasicResponseHandler();
        String response = client.execute(get, handler);
        client.close();
        return response;
    }

    public static String get(String url, List<NameValuePair> parameters) throws ClientProtocolException, IOException {
        StringBuilder sb = new StringBuilder();
        if (url.contains("?")) {
            sb.append(url).append("&");
        } else {
            sb.append(url).append("?");
        }
        for (int i = 0; i < parameters.size(); i++) {
            sb.append(parameters.get(i).getName()).append("=").append(parameters.get(i).getValue());
            if (i < parameters.size() - 1) {
                sb.append("&");
            }
        }
        return get(sb.toString());
    }

    public static String get(String url, Map<String, Object> parameters) throws ClientProtocolException, IOException {
        StringBuilder sb = new StringBuilder();
        if (url.contains("?")) {
            sb.append(url).append("&");
        } else {
            sb.append(url).append("?");
        }
        Set<String> keySet = parameters.keySet();
        for (String key : keySet) {
            sb.append(key).append("=").append(parameters.get(key)).append("&");
        }
        url = sb.toString();
        if (url.endsWith("&") || url.endsWith("?")) {
            url = url.substring(0, url.length() - 1);
        }
        return get(sb.toString());
    }

    public static void main(String[] args) throws ClientProtocolException, IOException, InterruptedException {

        List<Integer> agentIds = new ArrayList<Integer>(); agentIds.add(203);
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        data.add(new BasicNameValuePair("deviceId", "")); data.add(new
        BasicNameValuePair("startTime", "")); data.add(new
        BasicNameValuePair("endTime", "")); data.add(new
        BasicNameValuePair("agentIds", JsonUtil.stringify(agentIds)));
        data.add(new BasicNameValuePair("pageIndex", "1")); data.add(new
        BasicNameValuePair("pageSize", "10"));

        String aaa =
        post("http://192.168.2.140/am/device/getAgentDeviceInfoList", data);

        System.out.println(aaa);
        /*
         * Map<String, Object> pm = new HashMap<String, Object>();
         * pm.put("name", "adsfasdf"); pm.put("age", 18); String res =
         * get("http://192.168.2.123:3000/tv", pm); System.out.println(res);
         * 
         * List<NameValuePair> pl = new ArrayList<NameValuePair>(); pl.add(new
         * BasicNameValuePair("FacilityID", "Tsingda123456"));
         * System.out.println(get("http://192.168.2.123:3000/tv", pl));
         * 
         * List<NameValuePair> parameters = new ArrayList<NameValuePair>();
         * parameters.add(new BasicNameValuePair("FacilityID",
         * "Tsingda123456")); String result = HttpUtil.post(
         * "http://192.168.2.167:10001/api/ManageOrderInfo/GetFacilityStatistics"
         * , parameters);
         * 
         * CollectionType type = JsonUtil.constructCollectionType(List.class,
         * Map.class); List<Map<String, Object>> r = JsonUtil.parse(result,
         * type); for (Map<String, Object> map : r) {
         * System.out.println(map.get("FacilityID"));
         * System.out.println(map.get("OrderCount")); }
         * System.out.println(result);
         */
    }
}
