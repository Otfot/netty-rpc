package com.netty.rpc.codec;

import java.io.Serializable;

/**
 * RPC 消息响应
 * @author otfot
 * @date 2021/05/01
 */
public class RpcResponse implements Serializable {

    private static final long serialVersionUID = -7842259508088587598L;

    private String requstID;
    private String error;
    private Object result;



    public String getRequstID() {
        return requstID;
    }

    public void setRequstID(String requstID) {
        this.requstID = requstID;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
