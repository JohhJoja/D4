package com.dima.eliseev;

public class Singleton {
    private static Singleton instance;
    private Singleton() {}
    static Singleton Single(){
        if (instance==null){
            instance = new Singleton();
        }
        return instance;
    }
}
