package com.dima.eliseev;

public class My_thread extends Thread{

    boolean run_ = true;

    @Override
    public void run() {
        int i = 0;
        while (run_){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            i++;
            System.out.println("This thread running already: "+i+"s");
        }
    }
}
