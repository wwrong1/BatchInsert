package com.wwr.demo.impl;

import com.wwr.demo.model.Student;
import com.wwr.demo.util.SplitToBatchUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class BatchInsertThread implements Runnable{

    private SplitToBatchUtil splitToBatchUtil;
    /**数据集合*/
    private List<Student> list;

    /** 每个线程处理的每批数据的长度 */
    private int sumOfPerBatch;

    private CountDownLatch end;


    public BatchInsertThread(SplitToBatchUtil splitToBatchUtil, List<Student> list, int sumOfPerBatch, CountDownLatch end) {
        this.splitToBatchUtil = splitToBatchUtil;
        this.list = list;
        this.sumOfPerBatch = sumOfPerBatch;
        this.end = end;
    }

    @Transactional
    @Override
    public void run() {
        try {

            if (list != null && !list.isEmpty()) {
                splitToBatchUtil.insert(list,sumOfPerBatch);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 当一个线程执行完 了计数要减一不然这个线程会被一直挂起
            end.countDown();
        }
    }
}

