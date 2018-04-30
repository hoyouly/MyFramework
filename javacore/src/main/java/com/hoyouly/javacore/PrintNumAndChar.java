package com.hoyouly.javacore;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 数组A内容为 1,2,3,4...52 ,数组B内容为26个英文字母，使用两个线程分别输入两个数组，
 * 打印内容为：12a34b56c78e....... 这样的规律
 */
public class PrintNumAndChar {

    public static void main(String[] args) {
        AtomicBoolean isNum = new AtomicBoolean();
        System.out.println(" isNum init " + isNum.get());
        int[] num = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        char[] chars = {'a', 'b', 'c', 'd', 'e'};

//        new PrintNum(num, isNum).start();
//        new PrintChar(chars, isNum).start();

//        PrintNum2 printNum2 = new PrintNum2(num);
//        PrintChar2 printChar2 = new PrintChar2(chars);
//        printChar2.setPrintNum2(printNum2);
//        printNum2.setPrintChar2(printChar2);
//        printNum2.start();
//        printChar2.start();

        Lock lock=new ReentrantLock();
        Condition printNum=lock.newCondition();
        Condition printChar=lock.newCondition();
        new PrintNum3(num,printNum,printChar,lock).start();
        new PrintChar3(chars,printNum,printChar,lock).start();


    }

    //方案一 使用自旋锁

    public static class PrintNum extends Thread {
        private int[] num;
        private AtomicBoolean isNum;

        public PrintNum(int[] num, AtomicBoolean isNum) {
            this.num = num;
            this.isNum = isNum;
        }

        @Override
        public void run() {
            int count = 0;
            for (int i = 0; i < num.length; i++) {
                while (isNum.get()) {//自旋
                    Thread.yield();//暂停当前正在运行的线程，并让其他或者自己的线程执行
                }
                System.out.print(num[i]);
                count++;
                if (count == 2) {
                    isNum.set(true);
                    count = 0;
                }
            }
            isNum.set(true);
        }
    }

    public static class PrintChar extends Thread {
        private char[] chars;
        private AtomicBoolean isNum;

        public PrintChar(char[] chars, AtomicBoolean isNum) {
            this.chars = chars;
            this.isNum = isNum;
        }

        @Override
        public void run() {
            for (int i = 0; i < chars.length; i++) {
                while (!isNum.get()) {
                    Thread.yield();
                }
                System.out.print(chars[i]);
                isNum.set(false);
            }
            isNum.set(false);
        }
    }

    //方案二 LockSupport 直接等待和回复

    public static class PrintNum2 extends Thread {
        private int[] num;
        private PrintChar2 printChar2;

        public PrintNum2(int[] num) {
            this.num = num;
        }

        public void setPrintChar2(PrintChar2 printChar2) {
            this.printChar2 = printChar2;
        }

        @Override
        public void run() {
            int count = 0;
            for (int i = 0; i < num.length; i++) {
                if (count == 2) {
                    count = 0;
                    LockSupport.unpark(printChar2);//如果给定的线程的许可上不可用，则使可用
                    LockSupport.park();//禁用当前线程，除非许可可用
                }
                System.out.print(num[i]);
                count++;
            }
            //循环结束，开启另外一个线程
            LockSupport.unpark(printChar2);
        }
    }

    private static class PrintChar2 extends Thread {
        private char[] chars;
        private PrintNum2 printNum2;

        public PrintChar2(char[] chars) {
            this.chars = chars;
        }

        public void setPrintNum2(PrintNum2 printNum2) {
            this.printNum2 = printNum2;
        }

        @Override
        public void run() {
            for (int i = 0; i < chars.length; i++) {
                System.out.print(chars[i]);
                LockSupport.unpark(printNum2);
                LockSupport.park();
            }
            LockSupport.unpark(printNum2);
        }
    }


    //wait/notify(需要对象锁）或者lock/Condition


    public static class PrintNum3 extends Thread {
        private int[] nums;
        private Condition printNum;
        private Condition printChar;
        private Lock canPrint;

        public PrintNum3(int[] nums, Condition printNum, Condition printChar, Lock canPrint) {
            this.nums = nums;
            this.printNum = printNum;
            this.printChar = printChar;
            this.canPrint = canPrint;
        }

        @Override
        public void run() {
            int count = 0;
            try {
                for (int num : nums) {
                    if (count == 2) {
                        canPrint.lock();
                        count = 0;
                        printNum.await();//设置当前线程等待
                        printChar.signal();//唤醒另外一个线程
                        canPrint.unlock();
                    }
                    System.out.print(num);
                    count++;
                }

                canPrint.lock();
                printChar.signal();
                canPrint.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static  class PrintChar3 extends Thread{
        private  char[] chars;
        private Condition printNum;
        private Condition printChar;
        private Lock canPrint;

        public PrintChar3(char[] chars, Condition printNum, Condition printChar, Lock canPrint) {
            this.chars = chars;
            this.printNum = printNum;
            this.printChar = printChar;
            this.canPrint = canPrint;
        }

        @Override
        public void run() {
            int count=0;
            try {
                Thread.sleep(100);
                for (char aChar : chars) {
                    if(count==1){
                        count=0;
                        canPrint.lock();
                        printNum.signal();
                        printChar.await();
                        canPrint.unlock();
                    }
                    System.out.print(aChar);
                    count++;

                }
                canPrint.lock();
                printNum.signal();
                canPrint.unlock();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
