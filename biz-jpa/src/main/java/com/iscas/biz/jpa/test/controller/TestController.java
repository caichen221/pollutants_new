package com.iscas.biz.jpa.test.controller;

import com.iscas.biz.jpa.test.domain.App;
import com.iscas.biz.jpa.test.domain.AppDao;
import com.iscas.biz.jpa.test.domain.Organizational;
import com.iscas.biz.jpa.test.domain.OrganizationalDao;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/15 17:24
 * @since jdk1.8
 */
@RestController
public class TestController extends BaseController {
    @Autowired
    private AppDao appDao;
    @Autowired
    private OrganizationalDao orgDao;

    /**
     * 测试新增
     * */
    @GetMapping("/testAdd")
    public ResponseEntity testAdd() {
        var app = new App();
        app.setAppAddr("192.168.100.88").setName("fwfwfwgwegw");
        var save = appDao.save(app);
        var response = getResponse();
        response.setValue(save);
        return response;
    }

    /**
     * 测试CascadeType.Persist,保存父表，关联保存子表，这样子表的外键不是插入值，不建议用
     * */
    @GetMapping("/testAddPersist")
    public ResponseEntity testAddPersist() {
        var app = new App();
        app.setAppAddr("192.168.100.88").setName("zhaoliu1");
        Organizational organizational = new Organizational();
        organizational.setName("org21");
        app.setOrgs(Arrays.asList(organizational));
        var save = appDao.save(app);
        var response = getResponse();
        response.setValue(save);
        return response;
    }

    /**
     * 测试CascadeType.Persist,保存父表，关联保存子表，这样子表的外键会插入值
     * */
    @GetMapping("/testAddPersist2")
    public ResponseEntity testAddPersist2() {
        var app = new App();
        app.setAppAddr("192.168.100.88").setName("zhaoliu3");

        Organizational organizational = new Organizational();
        organizational.setName("org24");
        //给子表注入主表就可以插入值
        organizational.setApp(app);
        app.setOrgs(Arrays.asList(organizational));
        var save = appDao.save(app);
        var response = getResponse();
        response.setValue(save);
        return response;
    }

    /**
     * 测试CascadeType.Persist,修改父表，关联修改子表
     * */
    @GetMapping("/testUpdateRersist")
    public ResponseEntity testUpdatePersist() {
        var appOptional = appDao.findById(7);
        var app = appOptional.get();
        app.setName("wangwu3");
        var orgs = app.getOrgs();
        if (CollectionUtils.isNotEmpty(orgs)) {
            var org = orgs.get(0);
            org.setName("orgmmm");
        }
        App save = appDao.save(app);
        var response = getResponse();
        response.setValue(save);
        return response;
    }

    /**
     * 测试CascadeType.Persist,修改父表，关联把子表关联子表的字段清空
     * */
    @GetMapping("/testUpdateRersist2")
    public ResponseEntity testUpdatePersist2() {
        var appOptional = appDao.findById(8);
        var app = appOptional.get();
        List<Organizational> orgs = app.getOrgs();
        if (CollectionUtils.isNotEmpty(orgs)) {
            for (Organizational org : orgs) {
                org.setApp(null);
            }
        }
        App save = appDao.save(app);
        var response = getResponse();
        response.setValue(save);
        return response;
    }

    /**
     * 测试查询
     * */
    @GetMapping("/testFind1")
    public ResponseEntity testFind1() {
        var optionalOrganizational = orgDao.findById(4);
        Organizational org = optionalOrganizational.get();
        var response = getResponse();
        response.setValue(org);
        return response;
    }
    /**
     * 测试CascadeType.MERGE,保存父表，关联保存子表，不成功
     * */
    @GetMapping("/testAddMerge")
    public ResponseEntity testAddMerge() {
        var app = new App();
        app.setAppAddr("192.168.100.88").setName("zhaoliu11");
        Organizational organizational = new Organizational();
        organizational.setName("org28");
        app.setOrgs(Arrays.asList(organizational));
        var save = appDao.save(app);
        var response = getResponse();
        response.setValue(save);
        return response;
    }

    /**
     * 测试CascadeType.MERGE,级联修改
     * */
    @GetMapping("/testUpdateMerge")
    public ResponseEntity testUpdateMerge() {
        var appOptional = appDao.findById(7);
        var app = appOptional.get();
        app.setName("wangwu4");
        var orgs = app.getOrgs();
        if (CollectionUtils.isNotEmpty(orgs)) {
            var org = orgs.get(0);
            org.setName("orgnnn");
        }
        App save = appDao.save(app);
        var response = getResponse();
        response.setValue(save);
        return response;
    }

    /**
     * 测试CascadeType.MERGE,修改父表，关联把子表关联子表的字段清空
     * */
    @GetMapping("/testUpdateMerge2")
    public ResponseEntity testUpdateMerge2() {
        var appOptional = appDao.findById(8);
        var app = appOptional.get();
        List<Organizational> orgs = app.getOrgs();
        if (CollectionUtils.isNotEmpty(orgs)) {
            for (Organizational org : orgs) {
                org.setApp(null);
            }
        }
        App save = appDao.save(app);
        var response = getResponse();
        response.setValue(save);
        return response;
    }


    /**
     * 测试CascadeType.REMOVE,删除父表，关联把子表数据删除
     * */
    @GetMapping("/testRemove")
    public ResponseEntity testRemove() {
        var appOptional = appDao.findById(8);
        var app = appOptional.get();
        appDao.delete(app);
        var response = getResponse();
        response.setValue("success");
        return response;
    }



}
