package org.oza.ego.base.utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtils {

    /**
     * 执行 get 请求，可以有参数也可以无参数
     * @param url 请求地址
     * @param params 请求参数
     * @return 响应实体，字符串格式
     */
    public static String doGet(String url, Map<String, String> params){
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = null;
        try {
            httpClient = HttpClients.createDefault();
            URIBuilder uriBuilder = new URIBuilder(url);

            //若参数不为空
            if (null != params) {
                //循环设置参数
                for (String key : params.keySet()) {
                    uriBuilder.setParameter(key, params.get(key));
                }
            }

            //根据Uri创建get请求
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            //执行
            response = httpClient.execute(httpGet);

            if (200 == response.getStatusLine().getStatusCode()) {
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        httpClient.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return result;
    }

    /**
     * 执行 post 请求，可以有参数也可以无参数
     * @param url 请求地址
     * @param params 请求参数
     * @return 响应实体，字符串格式
     */
    public static String doPost(String url, Map<String, String> params) {
        //4.0以上的HttpClient版本中，post请求需要指定重定向策略，不指定则按默认的重定向策略
        CloseableHttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        CloseableHttpResponse response = null;
        String result = null;

        try {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> pairs = new ArrayList<>();

            //如果参数不为空，循环将参数设置到 pairs 集合里
            if (null != params) {
                for (String key : params.keySet()) {
                    pairs.add(new BasicNameValuePair(key, params.get(key)));
                }
            }
            //将参数放入请求实体
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs);
            //添加请求实体
            httpPost.setEntity(entity);
            //添加请求头，应对反爬虫机制，将自己伪装成浏览器
            httpPost.addHeader("user-agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
            //执行请求
            response = httpClient.execute(httpPost);

            if (200 == response.getStatusLine().getStatusCode()) {
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        httpClient.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }
}
