package com.ky.backtracking.service;

import com.ky.backtracking.dao.UserDao;
import com.ky.backtracking.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    public void addUser(User user) {
        userDao.save(user);
    }

    public void updateUser(User user) {
        userDao.save(user);
    }

    public void updateUserStatus(User user) {
        userDao.updateUserStatus(user.getStatus(), user.getUuid());
    }

    public void deleteUserByUuid(Long uuid) {
        userDao.deleteByUuid(uuid);
    }

    public User findUserByUuid(Long uuid) {
        return userDao.findByUuid(uuid);
    }

    public User findUserByPNumber(String pnumber) {
        return userDao.findByPNumber(pnumber);
    }

}
