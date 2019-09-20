package com.ky.backtracking.controller;

import com.ky.backtracking.dao.BaseDataDao;
import com.ky.backtracking.dao.GameDataDao;
import com.ky.backtracking.model.BaseData;
import com.ky.backtracking.model.Game;
import com.ky.backtracking.model.GameData;
import com.ky.backtracking.model.GameList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class DataEmbedController {

    private static final Logger LOG = LoggerFactory.getLogger(DataEmbedController.class);

    @Autowired
    private BaseDataDao baseDataDao;
    @Autowired
    private GameDataDao gameDataDao;

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
