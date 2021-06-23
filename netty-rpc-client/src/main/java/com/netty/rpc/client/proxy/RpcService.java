package com.netty.rpc.client.proxy;

import com.netty.rpc.client.handler.RpcFuture;

/**
 * @author otfot
 * @date 2021/05/03
 */
public interface RpcService<T, P, FN extends SerializableFunction<T>> {

    /**
     * 异步 RPC 调用
     * @param funcName
     * @param args
     * @return
     * @throws Exception
     */
    RpcFuture call(String funcName, Object... args) throws Exception ;


    /**
     * lambda method reference
     * @param fn
     * @param args
     * @return
     * @throws Exception
     */
    RpcFuture call(FN fn, Object... args) throws Exception;

}
