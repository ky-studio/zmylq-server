package com.ky.backtracking.controller;

import com.ky.backtracking.model.RankList;
import com.ky.backtracking.service.AchieveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RankListController {

    private static final Logger LOG = LoggerFactory.getLogger(RankListController.class);

    @Autowired
    private AchieveService achieveService;

    /*
     * 获取排行榜信息
     */
    @RequestMapping(value = "/btl/rank", method = RequestMethod.GET)
    @ResponseBody
    public RankList rank(@RequestParam(name = "uuid") String uuid_str) {
        Long uuid = Long.valueOf(uuid_str);
        RankList rankList = achieveService.getRankList(uuid);
        LOG.info("get ranklist, user uuid: {}", uuid);
        return rankList;
    }
}
