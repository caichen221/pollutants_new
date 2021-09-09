package com.iscas.common.tools.mapstruct;

import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 用户实体转换接口
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/9/9 16:34
 * @since jdk1.8
 */
@Mapper
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    @MapMapping
    TestUserVO1 toTestUserVO1(TestUserDTO testUser);

//    TestUserVO2 toTestUserVO2(TestUserDTO testUser);
}
