package com.ky.backtracking.dao;

import com.ky.backtracking.model.Achievement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface AchievementDao extends CrudRepository<Achievement, Long> {

    Achievement findByUuid(Long uuid);

    List<Achievement> findTop200ByChildhoodOrderByChtimeAsc(boolean childhood);

    List<Achievement> findTop200ByUniversityOrderByUntimeAsc(boolean university);

    List<Achievement> findTop200ByOrderByNjmaxtimeDesc();

    void deleteAchievementsByUuidAfter(Long uuid);

    @Query("select max(ach.uuid) from Achievement ach")
    Long findMaxUuid();

    List<Achievement> findByUuidBetween(Long begin, Long end);
    List<Achievement> findByUuidAfter(Long begin);
}
