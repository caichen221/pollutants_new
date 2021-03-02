package com.iscas.biz.util;

import lombok.Cleanup;

import java.io.*;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/1/21 17:02
 * @since jdk1.8
 */
public class CloneUtils {

    public static <T extends Serializable> T clone(T source) {
        T clone = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            @Cleanup ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(source);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            @Cleanup ObjectInputStream ois = new ObjectInputStream(bais);
            clone = (T) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clone;
    }
}
