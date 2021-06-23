package com.netty.rpc.codec;

import com.netty.rpc.serializer.kryo.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * RPC 解码器，将数据的二进制流转化为 可读消息
 * @author otfot
 * @date 2021/05/01
 */
public class RpcDecoder  extends ByteToMessageDecoder {

    private static final Logger logger = LoggerFactory.getLogger(RpcDecoder.class);
    /**
     * 将字节数据解码成什么类型的数据
     */
    private Class<?> genericClass;
    /**
     * 反序列化的方式
     */
    private Serializer serializer;

    public RpcDecoder (Class<?> genericClass, Serializer serializer) {
        this.genericClass = genericClass;
        this.serializer = serializer;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        // 消息头部长度为 4
        if (in.readableBytes() < 4) {
            return ;
        }
        // 标记一下当前的 起始位置
        in.markReaderIndex();

        int dataLength = in.readInt();

        // 当前没有收到足够的数据
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }

        byte[] data = new byte[dataLength];

        in.readBytes(data);
        Object obj = null;

        try {
            obj = serializer.deserialize(data, genericClass);
            out.add(obj);
        } catch (Exception e) {
            logger.error("Decode error: " + e.toString());
        }



    }
}
