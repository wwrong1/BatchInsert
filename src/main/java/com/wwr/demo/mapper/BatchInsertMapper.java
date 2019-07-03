package com.wwr.demo.mapper;

import com.wwr.demo.model.Student;

import java.util.List;

public interface BatchInsertMapper {
    void batchInsert(List<Student> list);
}
