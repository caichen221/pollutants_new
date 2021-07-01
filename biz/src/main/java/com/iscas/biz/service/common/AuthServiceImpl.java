package com.iscas.biz.service.common;

import com.iscas.base.biz.config.Constants;
import com.iscas.base.biz.model.auth.Menu;
import com.iscas.base.biz.model.auth.Role;
import com.iscas.base.biz.model.auth.Url;
import com.iscas.base.biz.service.AbstractAuthService;
import com.iscas.base.biz.service.IAuthCacheService;
import com.iscas.base.biz.util.CustomSession;
import com.iscas.base.biz.util.JWTUtils;
import com.iscas.base.biz.util.LoginCacheUtils;
import com.iscas.biz.domain.common.User;
import com.iscas.biz.mapper.common.MenuMapper;
import com.iscas.biz.mapper.common.ResourceMapper;
import com.iscas.biz.mapper.common.RoleMapper;
import com.iscas.biz.mapper.common.UserMapper;
import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import com.iscas.common.tools.core.security.AesUtils;
import com.iscas.common.tools.core.security.MD5Utils;
import com.iscas.common.tools.exception.lambda.LambdaExceptionUtils;
import com.iscas.common.web.tools.cookie.CookieUtils;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.LoginException;
import com.iscas.templet.view.tree.TreeResponseData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.weaver.ast.Var;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户认证鉴权service
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/7/16 18:58
 * @since jdk1.8
 */
@Service()
@Slf4j
@ConditionalOnMybatis
public class AuthServiceImpl extends AbstractAuthService {
    private static final String CACHE_KEY_LOGIN_ERROR_COUNT = "CACHE_KEY_LOGIN_ERROR_COUNT";
    private static final String CACHE_KEY_USER_LOCK = "CACHE_KEY_USER_LOCK";
    private static final int MAX_LOGIN_ERROR_COUNT = 5;
    private final IAuthCacheService authCacheService;
    private final ResourceMapper resourceMapper;
    private final RoleMapper roleMapper;
    private final MenuMapper menuMapper;
    private final UserMapper userMapper;
    private final MenuService menuService;
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);


    public AuthServiceImpl(IAuthCacheService authCacheService, ResourceMapper resourceMapper,
                           RoleMapper roleMapper, MenuMapper menuMapper, UserMapper userMapper, MenuService menuService) {
        this.authCacheService = authCacheService;
        this.resourceMapper = resourceMapper;
        this.roleMapper = roleMapper;
        this.menuMapper = menuMapper;
        this.userMapper = userMapper;
        this.menuService = menuService;
    }

    //    @Autowired
