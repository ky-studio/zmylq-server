package com.ky.backtracking.controller;

import com.ky.backtracking.common.BtlConfig;
import com.ky.backtracking.model.*;
import com.ky.backtracking.service.AchieveService;
import com.ky.backtracking.common.AsyncTask;
import com.ky.backtracking.service.SaveService;
import com.ky.backtracking.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@EnableAsync
@RestController
public class MainController {

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private SaveService saveService;
    @Autowired
    private AchieveService achieveService;
    @Autowired
    private AsyncTask asyncTask;

    /*
     * 客户端测试是否能连接到服务器
     */
    @RequestMapping(value = "/btl/testping", method = RequestMethod.GET)
    @ResponseBody
    public String testPing() {
        LOG.info("client connect!");
        return "OK";
    }


    /*
     * 用户第一次进入游戏，添加一个用户账号，返回给客户端一个UUID
     * 如果有手机号同时添加存档信息到存档表，如果没有则为游客不添加存档
     */
    @RequestMapping(value = "/btl/register", method = RequestMethod.GET)
    @ResponseBody
    public String register(@RequestParam(name = "pnumber", required = false) String pNumber, @RequestParam(name = "datetime") String datetime) {
//        Date now = new Date();
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        // 手机号不为空
        Long uuid = 0L;
        if (pNumber != null && !pNumber.isEmpty()) {
            User user = userService.findUserByPNumber(pNumber);
            // 手机号已存在，修改登录状态
            if (user != null) {
                user.setStatus(true);
                user.setLastLoginDate(datetime);
                userService.updateUser(user);
                uuid = user.getUuid();
                // 从服务器同步存档到本地
                LOG.info("register pnumber exist, uuid: {}", uuid);
                return "REG_SYN_FROM_SERVER:" + String.valueOf(uuid);
            } else {
                // 手机号不存在，添加新用户
                user = new User(pNumber,true, datetime, "0.0");
                userService.addUser(user);
                uuid = user.getUuid();
                asyncTask.initSavesAndAchievement(uuid);
                LOG.info("register pnumber not exist, uuid: {}", uuid);
                return "REG_SUCCESS:" + String.valueOf(uuid);
            }

        } else { // 手机号为空，添加游客
            User user = new User(null,true, datetime, "0.0");
            userService.addUser(user);
            uuid = user.getUuid();
            // 新建空成就
            Achievement achievement = new Achievement(uuid);
            achieveService.addAchievement(achievement);
            LOG.info("register without pnumber, uuid: {}", uuid);
            return "REG_SUCCESS:" + String.valueOf(uuid);
        }
    }

    /*
     * 已有账号的用户登录游戏，更新服务器用户信息
     * 对比服务器上次登录时间和客户端上次登录时间，
     */
    @RequestMapping(value = "/btl/login", method = RequestMethod.GET)
    @ResponseBody
    public String login(@RequestParam(name = "uuid") String uuid_str, @RequestParam(name = "datetime") String datetime) {
        Long uuid = Long.valueOf(uuid_str);
        User user = userService.findUserByUuid(uuid);
        if (user == null) {
            LOG.info("login fail, uuid: {} not exist", uuid);
            return "LOGIN_FAIL";
        }
        String svrLastLoginDate = user.getLastLoginDate();
//        Date now = new Date();
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        user.setStatus(true);
        user.setLastLoginDate(datetime);
        userService.updateUser(user);
        LOG.info("login success, uuid: {}", uuid);
        return "LOGIN_SUCCESS#" + svrLastLoginDate;
    }

    /*
     * 用户退出游戏时更新用户状态
     */
    @RequestMapping(value = "/btl/quit", method = RequestMethod.GET)
    @ResponseBody
    public String quit(@RequestParam(name = "uuid") String uuid_str) {
        Long uuid = Long.valueOf(uuid_str);
        User user = userService.findUserByUuid(uuid);
        if (user == null) {
            LOG.info("quit fail, user uuid: {} not found", uuid);
            return "QUIT_FAIL";
        } else {
            user.setStatus(false);
            userService.updateUser(user);
            LOG.info("quit success, user uuid: {}", uuid);
            return "QUIT_SUCCESS";
        }
    }

    /*
     * 游客用户绑定或者修改手机号,更新用户手机号，新建空存档
     */
    @RequestMapping(value = "/btl/bind", method = RequestMethod.GET)
    @ResponseBody
    public String bind(@RequestParam(name = "uuid") String uuid_str, @RequestParam(name = "pnumber") String pNumber) {
        Long uuid = Long.valueOf(uuid_str);
        User user = userService.findUserByUuid(uuid);
        if (user == null) {
            LOG.info("bind fail, user uuid: {} not found", uuid);
            return "BIND_FAIL";
        } else {
            if (user.getpNumber() == null) { // 游客绑定手机号
                User tmp = userService.findUserByPNumber(pNumber);
                if (tmp != null) {
                    // 绑定已经使用过的手机号, 删除当前游客账号，返回已绑定手机号的账号uuid，客户端将本地保存的uuid修改为返回的uuid
                    // 当客户端收到此类返回应该再向服务器发送请求将本地存档同步到服务器或是同步服务器存档到本地
                    userService.deleteUserByUuid(uuid);
                    LOG.info("bind pnumber exist, change user uuid: {} to {}", uuid, tmp.getUuid());
                    return "BIND_PNUMBER_EXIST:" + tmp.getUuid();
                }  else {
                    // 绑定未使用的手机号
                    // 更新手机号
                    user.setpNumber(pNumber);
                    userService.updateUser(user);
                    // 新建空存档
                    for (int i = 0; i <= 4; i++) {
                        Save save = new Save(new SaveMultiKey(uuid, i), "");
                        saveService.addSave(save);
                    }
                    // 将本地存档同步到服务器, 客户端收到这个返回再发送一次writesave请求
                    LOG.info("bind success, user uuid: {} bind to pnumber: {}", user.getUuid(), user.getpNumber());
                    return "BIND_SYN_TO_SERVER";
                }
            } else { // 已经存在手机号的用户不能再绑定手机号
                LOG.info("bind illegal, user uuid: {} had pnumber: {}", user.getUuid(), user.getpNumber());
                return "BIND_ILLEGAL";
            }
        }
    }
}
