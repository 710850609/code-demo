package org.linbo.demo.threadPool;

/**
 * @author LinBo
 * @date 2019-10-12 13:45
 */
public class MyRunner implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
