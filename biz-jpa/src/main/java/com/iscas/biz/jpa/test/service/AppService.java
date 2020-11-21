package com.iscas.biz.jpa.test.service;

import com.iscas.biz.jpa.test.domain.AppDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/15 15:19
 * @since jdk1.8
 */
@Service
public class AppService {
    @Autowired
    private AppDao appDao;
}
