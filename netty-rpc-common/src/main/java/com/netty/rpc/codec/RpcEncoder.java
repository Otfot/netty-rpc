package com.netty.rpc.codec;

import com.netty.rpc.serializer.kryo.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息编码， 将 可读信息编码为 字节数组
 * @author otfot
 * @date 2021/05/01
 */
public class RpcEncoder extends MessageToByteEncoder<RpcResponse> {

    private static final Logger logger = LoggerFactory.getLogger(RpcResponse.class);
    private Class<?> genericClass;
    private Serializer serializer;

    public RpcEncoder(Serializer serializer) {

        this.serializer = serializer;
    }


    @Override
    protected void encode(ChannelHandlerContext ctx, RpcResponse msg, ByteBuf out) throws Exception {

        try {
            byte[] data = serializer.serialize(msg);
            // 写入数据长度
            out.writeInt(data.length);
            out.writeBytes(data);
        } catch (Exception e) {
            logger.error("Encode error: " + e.toString());
        }
    }
}
