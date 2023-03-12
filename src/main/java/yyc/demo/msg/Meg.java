package yyc.demo.msg;

public class Meg {
    private String code;
    private String info;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Meg{" +
                "code='" + code + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
