package com.ky.backtracking.dao;

import com.ky.backtracking.model.GameData;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface GameDataDao extends CrudRepository<GameData, Long> {

    GameData findByUuidAndGamecode(Long uuid, int gcode);
    GameData findTopByUuidAndGamecodeOrderBySavenumDesc(Long uuid, int gcode);
}
