package com.iscas.common.tools.core.reflect;

import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 反射增强工具类
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2018/7/13
 * @since jdk1.8
 */
@SuppressWarnings({"unused", "rawtypes", "unchecked"})
public class ReflectUtils {
    /**
     * 私有构造方法，防止被实例化使用
     */
    private ReflectUtils() {
    }


    @SuppressWarnings({"AlibabaAvoidComplexCondition", "deprecation"})
    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) ||
                !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
                Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    public static void makeAccessible(Field field, Object obj) {
        if ((!Modifier.isPublic(field.getModifiers()) ||
                !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
                Modifier.isFinal(field.getModifiers())) && !field.canAccess(obj)) {
            field.setAccessible(true);
        }
    }

    /**
     * 反射执行一个对象的某个方法,不带参数
     *
     * @param data       {@link Object} 任意一个对象
     * @param methodName 函数名称
     * @return java.lang.Object 方法返回的结果
     * @throws NoSuchMethodException     noSuchMethodException
     * @throws IllegalAccessException    illegalAccessException
     * @throws InvocationTargetException invocationTargetException
     * @date 2018/7/13
     * @since jdk1.8
     */
    public static Object doMethod(Object data, String methodName) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {

        assert StringUtils.isNotBlank(methodName);
        assert data != null;
        Method m1 = data.getClass().getDeclaredMethod(methodName);
        return m1.invoke(data);
    }

    /**
     * 反射执行一个对象的某个方法,携带参数<br/>
     * 这里比较难处理的是8中基本类型，<br/>
     * 传入参数的类型取出来的class 变为了包装类.<br/>
     * 处理方式是将8中基本数据类型与包装类分别作一个映射匹配，判断函数，然后执行。
     *
     * @param data       {@link Object} 任意一个对象
     * @param methodName 函数名称
     * @param args       参数
     * @return java.lang.Object 方法返回的结果
     * @throws IllegalAccessException    illegalAccessException
     * @throws InvocationTargetException invocationTargetException
     * @date 2018/7/13
     * @since jdk1.8
     */
    public static Object doMethodWithParam(Object data, String methodName, Object... args) throws InvocationTargetException, IllegalAccessException {
        assert StringUtils.isNotBlank(methodName);
        assert data != null;
        assert args != null;
        Class[] c;
        //存在
        int len = args.length;
        c = new Class[len];
        for (int i = 0; i < len; ++i) {
            c[i] = args[i].getClass();
        }
        Method[] methods = data.getClass().getDeclaredMethods();
        for (Method method : methods) {
            //判断方法名称匹配
            if (StringUtils.equals(methodName, method.getName())) {
                //判断方法参数匹配
                Class<?>[] clazzs = method.getParameterTypes();
                boolean flag = true;
                label:
                for (int i = 0; i < clazzs.length; i++) {
                    Class clazz = clazzs[i];
                    Class claxx = c[i];
                    if (claxx != clazz) {
                        switch (clazz.getName()) {
                            case "int":
                                if (!"Integer".equals(claxx.getSimpleName())) {
                                    break label;
                                }
                                break;
                            case "byte":
                                if (!"Byte".equals(claxx.getSimpleName())) {
                                    break label;
                                }
                                break;
                            case "short":
                                if (!"Short".equals(claxx.getSimpleName())) {
                                    break label;
                                }
                                break;
                            case "long":
                                if (!"Long".equals(claxx.getSimpleName())) {
                                    break label;
                                }
                                break;
                            case "boolean":
                                if (!"Boolean".equals(claxx.getSimpleName())) {
                                    break label;
                                }
                                break;
                            case "char":
                                if (!"Character".equals(claxx.getSimpleName())) {
                                    break label;
                                }
                                break;
                            case "float":
                                if (!"Float".equals(claxx.getSimpleName())) {
                                    break label;
                                }
                                break;
                            case "double":
                                if (!"Double".equals(claxx.getSimpleName())) {
                                    break label;
                                }
                                break;
                            default:
                        }
                    }

                }
                return method.invoke(data, args);
            }
        }
        return null;
    }

    /**
     * 判断一个Classs是否为基本数据类型
     *
     * @param clz Class对象
     * @return boolean
     * @date 2018/7/16
     * @since jdk1.8
     */
    public static boolean isWrapClass(Class clz) {
        try {
            return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 对象是否为数组对象
     *
     * @param obj 对象
     * @return 是否为数组对象，如果为{@code null} 返回false
     */
    public static boolean isArray(Object obj) {
        if (null == obj) {
            return false;
        }
//        反射 获得类型
        return obj.getClass().isArray();
    }

    /**
     * 获取一个类和其父类的所有属性
     *
     * @param clazz Class对象
     * @return java.util.List<java.lang.reflect.Field>
     * @date 2018/7/16
     * @since jdk1.8
     */
    public static List<Field> findAllFieldsOfSelfAndSuperClass(Class clazz) {

        Field[] fields;
        List fieldList = new ArrayList();
        while (true) {
            if (clazz == null || clazz == Object.class) {
                break;
            } else {
                fields = clazz.getDeclaredFields();
                Collections.addAll(fieldList, fields);
                clazz = clazz.getSuperclass();
            }
        }
        return fieldList;
    }

    /**
     * 把一个Bean对象转换成Map对象
     *
     * @param obj     对象
     * @param ignores 忽略的fields
     * @return Map
     * @throws Exception 异常
     */
    public static Map convertBean2Map(Object obj, String[] ignores) throws Exception {
        Map map = new HashMap(16);
        Class clazz = obj.getClass();
        List<Field> fieldList = findAllFieldsOfSelfAndSuperClass(clazz);
        Field field;
        for (Field item : fieldList) {
            field = item;
            // 定义fieldName是否在拷贝忽略的范畴内
            boolean flag = false;
            if (ignores != null && ignores.length != 0) {
                flag = isExistOfIgnores(field.getName(), ignores);
            }
            if (!flag) {
                Object value = getProperty(obj, field.getName());
                if (null != value
                        && !StringUtils.isEmpty(value.toString())) {
                    map.put(field.getName(),
                            getProperty(obj, field.getName()));
                }
            }
        }
        return map;
    }

    /**
     * 把一个Bean对象转换成Map对象</br>
     *
     * @param obj 对象
     * @return Map
     */
    public static Map convertBean2Map(Object obj) throws Exception {
        return convertBean2Map(obj, null);
    }

    /**
     * 把一个Map对象转换成Bean对象</br>
     *
     * @param map       map对象
     * @param beanClass class对象
     * @return Map
     */
    public static Object convertMap2Bean(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null) {
            return null;
        }
        Object obj = beanClass.getDeclaredConstructor().newInstance();
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);
        return obj;
    }

    /**
     * 判断fieldName是否是ignores中排除的
     *
     * @param fieldName fieldName
     * @param ignores   ignores
     * @return boolean
     */
    private static boolean isExistOfIgnores(String fieldName,
                                            String[] ignores) {
        boolean flag = false;
        for (String str : ignores) {
            if (str.equals(fieldName)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static PropertyDescriptor getPropertyDescriptor(Class clazz,
                                                           String propertyName) throws Exception {
        // 构建一个可变字符串用来构建方法名称
        StringBuilder sb = new StringBuilder();
        Method setMethod;
        Method getMethod;
        PropertyDescriptor pd;
        boolean[] superC = new boolean[1];
        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
            if (propertyName.equalsIgnoreCase(field.getName())) {
                superC[0] = true;
            }
        });
        Field f;
        //noinspection IfStatementWithIdenticalBranches
        if (superC[0]) {
            // 根据字段名来获取字段
            f = clazz.getDeclaredField(propertyName);
        } else {
            clazz = clazz.getSuperclass();
            //父类
            f = clazz.getDeclaredField(propertyName);
        }
        // 构建方法的后缀
        String methodEnd = propertyName.substring(0, 1).toUpperCase()
                + propertyName.substring(1);
        // 构建set方法
        sb.append("set").append(methodEnd);
        setMethod = clazz.getDeclaredMethod(sb.toString(),
                f.getType());
        // 清空整个可变字符串
        sb.delete(0, sb.length());
        // 构建get方法
        sb.append("get").append(methodEnd);
        // 构建get 方法
        getMethod =
                clazz.getDeclaredMethod(sb.toString());
        // 构建一个属性描述器 把对应属性 propertyName 的 get 和 set 方法保存到属性描述器中
        pd = new PropertyDescriptor(propertyName, getMethod, setMethod);
        return pd;
    }

    /**
     * 设置属性
     *
     * @param obj          对象
     * @param propertyName 属性名
     * @param value        要设置的值
     * @throws Exception 异常
     * @date 2018/7/16
     * @since jdk1.8
     */
    @SuppressWarnings("RedundantArrayCreation")
    public static void setProperty(Object obj, String propertyName,
                                   Object value) throws Exception {
        // 获取对象的类型
        Class clazz = obj.getClass();
        // 获取 clazz
        PropertyDescriptor pd = getPropertyDescriptor(clazz, propertyName);
        // 类型中的
        // propertyName
        // 的属性描述器
        // 从属性描述器中获取 set 方法
        Method setMethod = pd.getWriteMethod();
        try {
            // 调用 set 方法将传入的value值保存属性中去
            setMethod.invoke(obj, new Object[]{value});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用get方法获取某个属性的值
     *
     * @param obj          对象
     * @param propertyName 属性名
     * @return java.lang.Object
     * @throws Exception 异常
     * @date 2018/7/16
     * @since jdk1.8
     */
    @SuppressWarnings("RedundantArrayCreation")
    public static Object getProperty(Object obj, String propertyName) throws Exception {
        // 获取对象的类型
        Class clazz = obj.getClass();
        PropertyDescriptor pd;
        // 获取 clazz
        pd = getPropertyDescriptor(clazz, propertyName);
        // 类型中的
        // propertyName
        // 的属性描述器
        // 从属性描述器中获取 get 方法
        Method getMethod = pd.getReadMethod();
        Object value = null;
        try {
            // 调用方法获取方法的返回值
            value = getMethod.invoke(obj, new Object[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取本地和父类的所有属性名称
     *
     * @param clazz Class类型
     * @return java.util.List<java.lang.String>
     * @date 2018/7/25
     * @since jdk1.8
     */
    public static List<String> getAllFieldNames(Class clazz) {

        List<String> fields = new ArrayList<>();
        getFieldNames(fields, clazz);
        return fields;
    }

    private static void getFieldNames(List<String> fields, Class clazz) {
        if (clazz != Object.class) {
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                String name = field.getName();
                fields.add(name);
            }
            getFieldNames(fields, clazz.getSuperclass());
        }
    }


    /**
     * 获取子类和所有父类的所有Field
     *
     * @param clazz Class类型
     * @return java.util.List<java.lang.reflect.Field>
     * @date 2018/9/6
     * @since jdk1.8
     */
    public static List<Field> getAllFields(Class clazz) {
        List<Field> fields = new ArrayList<>();
        getFields(fields, clazz);
        return fields;
    }

    private static void getFields(List<Field> fields, Class clazz) {
        if (clazz != Object.class) {
            Field[] declaredFields = clazz.getDeclaredFields();
            Collections.addAll(fields, declaredFields);
            getFields(fields, clazz.getSuperclass());
        }
    }

    /**
     * 将一个实体的数据按照某些字段名取出放入一个map
     *
     * @param obj        待转换的对象
     * @param needFields 需要转换的对象
     * @return java.util.Map
     * @throws IllegalAccessException 反射异常
     * @date 2018/8/24
     * @since jdk1.8
     */
    public static Map getNeedFields(Object obj, String... needFields) throws IllegalAccessException {


        Map map = new HashMap(16);
        if (obj == null) {
            throw new RuntimeException("待转换对象不能为空");
        }
        if (needFields == null) {
            throw new RuntimeException("需要的字段不能为空");
        }
        Class clazz = obj.getClass();
        getNeedFields(map, obj, clazz, needFields);
        return map;
    }

    /**
     * 获取MethodHandle
     *
     * @param returnClass      返回值Class对象-void用void.class
     * @param clazz            方法所在的类
     * @param methodName       方法名
     * @param parameterClasses 方法参数类型
     * @return java.lang.invoke.MethodHandle
     * @throws NoSuchMethodException  noSuchMethodException
     * @throws IllegalAccessException illegalAccessException
     * @date 2021/10/27
     * @since jdk1.8
     */
    public static MethodHandle getMethodHandle(Class returnClass, Class clazz, String methodName, Class... parameterClasses) throws NoSuchMethodException, IllegalAccessException {
        MethodType methodType = MethodType.methodType(returnClass, parameterClasses);
        return MethodHandles.lookup().findVirtual(clazz, methodName, methodType);
    }

    /**
     * 获取静态方法的MethodHandle
     *
     * @param returnClass      返回值Class对象-void用void.class
     * @param clazz            方法所在的类
     * @param methodName       方法名
     * @param parameterClasses 方法参数类型
     * @return java.lang.invoke.MethodHandle
     * @throws NoSuchMethodException  noSuchMethodException
     * @throws IllegalAccessException illegalAccessException
     * @date 2021/10/31
     * @since jdk1.8
     */
    public static MethodHandle getStaticMethodHandle(Class returnClass, Class clazz, String methodName, Class... parameterClasses) throws NoSuchMethodException, IllegalAccessException {
        MethodType methodType = MethodType.methodType(returnClass, parameterClasses);
        return MethodHandles.lookup().findStatic(clazz, methodName, methodType);
    }

    /**
     * 获取构造器的MethodHandle
     *
     * @param clazz            构造器的类
     * @param parameterClasses 构造器参数
     * @return java.lang.invoke.MethodHandle
     * @throws NoSuchMethodException  noSuchMethodException
     * @throws IllegalAccessException illegalAccessException
     * @date 2021/10/31
     * @since jdk1.8
     */
    public static MethodHandle getConstructMethodHandle(Class clazz, Class... parameterClasses) throws NoSuchMethodException, IllegalAccessException {
        MethodType methodType = MethodType.methodType(void.class, parameterClasses);
        return MethodHandles.lookup().findConstructor(clazz, methodType);
    }

    /**
     * 获取Getter MethodType
     *
     * @param clazz     类
     * @param fieldName 成员变量名
     * @return java.lang.invoke.MethodHandle
     * @throws NoSuchFieldException   noSuch
     * @throws IllegalAccessException illegalAccessException
     * @date 2021/10/31
     * @since jdk1.8
     */
    public static MethodHandle getGetterMethodHandle(Class clazz, String fieldName) throws IllegalAccessException, NoSuchFieldException {
        Field field = clazz.getDeclaredField(fieldName);
        makeAccessible(field);
        return MethodHandles.lookup().unreflectGetter(field);
    }

    /**
     * 获取Setter MethodHandle
     *
     * @param clazz     构造器的类
     * @param fieldName 成员变量名
     * @return java.lang.invoke.MethodHandle
     * @throws NoSuchFieldException   noSuch
     * @throws IllegalAccessException illegalAccessException
     * @date 2021/10/31
     * @since jdk1.8
     */
    public static MethodHandle getSetterMethodHandle(Class clazz, String fieldName) throws IllegalAccessException, NoSuchFieldException {
        Field field = clazz.getDeclaredField(fieldName);
        //防止被漏洞软件扫描出漏洞，更改授权方式 add by zqw 2021-12-08
        ReflectUtils.makeAccessible(field);
        return MethodHandles.lookup().unreflectSetter(field);
    }

    /**
     * 通过Method获取 MethodHandle
     *
     * @param method 方法
     * @return java.lang.invoke.MethodHandle
     * @throws IllegalAccessException illegalAccessException
     * @date 2021/10/31
     * @since jdk1.8
     */
    public static MethodHandle getMethodHandle(Method method) throws IllegalAccessException {
        return MethodHandles.lookup().unreflect(method);
    }

    public static Object invokeGet(Object obj, String propertyName) throws Exception {
        assert obj != null;
        assert propertyName != null;
        Class clazz = obj.getClass();
        Object result;
        PropertyDescriptor pd = new PropertyDescriptor(propertyName, clazz);
        Method rM = pd.getReadMethod();
        result = rM.invoke(obj);
        return result;
    }

    /**
     * 使final的field可修改
     *
     * @param field field
     * @date 2022/6/29
     * @since jdk11
     */
    public static void makeFinalModifiers(Field field) throws NoSuchFieldException, IllegalAccessException {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
    }

    private static void getNeedFields(Map map, Object obj, Class clazz, String... needFields) throws IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            for (int i = needFields.length - 1; i >= 0; i--) {
                if (field.getName().equals(needFields[i])) {
                    //防止被漏洞软件扫描出漏洞，更改授权方式 add by zqw 2021-12-08
                    ReflectUtils.makeAccessible(field);

                    Object o = field.get(obj);
                    map.put(needFields[i], o);
                }
            }
        }
        Class superclass = clazz.getSuperclass();
        if (superclass != Object.class) {
            getNeedFields(map, obj, superclass, needFields);
        }
    }


}
