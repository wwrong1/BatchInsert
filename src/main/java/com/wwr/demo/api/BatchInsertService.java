package com.wwr.demo.api;

import com.wwr.demo.model.Student;
import java.util.List;

public interface BatchInsertService {
    void batchInsertByThread(List<Student> list) throws Exception;
    void batchInsert(List<Student> list);
}
