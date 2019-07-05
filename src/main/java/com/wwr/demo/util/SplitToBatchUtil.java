package com.wwr.demo.util;

import com.wwr.demo.mapper.BatchInsertMapper;
import com.wwr.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class SplitToBatchUtil {

    @Autowired
    private BatchInsertMapper batchInsertMapper;

    public SplitToBatchUtil(){}

    public SplitToBatchUtil(BatchInsertMapper batchInsertMapper) {
        this.batchInsertMapper = batchInsertMapper;
    }

    public void insert(List<Student> list, int sumOfPerBatch){
        List<Student> newList;

        for (int i = 0; i < list.size()/sumOfPerBatch; i++) {
            /* 计算每个线程执行的数据 */
            if ((i + 1) == list.size()/sumOfPerBatch) {
                int startIdx = (i * sumOfPerBatch);
                int endIdx = list.size();

                newList = list.subList(startIdx, endIdx);
            } else {
                int startIdx = (i * sumOfPerBatch);
                int endIdx = (i + 1) * sumOfPerBatch;

                newList = list.subList(startIdx, endIdx);
            }
            batchInsertMapper.batchInsert(newList);
        }
    }

}
