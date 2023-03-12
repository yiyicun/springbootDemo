package yyc.demo.msg;

import java.io.Serializable;

public class RespHeader implements Serializable {

    private static long serialVersionUID = -54337110725113825L;

    private String globalSerNo;
    private String txnSerno;

    public String getGlobalSerNo() {
        return globalSerNo;
    }

    public void setGlobalSerNo(String globalSerNo) {
        this.globalSerNo = globalSerNo;
    }

    public String getTxnSerno() {
        return txnSerno;
    }

    public void setTxnSerno(String txnSerno) {
        this.txnSerno = txnSerno;
    }
}
