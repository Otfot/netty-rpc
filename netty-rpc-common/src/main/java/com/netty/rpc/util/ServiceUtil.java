package com.netty.rpc.util;


import java.util.Objects;

/**
 * 服务工具类
 *
 * @author otfot
 * @date 2021/04/28
 */
public class ServiceUtil {
    public static final String SERVICE_CONCAT_TOKEN = "#";

    public static String makeServiceKey(String interfaceName, String version){
        String serviceKey = interfaceName;
        if (Objects.nonNull(version) && version.trim().length() > 0) {
            serviceKey += SERVICE_CONCAT_TOKEN.concat(version);
        }

        return serviceKey;
    }
}
