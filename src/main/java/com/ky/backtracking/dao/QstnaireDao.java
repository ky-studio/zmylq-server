package com.ky.backtracking.dao;

import com.ky.backtracking.model.Qstnaire;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface QstnaireDao extends CrudRepository<Qstnaire, Long> {

    Qstnaire findByQid(Long qid);

    void deleteQstnairesByQidAfter(Long qid);

    @Query("select max(qs.qid) from Qstnaire qs")
    Long findMaxQid();

    List<Qstnaire> findByQidBetween(Long begin, Long end);
}
