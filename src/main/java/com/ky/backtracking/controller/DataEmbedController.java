package com.ky.backtracking.controller;

import com.ky.backtracking.common.AsyncTask;
import com.ky.backtracking.model.BaseData;
import com.ky.backtracking.model.GameList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
public class DataEmbedController {

    private static final Logger LOG = LoggerFactory.getLogger(DataEmbedController.class);

    @Autowired
    private AsyncTask asyncTask;

    /*
     * 提交基本数据
     */
    @RequestMapping(value = "/btl/dep/base", method = RequestMethod.POST)
    @ResponseBody
    public void depBaseData(HttpServletRequest request, @RequestBody BaseData data) {
//        LOG.info("IP: {}", request.getRemoteAddr());
//        LOG.info("Port: {}", request.getRemotePort());
        //data.setIp(request.getRemoteAddr());
        //LOG.info("X-Real-IP: {}", request.getHeader("X-Real-IP"));
        //LOG.info("X-Forwarded-For: {}", request.getHeader("X-Forwarded-For"));
        //data.setIp(request.getHeader("X-Forwarded-For").toString());
        data.setPort(request.getRemotePort());
        asyncTask.commitBaseData(data);
    }

    /*
     * 提交游戏数据
     */
    @RequestMapping(value = "/btl/dep/game", method = RequestMethod.POST)
    @ResponseBody
    public void depGameData(@RequestBody GameList data) {
        asyncTask.commitGameData(data);
    }
}
