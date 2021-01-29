package com.iscas.common.tools.core.io.serial;

import lombok.Cleanup;

import java.io.*;

/**
 * Java对象序列化和反序列化
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/7/11 8:47
 * @since jdk1.8
 */
public class JdkSerializableUtils {
    private JdkSerializableUtils() {}

    /**
     * 序列化
     * @version 1.0
     * @since jdk1.8
     * @date 2021/1/6
     * @param obj 对象
     * @throws
     * @return byte[]
     */
    public static byte[] serialize(Serializable obj) throws IOException {
        //序列化字节流
        @Cleanup ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //对象读取
        @Cleanup ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        //转换成字节
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 序列化
     * @version 1.0
     * @since jdk1.8
     * @date 2021/1/6
     * @param obj 对象
     * @param objectOutputStream 输出流
     * @throws
     * @return byte[]
     */
    public static void serialize(Serializable obj, ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(obj);
    }

    /**
     * 反序列化
     * @version 1.0
     * @since jdk1.8
     * @date 2021/1/6
     * @param bytes 字节数组
     * @throws
     * @return T
     */
    public static <T extends Serializable> T deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        //反序列化
        //直接读取直接，用对象输入流直接读取出来
        @Cleanup ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        @Cleanup ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        //读取后转成对应对象
        return (T)objectInputStream.readObject();
    }

    /**
     * 反序列化
     * @version 1.0
     * @since jdk1.8
     * @date 2021/1/6
     * @param objectInputStream 输入流
     * @throws
     * @return T
     */
    public static <T extends Serializable> T deserialize(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        //读取后转成对应对象
        return (T)objectInputStream.readObject();
    }
}
