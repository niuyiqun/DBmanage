package com.example.dbmanege.controller;

import com.example.dbmanege.entity.*;
import com.example.dbmanege.result.Result;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author niu
 * @Description:
 * @date 2021/11/1515:32
 */

@RestController
@RequestMapping("/lab5")
public class Lab5Controller {


    @Autowired
    private MongoTemplate mongoTemplate;

    private static final Logger logger = LogManager.getLogger(Lab5Controller.class);


    /**
     * @author: niu
     * @Description: 课程编号-课程名称-课程学分-教师编号-课程教师-操作
     * @Params: 接收一个学生学号
     */
    @RequestMapping("/getInfo")
    public Result getInfo() {
        try {
            String sid = "200800020101";

            List<StudentCourse> studentCourseList = mongoTemplate.find(
                    new Query().addCriteria(Criteria.where("sid").is(sid)), StudentCourse.class);

            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            // 获取所有课程信息
            List<Course> courseList = mongoTemplate.findAll(Course.class);

            List<TeacherCourse> teacherCourses = new ArrayList<>();
            for (Course c : courseList) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("课程编号", c.getCid());
                map.put("课程名称", c.getName());
                map.put("课程学分", Integer.toString(c.getCredit()));
                boolean selected = false;
                for (StudentCourse studentCourse:studentCourseList){
                    if (c.getCid().equals(studentCourse.getCid())) {
                        selected = true;
                        break;
                    }
                }
                if (selected){
                    map.put("selected",true);
                }else {
                    map.put("selected",false);
                }
                mapList.add(map);
            }

            for (int i = 0; i < courseList.size(); i++) {
                System.out.println("第" + i + "轮最外层循环");
                // 查询教师编号
                Query query1 = new Query();
                query1.addCriteria(Criteria.where("cid").is(courseList.get(i).getCid()));
                List<TeacherCourse> temp = mongoTemplate.find(query1, TeacherCourse.class);
                System.out.println("第" + i + "轮最外层循环查询到的TC" + temp.toString());
                if (temp.size() == 1) {
                    mapList.get(i).put("教师编号", temp.get(0).getTid());
                    Query query2 = new Query();
                    query2.addCriteria(Criteria.where("tid").is(temp.get(0).getTid()));
                    Teacher teacher1 = mongoTemplate.findOne(query2, Teacher.class);
                    assert teacher1 != null;
                    mapList.get(i).put("教师名称", teacher1.getName());
//                List<TeacherCourse> temp = mongoTemplate.find(query1,TeacherCourse.class);
                } else if (temp.size() > 1) {  // 当一个课程有多名老师教授的时候
                    mapList.get(i).put("教师编号", temp.get(0).getTid());
                    Query query2 = new Query();
                    query2.addCriteria(Criteria.where("tid").is(temp.get(0).getTid()));
                    Teacher teacher = mongoTemplate.findOne(query2, Teacher.class);
                    assert teacher != null;
                    mapList.get(i).put("教师名称", teacher.getName());
                    for (int j = 1; j < temp.size(); j++) {
                        Map<String, Object> mapT = new HashMap<String, Object>();
                        mapT.put("课程编号", courseList.get(i).getCid());
                        mapT.put("课程名称", courseList.get(i).getName());
                        mapT.put("课程学分", Integer.toString(courseList.get(i).getCredit()));
                        mapT.put("教师编号", temp.get(j).getTid());
                        Query query3 = new Query();
                        query3.addCriteria(Criteria.where("tid").is(temp.get(i).getTid()));
                        Teacher teacher2 = mongoTemplate.findOne(query3, Teacher.class);
                        assert teacher2 != null;
                        mapT.put("教师名称", teacher2.getName());
                        mapList.add(mapT);
                    }
                }

            }


            System.out.println("mapList" + mapList.toString());
//        logger.debug("以下mapList输出");
//        logger.debug(mapList);

            Map<String, Object> m = new HashMap<>();
            m.put("list", mapList);
            return Result.success().data(m);
        }catch (Exception e){
            logger.error("获取选课信息失败");
            logger.error(e.getMessage());
            return Result.error();
        }
    }

    @RequestMapping("/select")
    public Result selectCourse(String cid,String sid){
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("sid").is(sid));
            Update update = new Update();
            update.set("cid",cid);
            UpdateResult upsert = mongoTemplate.upsert(query, update, StudentCourse.class);
            System.out.println(upsert);
            return Result.success().message("选课成功");
        }catch (Exception e){
            logger.error("选课失败");
            logger.error(e.getMessage());
            return Result.error();
        }
    }
}
