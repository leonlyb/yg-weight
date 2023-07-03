package com.example.dechuan.utils;

import com.alibaba.fastjson.JSONObject;
import com.example.dechuan.model.kanban.RequestParma;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;

/**
 * http 请求工具类
 * @author
 * @version 1.00.00
 * 修改记录
 * 修改后版本:
 * 修改人：
 * 修改日期:
 * 修改内容:
 */
public class HttpClientUtils {
    /**
     * 看板url
     */
    public static final String posturl="https://uatkanban.unilever-china.com/kanban-web-api/weighbridge/in";

    public static String doApiPost(RequestParma rp) {
        String url=posturl;
        // 创建httpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建post请求方式实例
        HttpPost httpPost = new HttpPost(url);
        // 设置请求头 发送的是json数据格式
        httpPost.setHeader("Content-type", "application/json;charset=utf-8");
        httpPost.setHeader("Connection", "Close");
        httpPost.setHeader("Authorization", "Basic " + Base64.getUrlEncoder().encodeToString(("d2VpZ2hicmlkZ2UtY2xpZW50X2lk" + ":" + "d2VpZ2hicmlkZ2UtY2xpZW50X3NlY3JldA==").getBytes()));
        // 设置参数---设置消息实体 也就是携带的数据
        JSONObject jsonParam=new JSONObject();
//        if(bct.getPotNum() != null){
//            jsonParam.put("potNum", bct.getPotNum());
//        }
//        if(bct.getStartDate() != null){
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            jsonParam.put("beginDate", sdf.format(bct.getStartDate()));
//            jsonParam.put("endDate",  sdf.format(bct.getEndDate()));
//        }
        jsonParam.put("plateNo", "1111");
        jsonParam.put("weight", "200.0");
        jsonParam.put("date", "2023-02-15");
        jsonParam.put("uniqueCode", "1111");
        StringEntity entity = new StringEntity(jsonParam.toString(), Charset.forName("UTF-8"));
        // 设置编码格式
        entity.setContentEncoding("UTF-8");
        // 发送Json格式的数据请求
        entity.setContentType("application/json");
        // 把请求消息实体塞进去
        httpPost.setEntity(entity);
        // 执行http的post请求
        CloseableHttpResponse httpResponse;
        String result = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(result);
            result = jsonObject.getString("data");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        RequestParma rp = new RequestParma();
        doApiPost(rp);
    }

}
