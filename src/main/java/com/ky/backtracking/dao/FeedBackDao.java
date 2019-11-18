package com.ky.backtracking.dao;

import com.ky.backtracking.model.FeedBack;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface FeedBackDao extends CrudRepository<FeedBack, Long> {

    @Query("select max(fb.fid) from FeedBack fb")
    Long findMaxFid();

    List<FeedBack> findByFidBetween(Long begin, Long end);
}
