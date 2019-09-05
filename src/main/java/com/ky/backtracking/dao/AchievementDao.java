package com.ky.backtracking.dao;

import com.ky.backtracking.model.Achievement;
import com.ky.backtracking.model.Save;
import com.ky.backtracking.model.SaveMultiKey;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface AchievementDao extends CrudRepository<Achievement, Long> {

    Achievement findByUuid(Long uuid);
}