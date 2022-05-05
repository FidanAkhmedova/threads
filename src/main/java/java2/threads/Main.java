package java2.threads;

public class Main {
    public static final int size = 10;//000000;
    public static final int h = size / 2;
    public static final Object obj1 = new Object();
    public static final Object obj2 = new Object();
    public static void main(String[] args) throws InterruptedException {
        MyThread thread1 = new MyThread();
        MyThread thread2 = new MyThread();

        float[] arr = new float[size];
        firstMethod();
        secondMethod(thread1, thread2);
        thread1.join();
        thread2.join();
    }
    public static void printArray(float[] arr){
        for (int j = 0; j < arr.length; j++) {
            System.out.println(arr[j]);
        }
    }
    public static void firstMethod() {
        int size = 20;
        float[] arr = new float[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("One thread time: " + (System.currentTimeMillis() -
                startTime) + " ms.");
        printArray(arr);
    }
    public static void secondMethod(Thread thread1, Thread thread2) {
        int size = 10;
        float[] arr = new float[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;
        }
        float a2[] = new float[h];
        float a1[] = new float[h];
        long a = System.currentTimeMillis();
        System.out.println("start time for separating arrays");
        System.arraycopy(arr, h, a2, 0, h);
        System.arraycopy(arr, 0, a1, 0, h);
        System.currentTimeMillis();
        System.out.println(System.currentTimeMillis() - a);
//        System.out.println("end time for separating arrays");
        thread1.start();
        thread2.start();
        synchronized (obj1) {
            System.out.println("start time for calculating first part array");
            long b = System.currentTimeMillis();
            for (int i = 0; i < a1.length; i++) {
                a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            System.currentTimeMillis();
            System.out.println(System.currentTimeMillis() - b);
            System.out.println("end time for calculating first part array");
            printArray(a1);
        }
        synchronized (obj2) {
            System.out.println("start time for calculating second part array");
            long c = System.currentTimeMillis();
            for (int i = 0; i < a2.length; i++) {
                a2[i] = (float) (a2[i] * Math.sin(0.2f + (i+ a1.length-1) / 5) * Math.cos(0.2f + (i+ a1.length-1) / 5) * Math.cos(0.4f + (i+ a1.length-1) / 2));
            }
            System.currentTimeMillis();
            System.out.println(System.currentTimeMillis() - c);
            System.out.println("end time for calculating second part array");
            printArray(a2);
        }
        System.out.println("start time for joining arrays");
        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
        System.out.println("end time for joining arrays");
        printArray(arr);
        System.out.println("Two thread time: " + (System.currentTimeMillis() -
                a) + " ms.");
    }
}
 class MyThread extends Thread {
    String threadName;
    @Override
    public void run() {
        System.out.println("thread starting");
        System.out.println(Thread.currentThread().getName());
    }
}