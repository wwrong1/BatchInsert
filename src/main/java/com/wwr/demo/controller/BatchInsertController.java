package com.wwr.demo.controller;

import com.wwr.demo.api.BatchInsertService;
import com.wwr.demo.model.Student;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@Controller
@RestController("/demo")
public class BatchInsertController {

    @Autowired
    private BatchInsertService testBatchInsertService;

    @ApiOperation(value="单线程批量添加", notes="单线程的多批量的大数据添加方法")
    @RequestMapping(value="/batchInsert/{sum}",  method = RequestMethod.GET)
    public String batchInsert(@PathVariable("sum") Integer sum) {
        long startTime = System.currentTimeMillis();

        try {
            List<Student> list = new LinkedList<>();

            Student info = null;

            for (int i = 0; i < sum; i++) {
                info = new Student();
                info.setStu_number(i);
                info.setStu_name("text"+i);
                info.setStu_xb("man");
                info.setStu_age(i%100);
                list.add(info);
            }

            testBatchInsertService.batchInsert(list);

            System.out.println("------Batch Insert Success------");

        } catch (Exception e) {
            e.printStackTrace();
        }
        long t = System.currentTimeMillis() - startTime;
        System.out.println("耗时(s):" + t/1000);
        return "添加成功,耗时(s):" + t/1000;
    }

    @ApiOperation(value="多线程批量添加", notes="多线程的多批量的大数据添加方法")
//    @ApiImplicitParam(name = "sum", value = "数据总量", required = true, dataType = "Integer")
    @RequestMapping(value="/batchInsertByThreads/{sum}", method = RequestMethod.GET)
    public String batchInsertByThreads(@RequestParam("sum") Integer sum) {
        long startTime = System.currentTimeMillis();

        try {
            List<Student> list = new LinkedList<>();

            Student info = null;

            for (int i = 0; i <sum; i++) {
                info = new Student();
                info.setStu_number(i);
                info.setStu_name("li ming"+i);
                info.setStu_xb("man");
                info.setStu_age(i%100);
                list.add(info);
            }

            testBatchInsertService.batchInsertByThread(list);

            System.out.println("------Batch Insert Success------");

        } catch (Exception e) {
            e.printStackTrace();
        }
        long t = System.currentTimeMillis() - startTime;
        System.out.println("耗时(s):" + t/1000);
        return "添加成功,耗时(s):" + t/1000;
    }

//    @GetMapping("/test/{id}")
//    public String test(@PathVariable String id){
//        System.out.println("===================="+id);
//        return id;
//    }
}
