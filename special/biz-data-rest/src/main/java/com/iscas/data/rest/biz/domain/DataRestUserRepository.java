package com.iscas.data.rest.biz.domain;

import com.iscas.data.rest.biz.model.DataRestUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/7/17 14:10
 * @since jdk1.8
 */
@RepositoryRestResource(path = "datarestusers")
public interface DataRestUserRepository extends JpaRepository<DataRestUser, Integer> {
}
