package com.iscas.biz.mapper.common;

import com.iscas.biz.domain.common.WsData;
import com.iscas.biz.mp.enhancer.mapper.DynamicMapper;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface WsDataMapper extends DynamicMapper<WsData> {

//    int deleteByExample(WsDataExample example);


//    int insert(WsData record);



//    List<WsData> selectByExample(WsDataExample example);

//    WsData selectByPrimaryKey(Integer id);






//    int updateByPrimaryKey(WsData record);

    @Delete("delete from ws_data where create_time < #{time,jdbcType=TIMESTAMP}")
    int deleteByTime(Date time);
}