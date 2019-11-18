package com.ky.backtracking.common;

import com.ky.backtracking.dao.BaseDataDao;
import com.ky.backtracking.dao.FeedBackDao;
import com.ky.backtracking.dao.GameDataDao;
import com.ky.backtracking.dao.QstnaireDao;
import com.ky.backtracking.model.*;
import com.ky.backtracking.service.AchieveService;
import com.ky.backtracking.service.SaveService;
import com.ky.backtracking.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class AsyncTask {

    private static final Logger LOG = LoggerFactory.getLogger(AsyncTask.class);

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
    @Autowired
    private Base64Util base64Util;

    @Async
    public void initSavesAndAchievement(Long uuid) {
// 新手机号注册用户新建空存档
        String defsave[] = {
                "0;1.0;0.7;\n",
                ";;0\n0;sceneMystery_Charpter1_child\n",
                ";;0\n0;sceneMystery_Charpter2\n",
                ";;0\n0;sceneMystery_Charpter3\n",
                ";;0\n0;sceneMystery_Charpter4\n"
        };
        for (int i = 0; i <= 4; i++) {
            Save save = new Save(new SaveMultiKey(uuid, i), defsave[i]);
            saveService.addSave(save);
        }
        // 新建空成就
        Achievement achievement = new Achievement(uuid);
        achieveService.addAchievement(achievement);
        LOG.info("initSavesAndAchievement completed, user uuid: {}", uuid);
    }

    public void writeSynInfo(SynInfo synInfo) {
        User user = userService.findUserByUuid(synInfo.getUuid());
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
            achievement.setChtime((float)(Math.round(tmpAchieve.getChtime()*100)/100));
            achievement.setUntime((float)(Math.round(tmpAchieve.getUntime()*100)/100));
            achieveService.updateAchievement(achievement);
            LOG.info("write achievement, user uuid: {}", synInfo.getUuid());
            //LOG.info("achievement value: {}", achievement.toString());
        } else {
            LOG.info("write achievement, user uuid: {} not found", synInfo.getUuid());
        }
    }

    @Async
    public void commitFeedBack(FeedBack data) {
        FeedBack feedBack = new FeedBack(data);
        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        feedBack.setFeedbackTime(df.format(now));
        String saveimagename = String.format("FB%s%s", df1.format(now), CommonUtil.randomNum(4));
        if (base64Util.Base64str2Image(data.getBase64img(), saveimagename)) {
            feedBack.setBase64img(saveimagename);
            LOG.info("save feedback image:{} success", saveimagename);
        } else {
            LOG.info("save feedback image:{} fail", saveimagename);
            feedBack.setBase64img(null);
        }
        feedBackDao.save(feedBack);
        LOG.info("submit feedback, user uuid: {}", data.getUuid());
    }

    @Async
    public void commitQstnaire(Qstnaire data) {
        Qstnaire qstnaire = new Qstnaire(data);
        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        qstnaire.setUploadtime(df.format(now));
        qstnaireDao.save(qstnaire);
        LOG.info("submit qstnaire, user uuid: {}", data.getUuid());
    }

    @Async
    public void commitBaseData(BaseData data) {
//        if (data.getUuid() == 0) {
//            LOG.info("commit error uuid: 0");
//            return;
//        }
        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        BaseData baseData = new BaseData(data);
        // 设置时间戳为服务器时间
        baseData.setTimestamp(df.format(now));
        baseDataDao.save(baseData);
        LOG.info("submit basedata, user uuid: {}, optype: {}， opstatus: {}", data.getUuid(), data.getOptype(), data.getOpstatus());
    }

    @Async
    public void commitGameData(GameList data) {
        List<GameData> games = data.getGames();
        for (GameData game : games) {
            if (game.getUuid() == 0) {
                return;
            }
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
