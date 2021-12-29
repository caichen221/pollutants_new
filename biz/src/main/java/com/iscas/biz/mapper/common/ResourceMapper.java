package com.iscas.biz.mapper.common;

import com.iscas.biz.domain.common.Resource;
import com.iscas.biz.mp.enhancer.mapper.DynamicMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceMapper extends DynamicMapper<Resource> {
//    List<Resource> selectByExample(ResourceExample example);
}