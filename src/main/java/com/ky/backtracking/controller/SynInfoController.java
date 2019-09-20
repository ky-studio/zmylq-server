package com.ky.backtracking.controller;

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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SynInfoController {

    private static final Logger LOG = LoggerFactory.getLogger(SynInfoController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private SaveService saveService;
    @Autowired
    private AchieveService achieveService;

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
        User user = userService.findUserByUuid(synInfo.getUuid());
        String ret = "WRITESAVE_SUCCESS";
        if (user != null) {
            String pnumber = synInfo.getpNumber();
            if (pnumber != null && pnumber.length() > 0) {
                user.setpNumber(pnumber);
            } else {
                user.setpNumber(null);
            }
            user.setLastLoginDate(synInfo.getLastLoginDate());
            user.setTotalPlayTime(synInfo.getTotalPlayTime());
            userService.updateUser(user);
            LOG.info("write user, user uuid: {}", synInfo.getUuid());
        } else {
            ret = "WRITESAVE_FAIL";
            LOG.info("write user, user uuid: {} not found", synInfo.getUuid());
        }

        // 写入存档信息
        List<String> saves = synInfo.getSaves();
        for (int i = 0; i < saves.size(); i++) {
            Save save = saveService.findSaveByUuidAndSaveid(synInfo.getUuid(), i);
            if (save != null) {
                save.setContent(saves.get(i));
                saveService.updateSave(save);
                LOG.info("write save, user uuid: {} saveid: {}", synInfo.getUuid(), i);
            } else {
                LOG.info("write save, user uuid: {} saveid: {} not found", synInfo.getUuid(), i);
            }
        }

        // 写入成就信息
        Achievement achievement = achieveService.findAchieveByUuid(synInfo.getUuid());
        Achievement tmpAchieve = synInfo.getAchievement();
        if (achievement != null && tmpAchieve != null) {
            achievement.setHomework(tmpAchieve.isHomework());
            achievement.setBedroom(tmpAchieve.isBedroom());
            achievement.setWajue(tmpAchieve.isWajue());
            achievement.setChuji(tmpAchieve.isChuji());
            achievement.setGaoji(tmpAchieve.isGaoji());
            achievement.setZhiwang(tmpAchieve.isZhiwang());
            achievement.setNjmaxtime(tmpAchieve.getNjmaxtime());
            achievement.setVersatile(tmpAchieve.isVersatile());
            achievement.setProgramer(tmpAchieve.isProgramer());
            achievement.setDredger(tmpAchieve.isDredger());
            achievement.setChildhood(tmpAchieve.isChildhood());
            achievement.setUniversity(tmpAchieve.isUniversity());
            achievement.setChtime(tmpAchieve.getChtime());
            achievement.setUntime(tmpAchieve.getUntime());
            achieveService.updatAchievement(achievement);
            LOG.info("write achievement, user uuid: {}", synInfo.getUuid());
            LOG.info("achievement value: {}", achievement.toString());
        } else {
            LOG.info("write achievement, user uuid: {} not found", synInfo.getUuid());
        }

        return ret;
    }
}
