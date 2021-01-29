package com.iscas.common.tools.core.io.serial;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/28 21:42
 * @since jdk1.8
 */
public class KryoSerializerUtils {

    private static ThreadLocal<Kryo> kryoThreadLocal = new ThreadLocal<>();


    private static Kryo getKryo() {
        Kryo kryo = kryoThreadLocal.get();
        if (kryo == null) {
            synchronized (KryoSerializerUtils.class) {
                if (kryo == null) {
                    kryo = new Kryo();

                    //支持对象循环引用（否则会栈溢出）

                    kryo.setReferences(true); //默认值就是 true，添加此行的目的是为了提醒维护者，不要改变这个配置

                    //不强制要求注册类（注册行为无法保证多个 JVM 内同一个类的注册编号相同；而且业务系统中大量的 Class 也难以一一注册）

                    kryo.setRegistrationRequired(false); //默认值就是 false，添加此行的目的是为了提醒维护者，不要改变这个配置

                    kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
                    kryoThreadLocal.set(kryo);
                }
            }
        }
        return kryo;
    }

    /**
     * <p>序列化对象，返回字节数组</p>
     * <p>建议直接使用外部传入的流{@link #serialize(Object, Output)}</p>
     * @version 1.0
     * @since jdk1.8
     * @date 2021/1/29
     * @param obj 待序列化得对象
     * @throws
     * @return byte[]
     */
    public static <T> byte[] serialize(T obj) {
        Kryo kryo = getKryo();

        kryo.register(obj.getClass());

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        Output output = new Output(os);

        kryo.writeObject(output, obj);

        output.flush();
        output.close();
        return os.toByteArray();

    }

    /**
     * 序列化，直接通过一个输出流输出
     * @version 1.0
     * @since jdk1.8
     * @date 2021/1/29
     * @param obj 待序列化对象
     * @param os 输出流
     * @throws
     * @return void
     */
    public static <T> void serialize(T obj, Output os) {
        Kryo kryo = getKryo();

        kryo.register(obj.getClass());

        kryo.writeObject(os, obj);

        os.flush();

    }

    /**
     * 反序列化
     * @version 1.0
     * @since jdk1.8
     * @date 2021/1/29
     * @param bytes 字节数组
     * @param clazz 对象
     * @throws
     * @return T
     */
    public static <T> T deserialize(byte[] bytes, Class<T> clazz) {
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);

        Input input = new Input(is);

        Kryo kryo = getKryo();

        return kryo.readObject(input, clazz);

    }

    /**
     * 反序列化，读取输入
     * @version 1.0
     * @since jdk1.8
     * @date 2021/1/29
     * @param input 输入
     * @param clazz 对象
     * @throws
     * @return T
     */
    public static <T> T deserialize(Input input, Class<T> clazz) {

        Kryo kryo = getKryo();

        return kryo.readObject(input, clazz);

    }
}
