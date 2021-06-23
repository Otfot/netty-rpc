package com.netty.rpc.serializer.kryo;

/**
 * 统一的序列化，反序列化接口
 * @author otfot
 * @date 2021/05/01
 */
public interface Serializer {

    /**
     * 泛型序列化接口
     * @param obj 待序列化对象
     * @param <T>
     * @return
     */
    <T> byte[] serialize(T obj);

    /**
     * 泛型反序列化接口
     * @param bytes 待反序列化二进制数据
     * @param clazz 反序列化成的对象类型
     * @param <T>
     * @return
     */
    <T> Object deserialize(byte[] bytes, Class<T> clazz);
}
