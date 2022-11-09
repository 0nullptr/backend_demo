package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.dao.entity.Parent;
import com.demo.dao.mapper.ParentMapper;

@SpringBootApplication
@Service
public class ParentService {
    @Autowired
    private ParentMapper parentMapper;
    
    public int isParentExist(Long ParentID) {
        QueryWrapper<Parent> parentWrapper = new QueryWrapper<>();
        parentWrapper.eq("ParentID", ParentID);
        int Count = parentMapper.selectCount(parentWrapper);
        return Count;
    }

    public void createParent(Long ParentID) {
        Parent parent = new Parent();
        parent.setParentID(ParentID);
        parentMapper.insert(parent);
    }
}
