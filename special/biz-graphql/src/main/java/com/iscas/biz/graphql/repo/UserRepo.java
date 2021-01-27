package com.iscas.biz.graphql.repo;

import com.iscas.biz.graphql.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hanliwei
 * @create 2019-03-02 11:18
 */
public interface UserRepo extends JpaRepository<User,Long> {
    /**
     * @descript: 通过用户名查找用户
     * @auther: hanliwei
     * @date: 2019/3/2 11:29
     * @param name  用户名
     * @return User
     */
    User findUserByName(String name);

    User findUserByEmail(String email);

    User findUserByEmailAndPwd(String email,String pwd);

}
