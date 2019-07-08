package com.wwr.demo.mapper;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.wwr.demo.BatchApplication;
import com.wwr.demo.api.BatchInsertService;
import com.wwr.demo.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BatchApplication.class/*, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT*/)
public class BatchInsertMapperTest {

    @Autowired
    private BatchInsertService testBatchInsertService;

    @Test
    public void batchInsertByThread() {
        try {
            List<Student> list = new LinkedList<>();

            Student info = null;

            for (int i = 0; i <10000; i++) {
                info = new Student();
                info.setStu_number(i);
                info.setStu_name("li ming"+i);
                info.setStu_xb("man");
                info.setStu_age(i%100);
                list.add(info);
            }
            ArrayList l = new ArrayList();
            for (int i = 1;i<=100;i++) {
                for(int j = 1;j<=16;j++){
                    long startTime = System.currentTimeMillis();
                    testBatchInsertService.batchInsertByThread(list, 25 * i, j);
                    System.out.println(list.size());
                    long t = System.currentTimeMillis() - startTime;
                    long[] m={j,25*i,t};
                    l.add(m);
                }
            }
            for (int i = 0;i<l.size();i++){
                long[] m = (long[]) l.get(i);
                System.out.println("["+m[0]+","+m[1]+","+m[2]+"],");
            }
            System.out.println("------Batch Insert Success------");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void batchInsert() {

        long startTime = System.currentTimeMillis();

        try {
            List<Student> list = new LinkedList<>();

            Student info = null;

            for (int i = 0; i < 1000000; i++) {
                info = new Student();
                info.setStu_number(i);
                info.setStu_name("text"+i);
                info.setStu_xb("man");
                info.setStu_age(i%100);

                list.add(info);
            }

            testBatchInsertService.batchInsert(list,2500);

            System.out.println("------Batch Insert Success------");

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("耗时(s):" + (System.currentTimeMillis() - startTime)/1000);
    }

}
