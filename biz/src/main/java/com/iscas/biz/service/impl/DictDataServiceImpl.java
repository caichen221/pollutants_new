package com.iscas.biz.service.impl;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iscas.base.biz.config.Constants;
import com.iscas.base.biz.util.CacheUtils;
import com.iscas.biz.mapper.DictDataMapper;
import com.iscas.biz.mapper.DictDataTypeMapper;
import com.iscas.biz.model.DictData;
import com.iscas.biz.model.DictDataType;
import com.iscas.biz.model.Param;
import com.iscas.biz.service.DictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/2/25 16:13
 * @since jdk1.8
 */
@Service
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements DictDataService {
    @Autowired
    private DictDataMapper dictDataMapper;
    @Autowired
    private DictDataTypeMapper dictDataTypeMapper;

    @Override
    public boolean deleteByIds(List<Object> ids) {
        //系统类的参数不允许删除
        UpdateWrapper<DictData> wrapper = new UpdateWrapper();
        wrapper.in("id", ids);
        wrapper.ne("dict_data_type", "系统类");
        List<DictData> paramList = dictDataMapper.selectList(wrapper);
        List<String> dictDataTypes = Optional.ofNullable(paramList)
                .filter(params -> params.size() > 0)
                .map(params -> params.stream().map(DictData::getDictDataType).collect(Collectors.toList()))
                .orElse(new ArrayList<>());
        //清理缓存
        CacheUtils.evictCache(Constants.CACHE_DICT_NAME, dictDataTypes);
        //删除字典数据表
        dictDataMapper.delete(wrapper);
        //删除字典数据类型表
        UpdateWrapper<DictDataType> deleteDictDataTypeWrapper = new UpdateWrapper();
        deleteDictDataTypeWrapper.in("dict_data_type", dictDataTypes);
        dictDataTypeMapper.delete(deleteDictDataTypeWrapper);
        return true;
    }
}
