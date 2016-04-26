package com.tsingda.simple.pressure;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PressureTestApp {
    
    private static final Logger logger = LoggerFactory.getLogger(PressureTestApp.class); 

    public static void main(String[] args) throws InterruptedException {
        
        CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 10000; i++) {
            TestThread thread = new TestThread(latch);
            thread.start();
            logger.info("已经创建：{} 个线程", i + 1);
        }
        logger.info("5秒后线程启动");
        Thread.sleep(5000);
        logger.info("线程启动");
        latch.countDown();
    }

}