//    private UserService userService;
    @Cacheable(value = "auth", key = "'url_map'")
    @Override
    public Map<String, Url> getUrls() {
        log.debug("------读取url信息------");
        List<com.iscas.biz.domain.common.Resource> resources = resourceMapper.selectByExample(null);
        if (CollectionUtils.isNotEmpty(resources)) {
            return resources.stream().map(LambdaExceptionUtils.lambdaWrapper(resource -> {
                Url url = new Url();
                url.setKey(String.valueOf(resource.getResourceId()))
                        .setName(resource.getResourceUrl());
                return url;
            })).collect(Collectors.toMap(Url::getKey, url -> url));
        }
        return new HashMap<>();
    }

    @Cacheable(value = "auth", key = "'menus'")
    @Override
    public List<Menu> getMenus() {
        List<Menu> menus = new ArrayList<>();
        List<com.iscas.biz.domain.common.Menu> dbMenus = menuMapper.selectByExample(null);
        if (CollectionUtils.isNotEmpty(dbMenus)) {
            menus = dbMenus.stream().map(LambdaExceptionUtils.lambdaWrapper(m -> {
                Menu menu = new Menu();
                menu.setName(m.getMenuName());
                menu.setKey(String.valueOf(m.getMenuId()));
                return menu;
            })).collect(Collectors.toList());
        }
        return menus;
    }


    @Cacheable(value = "auth", key = "'username:'.concat(#username)")
    @Override
    public List<Role> getRoles(String username) {
        List<Role> roles = new ArrayList<>();
        List<Map> userRoleMaps = userMapper.selectUserRoleByUsername(username);
        if (CollectionUtils.isNotEmpty(userRoleMaps)) {
            for (Map userRoleMap : userRoleMaps) {
                Integer roleId = (Integer) userRoleMap.get("role_id");
                String roleName = (String) userRoleMap.get("role_name");
                Map<String, Role> auth = getAuth();
                Role role = auth.get(String.valueOf(roleId));
                if (role != null) roles.add(role);

//                Role role = new Role();
//                role.setName(roleName);
//                role.setKey(String.valueOf(roleId));
//                roles.add(role);
            }
        }
        return roles;
    }


    @Override
    public void invalidToken(HttpServletRequest request) {
        String token = null;
        token = request.getHeader(TOKEN_KEY);
        if (token == null) {
            //尝试从cookie中拿author
            Cookie cookie = CookieUtils.getCookieByName(request, TOKEN_KEY);
            if (cookie != null) {
                token = cookie.getValue();
            }
        }
//        CaffCacheUtils.remove(token);
        authCacheService.remove(token);
        request.getSession().invalidate();
    }

    @Cacheable(value = "auth", key = "'role_map'")
    @Override
    public Map<String, Role> getAuth() {
        log.debug("------读取角色信息------");
        Map<String, Role> result = new HashMap<>(2 << 6);
        List<com.iscas.biz.domain.common.Role> commonRoles = roleMapper.selectByExample(null);
        Map<String, Url> urls = getUrls();
        List<Map> menuRoles = menuMapper.selectMenuRole();
        List<Map> roleResources = roleMapper.selectRoleResource();

        Map<Integer, List<Menu>> menuRoleMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(menuRoles)) {
            for (Map menuRole : menuRoles) {
                int roleId = (int) menuRole.get("role_id");
                int menuId = (int) menuRole.get("menu_id");
                String menuName = (String) menuRole.get("menu_name");
                List<Menu> maps = menuRoleMap.get(roleId);
                if (maps == null) {
                    maps = new ArrayList<>();
                    menuRoleMap.put(roleId, maps);
                }
                Menu menu = new Menu();
                menu.setKey(String.valueOf(menuId));
                menu.setName(menuName);
                maps.add(menu);
            }
        }
        Map<Integer, List<Url>> urlRoleMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(roleResources)) {
            for (Map roleResource : roleResources) {
                int roleId = (int) roleResource.get("role_id");
                int resourceId = (int) roleResource.get("resource_id");
                Url url = urls.get(String.valueOf(resourceId));
                List<Url> maps = urlRoleMap.get(roleId);
                if (maps == null) {
                    maps = new ArrayList<>();
                    urlRoleMap.put(roleId, maps);
                }
                if (url != null) {
                    maps.add(url);
                }
            }
        }

        if (CollectionUtils.isNotEmpty(commonRoles)) {
            result = commonRoles.stream().map(LambdaExceptionUtils.lambdaWrapper(r -> {
                Role role = new Role();
                role.setKey(String.valueOf(r.getRoleId()));
                role.setName(r.getRoleName());
                role.setMenus(menuRoleMap.get(r.getRoleId()));
                role.setUrls(urlRoleMap.get(r.getRoleId()));
                return role;
            })).collect(Collectors.toMap(Role::getKey, r -> r));
        }
        return result;
    }

    @Override
    public void loginHandler(HttpServletResponse response, Map<String, String> user, ResponseEntity responseEntity, int expire, int cookieExpire) throws LoginException {
        String pwd = user.get("password");
        String username = user.get("username");
        String key = user.get("key");
        String loginKey = LoginCacheUtils.get(key);
        if (loginKey == null) {
            throw new LoginException("未获得加密码，拒绝登录");
        }
        LoginCacheUtils.remove(key);
        try {
            username = AesUtils.aesDecrypt(username, loginKey);
            pwd = AesUtils.aesDecrypt(pwd, loginKey);
        } catch (Exception e) {
            e.printStackTrace();
            throw new LoginException("非法登陆", e.getMessage());
        }
        String userLockedKey = CACHE_KEY_USER_LOCK + "_" + username;
        String userLoginErrorCountKey = CACHE_KEY_LOGIN_ERROR_COUNT + "_" + username;
        if (authCacheService.get(userLockedKey) != null) {
            throw new LoginException("用户登录连续失败次数过多，已被锁定，解锁时间2分钟");
        }
        Integer errCount = (Integer) authCacheService.get(userLoginErrorCountKey);
        if (errCount != null && errCount >= MAX_LOGIN_ERROR_COUNT) {
            authCacheService.set(userLockedKey, "locked");
            scheduledExecutorService.scheduleWithFixedDelay(() -> {
                authCacheService.remove(userLockedKey);
                authCacheService.remove(userLoginErrorCountKey);
            }, 0, 120, TimeUnit.SECONDS);
            throw new LoginException("用户登录连续失败次数过多，已被锁定，解锁时间2分钟");
        }
//        User dbUser = null;
        User dbUser = userMapper.selectByUserName(username);
//        User dbUser = userService.getOne(queryWrapper);

        if (dbUser == null) {
            throw new LoginException("用户不存在");
        } else {
            //加盐校验用户密码
            boolean verify = false;
            try {
                verify = MD5Utils.saltVerify(pwd, dbUser.getUserPwd());
            } catch (Exception e) {
                e.printStackTrace();
                throw new LoginException("校验密码出错");
            }

            if (!verify) {
                Integer count = (Integer) authCacheService.get(userLoginErrorCountKey);
                int errorCount = count == null ? 1 : count + 1;
                authCacheService.set(userLoginErrorCountKey, errorCount);
                throw new LoginException("密码错误");
            }

            String token = null;
            try {
                String sessionId = UUID.randomUUID().toString();
                token = JWTUtils.createToken(username, expire);
                //清除以前的TOKEN
                //暂时加上这个处理
//                String tokenold = (String) CaffCacheUtils.get("user-token" + username);
                String tokenold = (String) authCacheService.get("user-token" + username);
                if (tokenold != null) {
//                    CaffCacheUtils.remove(tokenold);
                    authCacheService.remove(tokenold);
                }
//                CaffCacheUtils.set("user-token" + username, token);
                authCacheService.set("user-token" + username, token);

                CookieUtils.setCookie(response, TOKEN_KEY, token, cookieExpire);
                List<Role> roles = getRoles(username);
//                Map<String, Role> auth = getAuth();
//                List<Role> roles = new ArrayList<>();
//                if (roleKey != null) {
//                    for (String s : roleKey.split(",")) {
//                        Role role = auth.get(s);
//                        if (role != null) {
//                            roles.add(role);
//                        }
//                    }
//                }

                Map map = new HashMap<>(2 << 2);
                List<String> menus = new ArrayList<>();
                List<Menu> menuList = new ArrayList<>();
                for (Role role : roles) {
                    if (Objects.equals(role.getName(), Constants.SUPER_ROLE_KEY)) {
                        //超级管理员角色
                        List<Menu> dbMenus = getMenus();
                        if (CollectionUtils.isNotEmpty(dbMenus)) menuList.addAll(dbMenus);
                    } else {
                        List<Menu> roleMenus = role.getMenus();
                        if (roleMenus != null) menuList.addAll(roleMenus);
                    }
                }
                if (CollectionUtils.isNotEmpty(menuList)) {
                    menuList.add(new Menu("-1","首页"));
                    menus = menuList.stream().map(ml -> ml.getName()).distinct().collect(Collectors.toList());
                    //修改返回菜单的数据结构
                    TreeResponseData<com.iscas.biz.domain.common.Menu> tree = menuService.getTree();
                    TreeResponseData<com.iscas.biz.domain.common.Menu> finalMenus = getFinalMenus(tree, menus);
                    map.put("menu", finalMenus);
                }

//                map.put("menu", menus);
                map.put(Constants.TOKEN_KEY, token);

//                map.put("role", role);
//                map.put("roleId", map.get("orgId"));
                map.put("userId", dbUser.getUserId());
                map.put("username", dbUser.getUserName());
                map.put("userRealName", dbUser.getUserRealName());

                responseEntity.setValue(map);
                dbUser.setUserPwd(null);
                //创建一个虚拟session
                CustomSession.setAttribute(sessionId, SESSION_USER, dbUser);

                authCacheService.remove(userLockedKey);
                authCacheService.remove(userLoginErrorCountKey);

                //处理多用户登陆的问题
//                if (username != null) {
//                    synchronized (username.intern()) {
//                        User dbUser1 = userService.getById(dbUser.getId());
//                        String sessionId = dbUser1.getSessionId();
//                        //如果数据库sessionId部位空，并且session不是当前SESSION
//                        if (sessionId != null && !StringUtils.equals(sessionId, session.getId())) {
//                            HttpSession session1 = MySessionContext.getSession(sessionId);
//                            //使之前的session失效
//                            if (session1 != null) {
//                                session1.invalidate();
//                            }
//
//                        }
//                        dbUser1.setSessionId(session.getId());
//                        userService.updateById(dbUser1);
//
//                        //处理websocket的问题
//                        WebSocketSession webSocketSession = (WebSocketSession) CaffCacheUtils.get("websocketSession:" + dbUser.getUsername());
//                        if (webSocketSession != null) {
//                            webSocketSession.close();
//                        }
//                    }
//                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw new LoginException("登录时创建token异常", e);
            }
//            catch (AuthConfigException e) {
//                e.printStackTrace();
//                throw new LoginException("读取权限配置信息出错", e);
//            }
            catch (IOException e) {
                e.printStackTrace();
                throw new LoginException("登录异常", e);
            }
        }
    }

    private TreeResponseData<com.iscas.biz.domain.common.Menu> getFinalMenus(TreeResponseData<com.iscas.biz.domain.common.Menu> tree, List<String> menus) {

        Optional.ofNullable(tree).map(t -> t.getData()).ifPresent(t -> {
            tree.setPath(tree.getData().getMenuPage());
        });

        List<TreeResponseData<com.iscas.biz.domain.common.Menu>> children = tree.getChildren();
        if (CollectionUtils.isEmpty(children)) {
            return null;
        }
        List<TreeResponseData<com.iscas.biz.domain.common.Menu>> copyChildren = new ArrayList<>();
        children.stream().forEach(child -> copyChildren.add(child.clone()));
        tree.setChildren(copyChildren);

        children.stream().forEach(child -> {
            if (child == null) return;
            if (!menus.contains(child.getData().getMenuName())) {
                copyChildren.remove(child);
            }
        });
        copyChildren.stream().forEach(child -> getFinalMenus(child, menus));
        return tree;
    }

}
