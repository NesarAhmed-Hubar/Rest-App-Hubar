package com.hubartech.rest_app.repository;

import com.hubartech.rest_app.model.User;
import com.hubartech.rest_app.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    UserInfo findByUser(User user);
}