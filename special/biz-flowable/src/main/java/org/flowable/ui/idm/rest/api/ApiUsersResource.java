package org.flowable.ui.idm.rest.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.flowable.ui.common.model.GroupRepresentation;
import org.flowable.ui.common.model.UserRepresentation;
import org.flowable.ui.common.service.exception.NotFoundException;
import org.flowable.ui.idm.model.UserInformation;
import org.flowable.ui.idm.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/18 21:00
 * @since jdk11
 */
@RestController
@RequestMapping(value = "/api")
@SuppressWarnings(value = "unused")
@RequiredArgsConstructor
@Slf4j
public class ApiUsersResource {
    protected final UserService userService;

    @RequestMapping(value = "/idm/users/{userId}", method = RequestMethod.GET, produces = {"application/json"})
    public UserRepresentation getUserInformation(@PathVariable String userId) {
        UserInformation userInformation = userService.getUserInformation(userId);
        if (userInformation != null) {
            UserRepresentation userRepresentation = new UserRepresentation(userInformation.getUser());
            if (userInformation.getGroups() != null) {
                for (Group group : userInformation.getGroups()) {
                    userRepresentation.getGroups().add(new GroupRepresentation(group));
                }
            }
            if (userInformation.getPrivileges() != null) {
                for (String privilege : userInformation.getPrivileges()) {
                    userRepresentation.getPrivileges().add(privilege);
                }
            }
            return userRepresentation;
        } else {
            throw new NotFoundException();
        }
    }

    @SuppressWarnings("AlibabaRemoveCommentedCode")
    @RequestMapping(value = "/idm/users", method = RequestMethod.GET, produces = {"application/json"})
    public List<UserRepresentation> findUsersByFilter(@RequestParam("filter") String filter, HttpServletResponse response) {
        // HttpClient 转发 过来的，eg. HttpClientUtils.httpGet("http://39.105.173.191:5555/jsite/api/idm/users?filter=xxx");
        // 存在跨域问题 所以增加下面的设置  参考：https://blog.csdn.net/qq_41463655/article/details/89703831
        response.setHeader("Access-Control-Allow-Origin", "*");
        log.debug("-------------------findUsersByFilter------------------------");
        List<User> users = userService.getUsers(filter, null, null);
        List<UserRepresentation> result = new ArrayList<>();
        for (User user : users) {
            result.add(new UserRepresentation(user));
        }
        return result;
    }
}
