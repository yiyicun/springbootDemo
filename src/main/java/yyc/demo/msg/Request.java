package yyc.demo.msg;

import java.io.Serializable;

public class Request<T> implements Serializable {
    private static long serialVersionUID = -54337110725113724L;
    private ReqHeader reqHeader;
    private String reqMsgAuth;
    private T reqBody;
    private Object reqAttach;

    public ReqHeader getReqHeader() {
        return reqHeader;
    }

    public void setReqHeader(ReqHeader reqHeader) {
        this.reqHeader = reqHeader;
    }

    public String getReqMsgAuth() {
        return reqMsgAuth;
    }

    public void setReqMsgAuth(String reqMsgAuth) {
        this.reqMsgAuth = reqMsgAuth;
    }

    public T getReqBody() {
        return reqBody;
    }

    public void setReqBody(T reqBody) {
        this.reqBody = reqBody;
    }

    public Object getReqAttach() {
        return reqAttach;
    }

    public void setReqAttach(Object reqAttach) {
        this.reqAttach = reqAttach;
    }
}
