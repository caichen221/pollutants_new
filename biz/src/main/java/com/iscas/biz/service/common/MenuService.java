package com.iscas.biz.service.common;

import com.iscas.base.biz.service.common.SpringService;
import com.iscas.biz.domain.common.*;
import com.iscas.biz.mapper.common.*;
import com.iscas.biz.mp.mapper.DynamicMapper;
import com.iscas.biz.mp.util.ValidatePropDistinctUtils;
import com.iscas.common.tools.assertion.AssertObjUtils;
import com.iscas.templet.exception.ValidDataException;
import com.iscas.templet.view.tree.TreeResponseData;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单service
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/22 8:26
 * @since jdk1.8
 */
@Service
public class MenuService {
    private final RoleMapper roleMapper;
    private final MenuMapper menuMapper;
    private final RoleMenuMapper roleMenuMapper;
    private final OprationMapper oprationMapper;
    private final MenuOprationMapper menuOprationMapper;


    public MenuService(RoleMapper roleMapper, MenuMapper menuMapper, RoleMenuMapper roleMenuMapper,
                       OprationMapper oprationMapper, MenuOprationMapper menuOprationMapper) {
        this.roleMapper = roleMapper;
        this.menuMapper = menuMapper;
        this.roleMenuMapper = roleMenuMapper;
        this.oprationMapper = oprationMapper;
        this.menuOprationMapper = menuOprationMapper;
    }

    public TreeResponseData<Menu> getTree() {
        List<Menu> menus = menuMapper.selectByExample(null);
        TreeResponseData<Menu> root = new TreeResponseData<>();
        root.setId("-1");
        root.setValue("root");
        root.setLabel("菜单");
        if (CollectionUtils.isNotEmpty(menus)) {
            Map<Integer, List<TreeResponseData<Menu>>> childOrgs = getChildMenus(menus);
            combineNode(null, root, childOrgs);
        }
        return root;
    }

    private void combineNode(Object pid, TreeResponseData<Menu> treeResponseData, Map<Integer, List<TreeResponseData<Menu>>> childOrgs) {
        List<TreeResponseData<Menu>> treeDataOrgs = childOrgs.get(pid);
        if (CollectionUtils.isNotEmpty(treeDataOrgs)) {
            treeResponseData.setChildren(treeDataOrgs);
            for (TreeResponseData<Menu> treeDataOrg : treeDataOrgs) {
                Integer id = (Integer) treeDataOrg.getId();
                combineNode(id, treeDataOrg, childOrgs);
            }
        }
    }

    private Map<Integer, List<TreeResponseData<Menu>>> getChildMenus(List<Menu> menus) {
        List<RoleMenuKey> roleMenuKeys = roleMenuMapper.selectByExample(null);
        List<Role> allRoles = roleMapper.selectByExample(null);
        Map<Integer, List<Role>> menuRoleMap = new HashMap<>();
        Map<Integer, Role> roleMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(allRoles)) {
            roleMap = allRoles.stream().collect(Collectors.toMap(Role::getRoleId, role -> role));
        }

        if (CollectionUtils.isNotEmpty(roleMenuKeys)) {
            for (RoleMenuKey roleMenuKey : roleMenuKeys) {
                List<Role> roles1 = menuRoleMap.get(roleMenuKey.getMenuId());
                if (roles1 == null) {
                    roles1 = new ArrayList<>();
                    menuRoleMap.put(roleMenuKey.getMenuId(), roles1);
                }
                Role role = roleMap.get(roleMenuKey.getRoleId());
                if (role != null) {
                    menuRoleMap.get(roleMenuKey.getMenuId()).add(role);
                }
            }
        }

