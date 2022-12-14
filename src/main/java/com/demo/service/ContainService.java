package com.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.dao.entity.Contain;
import com.demo.dao.entity.Dish;
import com.demo.dao.mapper.ContainMapper;

@SpringBootApplication
@Service
public class ContainService {
    @Autowired
    private ContainMapper containMapper;

    public Contain getContainByBoxIDAndSellInfo(Long boxID, String SellDate, Integer SellMeal) {
        QueryWrapper<Contain> containWrapper = new QueryWrapper<>();
        containWrapper
                .eq("BoxID", boxID)
                .eq("SellDate", SellDate)
                .eq("SellMeal", SellMeal);
        List<Contain> containList = containMapper.selectList(containWrapper);
        Contain contain;
        if (containList.size() == 0) {
            contain = null;
        } else {
            contain = containList.get(containList.size() - 1);
        }
        return contain;
    }

    public List<Contain> getContainsByTimeFrame(String start, String end) {
        QueryWrapper<Contain> ContainWrapper = new QueryWrapper<>();
        ContainWrapper.between("SellDate", start, end);
        List<Contain> ContainList = containMapper.selectList(ContainWrapper);
        return ContainList;
    }

    public void insertContain(Dish dish, Long BoxID, Date SellDate, Integer SellMeal) {
        Contain contain = new Contain();
        contain.setDishID(dish.getDishID())
                .setDishName(dish.getDishName())
                .setDishValue(dish.getDishValue())
                .setSellDate(SellDate)
                .setSellMeal(SellMeal)
                .setBoxID(BoxID);
        containMapper.insert(contain);
    }
}
