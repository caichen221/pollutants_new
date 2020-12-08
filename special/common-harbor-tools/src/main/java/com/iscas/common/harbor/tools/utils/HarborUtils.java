package com.iscas.common.harbor.tools.utils;

import com.iscas.common.harbor.tools.OkHttpCustomClient;
import com.iscas.common.harbor.tools.OkHttpProps;
import com.iscas.common.harbor.tools.exception.CallHarborException;
import com.iscas.common.harbor.tools.model.ModuleHealth;
import com.iscas.common.harbor.tools.model.Project;
import com.iscas.common.tools.core.date.DateSafeUtils;
import com.iscas.common.web.tools.json.JsonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiFunction;

/**
 * 访问Harbor的工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/12/8 18:39
 * @since jdk1.8
 */
public class HarborUtils {
    private static String username = "admin";
    private static String password = "Harbor12345";
    private static String url = "http://192.168.100.96:80/api";
    private static OkHttpCustomClient httpClient = new OkHttpCustomClient(new OkHttpProps());

    public static void setProp(String username, String password, String url) {
        HarborUtils.username = username;
        HarborUtils.password = password;
        HarborUtils.url = url;
    }


    private HarborUtils() {}

    private static void throwResultError(String s) throws CallHarborException {
        if (s != null && s.startsWith("<!DOCTYPE HTML>")) {
            throw new CallHarborException("调用Harbor出错");
        }
    }

    /**
     * 查看Harbor各个组件模块健康状况
     * */
    public static List<ModuleHealth> health() throws IOException, CallHarborException {
        List<ModuleHealth> moduleHealths = new ArrayList<>();
        String visitUrl = url + "/health";
        String result = httpClient.doGet(visitUrl);
        throwResultError(result);
        Map map = JsonUtils.fromJson(result, Map.class);
        if (MapUtils.isNotEmpty(map) && map.containsKey("components")) {
            List list = (List) map.get("components");
            if (CollectionUtils.isNotEmpty(list)) {
                for (Object o : list) {
                    Map healthMap = (Map) o;
                    ModuleHealth moduleHealth = new ModuleHealth();
                    moduleHealth.setName((String) healthMap.get("name"));
                    moduleHealth.setStatus((String) healthMap.get("status"));
                    moduleHealths.add(moduleHealth);
                }
            }
        }
        return moduleHealths;
    }

    /**
     * 查看Harbor的project,模糊查询
     * */
    public static List<Project> search(String projectName) throws IOException, CallHarborException {
        List<Project> projects = new ArrayList<>();
        String visitUrl = url + "/search" ;
        if (StringUtils.isNotEmpty(projectName)) {
            visitUrl += "?q=" + projectName;
        }
        String result = httpClient.doGet(visitUrl);
        System.out.println(result);
        throwResultError(result);
        Map map = JsonUtils.fromJson(result, Map.class);
        if (MapUtils.isNotEmpty(map)) {
            List<Map> projectMaps = (List<Map>) map.get("project");
            if (CollectionUtils.isNotEmpty(projectMaps)) {

                BiFunction<String, Object, Object> biFunction = new BiFunction<String, Object, Object>() {
                    @Override
                    public Object apply(String s, Object s2) {
                        if (Objects.equals("createTime", s) || Objects.equals("updateTime", s)) {
                            if (s2 != null) {
                                Date date = null;
                                try {
                                    date = DateSafeUtils.parse(s2.toString().substring(0, s2.toString().length() - 4), "yyyy-MM-dd'T'HH:mm:ss.SSS");
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                                return date;
                            }
                        } else if (Objects.equals("projectPublic", s)) {
                            Map meta = (Map) s2;
                            return Boolean.valueOf((String) meta.get("public"));
                        }
                        return s2;
                    }
                };

                projects = Json2ObjUtils.json2List(Project.class, projectMaps, biFunction,
                        "project_id", "projectId", "owner_id", "ownerId", "name", "name",
                        "creation_time", "createTime", "update_time", "updateTime", "deleted", "deleted",
                        "repo_count", "repoCount", "metadata", "projectPublic");
            }
        }

        return projects;
    }

}
