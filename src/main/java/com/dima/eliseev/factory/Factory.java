package com.dima.eliseev.factory;

interface Vehicle{
    void drive();
}

public class Factory {

    public static class Car implements Vehicle{
        @Override
        public void drive() {
            System.out.println("CAAAR!");
        }
    }
    public static class Bike implements Vehicle{
        @Override
        public void drive() {
            System.out.println("BIIIKEEEEE!!!!");
        }
    }
    public static class FactoryPattern{
        static Vehicle VeFactory(String type){
            switch (type){
                case "car": return new Car();
                case "bike": return new Bike();
                default: return null;
            }
        }
    }

    public static void main(String[] args) {
        Vehicle car = FactoryPattern.VeFactory("car");
        Vehicle bike = FactoryPattern.VeFactory("bike");
    }
}
