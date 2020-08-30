package com.example.lib;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MyThreadTestClass {

    public MyThreadTestClass() {
        super();
    }

    public static class MyThread implements Callable<String> {

        @Override
        public String call() throws Exception {
            return null;
        }
    }


    public static void main(String[] args) throws Exception{
        System.out.println("asasas");
        MyThread myThread = new MyThread();
        FutureTask<String> futureTask = new FutureTask(myThread);
        new Thread(futureTask).start();//start才能证明是线程，run只是一个普通的执行
        try {
            futureTask.get();//这个是有阻塞效果的
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        UseThread useThread = new UseThread();
        useThread.start();
        useThread.setDaemon(true);
        UseThread2 useThread22 = new UseThread2();
//        useThread.start();

//        Thread.sleep(10);
//        useThread.join();
//        useThread.interrupt();//停止不下来的，这是是发送信号而已

    }

    /**
     * end thread
     */
    private static class UseThread extends Thread {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
//            while(!isInterrupted()) {//or Thread.currentThread.isInterrupted()
//                System.out.println("~~~:" + name + isInterrupted());
//            }
            System.out.println("~~~:" + name + isInterrupted());
        }
    }

    private static class UseThread2 extends Thread {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            System.out.println("222~~~:" + name + isInterrupted());
        }
    }

}
