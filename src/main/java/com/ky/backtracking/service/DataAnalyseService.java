package com.ky.backtracking.service;

import com.ky.backtracking.dao.*;
import com.ky.backtracking.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import com.google.common.collect.Lists;

@Service
@Transactional
public class DataAnalyseService {

    @Autowired
    private FeedBackDao feedBackDao;
    @Autowired
    private QstnaireDao qstnaireDao;
    @Autowired
    private BaseDataDao baseDataDao;
    @Autowired
    private GameDataDao gameDataDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AchievementDao achievementDao;
    @Autowired
    private SaveDao saveDao;

    public List<FeedBack> findAllFeedBack() {
        return Lists.newArrayList(feedBackDao.findAll());
    }

    public List<FeedBack> findFeedBackBetween(Long begin, Long end) {
        return feedBackDao.findByFidBetween(begin, end);
    }

    public List<Qstnaire> findAllQstnaire() {
        return Lists.newArrayList(qstnaireDao.findAll());
    }

    public List<Qstnaire> findQstnaireBetween(Long begin, Long end) {
        return qstnaireDao.findByQidBetween(begin, end);
    }

    public List<BaseData> findAllBaseData() {
        return Lists.newArrayList(baseDataDao.findAll());
    }

    public List<BaseData> findBaseDataBetween(String begin, String end) {
        return baseDataDao.findByTimestampBetween(begin, end);
    }

    public List<GameData> findAllGameData() {
        return Lists.newArrayList(gameDataDao.findAll());
    }

    public List<GameData> findGameDataBetween(Long begin, Long end) {
        return gameDataDao.findByGidBetween(begin, end);
    }

    public List<User> findAllUser() {
        return userDao.findByUuidAfter(10199L);
    }

    public List<User> findUserBetween(Long begin, Long end) {
        return userDao.findByUuidBetween(begin, end);
    }

    public List<Achievement> findAllAchievement() {
        return achievementDao.findByUuidAfter(10199L);
    }

    public List<Achievement> findAchievementBetween(Long begin, Long end) {
        return achievementDao.findByUuidBetween(begin, end);
    }

    public List<Save> findAllSave() {
        return Lists.newArrayList(saveDao.findAll());
    }

    public Long findGamedataMaxGid() {
        Long ret = gameDataDao.findMaxGid();
        if (ret != null) {
            return ret;
        } else {
            return -1L;
        }
    }

    public Long findAchievementMaxUuid() {
        Long ret = achievementDao.findMaxUuid();
        if (ret != null) {
            return ret;
        } else {
            return -1L;
        }
    }

    public Long findFeedbackMaxFid() {
        Long ret = feedBackDao.findMaxFid();
        if (ret != null) {
            return ret;
        } else {
            return -1L;
        }
    }

    public Long findQstnaireMaxQid() {
        Long ret = qstnaireDao.findMaxQid();
        if (ret != null) {
            return ret;
        } else {
            return -1L;
        }
    }

    public Long findUserMaxUuid() {
        Long ret = userDao.findMaxUuid();
        if (ret != null) {
            return ret;
        } else {
            return -1L;
        }
    }
 }
