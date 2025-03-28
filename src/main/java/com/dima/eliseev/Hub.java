package com.dima.eliseev;

import com.dima.eliseev.Prototype.Prototype;
import com.dima.eliseev.abstractfacory.AbstractFactory;
//import com.dima.eliseev.abstractfacory.FACTORY;
//import com.dima.eliseev.abstractfacory.PC;

public class Hub {

//   FACTORY OldF = new AbstractFactory.OldFactory();
//   com.dima.eliseev.abstractfacory.Car car = OldF.createCars();
//   PC pc = OldF.creTEpc();
//
//   public void H(){
//       car.drive();
//       pc.play();
//   }

    public static void main(String[] args) {

        Prototype origin = new Prototype(10,"Dimasik");
        Prototype proto_ = (Prototype) origin.clone();

        origin.name = "Domestos";
        System.out.println(origin.toString());
        System.out.println(proto_.toString());
        //new Hub().H();
    }

}
