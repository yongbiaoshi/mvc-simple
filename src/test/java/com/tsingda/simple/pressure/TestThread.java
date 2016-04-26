package com.tsingda.simple.pressure;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestThread extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(TestThread.class);
    private CountDownLatch latch;

    public TestThread(CountDownLatch latch) {
        this.latch = latch;
    }
    
    @Override
    public void run() {
        try {
            latch.await();
            Thread current = Thread.currentThread();
            long threadId = current.getId();
            String url = String.format("http://192.168.2.123:3000/demo/test?threadId=%s&msg=%s", threadId,
                    current.getName());
            String res = HttpUtils.get(url);
            logger.info("请求结束：{}", res);
        } catch (InterruptedException | IOException e) {
            logger.error("请求结束,出错：{}", e.getMessage());
        }
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

}
