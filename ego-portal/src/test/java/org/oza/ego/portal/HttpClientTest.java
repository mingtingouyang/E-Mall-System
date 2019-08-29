package org.oza.ego.portal;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class HttpClientTest {

    //无参数 get 请求
    @Test
    public void doGet() {
        //创建 HttpClient 对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建 Get 请求
        HttpGet httpGet = new HttpGet("http://www.baidu.com/");
        CloseableHttpResponse response = null;
        try {
            //执行请求
            response = httpClient.execute(httpGet);
            //输出请求状态
            System.out.println(response.getStatusLine());
            //判断请求状态并输出响应内容
            if (response.getStatusLine().getStatusCode() == 200){
                //使用工具将响应实体字符串化
                String content = EntityUtils.toString(response.getEntity());
                System.out.println("content:\n" + content);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (null != httpClient) {
                        try {
                            httpClient.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    //get请求带参数
    @Test
    public void doGetWithParam() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        try {
            //定义请求的参数，使用 URIBuilder 工具创建
            URI uri = new URIBuilder("http://www.baidu.com/s").setParameter("wd", "你好世界").build();
            System.out.println("URI: " + uri);
            //创建请求对象
            HttpGet httpGet = new HttpGet(uri);
            //execute
            response = httpClient.execute(httpGet);
            //判断响应状态
            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println(EntityUtils.toString(response.getEntity()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (null != httpClient) {
                        try {
                            httpClient.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    //POST 请求
    @Test
    public void post() {
        //创建 HttpClient 对象
        //CloseableHttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建 post 请求
        HttpPost httpPost = new HttpPost("http://localhost:8080/item/536563");

        CloseableHttpResponse response = null;

        try {
            //执行请求
            response = httpClient.execute(httpPost);
            System.out.println(response.getStatusLine());
            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println(EntityUtils.toString(response.getEntity()));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (null != httpClient) {
                        try {
                            httpClient.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    //执行 post 请求带参数
    @Test
    public void postWithParam() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/item/cat/list");
        //可以设置多个参数
        ArrayList<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("id", "2"));
        //将参数集合封装成请求实体
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters);
        //将请求实体放入请求中
        httpPost.setEntity(entity);
        //执行请求
        CloseableHttpResponse response = httpClient.execute(httpPost);

        //输出结果
        System.out.println(response.getStatusLine());
        if (response.getStatusLine().getStatusCode() == 200) {
            System.out.println(EntityUtils.toString(response.getEntity()));
        }
        //关闭资源
        if (null != response) {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != httpClient) {
                    try {
                        httpClient.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
