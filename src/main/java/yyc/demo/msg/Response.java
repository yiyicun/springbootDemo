package yyc.demo.msg;

import java.io.Serializable;

public class Response<T> implements Serializable {

    private static long serialVersionUID = -54337110725113734L;
    private RespHeader respHeader;
    private String respqMsgAuth;
    private T respBody;
    private Object respAttach;

    public RespHeader getRespHeader() {
        return respHeader;
    }

    public void setRespHeader(RespHeader respHeader) {
        this.respHeader = respHeader;
    }

    public String getRespqMsgAuth() {
        return respqMsgAuth;
    }

    public void setRespqMsgAuth(String respqMsgAuth) {
        this.respqMsgAuth = respqMsgAuth;
    }

    public T getRespBody() {
        return respBody;
    }

    public void setRespBody(T respBody) {
        this.respBody = respBody;
    }

    public Object getRespAttach() {
        return respAttach;
    }

    public void setRespAttach(Object respAttach) {
        this.respAttach = respAttach;
    }
}
