package com.netty.rpc.protocol;

import com.netty.rpc.util.JsonUtil;

import java.io.Serializable;
import java.util.Objects;

/**
 * RPC 服务信息
 * @author otfot
 * @date 2021/05/02
 */
public class RpcServiceInfo implements Serializable {

    private static final long serialVersionUID = -1607999766870855762L;

    private String serviceName;
    private String version;


    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RpcServiceInfo that = (RpcServiceInfo) o;
        return Objects.equals(serviceName, that.getServiceName()) && Objects.equals(version, that.getVersion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceName, version);
    }

    public String toJson() {
        return JsonUtil.objectToJson(this);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
