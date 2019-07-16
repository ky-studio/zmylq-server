package com.ky.zmylq.dao;

import com.ky.zmylq.model.Save;
import com.ky.zmylq.model.SaveMultiKey;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface SaveDao extends CrudRepository<Save, SaveMultiKey> {

}
