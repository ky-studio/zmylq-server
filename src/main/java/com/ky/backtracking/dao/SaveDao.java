package com.ky.backtracking.dao;

import com.ky.backtracking.model.Save;
import com.ky.backtracking.model.SaveMultiKey;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface SaveDao extends CrudRepository<Save, SaveMultiKey> {

    void deleteBySaveMultiKey(SaveMultiKey mk);
    Save findBySaveMultiKey(SaveMultiKey mk);
}
