package com.dima.eliseev;

interface Vehicle {
    void drive();
}

public class Factory {

    public static class Car implements Vehicle {
        @Override
        public void drive() {
            System.out.println("I ride Car");
        }
    }
    public static class Bike implements Vehicle {
        @Override
        public void drive() {
            System.out.println("I drive Bike");
        }
    }

    public static class VehicleFactory {
        public static Vehicle createVehicle(String veh) {
            switch (veh.toLowerCase()) {  // Приводим к нижнему регистру для устойчивости
                case "car": return new Car();
                case "bike": return new Bike();
                default: throw new IllegalArgumentException("Unknown vehicle type: " + veh);
            }
        }
    }

    public static void main(String[] args) {
        Vehicle car = VehicleFactory.createVehicle("car");
        Vehicle bike = VehicleFactory.createVehicle("bike");
    }
}
