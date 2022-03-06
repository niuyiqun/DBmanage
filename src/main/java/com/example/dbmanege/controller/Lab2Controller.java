package com.example.dbmanege.controller;

import com.example.dbmanege.entity.Course;
import com.example.dbmanege.entity.Student;
import com.example.dbmanege.entity.Teacher;
import com.example.dbmanege.result.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author niu
 * @Description:
 * @date 2021/10/2311:44
 */

@RestController
@RequestMapping("/lab2")
//@CrossOrigin(origins = {"http://localhost:9528", "null"})
public class Lab2Controller {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final Logger logger = LogManager.getLogger(Lab2Controller.class);



    // 找出年龄小于20岁的所有学生
    @RequestMapping("/first")
    public Result first(){
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("age").lt(20));
            List<Student> studentList = mongoTemplate.find(query,Student.class);
            Map m = new HashMap<>();
            m.put("list",studentList);
            return Result.success().data(m);
        }catch (Exception e){
            logger.error("找出年龄小于20岁的所有学生错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }

    //找出年龄小于20岁且是软件学院的学生
    @RequestMapping("/second")
    public Result second(){
        try{
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.andOperator(
                    Criteria.where("age").lt(20),
                    Criteria.where("dname").is("SC")
            );
            query.addCriteria(criteria);
            List<Student> studentList = mongoTemplate.find(query,Student.class);
            Map m = new HashMap<>();
            m.put("list",studentList);
            return Result.success().data(m);
        }catch (Exception e){
            logger.error("找出年龄小于20岁且是软件学院的学生错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }


    //找出学生关系中的所有学生
    @RequestMapping("/third")
    public Result third(){
        try{
            List<Student> studentList = mongoTemplate.findAll(Student.class);
            Map m = new HashMap<>();
            m.put("list",studentList);
            return Result.success().data(m);
        }catch (Exception e){
            logger.error("找出年龄小于20岁且是软件学院的学生错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }


    //求所有学生的姓名、年龄
    @RequestMapping("/forth")
    public Result forth(){
        try{
            Query query = new Query();
            query.fields().include("name");
            query.fields().include("age");
            List<Student> studentList = mongoTemplate.find(query,Student.class);
            Map m = new HashMap<>();
            m.put("list",studentList);
            return Result.success().data(m);
        }catch (Exception e){
            logger.error("找出求所有学生的姓名、年龄错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }

    //找出年龄小于20岁的学生的姓名、性别
    @RequestMapping("/fifth")
    public Result fifth(){
        try{
            Query query = new Query(Criteria.where("age").lt(20));
            query.fields().include("name");
            query.fields().include("sex");
            List<Student> studentList = mongoTemplate.find(query,Student.class);
            Map m = new HashMap<>();
            m.put("list",studentList);
            return Result.success().data(m);
        }catch (Exception e){
            logger.error("找出年龄小于20岁的学生的姓名、性别错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }


    //检索所有课程情况
    @RequestMapping("/sixth")
    public Result sixth(){
        try{
            List<Course> courseList = mongoTemplate.findAll(Course.class);
            Map m = new HashMap<>();
            m.put("list",courseList);
            return Result.success().data(m);
        }catch (Exception e){
            logger.error("检索所有课程情况错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }

    //检索先行课号为“300001”的课程名
    @RequestMapping("/seventh")
    public Result seventh(){
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("fcid").is("300001"));
            query.fields().include("name");
            List<Course> nameList = mongoTemplate.find(query,Course.class);
            Map m = new HashMap<>();
            m.put("list",nameList);
            return Result.success().data(m);
        }catch (Exception e){
            logger.error("检索先行课号为“300001”的课程名错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }

    //找出年龄大于50岁的老师
    @RequestMapping("/eighth")
    public Result eighth(){
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("age").gt(50));
            List<Teacher> teacherList = mongoTemplate.find(query, Teacher.class);
            Map m = new HashMap<>();
            m.put("list",teacherList);
            return Result.success().data(m);
        }catch (Exception e){
            logger.error("检索先行课号为“300001”的课程名错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }

    //找出所有的男老师
    @RequestMapping("/ninth")
    public Result ninth(){
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("sex").is("M"));
            List<Teacher> teacherList = mongoTemplate.find(query, Teacher.class);
            Map m = new HashMap<>();
            m.put("list",teacherList);
            return Result.success().data(m);
        }catch (Exception e){
            logger.error("找出所有的男老师错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }


    //找出所有在CS学院的老师
    @RequestMapping("/tenth")
    public Result tenth(){
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("dname").gt("CS"));
            List<Teacher> teacherList = mongoTemplate.find(query, Teacher.class);
            Map m = new HashMap<>();
            m.put("list",teacherList);
            return Result.success().data(m);
        }catch (Exception e){
            logger.error("找出所有在CS学院的老师错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }

}
