package org.flowable.ui.idm.rest.api;

import lombok.RequiredArgsConstructor;
import org.flowable.idm.api.Group;
import org.flowable.ui.common.model.GroupRepresentation;
import org.flowable.ui.common.service.exception.NotFoundException;
import org.flowable.ui.idm.service.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/18 20:58
 * @since jdk11
 */
@RestController
@RequestMapping(value = "/api")
@SuppressWarnings(value = "unused")
@RequiredArgsConstructor
public class ApiGroupsResource {
    protected final GroupService groupService;

    @RequestMapping(value = "/idm/groups/{groupId}", method = RequestMethod.GET, produces = {"application/json"})
    public GroupRepresentation getGroupInformation(@PathVariable String groupId) {
        Group group = groupService.getGroup(groupId);
        if (group != null) {
            return new GroupRepresentation(group);

        } else {
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "/idm/groups", method = RequestMethod.GET, produces = {"application/json"})
    public List<GroupRepresentation> findGroupsByFilter(@RequestParam("filter") String filter) {
        List<GroupRepresentation> result = new ArrayList<>();
        List<Group> groups = groupService.getGroups(filter);
        for (Group group : groups) {
            result.add(new GroupRepresentation(group));
        }
        return result;
    }
}
