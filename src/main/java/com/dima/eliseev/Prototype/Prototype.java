package com.dima.eliseev.Prototype;

interface PROTO extends Cloneable{
    PROTO clone();
}

public class Prototype implements PROTO{
    public int i;
    public String name;

    public Prototype(int i, String name) {
        this.i = i;
        this.name = name;
    }

    @Override
    public PROTO clone() {
        try {
            return (PROTO) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Prototype{" +
                "i=" + i +
                ", name='" + name + '\'' +
                '}';
    }
}
