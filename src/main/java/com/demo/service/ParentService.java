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

    public boolean isParentExist(String UnionID) {
        QueryWrapper<Parent> parentWrapper = new QueryWrapper<>();
        parentWrapper.eq("UnionID", UnionID);
        int Count = parentMapper.selectCount(parentWrapper);
        return Count == 0 ? false : true;
    }

    public void createParent(String UnionID) {
        Parent parent = new Parent();
        parent.setUnionID(UnionID);
        parentMapper.insert(parent);
    }
}
