package com.netty.rpc.codec;

/**
 * idle 检测配置
 * @author otfot
 * @date 2021/05/01
 */
public class Beat {

    public static final int BEAT_INTERVAL = 30;
    public static final int BEAT_TIMEOUT = 3 * BEAT_INTERVAL;
    public static final String BEAT_ID = "BEAT_PING_PONG";

    public static RpcRequest BEAT_PING;


    static {
        BEAT_PING = new RpcRequest();
        BEAT_PING.setRequestID(BEAT_ID);
    }
}
