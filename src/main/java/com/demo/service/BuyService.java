package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.dao.entity.Buy;
import com.demo.dao.entity.Contain;
import com.demo.dao.mapper.BuyMapper;

@SpringBootApplication
@Service
public class BuyService {
    @Autowired
    private BuyMapper buyMapper;

    public void insertBuy(Long studentID, Long containID) {
        Buy buy = new Buy();
        buy.setStudentID(studentID)
                .setContainID(containID);
        buyMapper.insert(buy);
    }

    public List<Buy> getDishListByStudentIDAndContainIDs(Long StudentID, List<Contain> containIDs) {
        QueryWrapper<Buy> BuyWrapper = new QueryWrapper<>();
        BuyWrapper.eq("StudentID", StudentID).in("ContainID", containIDs);
        List<Buy> buyList = buyMapper.selectList(BuyWrapper);
        return buyList;
    }
}
