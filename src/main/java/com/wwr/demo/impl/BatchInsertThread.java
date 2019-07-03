package com.wwr.demo.impl;

import com.wwr.demo.model.Student;
import com.wwr.demo.mapper.BatchInsertMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class BatchInsertThread implements Runnable{

    private BatchInsertMapper batchInsertMapper;
    /**数据集合*/
    private List<Student> list;
    /** 每个线程处理的结束数据 */
    private CountDownLatch end;

    public BatchInsertThread(){

    }

    public BatchInsertThread(BatchInsertMapper batchInsertMapper, List<Student> list,  CountDownLatch end) {
        this.batchInsertMapper = batchInsertMapper;
        this.list = list;
        this.end = end;
    }
    @Transactional
    @Override
    public void run() {
        try {

            if (list != null && !list.isEmpty()) {
                batchInsertMapper.batchInsert(list);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 当一个线程执行完 了计数要减一不然这个线程会被一直挂起
            end.countDown();
        }
    }
}

