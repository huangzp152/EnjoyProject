package com.example.lib;

public class Res3 {
    private int id;//面包的id
    private boolean flag;//定义标记，默认是false 先生产，后消费

    public static void main(String[] args) {
        Res3 res3 = new Res3();
        ProduceRunnable produceRunnable = new ProduceRunnable(res3);
        ConsumeRunnable consumeRunnable = new ConsumeRunnable(res3);
        //开线程执行任务
        new Thread(produceRunnable).start();
        new Thread(consumeRunnable).start();
    }

    public synchronized void put(String name) {
        if(!flag) {
            id+=1;
            System.out.println(Thread.currentThread().getName()+ "生产者 生产了："+ this.id);
            flag = true;

            /**
             * 唤醒 wait（），唤醒被冻结的线程 如果没有也没事，java默认不会报错
             */
            notify();//wait或者notify必须要有锁包裹着
            /**
             * 唤醒完自己就冻结当前自己线程 冻结，释放cpu执行资格 执行权，cpu会去执行其他线程
             */
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //消费行为
    public synchronized  void out() {
        /**
         * 消费之前判断标记
         */
        if(flag) {
            System.out.println(Thread.currentThread().getName()+ ">>>>>>> 消费者 消费了" + this.id);
            flag = false;
        }

        /**
         * 唤醒 wait（），被冻结的线程 如果没有也没事，java默认不会报错
         */
        notify();//wait或者notify必须要有锁包裹着
        /**
         * 当前自己线程 冻结，释放cpu执行资格 执行权，cpu会去执行其他线程
         */
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class ProduceRunnable implements Runnable {

        private Res3 res;
        public ProduceRunnable(Res3 res) {
            this.res = res;
        }

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                res.put("bread");
            }
        }
    }

    static class ConsumeRunnable implements Runnable {

        private Res3 res;
        public ConsumeRunnable(Res3 res) {
            this.res = res;
        }

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                res.out();
            }
        }
    }

}
