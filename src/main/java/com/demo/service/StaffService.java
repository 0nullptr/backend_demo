package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.dao.entity.Staff;
import com.demo.dao.mapper.StaffMapper;

@SpringBootApplication
@Service
public class StaffService {
    @Autowired
    private StaffMapper staffMapper;

    public boolean isStaffExist(String UnionID) {
        QueryWrapper<Staff> staffWrapper = new QueryWrapper<>();
        staffWrapper.eq("UnionID", UnionID);
        int Count = staffMapper.selectCount(staffWrapper);
        return Count == 0 ? false : true;
    }

    public void createStaff(String UnionID) {
        Staff staff = new Staff();
        staff.setUnionID(UnionID);
        staff.setSchoolID((long)1);
        staffMapper.insert(staff);
    }

    public Long getStaffIDByUnionID(String UnionID) {
        QueryWrapper<Staff> staffWrapper = new QueryWrapper<>();
        staffWrapper.eq("UnionID", UnionID);
        Staff staff = staffMapper.selectOne(staffWrapper);
        return staff.getStaffID();
    }

    public Long getSchoolIDByUnionID(String UnionID) {
        QueryWrapper<Staff> staffWrapper = new QueryWrapper<>();
        staffWrapper.eq("UnionID", UnionID);
        Staff staff = staffMapper.selectOne(staffWrapper);
        return staff.getSchoolID();
    }
}
