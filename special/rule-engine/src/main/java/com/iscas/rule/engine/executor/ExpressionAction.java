package com.iscas.rule.engine.executor;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;

import java.util.Map;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2020/1/20 10:54
 * @since jdk1.8
 */
public class ExpressionAction {
    private ExpressionAction() {}
    public static boolean action(String expression, Map<String, Object> env) {
        Expression compiledExp2 = AviatorEvaluator.compile(expression);
        // 执行表达式
        Boolean result = (Boolean) compiledExp2.execute(env);
        return result;
    }

//    public static void main(String[] args) {
//        Map param = new HashMap();
//        param.put("p0", 801);
//        param.put("p1", 6.9);
//        param.put("p2", 6.9);
////        boolean action = action("(1==1) && (math.abs(p1)>3.8 && p1<17.5) && !(p0>=1.0 && p0!=-2.0)", param);
//        boolean action = action("(1==1) && (math.abs(p1)>3.8 && p1<17.5) && ((p0 >=-100.0 && p0 <= 3000.0) && (((p0 >= -100.0 && p0 <= -32.0) || (p0 >= 800.0 && p0 <= 3000.0)) ))", param);
//        System.out.println(action);
//    }
}
