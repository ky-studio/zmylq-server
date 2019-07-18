package com.ky.zmylq.service;

import com.ky.zmylq.dao.SaveDao;
import com.ky.zmylq.model.Save;
import com.ky.zmylq.model.SaveMultiKey;
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
