package com.netty.rpc.codec;


import com.netty.rpc.serializer.kryo.Serializer;

import java.io.Externalizable;
import java.io.Serializable;
import java.rmi.server.UID;

/**
 * RPC 消息请求
 *
 * @author otfot
 * @date 2021/04/28
 */
public class RpcRequest  implements Serializable {
    /**
     * UID 验证版本一致性
     */
    private static final long serialVersionUID = -130536746899475563L;

    private String requestID;
    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;
    private String version;



    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
