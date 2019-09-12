package com.ky.backtracking.dao;

import com.ky.backtracking.model.Qstnaire;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface QstnaireDao extends CrudRepository<Qstnaire, Long> {

}
