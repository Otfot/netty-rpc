package com.netty.rpc.server.registry;


import com.netty.rpc.config.Constant;
import com.netty.rpc.protocol.RpcProtocol;
import com.netty.rpc.protocol.RpcServiceInfo;
import com.netty.rpc.util.ServiceUtil;
import com.netty.rpc.zookeeper.CuratorClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 服务注册
 *
 * @author otfot
 * @date 2021/04/28
 */
public class ServiceRegistry {
    private static final Logger logger = LoggerFactory.getLogger(ServiceRegistry.class);
    /**
     * zookeeper 馆长客户端封装类，向zookeeper 服务端发送消息
     */
    private CuratorClient curatorClient;
    /**
     * 路径列表
     */
    private List<String> pathList = new ArrayList<>();


    public ServiceRegistry(String registerAddress) {
        this.curatorClient = new CuratorClient(registerAddress, 5000);
    }



    public void registerService(String host, int port, Map<String, Object> serviceMap) {
        // 向 zookeeper 注册该服务器上提供的服务
        List<RpcServiceInfo>  serviceInfoList = new ArrayList<>();
        for (String key : serviceMap.keySet()) {
            String[] serviceInfo = key.split(ServiceUtil.SERVICE_CONCAT_TOKEN);
            if (serviceInfo.length > 0) {
                RpcServiceInfo rpcServiceInfo = new RpcServiceInfo();
                rpcServiceInfo.setServiceName(serviceInfo[0]);


                if (serviceInfo.length == 2) {
                    rpcServiceInfo.setVersion(serviceInfo[1]);
                } else {
                    rpcServiceInfo.setVersion("");
                }
                logger.info("Register new service {}", key);
                serviceInfoList.add(rpcServiceInfo);
            } else {
                logger.warn("Can not get service name and version: {}", key);
            }
        }

        try {
            RpcProtocol rpcProtocol = new RpcProtocol();
            rpcProtocol.setHost(host);
            rpcProtocol.setPort(port);
            rpcProtocol.setServiceInfoList(serviceInfoList);
            String serviceData = rpcProtocol.toJson();
            byte[] bytes = serviceData.getBytes(StandardCharsets.UTF_8);
            String path = Constant.ZK_DATA_PATH + "-" + rpcProtocol.hashCode();
            path = this.curatorClient.createPathData(path,bytes);
            pathList.add(path);
            logger.info("Register {} new service, host: {}, port: {}", serviceInfoList.size(), host, port);
        } catch (Exception e) {
            logger.error("Register service fail, exception: {}", e.getMessage());
        }

        curatorClient.addConnectionStateListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
                if (connectionState == ConnectionState.RECONNECTED) {
                    logger.info("Connection state: {}, register service after reconnected", connectionState);
                    registerService(host, port, serviceMap);
                }
            }
        });
    }

    public void unregisterService() {
        logger.info("Unregister all service");

        for (String path : pathList) {
            try {
                this.curatorClient.deletePath(path);
            } catch (Exception e) {
                logger.error("Delete service path error: " + e.getMessage());
            }
        }

        this.curatorClient.close();

    }
}
