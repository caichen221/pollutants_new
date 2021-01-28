package com.iscas.common.tools.core.io.serial;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/28 21:42
 * @since jdk1.8
 */
public class KryoSerializerUtils {
    public static <T> byte[] serialize(T obj) {
        Kryo kryo = new Kryo();

        //支持对象循环引用（否则会栈溢出）

        kryo.setReferences(true); //默认值就是 true，添加此行的目的是为了提醒维护者，不要改变这个配置

        //不强制要求注册类（注册行为无法保证多个 JVM 内同一个类的注册编号相同；而且业务系统中大量的 Class 也难以一一注册）

        kryo.setRegistrationRequired(false); //默认值就是 false，添加此行的目的是为了提醒维护者，不要改变这个配置

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        Output output = new Output(os);

        kryo.writeObject(output, obj);

        output.flush();

        return os.toByteArray();

    }


    public static <T> T deserialize(byte[] bytes, Class<T> clazz) {
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);

        Input input = new Input(is);

        Kryo kryo = new Kryo();

        //支持对象循环引用（否则会栈溢出）

        kryo.setReferences(true); //默认值就是 true，添加此行的目的是为了提醒维护者，不要改变这个配置

        //不强制要求注册类（注册行为无法保证多个 JVM 内同一个类的注册编号相同；而且业务系统中大量的 Class 也难以一一注册）

        kryo.setRegistrationRequired(false); //默认值就是 false，添加此行的目的是为了提醒维护者，不要改变这个配置

        return kryo.readObject(input, clazz);

    }
}
