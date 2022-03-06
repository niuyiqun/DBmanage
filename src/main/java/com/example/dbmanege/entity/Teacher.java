package com.example.dbmanege.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author niu
 * @Description:
 * @date 2021/10/2311:32
 */


@Data
@Document(collection = "teacher")
public class Teacher {

    private String tid;
    private String name;
    private String sex;
    private int age;
    private String dname;
}
