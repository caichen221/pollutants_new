package com.iscas.biz.mp.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iscas.biz.mp.test.model.TestEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/3/26 14:08
 * @since jdk1.8
 */
@Repository
@Mapper
public interface TestEntityMapper extends BaseMapper<TestEntity> {
}
