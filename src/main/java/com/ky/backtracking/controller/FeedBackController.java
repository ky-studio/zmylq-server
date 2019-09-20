package com.ky.backtracking.controller;

import com.ky.backtracking.common.AsyncTask;
import com.ky.backtracking.model.FeedBack;
import com.ky.backtracking.model.Qstnaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FeedBackController {

    @Autowired
    private AsyncTask asyncTask;

    /*
     * 提交反馈信息
     */
    @RequestMapping(value = "/btl/feedback", method = RequestMethod.POST)
    @ResponseBody
    public void feedback(@RequestBody FeedBack data) {
        asyncTask.commitFeedBack(data);
    }

    /*
     * 提交问卷信息
     */
    @RequestMapping(value = "/btl/qstnaire", method =  RequestMethod.POST)
    @ResponseBody
    public void qstnaire(@RequestBody Qstnaire data) {
        asyncTask.commitQstnaire(data);
    }

}
