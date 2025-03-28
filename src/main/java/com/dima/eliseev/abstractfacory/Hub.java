package com.dima.eliseev.abstractfacory;

public class Hub {

   FACTORY OldF = new AbstractFactory.OldFactory();
   Car car = OldF.createCars();
   PC pc = OldF.creTEpc();

   public void H(){
       car.drive();
       pc.play();
   }

    public static void main(String[] args) {
        new Hub().H();
    }
}
