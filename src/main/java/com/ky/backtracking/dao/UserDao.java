package com.ky.backtracking.dao;

import com.ky.backtracking.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;

@Transactional
public interface UserDao extends PagingAndSortingRepository<User, Long> {

    void deleteByUuid(Long uuid);
    User findByUuid(Long uuid);
    User findByPNumber(String pnumber);
    @Modifying
    @Query("update User u set u.status = ?1 where u.uuid = ?2")
    void updateUserStatus(boolean status, Long uuid);
}
