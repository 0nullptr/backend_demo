package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.dao.entity.Student;
import com.demo.dao.mapper.StudentMapper;

@SpringBootApplication
@Service
public class StudentService {
    @Autowired
    private StudentMapper studentMapper;

    public Student getStudentByCardIDAndSchoolID(String CardID, Long SchoolID) {
        QueryWrapper<Student> studentWrapper = new QueryWrapper<>();
        studentWrapper
                .eq("CardID", CardID)
                .eq("SchoolID", SchoolID);
        Student student = studentMapper.selectOne(studentWrapper);
        return student;
    }
}
