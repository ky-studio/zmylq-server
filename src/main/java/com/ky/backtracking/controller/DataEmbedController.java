package com.ky.backtracking.controller;

import com.ky.backtracking.common.AsyncTask;
import com.ky.backtracking.model.BaseData;
import com.ky.backtracking.model.GameList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class DataEmbedController {

    @Autowired
    private AsyncTask asyncTask;

    /*
     * 提交基本数据
     */
    @RequestMapping(value = "/btl/dep/base", method = RequestMethod.POST)
    @ResponseBody
    public void depBaseData(@RequestBody BaseData data) {
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
