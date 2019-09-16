package com.ky.backtracking.controller;

import com.ky.backtracking.dao.BaseDataDao;
import com.ky.backtracking.dao.FeedBackDao;
import com.ky.backtracking.dao.GameDataDao;
import com.ky.backtracking.dao.QstnaireDao;
import com.ky.backtracking.model.*;
import com.ky.backtracking.service.AchieveService;
import com.ky.backtracking.service.SaveService;
import com.ky.backtracking.service.UserService;
import jdk.nashorn.internal.runtime.Debug;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import javax.jws.soap.SOAPBinding;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private SaveService saveService;

    @Autowired
    private AchieveService achieveService;

    @Autowired
    private FeedBackDao feedBackDao;

    @Autowired
    private QstnaireDao qstnaireDao;

    @Autowired
    private BaseDataDao baseDataDao;

    @Autowired
    private GameDataDao gameDataDao;

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

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
                // 如果是新手机号注册用户新建空存档
                for (int i = 0; i <= 4; i++) {
                    Save save = new Save(new SaveMultiKey(uuid, i), "");
                    saveService.addSave(save);
                }
                // 新建空成就
                Achievement achievement = new Achievement(uuid);
                achieveService.addAchievement(achievement);
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
            LOG.info("achievement value: {}", achievement.toString());
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

    /*
     * 提交基本数据
     */
    @RequestMapping(value = "/btl/dep/base", method = RequestMethod.POST)
    @ResponseBody
    public void depBaseData(@RequestBody BaseData data) {
        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        BaseData baseData = new BaseData(data);
        // 设置时间戳为服务器时间
        baseData.setTimestamp(df.format(now));
        baseDataDao.save(baseData);
        LOG.info("submit basedata, user uuid: {}", data.getUuid());
    }

    /*
     * 提交游戏数据
     */
    @RequestMapping(value = "/btl/dep/game", method = RequestMethod.POST)
    @ResponseBody
    public void depGameData(@RequestBody GameList data) {
        List<GameData> games = data.getGames();
        for (GameData game : games) {
            GameData gd = gameDataDao.findTopByUuidAndGamecodeOrderBySavenumDesc(game.getUuid(), game.getGamecode());
            if (gd == null) { // 首次直接添加
                GameData tmp = new GameData(game);
                tmp.setSavenum(1); // 设置初始编号为1
                gameDataDao.save(tmp);
                LOG.info("submit gamedata first, user uuid: {} gamecode: {}", game.getUuid(), game.getGamecode());
            } else { // 非首次更新
                if (game.getSavenum() == 0) {
                    // 点击次数无论任何游戏每次累加
                    gd.setClicks(gd.getClicks() + game.getClicks());

                    if (Game.IsSearchGame(gd.getGamecode())) { // 搜索类游戏
                        if (!gd.isGamestatus()) { // 如果还未通关
                            gd.setGamestatus(game.isGamestatus());
                            if (gd.getClicks() == 0) { // 未进入过该游戏
                                // System.out.println("Duration: " + game.getDuration());
                                gd.setDuration(gd.getDuration() + game.getDuration());
                            } else { // 已经进入过该游戏
                                // System.out.println("Temptime: " + game.getTemptime());
                                gd.setDuration(gd.getDuration() + game.getTemptime());
                            }
                            LOG.info("submit gamedata need search not passed, user uuid: {} gamecode: {}", game.getUuid(), game.getGamecode());
                        } else { // 如果已经通关状态和通关时长则不再修改
                            LOG.info("submit gamedata need search passed, user uuid: {} gamecode: {}", game.getUuid(), game.getGamecode());
                        }
                    } else { // 纯解密游戏
                        if (!gd.isGamestatus()) { // 如果还未通关
                            gd.setGamestatus(game.isGamestatus());
                            gd.setDuration(gd.getDuration() + game.getDuration());
                            LOG.info("submit gamedata normal not passed, user uuid: {} gamecode: {}", game.getUuid(), game.getGamecode());
                        } else { // 如果已经通关状态和通关时长则不再修改
                            LOG.info("submit gamedata normal passed, user uuid: {} gamecode: {}", game.getUuid(), game.getGamecode());
                        }
                    }
                    gameDataDao.save(gd);
                    LOG.info("submit gamedata without newgame, user uuid: {} gamecode: {}", game.getUuid(), game.getGamecode());
                } else {
                    GameData tmp = new GameData(game);
                    tmp.setSavenum(gd.getSavenum() + 1); // 设置编号加1
                    gameDataDao.save(tmp);
                    LOG.info("submit gamedata with newgame, user uuid: {} gamecode: {}", game.getUuid(), game.getGamecode());
                }

            }
        }
    }
}
