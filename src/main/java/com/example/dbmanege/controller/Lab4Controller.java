package com.example.dbmanege.controller;

import com.example.dbmanege.entity.Course;
import com.example.dbmanege.entity.Student;
import com.example.dbmanege.entity.Teacher;
import com.example.dbmanege.result.Result;
import com.example.dbmanege.util.ExcelUtil;
import com.mongodb.client.result.UpdateResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author niu
 * @Description:
 * @date 2021/10/3110:07
 */

@RestController
@RequestMapping("/lab4")
public class Lab4Controller {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final Logger logger = LogManager.getLogger(Lab4Controller.class);

    @Autowired
    ExcelUtil excelUtil;


    // 更新student
    @RequestMapping("/updateStudent")
    public Result updateStudent(@RequestBody Student student){
        try{
            return getResult(student);
        }catch (Exception e){
            logger.error("更新student错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }

    // 更新teacher
    @RequestMapping("/updateTeacher")
    public Result updateTeacher(@RequestBody Teacher teacher){
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("tid").is(teacher.getTid()));
            Update update = new Update();
            update.set("tid",teacher.getTid());
            update.set("name",teacher.getName());
            update.set("sex",teacher.getSex());
            update.set("age",teacher.getAge());
            update.set("dname",teacher.getDname());

            UpdateResult upsert = mongoTemplate.upsert(query, update, Teacher.class);
            System.out.println(upsert);
            return Result.success().message("修改成功");
        }catch (Exception e){
            logger.error("更新teacher错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }


    // 更新course
    @RequestMapping("/updateCourse")
    public Result updateCourse(@RequestBody Course course){
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("cid").is(course.getCid()));
            Update update = new Update();
            update.set("cid",course.getCid());
            update.set("name",course.getName());
            update.set("fcid",course.getFcid());
            update.set("credit",course.getCredit());

            UpdateResult upsert = mongoTemplate.upsert(query, update, Course.class);
            System.out.println(upsert);
            return Result.success().message("修改成功");
        }catch (Exception e){
            logger.error("更新course错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }


    // 根据excel更新student
    @RequestMapping("/updateExcelStudent")
    public Result updateExcelStudent(String path){

        System.out.println("path" + path);

        try {
            List<Student> list = excelUtil.importStudentExcel(path);
            for (Student s : list){
                return getResult(s);
            }
            logger.debug("使用excel更新student数据成功");
            return Result.success().message("使用excel插入student数据成功");
        } catch (Exception e) {
            logger.error("使用excel更新student数据错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }

    // 根据excel更新teacher
    @RequestMapping("/updateExcelTeacher")
    public Result updateExcelTeacher(String path){

        System.out.println("path" + path);

        try {
            List<Teacher> list = excelUtil.importTeacherExcel(path);
            for (Teacher t : list){
                Query query = new Query();
                query.addCriteria(Criteria.where("tid").is(t.getTid()));
                Update update = new Update();
                update.set("tid",t.getTid());
                update.set("name",t.getName());
                update.set("sex",t.getSex());
                update.set("age",t.getAge());
                update.set("dname",t.getDname());
                UpdateResult upsert = mongoTemplate.upsert(query, update, Teacher.class);
                System.out.println(upsert);
                return Result.success().message("修改成功");
            }
            logger.debug("使用excel更新teacher数据成功");
            return Result.success().message("使用excel插入teacher数据成功");
        } catch (Exception e) {
            logger.error("使用excel更新teacher数据错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }


    // 根据excel更新course
    @RequestMapping("/updateExcelCourse")
    public Result updateExcelCourse(String path){

        System.out.println("path" + path);

        try {
            List<Course> list = excelUtil.importCourseExcel(path);
            for (Course course : list){
                Query query = new Query();
                query.addCriteria(Criteria.where("cid").is(course.getCid()));
                Update update = new Update();
                update.set("cid",course.getCid());
                update.set("name",course.getName());
                update.set("fcid",course.getFcid());
                update.set("credit",course.getCredit());

                UpdateResult upsert = mongoTemplate.upsert(query, update, Course.class);
                System.out.println(upsert);
                return Result.success().message("修改成功");
            }
            logger.debug("使用excel更新course数据成功");
            return Result.success().message("使用excel插入course数据成功");
        } catch (Exception e) {
            logger.error("使用excel更新course数据错误");
            logger.error(e.getMessage());
            return Result.error();
        }
    }

    private Result getResult(Student s) {
        Query query = new Query();
        query.addCriteria(Criteria.where("sid").is(s.getSid()));
        Update update = new Update();
        update.set("sid",s.getSid());
        update.set("name",s.getName());
        update.set("sex",s.getSex());
        update.set("birthday",s.getBirthday());
        update.set("dname",s.getDname());
        update.set("dclass",s.getDclass());
        UpdateResult upsert = mongoTemplate.upsert(query, update, Student.class);
        System.out.println(upsert);
        return Result.success().message("修改成功");
    }


}
