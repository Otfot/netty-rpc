package com.netty.rpc.client.proxy;

import com.netty.rpc.client.connect.ConnectionManager;
import com.netty.rpc.client.handler.RpcClientHandler;
import com.netty.rpc.client.handler.RpcFuture;
import com.netty.rpc.codec.RpcRequest;
import com.netty.rpc.util.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author otfot
 * @date 2021/05/03
 */
public class ObjectProxy<T, P> implements InvocationHandler, RpcService<T, P, SerializableFunction<T>> {

    private static final Logger logger = LoggerFactory.getLogger(ObjectProxy.class);
    private Class<T> clazz;

    private String version;


    public ObjectProxy(Class<T> interfaceClass, String version) {
        this.clazz = interfaceClass;
        this.version = version;
    }

    @Override
    public RpcFuture call(String funcName, Object... args) throws Exception {
        return null;
    }

    @Override
    public RpcFuture call(SerializableFunction<T> tSerializableFunction, Object... args) throws Exception {
        return null;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // TODO 没懂
        if (Object.class == method.getDeclaringClass()) {
            String name = method.getName();
            if ("equals".equals(name)) {
                return proxy == args[0];
            } else if ("hashCode".equals(name)) {
                return System.identityHashCode(proxy);
            } else if ("toString".equals(name)) {
                return proxy.getClass().getName() + "@" +
                        Integer.toHexString(System.identityHashCode(proxy)) +
                        ", with InvocationHandler " + this;

            } else {
                throw new IllegalStateException(String.valueOf(method));
            }

        }


        RpcRequest request = new RpcRequest();
        request.setRequestID(UUID.randomUUID().toString());
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);
        request.setVersion(version);


        // debug
        if (logger.isDebugEnabled()) {
            // 输出传递的对象及方法
        }

        String serviceKey = ServiceUtil.makeServiceKey(method.getDeclaringClass().getName(), version);
//        RpcClientHandler handler = ConnectionManager.getInstance().chooseHandler(serviceKey);
//        RpcFuture rpcFuture = handler.sendRequest(request);

//        return rpcFuture.get();
        return null;
    }
}
