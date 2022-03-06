package com.example.dbmanege.controller;

import com.example.dbmanege.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;

/**
 * @author niu
 * @Description:
 * @date 2021/10/2019:50
 */


@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private MongoTemplate mongoTemplate;


    @RequestMapping("/queue")
    public String queue(){
        return mongoTemplate.findAll(Student.class).toString();
    }

    @RequestMapping("/find")
    public String find(){
        Query query = new Query(Criteria.where("name").is("WangXin"));
        Student student =mongoTemplate.findOne(query, Student.class);
//        System.out.println(student.getName());
        assert student != null;
        return student.toString();
    }

    @RequestMapping("/httpServletRequest")
    public String test1( HttpServletRequest request){
        System.out.println("Request URL :" + request.getRequestURI());
        System.out.println("Request URL :" + request.getProtocol());
        return request.toString();


    }


}
