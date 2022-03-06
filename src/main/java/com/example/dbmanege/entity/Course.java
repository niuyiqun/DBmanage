package com.example.dbmanege.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author niu
 * @Description:
 * @date 2021/10/2311:35
 */


@Data
@Document(value = "course")
public class Course {
    private String cid;
    private String name;
    private String fcid;
    private int credit;
}
