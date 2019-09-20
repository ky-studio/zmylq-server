package com.ky.backtracking.controller;

import com.ky.backtracking.common.AsyncTask;
import com.ky.backtracking.model.Achievement;
import com.ky.backtracking.model.Save;
import com.ky.backtracking.model.SynInfo;
import com.ky.backtracking.model.User;
import com.ky.backtracking.service.AchieveService;
import com.ky.backtracking.service.SaveService;
import com.ky.backtracking.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@EnableAsync
@RestController
public class SynInfoController {

    private static final Logger LOG = LoggerFactory.getLogger(SynInfoController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private SaveService saveService;
    @Autowired
    private AchieveService achieveService;
    @Autowired
    private AsyncTask asyncTask;

    /*
     * 客户端从服务器读取存档，以同步到本地
     */
    @RequestMapping(value = "/btl/readsave", method = RequestMethod.GET)
    @ResponseBody
    public SynInfo readSave(@RequestParam(name = "uuid") String uuid_str) {
        Long uuid = Long.valueOf(uuid_str);
        SynInfo synInfo = new SynInfo();
        // 读取用户信息
        User user = userService.findUserByUuid(uuid);
        if (user != null) {
            synInfo.setUuid(uuid);
            synInfo.setpNumber(user.getpNumber());
            synInfo.setLastLoginDate(user.getLastLoginDate());
            synInfo.setTotalPlayTime(user.getTotalPlayTime());
            LOG.info("read user, uuid: {}", uuid);
        } else {
            LOG.info("read user, user uuid: {} not found", uuid);
        }
        // 读取存档信息
        List<String> saves = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            Save save = saveService.findSaveByUuidAndSaveid(uuid, i);
            if (save != null) {
                saves.add(save.getContent());
                LOG.info("read save, uuid: {} saveid: {}", uuid, i);
            } else {
                LOG.info("read save, save uuid: {} saveid: {} not found", uuid, i);
            }
        }
        synInfo.setSaves(saves);
        // 读取成就信息
        Achievement achievement = achieveService.findAchieveByUuid(uuid);
        if (achievement != null) {
            synInfo.setAchievement(achievement);
            LOG.info("read achievement, user uuid: {}", uuid);
        } else {
            LOG.info("read achievement, user uuid: {} not found", uuid);
        }

        return synInfo;
    }

    /*
     * 客户端将本地存档更新同步写入服务器
     */
    @RequestMapping(value = "/btl/writesave", method = RequestMethod.POST)
    @ResponseBody
    public String writeSave(@RequestBody SynInfo synInfo) {
        // 写入用户信息
        asyncTask.writeSynInfo(synInfo);
        return "WRITESAVE_SUCCESS";
    }
}
