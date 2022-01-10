package com.company;

public class Main {
    private static final StringBuffer buffer = new StringBuffer();
    private static boolean isTimeToStop1 = false;
    private static boolean isTimeToStop2 = true;
    private static boolean isTimeToStop3 = true;


    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            for(int i = 0; i < 4; i++) {
                synchronized (buffer){
                    while(isTimeToStop1){
                        try {
                            buffer.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    buffer.append("Hello ");
                    isTimeToStop1 = true;
                    isTimeToStop2 = false;
                    buffer.notifyAll();
                }
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            for(int i = 0; i < 4; i++) {
                synchronized (buffer){
                    while (isTimeToStop2){
                        try {
                            buffer.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    buffer.append("world");
                    isTimeToStop2 = true;
                    isTimeToStop3 = false;
                    buffer.notifyAll();
                }
            }
        });
        t2.start();

        Thread t3 = new Thread(() -> {
            for(int i = 0; i < 4; i++) {
                synchronized (buffer){
                    while (isTimeToStop3){
                        try {
                            buffer.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    buffer.append("!");
                    System.out.println(buffer);
                    buffer.delete(0,buffer.length());
                    isTimeToStop3 = true;
                    isTimeToStop1 = false;
                    buffer.notifyAll();
                }
            }
        });
        t3.start();


    }
}
