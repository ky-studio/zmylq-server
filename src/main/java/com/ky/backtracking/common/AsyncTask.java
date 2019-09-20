package com.ky.backtracking.common;

import com.ky.backtracking.model.Achievement;
import com.ky.backtracking.model.Save;
import com.ky.backtracking.model.SaveMultiKey;
import com.ky.backtracking.service.AchieveService;
import com.ky.backtracking.service.SaveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncTask {

    private static final Logger LOG = LoggerFactory.getLogger(AsyncTask.class);

    @Autowired
    private SaveService saveService;
    @Autowired
    private AchieveService achieveService;

    @Async
    public void initSavesAndAchievement(Long uuid) {
// 新手机号注册用户新建空存档
        for (int i = 0; i <= 4; i++) {
            Save save = new Save(new SaveMultiKey(uuid, i), "");
            saveService.addSave(save);
        }
        // 新建空成就
        Achievement achievement = new Achievement(uuid);
        achieveService.addAchievement(achievement);
        LOG.info("initSavesAndAchievement completed, user uuid: {}", uuid);
    }

}
