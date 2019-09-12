package com.ky.backtracking.dao;

import com.ky.backtracking.model.FeedBack;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface FeedBackDao extends CrudRepository<FeedBack, Long> {
    
}
