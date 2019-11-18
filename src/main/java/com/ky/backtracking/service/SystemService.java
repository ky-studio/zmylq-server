package com.ky.backtracking.service;

import com.ky.backtracking.dao.*;
import com.ky.backtracking.model.Qstnaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Transactional
public class SystemService {

    @Autowired
    private QstnaireDao qstnaireDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SaveDao saveDao;
    @Autowired
    private AchievementDao achievementDao;
    @Autowired
    private FeedBackDao feedBackDao;
    @Autowired
    private BaseDataDao baseDataDao;
    @Autowired
    private GameDataDao gameDataDao;

    public Map<String, String> getAppinfo() {
        Qstnaire qstnaire = qstnaireDao.findByQid(1L);
        Map<String, String> appinfo = new LinkedHashMap<>();
;        if (qstnaire != null) {
            appinfo.put("version", qstnaire.getSuggesstionsdetails());
            appinfo.put("date", qstnaire.getUploadtime());
            appinfo.put("size", qstnaire.getGamediffculties().toString());
            return appinfo;
        } else {
            return null;
        }
    }

    public void setAppinfo(String version, String date, Float size) {
        Qstnaire qstnaire = qstnaireDao.findByQid(1L);
        if (qstnaire == null) {
            qstnaire = new Qstnaire();
            qstnaire.setUuid(0L);
            qstnaire.setGamestars(0f);
        }
        if (version != null) {
            qstnaire.setSuggesstionsdetails(version);
        }
        if (date != null) {
            qstnaire.setUploadtime(date);
        }
        if (size != null) {
            qstnaire.setGamediffculties(size);
        }
        qstnaireDao.save(qstnaire);
    }

    public String add1DownloadClickNumber() {
        Qstnaire qstnaire = qstnaireDao.findByQid(1L);
        if (qstnaire != null) {
            qstnaire.setUuid(qstnaire.getUuid() + 1);
            qstnaireDao.save(qstnaire);
            return qstnaire.getUuid().toString();
        } else {
            return "-1";
        }
    }

    public String getDownloadClickNumber() {
        Qstnaire qstnaire = qstnaireDao.findByQid(1L);
        if (qstnaire != null) {
            return qstnaire.getUuid().toString();
        } else {
            return "-1";
        }
    }

    public void setDownloadClickNumber(Long val) {
        Qstnaire qstnaire = qstnaireDao.findByQid(1L);
        if (qstnaire != null) {
            qstnaire.setUuid(val);
        }
    }

    public String add1DownloadCompletedNumber() {
        Qstnaire qstnaire = qstnaireDao.findByQid(1L);
        if (qstnaire != null) {
            qstnaire.setGamestars(qstnaire.getGamestars() + 1);
            qstnaireDao.save(qstnaire);
            return qstnaire.getGamestars().toString();
        } else {
            return "-1";
        }
    }

    public void clearDatabase() {
        userDao.deleteUsersByUuidAfter(10200L);
        achievementDao.deleteAchievementsByUuidAfter(10200L);
        saveDao.deleteAll();
        feedBackDao.deleteAll();
        qstnaireDao.deleteQstnairesByQidAfter(1L);
        baseDataDao.deleteAll();
        gameDataDao.deleteAll();
    }
}
