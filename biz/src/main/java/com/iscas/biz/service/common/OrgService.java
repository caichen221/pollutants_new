package com.iscas.biz.service.common;

import com.iscas.base.biz.service.common.SpringService;
import com.iscas.biz.domain.common.Org;
import com.iscas.biz.mapper.common.OrgMapper;
import com.iscas.biz.mp.mapper.DynamicMapper;
import com.iscas.biz.mp.util.ValidatePropDistinctUtils;
import com.iscas.common.tools.assertion.AssertObjUtils;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.ValidDataException;
import com.iscas.templet.view.table.ComboboxData;
import com.iscas.templet.view.tree.TreeResponseData;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public OrgService(OrgMapper orgMapper) {
        this.orgMapper = orgMapper;
    }

    public TreeResponseData<Org> getTree() {
        List<Org> orgs = orgMapper.selectByExample(null);
        TreeResponseData<Org> root = new TreeResponseData<>();
        root.setId("root");
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
        Map<Integer, List<TreeResponseData<Org>>> childOrgs = new HashMap<>();
        for (Org org : orgs) {
            Integer orgId = org.getOrgId();
            Integer orgPid = org.getOrgPid();
            if (!childOrgs.containsKey(orgPid)) {
                List<TreeResponseData<Org>> treeDatas = new ArrayList<>();
                childOrgs.put(orgPid, treeDatas);
            }
            TreeResponseData<Org> comboboxData = new TreeResponseData<>();
            comboboxData.setLabel(org.getOrgName())
                    .setId(orgId)
                    .setData(org);

            childOrgs.get(orgPid).add(comboboxData);
        }
        return childOrgs;
    }

    public int addOrg(Org org) throws ValidDataException {
        AssertObjUtils.assertNull(org.getOrgId(), "请求参数有误，orgId必须为空");
        ValidatePropDistinctUtils.validateFromMysql(SpringService.getBean(DynamicMapper.class), "org", "org_name", org.getOrgName());
        Date date = new Date();
        org.setOrgCreateTime(date)
                .setOrgUpdateTime(date);
        return orgMapper.insert(org);
    }

    public int editOrg(Org org) {
        AssertObjUtils.assertNotNull(org.getOrgId(), "请求参数有误，orgId不能为空");
        Date date = new Date();
        org.setOrgUpdateTime(date);
        return orgMapper.updateByPrimaryKeyWithBLOBs(org);
    }

    public int deleteOrg(Integer orgId) {
        return orgMapper.deleteByPrimaryKey(orgId);
    }
}
