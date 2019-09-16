package com.ky.backtracking.service;

import com.ky.backtracking.dao.AchievementDao;
import com.ky.backtracking.model.Achievement;
import com.ky.backtracking.model.RankList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AchieveService {

    @Autowired
    private AchievementDao achievementDao;

    private static final Logger LOG = LoggerFactory.getLogger(AchieveService.class);

    public void addAchievement(Achievement achievement) {
        achievementDao.save(achievement);
    }

    public void updatAchievement(Achievement achievement) {
        achievementDao.save(achievement);
    }

    public  Achievement findAchieveByUuid(Long uuid) {
        return achievementDao.findByUuid(uuid);
    }

    public RankList getRankList(Long uuid) {
        RankList rankList = new RankList();
        rankList.order1 = -1;
        rankList.list1 = new ArrayList<>();
        List<Achievement> achievementList = achievementDao.findTop50ByChildhoodOrderByChtimeAsc(true);
        for (int i = 0; i < achievementList.size(); i++) {
            if (achievementList.get(i).getUuid().equals(uuid)) {
                rankList.order1 = i + 1;
            }
            rankList.list1.add(achievementList.get(i).getChtime().toString());
        }
        rankList.order2 = -1;
        rankList.list2 = new ArrayList<>();
        achievementList = achievementDao.findTop50ByUniversityOrderByUntimeAsc(true);
        for (int i = 0; i < achievementList.size(); i++) {
            if (achievementList.get(i).getUuid().equals(uuid)) {
                rankList.order2 = i + 1;
            }
            rankList.list2.add(achievementList.get(i).getUntime().toString());
        }
        return rankList;
    }

}
