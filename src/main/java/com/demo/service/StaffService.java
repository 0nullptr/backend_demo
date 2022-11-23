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

    public int isStaffExist(Long StaffID) {
        QueryWrapper<Staff> staffWrapper = new QueryWrapper<>();
        staffWrapper.eq("StaffID", StaffID);
        int Count = staffMapper.selectCount(staffWrapper);
        return Count;
    }

    public void createStaff(Long StaffID) {
        Staff staff = new Staff();
        staff.setStaffID(StaffID);
        staffMapper.insert(staff);
    }
}
