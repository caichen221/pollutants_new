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
import com.iscas.biz.mapper.common.MenuMapper;
import com.iscas.biz.mapper.common.ResourceMapper;
import com.iscas.biz.mapper.common.RoleMapper;
import com.iscas.biz.model.User;
import com.iscas.common.tools.core.security.AesUtils;
import com.iscas.common.tools.core.security.MD5Utils;
import com.iscas.common.tools.exception.lambda.LambdaExceptionUtils;
import com.iscas.common.tools.xml.Dom4jUtils;
import com.iscas.common.web.tools.cookie.CookieUtils;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.AuthConfigException;
import com.iscas.templet.exception.LoginException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
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
public class AuthServiceImpl extends AbstractAuthService {
  private final IAuthCacheService authCacheService;
  private final ResourceMapper resourceMapper;
  private final RoleMapper roleMapper;
  private final MenuMapper menuMapper;

    public AuthServiceImpl(IAuthCacheService authCacheService, ResourceMapper resourceMapper,
                           RoleMapper roleMapper, MenuMapper menuMapper) {
        this.authCacheService = authCacheService;
        this.resourceMapper = resourceMapper;
        this.roleMapper = roleMapper;
        this.menuMapper = menuMapper;
    }

    //    @Autowired
//    private UserService userService;
    @Cacheable(value = "auth", key="'url_map'")
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

    @Override
    public String getRoles(String username) {
//        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
//        queryWrapper.eq("username", username);
//        User user = userService.getOne(queryWrapper);
//        return user.getRole();

        return null;
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

    @Cacheable(value = "auth", key="'role_map'")
    @Override
    public Map<String, Role> getAuth() throws IOException, AuthConfigException {
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

//        //读取
//        Resource resource = new ClassPathResource(AUTH_CONFIG_XML_NAME);
//        try(
//                InputStream inputStream = resource.getInputStream();
//        )
//        {
//            Document document = Dom4jUtils.getXMLByInputStream(inputStream);
//            Element rootElement = document.getRootElement();
//            //获取menus
//            Map<String, Menu> menuMap = getMenuMap(rootElement);
//            //获取URLs
//            Map<String, Url> urlMap = getUrlMap(rootElement);
//            //获取roles节点
//            Element rolesElement = Dom4jUtils.getChildElement(rootElement, "roles");
//            //获得role节点列表
//            List<Element> roleElements = rolesElement.elements("role");
//            if(!CollectionUtils.isEmpty(roleElements)){
//                for (Element roleElement: roleElements) {
//                    Role role = new Role();
//                    String roleKey = roleElement.attributeValue("key");
//                    String roleName = roleElement.attributeValue("name");
//                    role.setKey(roleKey);
//                    role.setName(roleName);
//                    //将配置的menus注入
//                    insertMenus(roleElement,menuMap, role);
//                    //将配置的url注入
//                    insertUrls(roleElement, urlMap, role);
//                    result.put(roleKey, role);
//                }
//            }
//        }
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
            throw new LoginException("非法登陆",e.getMessage());
        }
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username",username);
        User dbUser = null;
//        User dbUser = userService.getOne(queryWrapper);

        if (dbUser == null) {
            throw new LoginException("用户不存在");
        } else {
            //加盐校验用户密码
            boolean verify = false;
            try {
                verify = MD5Utils.saltVerify(pwd, dbUser.getPassword());
            } catch (Exception e) {
                e.printStackTrace();
                throw new LoginException("校验密码出错");
            }

            if (!verify) {
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
                String roleKey = getRoles(username);
                Map<String, Role> auth = getAuth();
                Role role = auth.get(roleKey);
                Map map = new HashMap<>(2 << 2);
//                map.put("permission", dbUser.getRole());
                map.put(Constants.TOKEN_KEY, token);
//                map.put("role", dbUser.getRole());
                map.put("userId", dbUser.getId());
                map.put("username", dbUser.getUsername());

                responseEntity.setValue(map);
                dbUser.setPassword(null);
                //创建一个虚拟session
                CustomSession.setAttribute(sessionId, SESSION_USER, dbUser);

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
            } catch (AuthConfigException e) {
                e.printStackTrace();
                throw new LoginException("读取权限配置信息出错", e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new LoginException("登录异常", e);
            }
        }
    }

}
