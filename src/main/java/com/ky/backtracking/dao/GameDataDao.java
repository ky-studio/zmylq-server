package com.ky.backtracking.dao;

import com.ky.backtracking.model.GameData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface GameDataDao extends CrudRepository<GameData, Long> {

    GameData findByUuidAndGamecode(Long uuid, int gcode);
    GameData findTopByUuidAndGamecodeOrderBySavenumDesc(Long uuid, int gcode);

    List<GameData> findByGidBetween(Long begin, Long end);

    @Query("select max(gd.gid) from GameData gd")
    Long findMaxGid();
}
