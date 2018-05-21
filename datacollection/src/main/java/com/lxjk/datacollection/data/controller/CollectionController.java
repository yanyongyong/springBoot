package com.lxjk.datacollection.data.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lxjk.datacollection.data.entity.User;
import com.lxjk.datacollection.data.util.ApiUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: ziv
 * @Date: 2018/5/17 14:50
 * @Description:
 */
@Slf4j
@RestController
public class CollectionController {


    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    ApiUrl apiUrl;
    private static String token;
    @Value("${api.name}")
    private  String name;
    @Value("${api.password}")
    private  String password;


    @RequestMapping(value = "/collection")
    public void collection(){
        String data = "yan";
        HttpEntity<String> formEntity = new HttpEntity<String>(data, getHttpHeaders());
        try{
            String a = restTemplate.postForObject(apiUrl.getApiURL(),formEntity,String.class);
            System.out.println(a);
        }catch (Exception e){
            log.info("token过时，重新登陆ing");
            login();
        }
    }

    @RequestMapping(value = "/login")
    public void login(){
        HttpEntity<User> formEntity = new HttpEntity<User>(new User(name,password), getHttpHeaders());
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl.getLoginURL(),formEntity ,String.class);
        JSONObject data = JSON.parseObject(responseEntity.getBody());
        log.info(responseEntity.getBody());
        token = (String) data.get("data");
    }

    /**
     * @Date: 2018/5/21 11:42
     * @Description: 请求头
     */
    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        return headers;
    }
}
