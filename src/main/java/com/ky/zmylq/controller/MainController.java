package com.ky.zmylq.controller;

import com.ky.zmylq.model.*;
import com.ky.zmylq.service.SaveService;
import com.ky.zmylq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private SaveService saveService;
    /*
     * 用户第一次进入游戏，添加一个用户账号，返回给客户端一个UUID
     */
    @RequestMapping("/zmylq/register")
    @ResponseBody
    public String register() {
        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        User user = new User(true, df.format(now));
        userService.addUser(user);
        Long uuid = user.getUuid();
        for (int i = 1; i <= 6; i++) {
            Save save = new Save(new SaveMultiKey(uuid, i), "", "", "", 0, 0, 0, 0, 0);
            saveService.addSave(save);
        }

        return String.valueOf(uuid);
    }

    /*
     * 已有账号的用户登录游戏，更新服务器用户信息
     */
    @RequestMapping(value = "/zmylq/login", method = RequestMethod.GET)
    @ResponseBody
    public void login(@RequestParam String uuid_str) {
        Long uuid = Long.valueOf(uuid_str);
        User user = userService.findUserByUuid(uuid);
        if (user == null) {
            return;
        }
        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        user.setStatus(true);
        user.setLastLoginDate(df.format(now));
        userService.updateUser(user);
    }

    @RequestMapping(value = "/zmylq/save", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody UserJson userJson) {
        User user = userService.findUserByUuid(userJson.getUuid());
        if (user == null) {
            return;
        }
        for (int i = 0; i < userJson.getSaves().size(); i++) {
            Save save = new Save();
            save.setSaveMultiKey(new SaveMultiKey(userJson.getUuid(), i + 1));
            SaveJson saveJson = userJson.getSaves().get(i);
            save.setPlayername(saveJson.getPlayername());
            save.setScence(saveJson.getScence());
            save.setChapter(saveJson.getChapter());
            save.setIntelligence(saveJson.getIntelligence());
            save.setFans(saveJson.getFans());
            save.setFavor1(saveJson.getFavor1());
            save.setFavor2(saveJson.getFavor2());
            save.setFavor3(saveJson.getFavor3());
            saveService.updateSave(save);
        }
    }

    /*
     * 用户退出游戏时更新用户信息，保存用户存档
     */
    @RequestMapping(value = "/zmylq/quit", method = RequestMethod.POST)
    @ResponseBody
    public void quit(@RequestBody UserJson userJson) {
        User user = userService.findUserByUuid(userJson.getUuid());
        if (user == null) {
            return;
        }
        user.setStatus(false);
        userService.updateUser(user);
        for (int i = 0; i < userJson.getSaves().size(); i++) {
            Save save = new Save();
            save.setSaveMultiKey(new SaveMultiKey(userJson.getUuid(), i + 1));
            SaveJson saveJson = userJson.getSaves().get(i);
            save.setPlayername(saveJson.getPlayername());
            save.setScence(saveJson.getScence());
            save.setChapter(saveJson.getChapter());
            save.setIntelligence(saveJson.getIntelligence());
            save.setFans(saveJson.getFans());
            save.setFavor1(saveJson.getFavor1());
            save.setFavor2(saveJson.getFavor2());
            save.setFavor3(saveJson.getFavor3());
            saveService.updateSave(save);
        }
    }
}
