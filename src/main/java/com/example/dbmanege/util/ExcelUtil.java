package com.example.dbmanege.util;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.example.dbmanege.entity.Course;
import com.example.dbmanege.entity.Student;
import com.example.dbmanege.entity.Teacher;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @author niu
 * @Description:
 * @date 2021/11/1315:13
 */


@Component
public class ExcelUtil {
    public List<Student> importStudentExcel(String path){
        ImportParams params = new ImportParams();
        List<Student> list = ExcelImportUtil.importExcel(new File(path),
                Student.class, params);

        return list;
//        System.out.println(list);
//        System.out.println(list.size());
    }

    public List<Teacher> importTeacherExcel(String path){
        ImportParams params = new ImportParams();
        List<Teacher> list = ExcelImportUtil.importExcel(new File(path),
                Teacher.class, params);
        return list;
    }

    public List<Course> importCourseExcel(String path){
        ImportParams params = new ImportParams();
        List<Course> list = ExcelImportUtil.importExcel(new File(path),
                Course.class, params);
        return list;
    }

    public static void main(String[] args) {
        new ExcelUtil().importStudentExcel("D:\\大学\\大三上\\非关系型数据库\\实验\\test.xlsx");
    }
}



