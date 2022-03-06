package com.example.dbmanege.controller;

import com.example.dbmanege.entity.Course;
import com.example.dbmanege.entity.Student;
import com.example.dbmanege.entity.Teacher;
import com.example.dbmanege.result.Result;
import com.example.dbmanege.util.ExcelUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author niu
 * @Description:
 * @date 2021/10/2614:47
 */


@RestController
@RequestMapping("/lab3")
public class Lab3Controller {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final Logger logger = LogManager.getLogger(Lab3Controller.class);

    @Autowired
    ExcelUtil excelUtil;

    // 获取所有学生
    @RequestMapping("/getStudent")
    public Result getStudent() {
        try {
            List<Student> studentList = mongoTemplate.findAll(Student.class);
            Map m = new HashMap<>();
            m.put("list", studentList);
            return Result.success().data(m);
        } catch (Exception e) {
            logger.error("获取所有学生错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }

    //获取所有课程
    @RequestMapping("/getCourse")
    public Result getCourse() {
        try {
            List<Course> courseList = mongoTemplate.findAll(Course.class);
            Map m = new HashMap<>();
            m.put("list", courseList);
            return Result.success().data(m);
        } catch (Exception e) {
            logger.error("检索所有课程情况错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }

    //获取所有老师
    @RequestMapping("/getTeacher")
    public Result getTeacher() {
        try {
            List<Teacher> courseList = mongoTemplate.findAll(Teacher.class);
            Map m = new HashMap<>();
            m.put("list", courseList);
            return Result.success().data(m);
        } catch (Exception e) {
            logger.error("获取所有老师错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }

    // 插入学生信息
    @RequestMapping("/insertStudent")
    public Result insertStudent(@RequestBody Student student) {

        System.out.println(student);
        try {
            mongoTemplate.insert(student);
            return Result.success().message("插入学生成功");
        } catch (Exception e) {
            logger.error("插入学生信息错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }

    // 插入老师信息
    @RequestMapping("/insertTeacher")
    public Result insertTeacher(@RequestBody Teacher teacher) {

        System.out.println(teacher);
        try {
            mongoTemplate.insert(teacher);
            return Result.success().message("插入老师成功");
        } catch (Exception e) {
            logger.error("插入老师信息错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }

    // 插入课程信息
    @RequestMapping("/insertCourse")
    public Result insertCourse(@RequestBody Course course) {

        System.out.println(course);
        try {
            mongoTemplate.insert(course);
            return Result.success().message("插入课程成功");
        } catch (Exception e) {
            logger.error("插入课程信息错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }

    // 使用excel插入数据

    @RequestMapping("/studentExcel")
    public Result studentExcel( String path) {
        System.out.println("path" + path);

        if (path==null)
            path = "D:\\大学\\大三上\\非关系型数据库\\实验\\test.xlsx";

        try {
            List<Student> list = excelUtil.importStudentExcel(path);
            for (Student s : list){
                mongoTemplate.insert(s);
            }
            logger.debug("使用excel插入student数据成功");
            return Result.success().message("使用excel插入student数据成功");
        } catch (Exception e) {
            logger.error("使用excel插入student数据错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }

    @RequestMapping("/teacherExcel")
    public Result teacherExcel(String path) {
        System.out.println("path" + path);
        try {
//            List<Teacher> list = excelUtil.importTeacherExcel(path);
//            for (Teacher s : list){
//                mongoTemplate.insert(s);
//            }
            logger.debug("使用excel插入teacher数据成功");
            return Result.success().message("使用excel插入Teacher数据成功");
        } catch (Exception e) {
            logger.error("使用excel插入Teacher数据错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }

    @RequestMapping("/courseExcel")
    public Result courseExcel(String path) {
        System.out.println("path" + path);
        try {
//            List<Course> list = excelUtil.importCourseExcel(path);
//            for (Course s : list){
//                mongoTemplate.insert(s);
//            }
            logger.debug("使用excel插入course数据成功");
            return Result.success().message("使用excel插入Teacher数据成功");
        } catch (Exception e) {
            logger.error("使用excel插入Teacher数据错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }
}
