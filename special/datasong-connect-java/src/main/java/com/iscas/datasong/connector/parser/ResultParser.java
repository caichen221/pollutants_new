package com.iscas.datasong.connector.parser;

import com.iscas.datasong.connector.parser.function.FunctionHandler;
import com.iscas.datasong.connector.util.CollectionUtils;
import com.iscas.datasong.lib.common.StatisticItem;
import com.iscas.datasong.lib.common.StatisticResult;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/7/27 13:16
 * @since jdk11
 */
public class ResultParser {
    private static final Map<String, FunctionHandler> FUNCTION_HANDLER_MAP = new ConcurrentHashMap<>(32);

    private ResultParser() {
    }



    public static void parse(List<Map<String, Object>> items, List<StatisticResult> statisticResults,
                             List<SelectItem> selectItems, boolean hasGroupBy) {
        if (hasGroupBy) {
            if (CollectionUtils.isNotEmpty(statisticResults)) {
                List<Map<String, Object>> itemsOld = new ArrayList<>(items);
                items.clear();
                parseStatisticResult(statisticResults.get(0), items, new HashMap<>());
            }
        }
        if (CollectionUtils.isNotEmpty(selectItems)) {
            Map<String, List<Object[]>> resultHandleMap = getResultHandleMap(selectItems);
            if (CollectionUtils.isNotEmpty(items)) {
                for (Map<String, Object> item : items) {
                    for (Map.Entry<String, List<Object[]>> entry : resultHandleMap.entrySet()) {
                        String cols = entry.getKey();
                        List<Object[]> handleList = entry.getValue();
                        for (Object[] o : handleList) {
                            if (o[1] == null) {
                                Alias alias = (Alias) o[0];
                                if (alias != null) {
                                    item.put(alias.getName(), item.get(cols));
                                }
                            } else if (o[1] instanceof Function) {
                                Alias alias = (Alias) o[0];
                                Function function = (Function) o[1];
                                List<String> multipartName = function.getMultipartName();
                                if (CollectionUtils.isNotEmpty(multipartName)) {
                                    String funcName = multipartName.get(0);
                                    // 缓存处理函数的类并处理各个函数
                                    Optional.ofNullable(FUNCTION_HANDLER_MAP.computeIfAbsent(funcName, key -> {
                                        String className = FunctionHandler.class.getPackageName() + "." + funcName.toUpperCase(Locale.ROOT) + "_Handler";
                                        try {
                                            Class<? extends FunctionHandler> aClass = (Class<? extends FunctionHandler>) Class.forName(className);
                                            return aClass.getDeclaredConstructor().newInstance();
                                        } catch (InvocationTargetException | IllegalAccessException |
                                                 InstantiationException | NoSuchMethodException |
                                                 ClassNotFoundException ignored) {
                                            return null;
                                        }
                                    })).ifPresent(functionHandler -> functionHandler.handle(item, alias, function));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static void parseStatisticResult(StatisticResult sr, List<Map<String, Object>> items, Map<String, Object> groupInfo) {
        String alias = sr.getAlias();
        List<StatisticItem> statisticItems = sr.getResult();
        if (CollectionUtils.isNotEmpty(statisticItems)) {
            for (StatisticItem statisticItem : statisticItems) {
                Object k = statisticItem.getKey();
                Map<String, Object> subGroupInfo = new HashMap<>();
                subGroupInfo.put(alias, k);
                subGroupInfo.putAll(groupInfo);
                List<StatisticResult> children = statisticItem.getChildren();
                if (CollectionUtils.isNotEmpty(children)) {
                    List<StatisticItem> result = children.get(0).getResult();

                    if (CollectionUtils.isNotEmpty(result) && result.get(0).getChildren() == null) {
                        // 处理最后的值
                        handleLastData(children, subGroupInfo, items);
                    } else {
                        for (StatisticResult child : children) {
                            parseStatisticResult(child, items, subGroupInfo);
                        }
                    }
                }
            }
        }
    }

    private static void handleLastData(List<StatisticResult> children, Map<String, Object> groupInfo, List<Map<String, Object>> items) {
        Map<String, Object> result = new HashMap<>();
        for (StatisticResult child : children) {
            StatisticItem statisticItem = child.getResult().get(0);
            String keyAsString = statisticItem.getKeyAsString();
            Object value = statisticItem.getValue();
            keyAsString = keyAsString.replace("iscas123", "+");
            keyAsString = keyAsString.replace("Iscas123", "/");
            keyAsString = keyAsString.replace("dengyu", "=");
            keyAsString = new String(Base64.getDecoder().decode(keyAsString), StandardCharsets.UTF_8);
            result.put(keyAsString, value);
        }
        result.putAll(groupInfo);
        items.add(result);
    }

    private static Map<String, List<Object[]>> getResultHandleMap(List<SelectItem> selectItems) {
        Map<String, List<Object[]>> resultHandleMap = new HashMap<>(2);
        for (SelectItem selectItem : selectItems) {
            if (selectItem instanceof AllColumns || selectItem instanceof Column) {
                continue;
            }
            if (selectItem instanceof SelectExpressionItem) {
                SelectExpressionItem selectExpressionItem = (SelectExpressionItem) selectItem;
                Expression expression = selectExpressionItem.getExpression();
                Alias alias = selectExpressionItem.getAlias();
                if (expression instanceof Column) {
                    Column column = (Column) expression;
                    String columnName = column.getColumnName();
                    if (alias != null) {
                        resultHandleMap.computeIfAbsent(columnName, k -> new ArrayList<>()).add(new Object[]{alias, null});
                    }
                } else if (expression instanceof Function) {
                    Function function = (Function) expression;
                    ExpressionList parameters = function.getParameters();
                    if (parameters != null) {
                        String colNames = parameters.getExpressions().stream().filter(exp -> exp instanceof Column).map(exp -> ((Column) exp).getColumnName())
                                .collect(Collectors.joining(","));
                        resultHandleMap.computeIfAbsent(colNames, k -> new ArrayList<>()).add(new Object[]{alias, function});
                    }
                }
            }
        }
        return resultHandleMap;
    }
}
