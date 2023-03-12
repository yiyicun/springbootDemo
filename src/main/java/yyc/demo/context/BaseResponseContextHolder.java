package yyc.demo.context;

import yyc.demo.exceptions.ComBizException;
import yyc.demo.msg.ReqHeader;
import yyc.demo.msg.Response;
import yyc.demo.threadlocal.CacheStore;

import java.sql.Timestamp;

public class BaseResponseContextHolder {

    private static final CacheStore<Response> responseContext = new CacheStore<>("Response OBject data");
    private static final CacheStore<ReqHeader> reqHeaderContext = new CacheStore<>("ReqHeader data");

    private BaseResponseContextHolder(){

    }

    public static Response getResponse(){
        if( responseContext.get() == null ) {
            throw new ComBizException("0155","response not exist");
        }
        return responseContext.get();
    }

    public static void setResponseContext(Response object){
        if( responseContext.get() != null ) {
            responseContext.remove();
        }
        responseContext.set(object);
    }

    public static void setReqHeader(ReqHeader reqHeader){
        if(reqHeaderContext.get() != null){
            reqHeaderContext.remove();
        }
        reqHeaderContext.set(reqHeader);
    }

    public static void cleanUp(){
        if(responseContext.get() != null){
            responseContext.remove();
        }
        if(reqHeaderContext.get() != null) {
            reqHeaderContext.remove();
        }
    }


}
