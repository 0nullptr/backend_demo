package com.demo.controller;

import java.util.HashMap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONObject;
import com.demo.properties.WeChatAppletProperties;

@RestController
@SpringBootApplication
@CrossOrigin
public class LoginController {
    @Autowired
    private WeChatAppletProperties weChatAppletProperties;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public HashMap<String, Object> login(HttpServletRequest request, @RequestBody String json) throws Exception {
        JSONObject jsonObject = JSONObject.parse(json);
        String code = jsonObject.getString("code");
        String state = jsonObject.getString("state");
        String url = "https://api.weixin.qq.com/sns/jscode2session"
                + "?appid=" + weChatAppletProperties.getAppid()
                + "&secret=" + weChatAppletProperties.getSecret()
                + "&js_code=" + code
                + "&grant_type=" + weChatAppletProperties.getGrantType();
        CloseableHttpClient httpClient = HttpClientBuilder
                .create()
                .build();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000)
                .setRedirectsEnabled(false).build();
        httpGet.setConfig(requestConfig);
        response = httpClient.execute(httpGet);
        HttpEntity responseEntity = response.getEntity();
        String res = null;
        if (responseEntity != null) {
            res = EntityUtils.toString(responseEntity);
        }
        if (httpClient != null) {
            httpClient.close();
        }
        if (response != null) {
            response.close();
        }
        JSONObject jObject = JSONObject.parse(res);
        HashMap<String, Object> hashMap = new HashMap<>();
        if (jObject.containsKey("errcode")) {
            hashMap.put("state", 0);
            hashMap.put("errcode", jObject.getInteger("errcode"));
            hashMap.put("errmsg", jObject.getString("errmsg"));
        } else {
            hashMap.put("state", 1);
            hashMap.put("openid", jObject.getString("openid"));
            hashMap.put("unionid", jObject.getString("unionid"));
            hashMap.put("session_key", jObject.getString("session_key"));
            HttpSession session = request.getSession();
            session.setAttribute("open_id", jObject.getString("openid"));
            redisTemplate.opsForValue().set("open_id:" + jObject.getString("openid"), session.getId());

            String UnionID = jObject.getString("unionid");
            Connection Conn;
            String driver = "com.mysql.jdbc.Driver";
            String sqlurl = "jdbc:mysql://localhost:3306/dishmanagedatabase?useUnicode=true&characterEncoding=utf-8&useSSL=false";
            String user = "root";
            String password = "root";
            try {
                Class.forName(driver);
                Conn = DriverManager.getConnection(sqlurl, user, password);
                Statement statement = Conn.createStatement();
                String sql1, sql2;
                if (state.equals("0")) {
                    sql1 = "select * from Staff where StuffID = '" + UnionID + "';";                    
                } else {
                    sql1 = "select * from Parent where ParentID = '" + UnionID + "';";  
                }
                ResultSet resultSet = statement.executeQuery(sql1);
                if(!resultSet.next()) {
                    if (state.equals("0")) {
                        sql2 = "insert into Staff values('" + UnionID + "');";                    
                    } else {
                        sql2 = "insert into Parent values('" + UnionID + "');";      
                    }
                    statement.execute(sql2);
                }              
                resultSet.close();
                Conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        
        return hashMap;
    }
}
