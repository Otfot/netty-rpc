package com.netty.rpc.client.proxy;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/**
 * @author otfot
 * @date 2021/05/03
 */
public interface SerializableFunction<T> extends Serializable {

    default String getName() throws Exception {

        Method write = this.getClass().getDeclaredMethod("writeReplace");
        write.setAccessible(true);
        // TODO 没懂
        SerializedLambda serializedLambda = (SerializedLambda)  write.invoke(this);

        return serializedLambda.getImplMethodName();
    }
}
