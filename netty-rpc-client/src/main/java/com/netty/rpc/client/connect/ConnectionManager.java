package com.netty.rpc.client.connect;

import com.netty.rpc.client.handler.RpcClientHandler;
import com.netty.rpc.protocol.RpcProtocol;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * RPC 连接管理
 * @author otfot
 * @date 2021/05/03
 */
public class ConnectionManager {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);

    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup(4);

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 8, 600L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000));

    private volatile  boolean running = true;

    private CopyOnWriteArraySet<RpcProtocol> rpcProtocolSet = new CopyOnWriteArraySet<>();

    private Map<RpcProtocol, RpcClientHandler> connectedServerNodes = new ConcurrentHashMap<>();

    private ReentrantLock lock = new ReentrantLock();
    private Condition connection = lock.newCondition();


    private ConnectionManager() {

    }

    private static class SingletonHolder {
        private static final ConnectionManager instance = new ConnectionManager();
    }

    public static ConnectionManager getInstance() {
        // TODO 静态类，当使用时加载？
        return SingletonHolder.instance;
    }

    private void removeAndCloseHandler(RpcProtocol rpcProtocol) {

        RpcClientHandler handler = connectedServerNodes.get(rpcProtocol);
        // 断开跟一个服务的连接
        if (handler != null) {
            handler.close();
        }

        connectedServerNodes.remove(rpcProtocol);
        rpcProtocolSet.remove(rpcProtocol);


    }

    private void singalAvailableHandler() {
        lock.lock();

        try {
            connection.signalAll();
        } finally {
            lock.unlock();
        }

    }

    public void stop() {
        running = false;

        //
        for (RpcProtocol rpcProtocol : rpcProtocolSet) {
            removeAndCloseHandler(rpcProtocol);
        }

        singalAvailableHandler();

        threadPoolExecutor.shutdown();
        eventLoopGroup.shutdownGracefully();

    }

}
