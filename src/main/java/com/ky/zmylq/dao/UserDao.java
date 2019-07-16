package com.ky.zmylq.dao;

import com.ky.zmylq.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface UserDao extends PagingAndSortingRepository<User, Integer> {
    public User findByUuid(int uuid);
}
