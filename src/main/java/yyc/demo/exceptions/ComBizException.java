package yyc.demo.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ComBizException extends RuntimeException{

    private String code;
    private String msg;
    private transient Object[] msgPlaceHolders;

    public ComBizException(String errorCode){
        this.code = errorCode;
    }

    public ComBizException(String errorCode,String msg){
        super(msg);
        this.code = errorCode;
        this.msg = msg;
    }

    public ComBizException(String errorCode,String msg,Throwable throwable){
        super(msg,throwable);
        this.code = errorCode;
        this.msg = msg;
    }

    public ComBizException(String errorCode,String msg,final Object[] msgPlaceHolders){
        this.code = errorCode;
        this.msg = msg;
        this.msgPlaceHolders = msgPlaceHolders;
    }



    @JsonIgnore
    public StackTraceElement[] getStackTrace(){
        return super.getStackTrace();
    }

    @JsonIgnore
    public synchronized Throwable getCause(){
        return super.getCause();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ComBizException{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
