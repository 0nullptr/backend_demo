package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.dao.entity.Window;
import com.demo.dao.mapper.WindowMapper;

@SpringBootApplication
@Service
public class WindowService {
    @Autowired
    private WindowMapper windowMapper;

    public List<Window> getWindowListBySchoolID(Long SchoolID) {
        QueryWrapper<Window> windowWrapper = new QueryWrapper<>();
        windowWrapper.eq("SchoolID", SchoolID);
        List<Window> WindowList = windowMapper.selectList(windowWrapper);
        return WindowList;
    }
}
