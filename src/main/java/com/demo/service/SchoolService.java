package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.dao.entity.School;
import com.demo.dao.mapper.SchoolMapper;

@SpringBootApplication
@Service
public class SchoolService {
    @Autowired
    private SchoolMapper schoolMapper;

    public Long getSchoolIDByStuffID(Long StuffID) {
        QueryWrapper<School> schoolWrapper = new QueryWrapper<>();
        schoolWrapper.eq("StuffID", StuffID);
        School School = schoolMapper.selectOne(schoolWrapper);
        Long SchoolID = School.getSchoolID();
        return SchoolID;
    }
}
