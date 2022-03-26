package com.iscas.biz.mp.service.common;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iscas.biz.mp.model.QrtzJob;
import com.iscas.templet.exception.BaseException;
import com.iscas.templet.view.table.TableResponseData;
import com.iscas.templet.view.table.TableSearchRequest;

import java.time.LocalDateTime;

/**
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2022/3/26 10:21
 * @since jdk1.8
 */
public interface IQrtzJobService extends IService<QrtzJob> {
    /**
     * 启动定时任务
     *
     * @param id 定时任务ID
     * @return void
     * @throws BaseException
     * @version 1.0
     * @date 2022/3/14
     * @since jdk11
     */
    void startJob(Integer id) throws BaseException;


    /**
     * 暂停定时任务
     *
     * @param id 定时任务ID
     * @return void
     * @throws BaseException
     * @version 1.0
     * @date 2022/3/14
     * @since jdk11
     */
    void pauseJob(Integer id) throws BaseException;

    /**
     * 查询任务
     *
     * @param request 请求条件
     * @return cn.ac.iscas.dmo.templet.view.table.TableResponseData
     * @throws
     * @version 1.0
     * @date 2022/3/14
     * @since jdk11
     */
    TableResponseData select(TableSearchRequest request);

    /**
     * 更新定时任务，只能修改cron表达式
     *
     * @version 1.0
     * @since jdk11
     * @date 2022/3/14
     * @param job 定时任务
     * @throws
     * @return void
     */
    void renew(QrtzJob job) throws BaseException;

    /**
     * 删除定时任务
     * @version 1.0
     * @since jdk11
     * @date 2022/3/14
     * @param id 定时任务ID
     * @throws
     * @return void
     */
    void delete(Integer id) throws BaseException;

    /**
     * 立即触发执行一个定时任务
     * @version 1.0
     * @since jdk11
     * @date 2022/3/14
     * @param id 定时任务ID
     * @throws
     * @return void
     */
    void trigger(Integer id) throws BaseException;

    /**
     * 判断定时器是否是待机模式
     * @version 1.0
     * @since jdk11
     * @date 2022/3/14
     * @param
     * @throws
     * @return boolean
     */
    boolean isInStandbyMode() throws BaseException;

    /**
     * 启动定时器
     * @version 1.0
     * @since jdk11
     * @date 2022/3/14
     * @param
     * @throws
     * @return
     */
    void startScheduler() throws BaseException;

    /**
     * 待机定时器
     * @version 1.0
     * @since jdk11
     * @date 2022/3/14
     * @param
     * @throws
     * @return
     */
    void standbyScheduler() throws BaseException;

    LocalDateTime getNextFireTime(String cronExpression) throws BaseException;
}
