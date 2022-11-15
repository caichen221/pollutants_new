package com.iscas.biz;

import com.iscas.common.web.tools.json.JsonUtils;
import com.iscas.datasong.lib.request.SearchDataRequest;
import com.iscas.datasong.lib.request.search.condition.search.BoolSearchCondition;
import com.iscas.datasong.lib.request.search.condition.search.TermSearchCondition;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        SearchDataRequest req = new SearchDataRequest();
        req.setStart(0);
        req.setSize(10);
        BoolSearchCondition boolSearchCondition = new BoolSearchCondition();
        TermSearchCondition termSearchCondition = new TermSearchCondition();
        termSearchCondition.setColumn("name");
        termSearchCondition.setValue("wangwu");
        boolSearchCondition.setMust(List.of(termSearchCondition));
        req.setSearch(boolSearchCondition);
        System.out.println(JsonUtils.toJson(boolSearchCondition));
    }
}
