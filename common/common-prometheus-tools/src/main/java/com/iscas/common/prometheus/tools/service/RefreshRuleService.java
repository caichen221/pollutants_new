package com.iscas.common.prometheus.tools.service;

import com.iscas.common.prometheus.tools.constant.Constants;
import com.iscas.common.prometheus.tools.exception.PrometheusException;
import com.iscas.common.prometheus.tools.model.Annotation;
import com.iscas.common.prometheus.tools.model.Label;
import com.iscas.common.prometheus.tools.model.rule.Group;
import com.iscas.common.prometheus.tools.model.rule.Rule;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 刷新告警规则实现
 * <p>
 * 默认需要在prometheus.yml中配置：
 * rule_files:
 * - "*-rule.yml"
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2023/4/6 14:43
 */
public class RefreshRuleService implements Constants {

    /**
     * 是否校验规则
     */
    private boolean checkRule;

    private String prometheusYamlPath;

    private PrometheusService prometheusService;

    public RefreshRuleService(boolean checkRule, String prometheusYamlPath, PrometheusService prometheusService) {
        this.checkRule = checkRule;
        this.prometheusYamlPath = prometheusYamlPath;
        this.prometheusService = prometheusService;
    }

    /**
     * 新增或修改告警规则
     *
     * @date 2023/4/6
     */
    public synchronized void deleteRule(List<Group> groups) throws PrometheusException {
        Yaml yaml = new Yaml();
        // 检测file，如果file为空，不能创建
        for (Group group : groups) {
            if (Objects.isNull(group.getFile())) {
                throw new PrometheusException("规则组:[" + group.getName() + "]的file属性为空");
            }
        }
        // 备份文件
        List<File> backupFiles = new ArrayList<>();
        try {
            // 按照file分组
            Map<String, List<Group>> groupMapList = groups.stream().collect(Collectors.groupingBy(Group::getFile));
            for (Map.Entry<String, List<Group>> groupEntry : groupMapList.entrySet()) {
                String fileName = groupEntry.getKey();
                List<Group> groupList = groupEntry.getValue();
                File file = new File(prometheusYamlPath, fileName);
                Map<String, Object> loads = yaml.load(new FileInputStream(file));
                List<Map<String, Object>> groupInYaml = (List<Map<String, Object>>) loads.get(GROUPS);
                // 备份
                File backupFile = new File(file.getParent(), file.getName() + "-backup");
                try (OutputStream backupOs = new FileOutputStream(backupFile)) {
                    Files.copy(Path.of(file.getPath()), backupOs);
                    backupFiles.add(backupFile);
                }
                for (Map<String, Object> groupMapInYaml : groupInYaml) {
                    String nameInYaml = (String) groupMapInYaml.get("name");
                    List<Map<String, Object>> oldRules = (List<Map<String, Object>>) groupMapInYaml.get("rules");
                    for (Group group : groupList) {
                        String name = group.getName();
                        if (Objects.equals(nameInYaml, name)) {
                            List<Rule> rules = group.getRules();
                            if (Objects.nonNull(rules)) {
                                for (Rule rule : rules) {
                                    // 删除名字相同的规则
                                    oldRules.removeIf(oldRule -> Objects.equals(oldRule.getOrDefault(ALERT, oldRule.get(RECORD)), rule.getName()));
                                }
                            }
                        }
                        break;
                    }
                }
                // 遍历一下，如果group的rules为空，将此group删除
                groupInYaml.removeIf(group -> {
                    List<Map<String, Object>> rules = (List<Map<String, Object>>) group.get("rules");
                    return rules == null || rules.size() == 0;
                });

                String yamlStr = yaml.dumpAsMap(loads);
                try (PrintWriter pw = new PrintWriter(file)) {
                    pw.println(yamlStr);
                }
                // 检测规则是否合法
                if (checkRule) {
                    promtoolCheck(prometheusYamlPath, fileName);
                }

//                // 遍历一下，如果group都没了，将此文件删除
//                if (groupInYaml.size() == 0 ) {
//                    boolean delete = file.delete();
//                    System.out.println(delete);
//                } else {
//
//                }
            }
            prometheusService.reload();
        } catch (PrometheusException e) {
            try {
                rollback(backupFiles, new ArrayList<>());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            throw e;
        } catch (Exception e) {
            try {
                rollback(backupFiles, new ArrayList<>());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            throw new PrometheusException("删除告警规则出错", e);
        }
    }

    /**
     * 新增或修改告警规则
     *
     * @date 2023/4/6
     */
    public synchronized void createOrUpdateRule(List<Group> groups) throws PrometheusException {
        Yaml yaml = new Yaml();
        // 检测file，如果file为空，不能创建
        for (Group group : groups) {
            if (Objects.isNull(group.getFile())) {
                throw new PrometheusException("规则组:[" + group.getName() + "]的file属性为空");
            }
        }
        // 备份文件
        List<File> backupFiles = new ArrayList<>();
        // 需要删除的文件
        List<File> deleteFiles = new ArrayList<>();

        try {
            // 按照file分组
            Map<String, List<Group>> groupMapList = groups.stream().collect(Collectors.groupingBy(Group::getFile));
            for (Map.Entry<String, List<Group>> groupEntry : groupMapList.entrySet()) {
                String fileName = groupEntry.getKey();
                List<Group> groupList = groupEntry.getValue();
                File file = new File(prometheusYamlPath, fileName);
                if (!file.exists()) {
                    // 如果此配置规则的文件不存在，新建一个配置文件
                    Map<String, Object> groupMap = handleGroupsEditor(groupList);
                    String yamlStr = yaml.dumpAsMap(groupMap);
                    try (PrintWriter pw = new PrintWriter(file)) {
                        pw.println(yamlStr);
                    }
                    deleteFiles.add(file);
                    // 检测
                    if (checkRule) {
                        promtoolCheck(prometheusYamlPath, file.getName());
                    }
                } else {
                    // 规则文件如果已存在，修改此文件
                    Map<String, Object> loads = yaml.load(new FileInputStream(file));
                    List<Map<String, Object>> groupInYaml = (List<Map<String, Object>>) loads.get(GROUPS);
                    Map<String, Map<String, Object>> groupInYamlMap = new HashMap<>();
                    if (Objects.nonNull(groupInYaml)) {
                        groupInYamlMap = groupInYaml.stream().collect(Collectors.toMap(map -> (String) map.get("name"), map -> map));
                    }
                    for (Group group : groupList) {
                        String name = group.getName();
                        if (groupInYamlMap.containsKey(name)) {
                            Map<String, Object> oldGroupMap = groupInYamlMap.get(name);

                            // 处理rule
                            List<Rule> rules = group.getRules();
                            List<Map<String, Object>> oldRules = (List<Map<String, Object>>) oldGroupMap.get("rules");
                            if (Objects.isNull(oldRules)) {
                                oldRules = new ArrayList<>();
                            }
                            if (Objects.nonNull(rules)) {
                                for (Rule rule : rules) {
                                    // 生成新的rule
                                    boolean update = false;
                                    Map<String, Object> newRuleMap = handleRule(rule);
                                    String ruleName = rule.getName();
                                    for (int i = 0; i < oldRules.size(); i++) {
                                        Map<String, Object> oldRule = oldRules.get(i);
                                        String oldName = (String) oldRule.getOrDefault(ALERT, oldRule.get(RECORD));
                                        if (Objects.equals(ruleName, oldName)) {
                                            // 替换原来的rule
                                            update = true;
                                            oldRules.set(i, newRuleMap);
                                            break;
                                        }
                                    }
                                    if (!update) {
                                        // 添加一个新的rule
                                        oldRules.add(newRuleMap);
                                    }
                                }
                            }
                        } else {
                            // 创建新的group
                            Map<String, Object> newGroupMap = handleGroup(group);
                            groupInYaml.add(newGroupMap);
                            groupInYamlMap.put((String) newGroupMap.get("name"), newGroupMap);
                        }
                    }
                    String yamlStr = yaml.dumpAsMap(loads);
                    // 备份
                    File backupFile = new File(file.getParent(), file.getName() + "-backup");
                    try (OutputStream backupOs = new FileOutputStream(backupFile)) {
                        Files.copy(Path.of(file.getPath()), backupOs);
                        backupFiles.add(backupFile);
                    }
                    try (PrintWriter pw = new PrintWriter(file)) {
                        pw.println(yamlStr);
                    }
                    if (checkRule) {
                        promtoolCheck(prometheusYamlPath, file.getName());
                    }
                }
            }
            // reload prometheus
            prometheusService.reload();
        } catch (PrometheusException e) {
            try {
                rollback(backupFiles, deleteFiles);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            throw e;
        } catch (Exception e) {
            try {
                rollback(backupFiles, deleteFiles);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            throw new PrometheusException("创建或修改告警规则出错", e);
        }
    }

    private void promtoolCheck(String path, String ruleYmlName) throws PrometheusException {
        String command = path + "/promtool check rules " +  path + "/" + ruleYmlName;
        String os = System.getProperty("os.name");
        //Windows操作系统
        if (os != null && os.toLowerCase().startsWith("windows")) {
            command = path + "/promtool.exe check rules " +  path + "/" + ruleYmlName;
        } else if (os != null && os.toLowerCase().startsWith("linux")) {//Linux操作系统
            command = path + "/promtool check rules " +  path + "/" + ruleYmlName;
        }
        //接收正常结果流
        ByteArrayOutputStream susStream = new ByteArrayOutputStream();
        //接收异常结果流
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        CommandLine commandLine = CommandLine.parse(command);
        DefaultExecutor exec = new DefaultExecutor();
        PumpStreamHandler streamHandler = new PumpStreamHandler(susStream, errStream);
        exec.setStreamHandler(streamHandler);
        try {
            exec.execute(commandLine);
        } catch (Exception e) {
            String errorStr = errStream.toString(StandardCharsets.UTF_8);
            throw new PrometheusException("检测告警规则失败:" + errorStr, e);
        }
        System.out.println(susStream.toString(StandardCharsets.UTF_8));
    }

//    public static void main(String[] args) throws IOException {
//        RefreshRuleService refreshRuleService = new RefreshRuleService(false, null, null);
//        refreshRuleService.promtoolCheck("D:\\soft\\prometheus\\prometheus-2.37.6.windows-amd64", "test-rule.yml");
//    }

    private void rollback(List<File> backupFiles, List<File> deleteFiles) throws IOException {
        for (File backupFile : backupFiles) {
            String fileName = backupFile.getName();
            fileName = fileName.substring(0, fileName.lastIndexOf("-backup"));
            try (
                    InputStream is = new FileInputStream(backupFile);
                    OutputStream os = new FileOutputStream(new File(backupFile.getParent(), fileName));
                    ) {
                is.transferTo(os);
            }
        }
        for (File deleteFile : deleteFiles) {
            deleteFile.delete();
        }
    }

    /**
     * 将告警规则实体转为json字符串
     */
    private Map<String, Object> handleGroupsEditor(List<Group> groups) throws PrometheusException {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        List<Map<String, Object>> groupMaps = new ArrayList<>();

        for (Group group : groups) {
            Map<String, Object> groupMap = handleGroup(group);
            groupMaps.add(groupMap);
        }

        resultMap.put(GROUPS, groupMaps);
        return resultMap;
    }

    private Map<String, Object> handleGroup(Group group) throws PrometheusException {
        Map<String, Object> groupMap = new LinkedHashMap<>();
        String name = group.getName();
        groupMap.put(NAME, name);
        List<Map<String, Object>> ruleList = new ArrayList<>();

        List<Rule> rules = group.getRules();
        if (Objects.nonNull(rules)) {
            for (Rule rule : rules) {
                Map<String, Object> ruleMap = handleRule(rule);

                ruleList.add(ruleMap);
            }
        }
        groupMap.put(RULES, ruleList);
        return groupMap;
    }

    private Map<String, Object> handleRule(Rule rule) throws PrometheusException {
        Map<String, Object> ruleMap = new LinkedHashMap<>();

        // 根据不同的类型，设置alert或record参数
        String type = rule.getType();
        if (Objects.equals(ALERTING, type)) {
            ruleMap.put(ALERT, rule.getName());
        } else if (Objects.equals(RECORDING, type)) {
            ruleMap.put(RECORD, rule.getName());
        } else {
            throw new PrometheusException("不支持的告警类型:" + type);
        }

        // expr
        ruleMap.put(EXPR, rule.getQuery());

        // for
        Integer duration = rule.getDuration();
        if (Objects.nonNull(duration)) {
            ruleMap.put(FOR, duration + "s");
        }

        // labels
        ruleMap.put(LABELS, convertLabel(rule.getLabels()));

        // annotations
        ruleMap.put(ANNOTATIONS, convertAnnotation(rule.getAnnotations()));
        return ruleMap;
    }

    private Map<String, String> convertLabel(List<Label> labels) {
        if (Objects.nonNull(labels)) {
            Map<String, String> result = new HashMap<>();
            for (Label label : labels) {
                result.put(label.getKey(), label.getValue());
            }
            return result;
        }
        return null;
    }

    private Map<String, String> convertAnnotation(List<Annotation> annotations) {
        if (Objects.nonNull(annotations)) {
            Map<String, String> result = new HashMap<>();
            for (Annotation annotation : annotations) {
                result.put(annotation.getKey(), annotation.getValue());
            }
            return result;
        }
        return null;
    }

}
