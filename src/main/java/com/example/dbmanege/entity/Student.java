package com.example.dbmanege.entity;

/**
 * @author niu
 * @Description:
 * @date 2021/10/2019:35
 */

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;



@Data
@Document(collection = "student")
public class Student {

    @Excel(name = "sid")
    private String sid;

    @Excel(name = "name")
    @Field("name")
    private String name;

    @Excel(name = "sex")
    private String sex;

    @Excel(name = "age")
    private int age;

    @Excel(name = "birthday")
    private String birthday;

    @Excel(name = "dname")
    private String dname;

    @Field("class")
    @Excel(name = "class")
    private String dclass;
}
