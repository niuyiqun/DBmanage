package com.example.dbmanege.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author niu
 * @Description:
 * @date 2021/10/2311:37
 */

@Data
@Document(value = "student_course")
public class StudentCourse {


    private String sid;
    private String cid;
    private int score;
    private String tid;
}
