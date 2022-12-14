package com.demo.controller;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dao.entity.Student;
import com.demo.service.StudentService;

@CrossOrigin
@SpringBootApplication
@RestController
public class ParentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/parent/getStudentInfo", method = RequestMethod.POST)
    public HashMap<String, Object> getStudentInfo(HttpCookie httpCookie) {
        HashMap<String, Object> returns = new HashMap<>();
        int state = 1;
        Long UnionID = Long.valueOf(httpCookie.getValue());
        List<Student> StudentList = studentService.getStudentListByParentID(UnionID);
        returns.put("state", state);
        returns.put("list", StudentList);
        return returns;
    }
}
