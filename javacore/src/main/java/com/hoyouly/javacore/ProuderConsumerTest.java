package com.hoyouly.javacore;

import java.util.Date;
import java.util.LinkedList;

/**
 * Created by hoyouly on 18-4-29.
 */

public class ProuderConsumerTest {

	public static void main(String[] args) {
		Buffer buffer = new Buffer(10);
		Producer producer = new Producer(buffer);
		Consumer consumer = new Consumer(buffer);

		for (int i = 0; i < 3; i++) {
			new Thread(producer, "producer-" + i).start();
		}
		for (int i = 0; i < 3; i++) {
			new Thread(consumer, "consumer- " + i).start();
		}
	}

	public static class Buffer {
		private int maxSize;
		private LinkedList<Date> storage;

		public Buffer(int maxSize) {
			this.maxSize = maxSize;
			storage = new LinkedList<>();
		}

		//生产方法
		public synchronized void put() {
			try {
				while (storage.size() == maxSize) {
					System.out.println(Thread.currentThread().getName() + "  wait");
					wait();//阻塞线程
				}
				storage.add(new Date());
				System.out.println(Thread.currentThread().getName() + "  put :" + storage.size());
				Thread.sleep(1000);
				notifyAll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public synchronized void take() {
			try {
				while (storage.size() == 0) {
					System.out.println(Thread.currentThread().getName() + "  wait");
					wait();//阻塞线程
				}
				Date date = storage.poll();
				System.out.println(Thread.currentThread().getName() + "  take :" + storage.size() + "   data: " + date.toGMTString());
				Thread.sleep(1000);
				notifyAll();
			} catch (InterruptedException e) {

			}
		}
	}

	public static class Producer extends Thread {
		private Buffer mBuffer;

		public Producer(Buffer buffer) {
			mBuffer = buffer;
		}

		@Override
		public void run() {
			while (true) {
				mBuffer.put();
			}
		}
	}

	public static class Consumer extends Thread {
		private Buffer mBuffer;

		public Consumer(Buffer buffer) {
			mBuffer = buffer;
		}

		@Override
		public void run() {
			while (true) {
				mBuffer.take();
			}
		}
	}

}
