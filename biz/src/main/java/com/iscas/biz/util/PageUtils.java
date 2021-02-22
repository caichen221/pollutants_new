package com.iscas.biz.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iscas.base.biz.util.SpringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/2/20 18:53
 * @since jdk1.8
 */
public class PageUtils<T> {

    public Page<T> getPage() {
        HttpServletRequest request = SpringUtils.getRequest();
        int pageNumber;
        int pageSize;
        try {
            pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
            pageSize = Integer.valueOf(request.getParameter("PageSize"));
        } catch (Exception e) {
            pageNumber = 0;
            pageSize = 10;
        }
        Page<T> page = new Page<>(pageNumber,pageSize);
        return page;
    }
}
