package com.ky.backtracking.service;

import com.ky.backtracking.dao.SaveDao;
import com.ky.backtracking.model.Save;
import com.ky.backtracking.model.SaveMultiKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SaveService {

    @Autowired
    private SaveDao saveDao;

    public void addSave(Save save) {
        saveDao.save(save);
    }

    public void updateSave(Save save) {
        saveDao.save(save);
    }

    public void deleteSaveByUuidAndSaveid(Long uuid, Integer saveid) {
        SaveMultiKey mk = new SaveMultiKey(uuid, saveid);
        saveDao.deleteBySaveMultiKey(mk);
    }

    public Save findSaveByUuidAndSaveid(Long uuid, Integer saveid) {
        SaveMultiKey mk = new SaveMultiKey(uuid, saveid);
        return saveDao.findBySaveMultiKey(mk);
    }

}
