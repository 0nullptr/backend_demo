package com.demo.controller;

import java.util.HashMap;

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
import com.demo.service.ParentService;
import com.demo.service.StaffService;

@RestController
@SpringBootApplication
@CrossOrigin
public class LoginController {
    @Autowired
    private WeChatAppletProperties weChatAppletProperties;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private StaffService staffService;

    @Autowired
    private ParentService parentService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public HashMap<String, Object> login(HttpServletRequest request, @RequestBody String json) throws Exception {
        JSONObject jsonObject = JSONObject.parse(json);
        String code = jsonObject.getString("code");
        int state = jsonObject.getIntValue("state");
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

            Long UnionID = Long.valueOf(jObject.getString("unionid"));
            if (state == 0) {
                if (staffService.isStaffExist(UnionID) == 0) {
                    staffService.createStaff(UnionID);
                }
            } else if (state == 1) {
                if (parentService.isParentExist(UnionID) == 0) {
                    parentService.createParent(UnionID);
                }
            }
        }
        
        return hashMap;
    }
}
