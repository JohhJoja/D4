package com.dima.eliseev;

public class Singleton {
    private static Singleton instance;
    int i = 0;
    private Singleton(){
        i++;
    }
    public static Singleton getInstance(){
        if (instance==null){
            instance = new Singleton();
        }
        return instance;
    }
    public void HIA(){
        System.out.println("I'm "+i);
    }

    public static void main(String[] args) {
        Singleton inst1 = new Singleton();
        Singleton inst2 = new Singleton();
        Singleton inst3 = new Singleton();
        inst1.HIA();
        inst2.HIA();
        inst3.HIA();
    }
}
