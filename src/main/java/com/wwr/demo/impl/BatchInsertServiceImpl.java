package com.wwr.demo.impl;

import com.wwr.demo.api.BatchInsertService;
import com.wwr.demo.model.Student;
import com.wwr.demo.mapper.BatchInsertMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service(value = "batchInsertService")
public class BatchInsertServiceImpl implements BatchInsertService {

    @Autowired
    private BatchInsertMapper batchInsertMapper;

    @Override
    @Transactional
    public void batchInsertByThread(List<Student> list) throws Exception {
        if (list == null || list.isEmpty()) {
            return;
        }
        // 一个线程处理每批的数据条数
        int count =2500;
        // 数据集合大小
        int listSize = list.size();
        // 开启的线程数
        int runSize = (listSize / count) + 1;
        // 存放每个线程的执行数据
        List<Student> newList = null;
        // 创建一个线程池，数量和开启线程的数量一样
        ExecutorService executor = Executors.newFixedThreadPool(16);
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
            BatchInsertThread thread = new BatchInsertThread(batchInsertMapper,newList, end);

            executor.execute(thread);
        }

        end.await();
        executor.shutdown();
    }

    @Override
    public void batchInsert(List<Student> list) {
        if (list == null || list.isEmpty()) {
            return;
        }

        List<Student> tempList = new LinkedList<>();

        for (int i = 0; i < list.size(); i++) {

            tempList.add(list.get(i));

            if (i % 1000 == 0) {
                batchInsertMapper.batchInsert(tempList);
                tempList.clear();
            }
        }

        batchInsertMapper.batchInsert(tempList);
    }
}

