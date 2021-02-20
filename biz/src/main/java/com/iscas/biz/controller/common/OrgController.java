package com.iscas.biz.controller.common;

import com.iscas.biz.domain.common.Org;
import com.iscas.biz.service.common.OrgService;
import com.iscas.templet.exception.BaseException;
import com.iscas.templet.view.tree.TreeResponse;
import com.iscas.templet.view.tree.TreeResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 组织机构管理
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/20 18:07
 * @since jdk1.8
 */
@Api(tags = "组织机构管理")
@RestController
@RequestMapping("/org")
public class OrgController {
    @Autowired
    private OrgService orgService;

    @ApiOperation(value="[组织机构]获取组织机构树", notes="create by:朱全文 2021-02-20")
//    @ApiImplicitParams(
//            {
//                    @ApiImplicitParam(name = "user", value = "用户名密码等信息", required = true, dataType = "Map")
//            }
//    )
    @GetMapping
    public TreeResponse get() throws BaseException {
        TreeResponse treeResponse = new TreeResponse();
        treeResponse.setMessage("操作成功");
        TreeResponseData<Org> treeResponseData = orgService.getTree();
        treeResponse.setValue(treeResponseData);
        return treeResponse;
    }
}
