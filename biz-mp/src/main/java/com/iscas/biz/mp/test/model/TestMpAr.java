package com.iscas.biz.mp.test.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 测试mybatis-plus AR模式
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/4/7 20:54
 * @since jdk1.8
 */
@Data
public class TestMpAr extends Model<TestMpAr> {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
}
