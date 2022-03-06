package com.example.dbmanege.controller;

import com.example.dbmanege.entity.Course;
import com.example.dbmanege.entity.Student;
import com.example.dbmanege.entity.StudentCourse;
import com.example.dbmanege.result.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author niu
 * @Description:
 * @date 2021/11/2110:50
 */

@RestController
@RequestMapping("/lab6")
public class Lab6Controller {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final Logger logger = LogManager.getLogger(Lab6Controller.class);


    @RequestMapping("/first")
    public Result first(){
        try {
            Query query = new Query();
            query.fields().include("cid");
            List<String> list = mongoTemplate.findDistinct("cid", StudentCourse.class,String.class);
            List<Course> courseList = new ArrayList<>();
            List<String> resultList = new ArrayList<>();
            for (String l :list){
                Query query1 = new Query();
                query1.addCriteria(Criteria.where("cid").is(l));
                query1.fields().include("name");
                courseList.add(mongoTemplate.findOne(query1, Course.class));
            }

            for (Course course:courseList){
                resultList.add(course.getName());
            }
            Map m = new HashMap<>();
            m.put("list",resultList);
            return Result.success().data(m);
//            query.addCriteria();
        }catch (Exception e){
            logger.error("first失败");
            logger.error(e.getMessage());
            return Result.error();
        }
    }

    @RequestMapping("/second")
    public Result second(){
        // 为啥平均值的显示不出来呢?
        //破案了,因为字段是string类型，所以排序的时候按照string类型来排了
        try {

            List<String> list = mongoTemplate.findDistinct("cid", StudentCourse.class,String.class);
            List<Course> courseList = new ArrayList<>();
            List<String> resultList = new ArrayList<>();

            Aggregation agg = Aggregation.newAggregation(
                    Aggregation.group("sid").avg("score").as("avg"),
                    Aggregation.sort(Sort.Direction.DESC,"avg"),//排序
                    Aggregation.limit(10)
            );

            AggregationResults<Object> outputType=mongoTemplate.aggregate(agg,"student_course",Object.class);
            List<Object> results=outputType.getMappedResults();

            System.out.println(mongoTemplate.aggregate(agg,"student_course",Object.class).getMappedResults());
            Map m = new HashMap<>();
            m.put("list",results);
            return Result.success().data(m);
//            query.addCriteria();
        }catch (Exception e){
            logger.error("second失败");
            logger.error(e.getMessage());
            return Result.error();
        }
    }

    @RequestMapping("/third")
    public Result third(){


        try {
            Aggregation agg = Aggregation.newAggregation(
                    Aggregation.group("sid").count().as("count"),
                    Aggregation.sort(Sort.Direction.DESC,"count"),//排序
                    Aggregation.limit(10)
            );
            AggregationResults<Object> outputType=mongoTemplate.aggregate(agg,"student_course",Object.class);
            List<Object> results=outputType.getMappedResults();

            System.out.println(mongoTemplate.aggregate(agg,"student_course",Object.class).getMappedResults());
            Map m = new HashMap<>();
            m.put("list",results);
            return Result.success().data(m);
//            query.addCriteria();
        }catch (Exception e){
            logger.error("third失败");
            logger.error(e.getMessage());
            return Result.error();
        }
    }

    @RequestMapping("/forth")
    public Result forth(){


        try {

            Aggregation agg = Aggregation.newAggregation(
                    Aggregation.group("sid").max("score").as("maxScore"),
                    Aggregation.sort(Sort.Direction.DESC,"maxScore")
            );
            AggregationResults<Object> outputType=mongoTemplate.aggregate(agg,"student_course",Object.class);
            List<Object> results=outputType.getMappedResults();

            for (Object obj : results){
                Query query = new Query();
                Criteria criteria = new Criteria();
                criteria.andOperator(
                        Criteria.where("sid").is(((Map)obj).get("_id")),
                        Criteria.where("score").is(((Map)obj).get("maxScore"))
                );
                query.addCriteria(criteria);
                StudentCourse sc = mongoTemplate.findOne(query,StudentCourse.class);
                System.out.println(sc);
                assert sc != null;
                ((Map)obj).put("cid",sc.getCid());

                Query query1 = new Query();
                query1.addCriteria(Criteria.where("cid").is(sc.getCid()));

                Course course = mongoTemplate.findOne(query1,Course.class);
                assert course !=null;
                ((Map)obj).put("name",course.getName());
            }



            System.out.println(mongoTemplate.aggregate(agg,"student_course",Object.class).getMappedResults());
            Map m = new HashMap<>();
            m.put("list",results);
            return Result.success().data(m);
//            query.addCriteria();
        }catch (Exception e){
            logger.error("forth失败");
            logger.error(e.getMessage());
            return Result.error();
        }
    }

}
