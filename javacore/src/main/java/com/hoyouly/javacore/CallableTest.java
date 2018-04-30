package com.hoyouly.javacore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableTest {
    public static void main(String[] args) throws InterruptedException {
        List<Future<Integer>> lise = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 2; i++) {
            Future<Integer> future = service.submit(new MyTask(10));
            lise.add(future);
        }

        System.out.println("集合长度： " + lise.size());
        for (Future<Integer> future : lise) {
            int sum = 0;
            while (!future.isDone()) ;//如果任务已完成，isDone返回true
            try {
                sum += future.get();//get()方法得到返回值
                System.out.println(sum);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }


    static class MyTask implements Callable<Integer> {

        private int upperBounds;

        public MyTask(int upperBounds) {
            this.upperBounds = upperBounds;
        }

        @Override
        public Integer call() throws Exception {
            int sum = 0;
            for (int i = 0; i < upperBounds; i++) {
                sum += i;

            }
            return sum;
        }
    }
}
