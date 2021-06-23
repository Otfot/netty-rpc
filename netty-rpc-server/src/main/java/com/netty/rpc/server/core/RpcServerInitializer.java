package com.netty.rpc.server.core;


import com.netty.rpc.codec.*;
import com.netty.rpc.serializer.kryo.KryoSerializer;
import com.netty.rpc.serializer.kryo.Serializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * RPC 服务器辅助配置类
 *
 * @author otfot
 * @date 2021/04/28
 */
public class RpcServerInitializer extends ChannelInitializer<SocketChannel> {

    private Map<String, Object> handlerMap;
    private ThreadPoolExecutor threadPoolExecutor;

    public RpcServerInitializer(Map<String, Object> handlerMap, ThreadPoolExecutor threadPoolExecutor) {
        this.handlerMap = handlerMap;
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        // TODO 为什么使用反射创建
        Serializer serializer = KryoSerializer.class.getDeclaredConstructor().newInstance();


        ChannelPipeline cp = ch.pipeline();
        // 增加 idle 心跳检测， 90 秒后，没有流入流量则关闭连接，没有出口流量发送 ping 测试对方是否还在
        cp.addLast(new IdleStateHandler(0, 0, Beat.BEAT_TIMEOUT, TimeUnit.SECONDS));

        // 使用不定长消息边界，一个消息帧的最大长度 65536 个字节，长度字段偏移量为 0 即消息开头即为长度字段，记录长度的字段长度为 4 个字节，长度字段值是否包括 消息头的长度， 不丢弃长度字段
        cp.addLast(new LengthFieldBasedFrameDecoder(65536,0, 4, 0, 0));

        cp.addLast(new RpcDecoder(RpcRequest.class, serializer));
        cp.addLast(new RpcEncoder(serializer));

        cp.addLast(new RpcServerHandler(handlerMap, threadPoolExecutor));


    }
}
