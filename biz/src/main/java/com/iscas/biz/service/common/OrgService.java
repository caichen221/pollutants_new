package com.iscas.biz.service.common;

import com.iscas.base.biz.service.common.SpringService;
import com.iscas.biz.domain.common.Org;
import com.iscas.biz.domain.common.OrgRoleExample;
import com.iscas.biz.domain.common.OrgRoleKey;
import com.iscas.biz.domain.common.Role;
import com.iscas.biz.mapper.common.OrgMapper;
import com.iscas.biz.mapper.common.OrgRoleMapper;
import com.iscas.biz.mapper.common.RoleMapper;
import com.iscas.biz.mp.mapper.DynamicMapper;
import com.iscas.biz.mp.util.ValidatePropDistinctUtils;
import com.iscas.common.tools.assertion.AssertObjUtils;
import com.iscas.templet.exception.ValidDataException;
import com.iscas.templet.view.tree.TreeResponseData;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 组织机构service
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/20 18:10
 * @since jdk1.8
 */
@Service
public class OrgService {
    private final OrgMapper orgMapper;
    private final OrgRoleMapper orgRoleMapper;
    private final RoleMapper roleMapper;


    public OrgService(OrgMapper orgMapper, OrgRoleMapper orgRoleMapper, RoleMapper roleMapper) {
        this.orgMapper = orgMapper;
        this.orgRoleMapper = orgRoleMapper;
        this.roleMapper = roleMapper;
    }

    public TreeResponseData<Org> getTree() {
        List<Org> orgs = orgMapper.selectByExample(null);
        TreeResponseData<Org> root = new TreeResponseData<>();
        root.setId("-1");
        root.setValue("root");
        root.setLabel("组织机构");
        if (CollectionUtils.isNotEmpty(orgs)) {
            Map<Integer, List<TreeResponseData<Org>>> childOrgs = getChildOrgs(orgs);
            combineNode(null, root, childOrgs);
        }
        return root;
    }

    private void combineNode(Integer pid, TreeResponseData treeResponseData, Map<Integer, List<TreeResponseData<Org>>> childOrgs) {
        List<TreeResponseData<Org>> treeDataOrgs = childOrgs.get(pid);
        if (CollectionUtils.isNotEmpty(treeDataOrgs)) {
            treeResponseData.setChildren(treeDataOrgs);
            for (TreeResponseData<Org> treeDataOrg : treeDataOrgs) {
                Integer id = (Integer) treeDataOrg.getId();
                combineNode(id, treeDataOrg, childOrgs);
            }
        }
    }

    private Map<Integer, List<TreeResponseData<Org>>> getChildOrgs(List<Org> orgs) {
        List<OrgRoleKey> orgRoleKeys = orgRoleMapper.selectByExample(null);
        List<Role> roles = roleMapper.selectByExample(null);
        Map<Integer, List<Role>> orgRoleMap = new HashMap<>();
        Map<Integer, Role> roleMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(roles)) {
            roleMap = roles.stream().collect(Collectors.toMap(Role::getRoleId, role -> role));
        }

        if (CollectionUtils.isNotEmpty(orgRoleKeys)) {
            for (OrgRoleKey orgRoleKey : orgRoleKeys) {
                List<Role> roles1 = orgRoleMap.get(orgRoleKey.getOrgId());
                if (roles1 == null) {
                    roles1 = new ArrayList<>();
                    orgRoleMap.put(orgRoleKey.getOrgId(), roles1);
                }
                Role role = roleMap.get(orgRoleKey.getRoleId());
                if (role != null) {
                    orgRoleMap.get(orgRoleKey.getOrgId()).add(role);
                }
            }
        }

        Map<Integer, List<TreeResponseData<Org>>> childOrgs = new HashMap<>();
        for (Org org : orgs) {
            Integer orgId = org.getOrgId();
            Integer orgPid = org.getOrgPid();
            if (!childOrgs.containsKey(orgPid)) {
                List<TreeResponseData<Org>> treeDatas = new ArrayList<>();
                childOrgs.put(orgPid, treeDatas);
            }
            TreeResponseData<Org> comboboxData = new TreeResponseData<>();
            List<Role> rs = orgRoleMap.get(orgId);
            comboboxData.setLabel(org.getOrgName())
                    .setId(orgId)
                    .setValue(orgId)
                    .setData(org);
            if (CollectionUtils.isNotEmpty(rs)) {
                StringJoiner roleNamesJoiner = new StringJoiner(",");
                for (Role r : rs) {
                    org.getRoleIds().add(r.getRoleId());
                    roleNamesJoiner.add(r.getRoleName());
                }
                org.setRoleNames(roleNamesJoiner.toString());
            }
            childOrgs.get(orgPid).add(comboboxData);
        }
        return childOrgs;
    }

//    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Throwable.class)
    public int addOrg(Org org) throws ValidDataException {
        AssertObjUtils.assertNull(org.getOrgId(), "请求参数有误，orgId必须为空");
        ValidatePropDistinctUtils.validateFromMysql(SpringService.getBean(DynamicMapper.class), "org", "org_name", org.getOrgName());
        Date date = new Date();
        org.setOrgCreateTime(date)
                .setOrgUpdateTime(date);
        int result = orgMapper.insert(org);
        List<Integer> roleIds = org.getRoleIds();
        //配置角色
        if (CollectionUtils.isNotEmpty(roleIds)) {
            for (Integer roleId : roleIds) {
                OrgRoleKey orgRoleKey = new OrgRoleKey();
                orgRoleKey.setOrgId(org.getOrgId());
                orgRoleKey.setRoleId(roleId);
                orgRoleMapper.insert(orgRoleKey);
            }
        }
        return result;
    }

    //    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Throwable.class)
    public int editOrg(Org org) {
        AssertObjUtils.assertNotNull(org.getOrgId(), "请求参数有误，orgId不能为空");
        Date date = new Date();
        org.setOrgUpdateTime(date);
        int result = orgMapper.updateByPrimaryKeyWithBLOBs(org);
        //配置角色
        List<Integer> roleIds = org.getRoleIds();
        OrgRoleExample orgRoleExample = new OrgRoleExample();
        orgRoleExample.createCriteria().andOrgIdEqualTo(org.getOrgId());
        orgRoleMapper.deleteByExample(orgRoleExample);
        if (CollectionUtils.isNotEmpty(roleIds)) {
            for (Integer roleId : roleIds) {
                OrgRoleKey orgRoleKey = new OrgRoleKey();
                orgRoleKey.setOrgId(org.getOrgId());
                orgRoleKey.setRoleId(roleId);
                orgRoleMapper.insert(orgRoleKey);
            }
        }
        return result;
    }

    public int deleteOrg(Integer orgId) {
        return orgMapper.deleteByPrimaryKey(orgId);
    }
}
