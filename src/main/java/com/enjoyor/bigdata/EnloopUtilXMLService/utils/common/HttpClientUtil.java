package com.enjoyor.bigdata.EnloopUtilXMLService.utils.common;

import com.enjoyor.bigdata.EnloopUtilXMLService.exception.IORuntimeException;
import com.enjoyor.bigdata.EnloopUtilXMLService.service.impl.XMLServiceImpl;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.validator.ParamAssert;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/11/28 14:06
 * @email aaron19940628@gmail.com
 * @date 2017/11/28 14:06.
 * @description
 */
public class HttpClientUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        List<NameValuePair> paramList = new ArrayList<>();
        String resultString = "";

        // 创建Http Post请求
        HttpPost httpPost = new HttpPost(url);
        ParamAssert.isNull(param, "POST 参数不能为空");
        // 创建参数列表
        for (String key : param.keySet()) {
            paramList.add(new BasicNameValuePair(key, param.get(key)));
        }
        try {
            // 创建模拟表单
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
            httpPost.setEntity(entity);

            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            LOGGER.warn("执行POST请求时发生错误");
            throw new RuntimeException("执行POST请求时发生错误");
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                LOGGER.warn("关闭HttpClient POST连接时失败！");
                throw new IORuntimeException(CloseableHttpResponse.class, "关闭HttpClient POST连接时失败！");
            }
        }
        return resultString;
    }
}
