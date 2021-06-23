package com.netty.rpc.serializer.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.Pool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 使用 Kryo 处理序列化与反序列化
 * @author otfot
 * @date 2021/05/01
 */
public class KryoSerializer implements  Serializer{
    private Pool<Kryo> pool = KryoPoolFactory.getKryoPoolInstance();


    @Override
    public <T> byte[] serialize(T obj) {
        Kryo kryo = pool.obtain();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Output out = new Output(byteArrayOutputStream);

        try {
            // 借助kryo 框架 将 obj 写入到 ByteArrayOutputStream
            kryo.writeObject(out, obj);
            out.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public <T> Object deserialize(byte[] bytes, Class<T> clazz) {
        Kryo kryo = pool.obtain();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        Input in = new Input(byteArrayInputStream);

        try {
            Object result = kryo.readObject(in, clazz);
            in.close();
            return result;

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                byteArrayInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
