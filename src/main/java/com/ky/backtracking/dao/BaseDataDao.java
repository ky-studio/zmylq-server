package com.ky.backtracking.dao;

import com.ky.backtracking.model.BaseData;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface BaseDataDao extends CrudRepository<BaseData, Long> {

}
