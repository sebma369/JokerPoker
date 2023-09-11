package com.example.jokerpoker;

public class WaitNotifyExample {
    private boolean condition = false; // 要等待的条件

    public synchronized void waitForCondition() throws InterruptedException {
        while (!condition) {
            wait(); // 等待条件满足
        }
        // 条件满足后执行后续操作
        System.out.println("条件已满足，继续执行后续操作...");
        System.out.println("utyiuotyhhjl");
    }

    public synchronized void setConditionTrue() {
        condition = true; // 设置条件为true
        notify(); // 唤醒等待的线程
    }

    public static void main(String[] args) {
        WaitNotifyExample example = new WaitNotifyExample();

        Thread waitThread = new Thread(() -> {
            try {
                example.waitForCondition(); // 等待条件满足
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread setConditionThread = new Thread(() -> {
            try {
                System.out.println("asds");
                Thread.sleep(5000); // 模拟一些操作
                System.out.println("sadsd");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            example.setConditionTrue(); // 设置条件为true，唤醒等待线程
        });
        System.out.println("asdsaddsadsadas");
        waitThread.start();
        setConditionThread.start();
    }
}
