package com.ky.backtracking.common;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ky.backtracking.controller.MsgIdentifyController;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class CommonUtil {

    private static final Logger LOG = LoggerFactory.getLogger(CommonUtil.class);

    private static final String SEND_MSG_URL = "http://sms.mobile.kgidc.cn/v4/message/send/";
    private static final String SECRETKEY = "7am49m4Mr3";
    private static final String CALLER = "mini_verify";

    public static String randomNum(int size) {
        String sources = "0123456789";
        Random rand = new Random();
        StringBuilder ret = new StringBuilder();
        for (int j = 0; j < size; j++)
        {
            ret.append(sources.charAt(rand.nextInt(9)));
        }
        return ret.toString();
    }

    public static String generateMd5Hex(String data) {
        return DigestUtils.md5Hex(data);
    }

    public static String httpPostRequest(String url, MultiValueMap<String, String> params) {
        RestTemplate restTemplate = new RestTemplate();

        //中文格式数据处理
        FormHttpMessageConverter fc = new FormHttpMessageConverter();
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        List<HttpMessageConverter<?>> partConverters = new ArrayList<HttpMessageConverter<?>>();
        partConverters.add(stringConverter);
        partConverters.add(new ResourceHttpMessageConverter());
        fc.setPartConverters(partConverters);
        restTemplate.getMessageConverters().addAll(Arrays.asList(fc,new MappingJackson2HttpMessageConverter()));
        //发送请求，设置请求返回数据格式为String（去除上面方法中使用的httpEntity）
        return restTemplate.postForEntity(url, params, String.class).getBody();
    }

    public static int sendMessage(String mobiles, String code, String time) {
        String content = String.format("人生溯游提示您的验证码：%s，请您及时输入验证码并根据提示完成操作，验证码5分钟内有效。如非本人操作，请忽略本短信。", code);
        String data = String.format("caller%scontent%smobiles%stime%s%s", CALLER, content, mobiles, time, SECRETKEY);
        String sign = DigestUtils.md5Hex(data);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("caller", CALLER);
        params.add("mobiles", mobiles);
        params.add("content", content);
        params.add("time", time);
        params.add("sign", sign);

        try {
            Gson gson = new Gson();
            String resp_str = httpPostRequest(SEND_MSG_URL, params);
            ResponseBody responseBody = gson.fromJson(resp_str, ResponseBody.class);
            if (responseBody.status == 1) {
                LOG.info("send message to mobile: {} success", mobiles);
                return 0;
            } else {
                LOG.info("send message to mobile: {} fail, errcode: {}, error: {}", mobiles, responseBody.errcode, responseBody.error);
                return -1;
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return -1;
    }
}

class ResponseBody {
    int status;
    int errcode;
    String error;
}
