package com.ky.backtracking.service;

import com.ky.backtracking.dao.AchievementDao;
import com.ky.backtracking.model.Achievement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AchieveService {

    @Autowired
    private AchievementDao achievementDao;

    public void addAchievement(Achievement achievement) {
        achievementDao.save(achievement);
    }

    public void updatAchievement(Achievement achievement) {
        achievementDao.save(achievement);
    }

    public  Achievement findAchieveByUuid(Long uuid) {
        return achievementDao.findByUuid(uuid);
    }

}
