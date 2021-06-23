package com.netty.rpc.protocol;

import com.netty.rpc.util.JsonUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Rpc 协议
 * @author otfot
 * @date 2021/05/02
 */
public class RpcProtocol implements Serializable {
    private static final long serialVersionUID = -3707346858486995002L;

    private String host;
    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<RpcServiceInfo> getServiceInfoList() {
        return serviceInfoList;
    }

    public void setServiceInfoList(List<RpcServiceInfo> serviceInfoList) {
        this.serviceInfoList = serviceInfoList;
    }

    private List<RpcServiceInfo> serviceInfoList;

    public String toJson() {
        return JsonUtil.objectToJson(this);
    }

    public static RpcProtocol fromJson(String json) {
        return JsonUtil.jsonToObject(json, RpcProtocol.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RpcProtocol that = (RpcProtocol) o;
        return port == that.getPort() && Objects.equals(host, that.getHost()) && isListEqual(serviceInfoList, that.getServiceInfoList());
    }

    private boolean isListEqual(List<RpcServiceInfo> thisList, List<RpcServiceInfo> thatList) {
        if (thisList == null && thatList == null) {
            return true;
        }
        if (Objects.nonNull(thisList) && Objects.nonNull(thatList) && thisList.containsAll(thatList) && thatList.containsAll(thisList)) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port, serviceInfoList.hashCode());
    }

    @Override
    public String toString() {
        return toJson();
    }
}
