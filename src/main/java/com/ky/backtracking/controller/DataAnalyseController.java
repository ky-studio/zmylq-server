package com.ky.backtracking.controller;

import com.ky.backtracking.model.*;
import com.ky.backtracking.service.DataAnalyseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DataAnalyseController {

    private static final Logger LOG = LoggerFactory.getLogger(DataAnalyseController.class);

    @Autowired
    private DataAnalyseService dataAnalyseService;

    /*
     * 获取gamedata表的最大gid
     */
    @RequestMapping(value = "/btl/uda/gamedata/maxgid", method = RequestMethod.GET)
    @ResponseBody
    public Long findGamedataMaxgid() {
        Long ret = dataAnalyseService.findGamedataMaxGid();
        LOG.info("find gamedata max: {}", ret);
        return ret;
    }

    /*
     * 获取achievement表的最大uuid
     */
    @RequestMapping(value = "/btl/uda/achieve/maxuuid", method = RequestMethod.GET)
    @ResponseBody
    public Long findAchievementMaxuuid() {
        Long ret = dataAnalyseService.findAchievementMaxUuid();
        LOG.info("find gamedata max: {}", ret);
        return ret;
    }

    /*
     * 获取feedback表的最大fid
     */
    @RequestMapping(value = "/btl/uda/feedback/maxfid", method = RequestMethod.GET)
    @ResponseBody
    public Long findFeedbackMaxfid() {
        Long ret = dataAnalyseService.findFeedbackMaxFid();
        LOG.info("find gamedata max: {}", ret);
        return ret;
    }


    /*
     * 获取qstnaire表的最大qid
     */
    @RequestMapping(value = "/btl/uda/qstnaire/maxqid", method = RequestMethod.GET)
    @ResponseBody
    public Long findQstnaireMaxqid() {
        Long ret = dataAnalyseService.findQstnaireMaxQid();
        LOG.info("find gamedata max: {}", ret);
        return ret;
    }

    /*
     * 获取User表的最大uuid
     */
    @RequestMapping(value = "/btl/uda/user/maxuuid", method = RequestMethod.GET)
    @ResponseBody
    public Long findUserMaxuuid() {
        Long ret = dataAnalyseService.findUserMaxUuid();
        LOG.info("find gamedata max: {}", ret);
        return ret;
    }

    /*
     * 获取basedata表的数据
     * 参数begin，end可选，都存在时获取时间戳在begin到end之间的数据
     */
    @RequestMapping(value = "/btl/uda/basedata", method = RequestMethod.GET)
    @ResponseBody
    public List<BaseData> findBasedata(@RequestParam(value = "begin", required = false) String begin,
                                       @RequestParam(value = "end", required = false) String end) {
        if (begin == null || end  == null) {
            LOG.info("Get All Basedata");
            return dataAnalyseService.findAllBaseData();
        } else {
            LOG.info("Get Basedata between {} and {}", begin, end);
            return dataAnalyseService.findBaseDataBetween(begin, end);
        }
    }

    /*
     * 获取gamedata表的数据
     * 参数begin，end可选，都存在时获取gid在begin到end之间的数据
     */
    @RequestMapping(value = "/btl/uda/gamedata", method = RequestMethod.GET)
    @ResponseBody
    public List<GameData> findGamedata(@RequestParam(value = "begin", required = false) Long begin,
                                       @RequestParam(value = "end", required = false) Long end) {
        if (begin == null || end == null) {
            LOG.info("Get All Gamedata");
            return dataAnalyseService.findAllGameData();
        } else {
            LOG.info("Get Gamedata between {} and {}", begin, end);
            return dataAnalyseService.findGameDataBetween(begin, end);
        }
    }

    /*
     * 获取feedback表的数据
     * 参数begin，end可选，都存在时获取fid在begin到end之间的数据，否则获取全部数据
     */
    @RequestMapping(value = "/btl/uda/feedback", method = RequestMethod.GET)
    @ResponseBody
    public List<FeedBack> findFeedback(@RequestParam(value = "begin", required = false) Long begin,
                                       @RequestParam(value = "end", required = false) Long end) {
        if (begin == null || end == null) {
            LOG.info("Get All Feedback");
            return dataAnalyseService.findAllFeedBack();
        } else {
            LOG.info("Get Feedback between {} and {}", begin, end);
            return dataAnalyseService.findFeedBackBetween(begin, end);
        }
    }

    /*
     * 获取qstnaire表的数据
     * 参数begin，end可选，都存在时获取qid在begin到end之间的数据，否则获取全部数据
     */
    @RequestMapping(value = "/btl/uda/qstnaire", method = RequestMethod.GET)
    @ResponseBody
    public List<Qstnaire> findQstnaire(@RequestParam(value = "begin", required = false) Long begin,
                                       @RequestParam(value = "end", required = false) Long end) {
        if (begin == null || end == null) {
            LOG.info("Get All Qstnaire");
            return dataAnalyseService.findAllQstnaire();
        } else {
            LOG.info("Get Qstnaire between {} and {}", begin, end);
            return dataAnalyseService.findQstnaireBetween(begin, end);
        }

    }

    /*
     * 获取User表的数据
     * 参数begin，end可选，都存在时获取uuid在begin到end之间的数据，否则获取全部数据
     */
    @RequestMapping(value = "/btl/uda/user", method = RequestMethod.GET)
    @ResponseBody
    public List<User> findUsers(@RequestParam(value = "begin", required = false) Long begin,
                                @RequestParam(value = "end", required = false) Long end) {
        if (begin == null || end == null) {
            LOG.info("Get All Users");
            return dataAnalyseService.findAllUser();
        } else {
            LOG.info("Get User between {} and {}", begin, end);
            return dataAnalyseService.findUserBetween(begin, end);
        }

    }

    /*
     * 获取achievement表的数据
     * 参数begin，end可选，都存在时获取uuid在begin到end之间的数据，否则获取全部数据
     */
    @RequestMapping(value = "/btl/uda/achieve", method = RequestMethod.GET)
    @ResponseBody
    public List<Achievement> findAchievements(@RequestParam(value = "begin", required = false) Long begin,
                                       @RequestParam(value = "end", required = false) Long end) {
        if (begin == null || end == null) {
            LOG.info("Get All Achievements");
            return dataAnalyseService.findAllAchievement();
        } else {
            LOG.info("Get Achievements between {} and {}", begin, end);
            return dataAnalyseService.findAchievementBetween(begin, end);
        }
    }

    /*
     * 获取save表的数据
     */
    @RequestMapping(value = "/btl/uda/save", method = RequestMethod.GET)
    @ResponseBody
    public List<Save> findSaves() {
        LOG.info("Get All Saves");
        return dataAnalyseService.findAllSave();
    }

}
