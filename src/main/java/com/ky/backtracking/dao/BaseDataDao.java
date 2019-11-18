package com.ky.backtracking.dao;

import com.ky.backtracking.model.BaseData;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface BaseDataDao extends CrudRepository<BaseData, Long> {

    List<BaseData> findByTimestampBetween(String begin, String end);
    List<BaseData> findByBidBetween(Long begin, Long end);

}
