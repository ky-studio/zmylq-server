package com.ky.backtracking.common;

import com.google.gson.Gson;
import com.ky.backtracking.controller.MsgIdentifyController;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

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
        HttpHeaders headers = new HttpHeaders();
        // 以Json的方式提交
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        //执行HTTP请求
        LOG.info(requestEntity.toString());
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        return response.getBody();
    }

    public static int sendMessage(String mobiles, String code, String time) {
        String content = String.format("人生溯游，您的验证码%s，请您及时输入验证码并根据提示完成操作，验证码5分钟内有效。如非本人操作，请忽略本短信。", code);
        String data = String.format("caller%scontent%smobiles%stime%s%s", CALLER, content, mobiles, time, SECRETKEY);
        String sign = DigestUtils.md5Hex(data);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("caller", CALLER);
        map.add("mobiles", mobiles);
        map.add("content", content);
        map.add("time", time);
        map.add("sign", sign);
        try {
            String resp_str = httpPostRequest(SEND_MSG_URL, map);
            Gson gson = new Gson();
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
