package com.iscas.biz.mp.test.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import com.iscas.biz.mp.test.mapper.ParentMapper;
import com.iscas.biz.mp.test.model.Parent;
import com.iscas.biz.mp.test.service.IParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/7/16
 * @since jdk1.8
 */
@Service
@ConditionalOnMybatis
public class ParentServiceImpl implements IParentService {
    @Autowired
    private ParentMapper parentMapper;

    @Override
    public List<Parent> findCascadeAllByCondition(Parent parent) {
        return parentMapper.selectCascadeAllByCondition(parent);
    }

    @Override
    public List<Parent> findCascadeAll() {
        //分页插件: 查询第1页，每页2行
        Page<Parent> page = PageHelper.startPage(1, 2);
        parentMapper.selectCascadeAll();
//        //数据表的总行数
//        page.getTotal();
//        //分页查询结果的总行数
//        page.size();
//        //第一个User对象，参考list，序号0是第一个元素，依此类推
//        page.get(0);
        return page.getResult();
    }

    @Override
    public int save(Parent parent) {
        return parentMapper.insert(parent);
    }
}
