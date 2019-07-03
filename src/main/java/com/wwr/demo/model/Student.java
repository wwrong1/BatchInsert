package com.wwr.demo.model;

public class Student {
    private int id;
    private int stu_number;
    private String stu_name;
    private String stu_xb;
    private int stu_age;

    public Student(){}

    public Student(int id, int stuNumber, String stuName, String xb, int stuAge) {
        this.id = id;
        this.stu_number = stuNumber;
        this.stu_name = stuName;
        stu_xb = xb;
        this.stu_age = stuAge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStu_number() {
        return stu_number;
    }

    public void setStu_number(int stu_number) {
        this.stu_number = stu_number;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public String getStu_xb() {
        return stu_xb;
    }

    public void setStu_xb(String stu_xb) {
        this.stu_xb = stu_xb;
    }

    public int getStu_age() {
        return stu_age;
    }

    public void setStu_age(int stu_age) {
        this.stu_age = stu_age;
    }
}
