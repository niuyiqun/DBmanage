package com.example.dbmanege.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author niu
 * @Description:
 * @date 2021/10/2311:38
 */


@Data
@Document(collection = "teacher_course")
public class TeacherCourse {

    private String tid;
    private String cid;

}
