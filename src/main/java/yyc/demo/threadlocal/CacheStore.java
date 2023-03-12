package yyc.demo.threadlocal;

import org.springframework.util.Assert;

public class CacheStore<T> extends ThreadLocal<T> implements ClearCache{

    private final String name;

    public CacheStore(String name){
        Assert.hasText(name,"name must no be empty");
        this.name = name;
    }

    public CacheStore(){
        this.name = "defalt thread local";
    }

    public String toString(){
        return this.name;
    }

    public void clearInfo(){
        this.remove();
    }


}
