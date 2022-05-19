package com.iscas.biz.controller.common.auth;

import com.iscas.biz.domain.common.Org;
import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import com.iscas.biz.service.common.OrgService;
import com.iscas.common.tools.assertion.AssertCollectionUtils;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.BaseException;
import com.iscas.templet.view.tree.TreeResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 组织机构管理
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2021/2/20 18:07
 * @since jdk1.8
 */
@SuppressWarnings({"unused", "rawtypes", "unchecked"})
@Api(tags = "组织机构管理")
@RestController
@RequestMapping("/org")
@Validated
@ConditionalOnMybatis
public class OrgController extends BaseController {
    private final OrgService orgService;

    public OrgController(OrgService orgService) {
        this.orgService = orgService;
    }

    @ApiOperation(value="[组织机构]获取组织机构树", notes="create by:朱全文 2021-02-20")
    @GetMapping
    public TreeResponse get() throws BaseException {
        return getTreeResponse().setValue(orgService.getTree());
    }

    @ApiOperation(value="[组织机构]新增组织机构节点", notes="create by:朱全文 2021-02-20")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "org", value = "组织机构数据", required = true, dataType = "Org")
            }
    )
    @PostMapping("/node")
    public ResponseEntity addNode(@Valid @RequestBody Org org) throws BaseException {
        return getResponse().setValue(orgService.addOrg(org));
    }

    @ApiOperation(value="[组织机构]修改组织机构节点", notes="create by:朱全文 2021-02-20")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "org", value = "组织机构数据", required = true, dataType = "Org")
            }
    )
    @PutMapping("/node")
    public ResponseEntity editNode(@Valid @RequestBody Org org) {
        return getResponse().setValue(orgService.editOrg(org));
    }

    @ApiOperation(value="[组织机构]删除组织机构节点", notes="create by:朱全文 2021-02-20")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "orgIds", value = "组织机构Id", required = true, dataType = "List")
            }
    )
    @PostMapping("/node/del")
    @Caching(evict = {
            @CacheEvict(value = "auth", key = "'url_map'"),
            @CacheEvict(value = "auth", key = "'menus'"),
            @CacheEvict(value = "auth", key = "'role_map'")
    })
    public ResponseEntity deleteNode(@RequestBody List<Integer> orgIds) {
        AssertCollectionUtils.assertCollectionNotEmpty(orgIds, "orgIds不能未空");
        orgService.deleteNode(orgIds);
        return getResponse();
    }

}
