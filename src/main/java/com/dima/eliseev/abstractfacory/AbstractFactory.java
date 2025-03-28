package com.dima.eliseev.abstractfacory;

interface Car{
    void drive();
}
interface PC{
    void play();
}
interface FACTORY{
    public Car createCars();
    public PC creTEpc();
}

public class AbstractFactory {

    public static class ModernCar implements Car{
        @Override
        public void drive() {
            System.out.println("I drive modern car");
        }
    }
    public static class OldCar implements Car{
        @Override
        public void drive() {
            System.out.println("I drive old car");
        }
    }
    public static class ModernPC implements PC{
        @Override
        public void play() {
            System.out.println("I play modern PC");
        }
    }
    public static class OldrPC implements PC{
        @Override
        public void play() {
            System.out.println("I play old PC");
        }
    }
    public static class ModernFactory implements FACTORY{
        @Override
        public PC creTEpc() {
            return new ModernPC();
        }
        @Override
        public Car createCars() {
            return new ModernCar();
        }
    }
    public static class OldFactory implements FACTORY{
        @Override
        public PC creTEpc() {
            return new OldrPC();
        }
        @Override
        public Car createCars() {
            return new OldCar();
        }
    }
}
