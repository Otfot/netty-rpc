package com.netty.rpc.server.core;


/**
 * 服务器接口，实现该接口即拥有启动和停止服务器功能
 *
 * @author otfot
 * @date 2021/04/28
 */
public interface Server {

    /**
     * 启动服务器
     *
     * @throws Exception
     */
    void start() throws Exception;

    /**
     * 停止服务器
     *
     * @throws Exception
     */
    void stop() throws Exception;
}
