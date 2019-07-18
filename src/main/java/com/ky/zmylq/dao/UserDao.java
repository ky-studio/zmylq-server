package com.ky.zmylq.dao;

import com.ky.zmylq.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface UserDao extends PagingAndSortingRepository<User, Long> {

    void deleteByUuid(Long uuid);
    User findByUuid(Long uuid);
    @Modifying
    @Query("update User u set u.status = ?1 where u.uuid = ?2")
    void updateUserStatus(boolean status, Long uuid);
}
