package com.ky.backtracking.dao;

import com.ky.backtracking.model.Achievement;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface AchievementDao extends CrudRepository<Achievement, Long> {

    Achievement findByUuid(Long uuid);

    List<Achievement> findTop50ByChildhoodOrderByChtimeAsc(boolean childhood);

    List<Achievement> findTop50ByUniversityOrderByUntimeAsc(boolean university);

}