package com.ky.zmylq.controller;

import com.ky.zmylq.dao.SaveDao;
import com.ky.zmylq.model.Save;
import com.ky.zmylq.model.SaveMultiKey;
import com.ky.zmylq.model.User;
import com.ky.zmylq.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
public class MainController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private SaveDao saveDao;


    /*
     * 用户第一次进入游戏，添加一个用户账号，返回给客户端一个UUID
     */
    @RequestMapping("/zmylq/register")
    @ResponseBody
    public String register() {
        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        User user = new User(true, df.format(now));
        userDao.save(user);
        int uuid = user.getUuid();
        for (int i = 1; i <= 6; i++) {
            Save save = new Save(new SaveMultiKey(uuid, i), "", "", "", 0, 0, 0, 0, 0);
            saveDao.save(save);
        }

        return String.valueOf(uuid);
    }

    /*
     * 已有账号的用户登录游戏，更新服务器用户信息
     */
    @RequestMapping("/zmylq/login")
    public void login() {

    }

    /*
     * 用户退出游戏时更新用户信息，保存用户存档
     */
    @RequestMapping("/zmylq/quit")
    public void quit() {

    }
}
