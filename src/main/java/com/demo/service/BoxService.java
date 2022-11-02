package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.dao.entity.Box;
import com.demo.dao.mapper.BoxMapper;

@SpringBootApplication
@Service
public class BoxService {
    @Autowired
    private BoxMapper boxMapper;

    public Box getBoxByPositionNumberAndWinodwID(Long PositionNumber, Long WindowID) {
        QueryWrapper<Box> boxWrapper = new QueryWrapper<>();
        boxWrapper
                .eq("PositionNumber", PositionNumber)
                .eq("WindowID", WindowID);
        Box box = boxMapper.selectOne(boxWrapper);
        return box;
    }
}
