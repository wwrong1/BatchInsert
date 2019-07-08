package com.wwr.demo.impl;

import com.wwr.demo.api.BatchInsertService;
import com.wwr.demo.model.Student;
import com.wwr.demo.util.SplitToBatchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service(value = "batchInsertService")
public class BatchInsertServiceImpl implements BatchInsertService {
    @Autowired
    private SplitToBatchUtil splitToBatchUtil;

    @Override
    @Transactional
    public void batchInsertByThread(List<Student> list,int sumOfPerBatch,int numOfThreads) throws Exception {
        if (list == null || list.isEmpty()) {
            return;
        }
        // 数据集合大小
        int listSize = list.size();
        // 一个线程处理每批的数据条数
        int count =listSize/numOfThreads;
        // 开启的线程数
        int runSize = numOfThreads;
        // 存放每个线程的执行数据
        List<Student> newList = null;
        // 创建一个线程池，数量和开启线程的数量一样
        ExecutorService executor = Executors.newFixedThreadPool(runSize);
        // 创建两个个计数器
        CountDownLatch end = new CountDownLatch(runSize);


        for (int i = 0; i < runSize; i++) {
            /* 计算每个线程执行的数据 */
            if ((i + 1) == runSize) {
                int startIdx = (i * count);
                int endIdx = list.size();


                newList = list.subList(startIdx, endIdx);
            } else {
                int startIdx = (i * count);
                int endIdx = (i + 1) * count;

                newList = list.subList(startIdx, endIdx);
            }

            BatchInsertThread thread = new BatchInsertThread(splitToBatchUtil,newList, sumOfPerBatch,end);

            executor.execute(thread);
        }

        end.await();
        executor.shutdown();
    }

    @Override
    public void batchInsert(List<Student> list,int sumOfPerBatch) {
        if (list == null || list.isEmpty()) {
            return;
        }
        splitToBatchUtil.splitAndInsert(list,sumOfPerBatch);
    }
}

