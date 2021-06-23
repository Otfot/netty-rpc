package com.netty.rpc.serializer.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy;
import com.esotericsoftware.kryo.util.Pool;
import com.netty.rpc.codec.RpcRequest;
import com.netty.rpc.codec.RpcResponse;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.util.Objects;

/**
 * Kryo 工厂池,多线程环境下，使用Kryo 实例池为了减少开销
 *
 * @author otfot
 * @date 2021/05/01
 */
public class KryoPoolFactory {

    /**
     * 单例模式
     */
    private static volatile KryoPoolFactory poolFactory = null;

    private Pool<Kryo> pool = new Pool<Kryo>(true, false, 8) {
        @Override
        protected Kryo create() {
            Kryo kryo = new Kryo();
            kryo.setReferences(false);
            kryo.register(RpcRequest.class);
            kryo.register(RpcResponse.class);
            kryo.setInstantiatorStrategy(new DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
            return kryo;
        }
    };

    private KryoPoolFactory() {}

    public static Pool<Kryo> getKryoPoolInstance() {
        // 不直接加锁是因为有可能已经创建好了
        if (Objects.isNull(poolFactory)) {
            // 当前线程获取锁，有可能是在另一个线程已经完成创建之后
            synchronized (KryoPoolFactory.class) {
                // 此时需要判断之前有没有线程已经创建了对象
                if (Objects.isNull(poolFactory)) {
                    poolFactory = new KryoPoolFactory();
                }
            }
        }
        return poolFactory.getPool();
    }


    private Pool<Kryo> getPool() {return pool;}

}
