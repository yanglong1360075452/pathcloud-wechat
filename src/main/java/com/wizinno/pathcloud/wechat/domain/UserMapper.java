package com.wizinno.pathcloud.wechat.domain;

import com.wizinno.pathcloud.wechat.condition.UserCondition;
import com.wizinno.pathcloud.wechat.domain.model.User;
import java.util.List;

public interface UserMapper {

    int insert(User record);

    List<User> selectAll();

    User selectByOpenId(String openId);

    User selectByUsername(String username);

    int update(User user);

    void deleteByOpenId(String openId);

    void deleteByUsername(String username);

    List<User> selectByCondition(UserCondition condition);
}