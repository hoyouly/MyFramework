package com.hoyouly.javacore;

public class MyClass {
    public static void main(String[] args) throws InterruptedException {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();

        PrinterThread pa = new PrinterThread("A", c, a);
        PrinterThread pb = new PrinterThread("B", a, b);
        PrinterThread pc = new PrinterThread("C", b, c);

        pa.start();
        Thread.sleep(100);
        pb.start();
        Thread.sleep(100);
        pc.start();
        Thread.sleep(100);
    }


    public static class PrinterThread extends Thread {
        private String name;
        private Object pre;
        private Object self;

        public PrinterThread(String name, Object pre, Object self) {
            this.name = name;
            this.pre = pre;
            this.self = self;
        }

        @Override
        public void run() {
            int count = 10;
            while (count > 0) {
                synchronized (pre) {
                    synchronized (self) {
                        System.out.println(name);
                        count--;
                        self.notify();
                    }

                    try {
                        pre.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
