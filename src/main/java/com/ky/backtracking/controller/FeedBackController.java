package com.ky.backtracking.controller;

import com.ky.backtracking.dao.FeedBackDao;
import com.ky.backtracking.dao.QstnaireDao;
import com.ky.backtracking.model.FeedBack;
import com.ky.backtracking.model.Qstnaire;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FeedBackController {

    private static final Logger LOG = LoggerFactory.getLogger(FeedBackController.class);

    @Autowired
    private FeedBackDao feedBackDao;
    @Autowired
    private QstnaireDao qstnaireDao;

    /*
     * 提交反馈信息
     */
    @RequestMapping(value = "/btl/feedback", method = RequestMethod.POST)
    @ResponseBody
    public void feedback(@RequestBody FeedBack data) {
        FeedBack feedBack = new FeedBack(data);
//        System.out.println(data.getClientVersion());
//        System.out.println(data.getNetworkType());
        feedBackDao.save(feedBack);
        LOG.info("submit feedback, user uuid: {}", data.getUuid());

    }

    /*
     * 提交问卷信息
     */
    @RequestMapping(value = "/btl/qstnaire", method =  RequestMethod.POST)
    @ResponseBody
    public void qstnaire(@RequestBody Qstnaire data) {
        Qstnaire qstnaire = new Qstnaire(data);
        qstnaireDao.save(qstnaire);
        LOG.info("submit qstnaire, user uuid: {}", data.getUuid());
    }

}
