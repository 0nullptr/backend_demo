package com.demo.service;

import java.util.List;

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

    public Box getBoxByPositionNumberAndWindowID(Long PositionNumber, Long WindowID) {
        QueryWrapper<Box> boxWrapper = new QueryWrapper<>();
        boxWrapper
                .eq("PositionNumber", PositionNumber)
                .eq("WindowID", WindowID);
        List<Box> BoxList = boxMapper.selectList(boxWrapper);
        Box box;
        if (BoxList.size() == 0) {
            box = null;
        } else {
            box = BoxList.get(BoxList.size() - 1);
        }
        return box;
    }
}
