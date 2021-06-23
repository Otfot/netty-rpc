package com.netty.rpc.client.handler;

import com.netty.rpc.codec.RpcResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author otfot
 * @date 2021/05/03
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    private volatile Channel channel;

    public void close() {
        // TODO 没明白
        channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {

    }
}