        List<Map> menuOprationMaps = menuMapper.selectMenuOpration();
        Map<Integer, List<Map>> menuOprationMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(menuOprationMaps)) {
            for (Map oprationMap : menuOprationMaps) {
                Integer menuId = (Integer) oprationMap.get("menu_id");
                List<Map> maps = menuOprationMap.get(menuId);
                if (maps == null) {
                    maps = new ArrayList<>();
                    menuOprationMap.put(menuId, maps);
                }
                menuOprationMap.get(menuId).add(oprationMap);
            }
        }


        Map<Integer, List<TreeResponseData<Menu>>> childOrgs = new HashMap<>();
        for (Menu menu : menus) {
            Integer menuId = menu.getMenuId();
            Integer menuPid = menu.getMenuPid();
            if (!childOrgs.containsKey(menuPid)) {
                List<TreeResponseData<Menu>> treeDatas = new ArrayList<>();
                childOrgs.put(menuPid, treeDatas);
            }
            TreeResponseData<Menu> treeResponseData = new TreeResponseData<>();
            List<Role> rs = menuRoleMap.get(menuId);
            List<Map> menuOprations = menuOprationMap.get(menuId);
            treeResponseData.setLabel(menu.getMenuName())
                    .setId(menu.getMenuId())
                    .setValue(menu.getMenuId())
                    .setData(menu);
            if (CollectionUtils.isNotEmpty(rs)) {
                StringJoiner roleNamesJoiner = new StringJoiner(",");
                for (Role r : rs) {
                    menu.getRoleIds().add(r.getRoleId());
                    roleNamesJoiner.add(r.getRoleName());
                }
                menu.setRoleNames(roleNamesJoiner.toString());
            }
            if (CollectionUtils.isNotEmpty(menuOprations)) {
                StringJoiner opNameJoiner = new StringJoiner(",");
                for (Map menuOpration : menuOprations) {
                    menu.getOprationIds().add((Integer) menuOpration.get("op_id"));
                    opNameJoiner.add((String) menuOpration.get("op_name"));
                }
                menu.setOprationNames(opNameJoiner.toString());
            }

            childOrgs.get(menuPid).add(treeResponseData);
        }
        return childOrgs;
    }

    //    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Throwable.class)

    @Caching(evict = {
            @CacheEvict(value = "auth", key = "'url_map'"),
            @CacheEvict(value = "auth", key = "'menus'"),
            @CacheEvict(value = "auth", key = "'role_map'")
    })
    public int addMenu(Menu menu) throws ValidDataException {
        AssertObjUtils.assertNull(menu.getMenuId(), "请求参数有误，menuId必须为空");
        ValidatePropDistinctUtils.validateFromMysql(SpringService.getBean(DynamicMapper.class), "menu", "menu_name", menu.getMenuName());
        Date date = new Date();
        menu.setMenuCreateTime(date)
                .setMenuUpdateTime(date);
        int result = menuMapper.insert(menu);
        List<Integer> roleIds = menu.getRoleIds();
        List<Integer> opIds = menu.getOprationIds();
        //配置角色
        insertRoleIds(roleIds, menu);

        //配置权限标识
        insertOprations(opIds, menu);

        return result;
    }

    @Caching(evict = {
            @CacheEvict(value = "auth", key = "'url_map'"),
            @CacheEvict(value = "auth", key = "'menus'"),
            @CacheEvict(value = "auth", key = "'role_map'")
    })
    //    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Throwable.class)
    public int editMenu(Menu menu) {
        AssertObjUtils.assertNotNull(menu.getMenuId(), "请求参数有误，menuId不能为空");
        Date date = new Date();
        menu.setMenuUpdateTime(date);
        int result = menuMapper.updateByPrimaryKey(menu);

        //配置角色
        List<Integer> roleIds = menu.getRoleIds();
        RoleMenuExample roleMenuExample = new RoleMenuExample();
        roleMenuExample.createCriteria().andMenuIdEqualTo(menu.getMenuId());
        roleMenuMapper.deleteByExample(roleMenuExample);
        insertRoleIds(roleIds, menu);

        //配置权限标识
        List<Integer> opIds = menu.getOprationIds();
        MenuOprationExample menuOprationExample = new MenuOprationExample();
        menuOprationExample.createCriteria().andMenuIdEqualTo(menu.getMenuId());
        menuOprationMapper.deleteByExample(menuOprationExample);
        insertOprations(opIds, menu);

        return result;
    }
    private void insertRoleIds(List<Integer> roleIds, Menu menu) {
        if (CollectionUtils.isNotEmpty(roleIds)) {
            for (Integer roleId : roleIds) {
                RoleMenuKey roleMenuKey = new RoleMenuKey();
                roleMenuKey.setMenuId(menu.getMenuId());
                roleMenuKey.setRoleId(roleId);
                roleMenuMapper.insert(roleMenuKey);
            }
        }
    }

    private void insertOprations(List<Integer> opIds, Menu menu) {
        if (CollectionUtils.isNotEmpty(opIds)) {
            for (Integer opId : opIds) {
                MenuOprationKey menuOprationKey = new MenuOprationKey();
                menuOprationKey.setMenuId(menu.getMenuId());
                menuOprationKey.setOpId(opId);
                menuOprationMapper.insert(menuOprationKey);
            }
        }
    }

    @Caching(evict = {
            @CacheEvict(value = "auth", key = "'url_map'"),
            @CacheEvict(value = "auth", key = "'menus'"),
            @CacheEvict(value = "auth", key = "'role_map'")
    })
    public int deleteMenu(Integer menuId) {
        return menuMapper.deleteByPrimaryKey(menuId);
    }
}
