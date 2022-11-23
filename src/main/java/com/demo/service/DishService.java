package com.demo.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.dao.entity.Dish;
import com.demo.dao.mapper.DishMapper;

@SpringBootApplication
@Service
public class DishService {
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private ContainService containService;

    @Autowired
    private SchoolService schoolService;

    public Dish getDish(Long DishID) {
        QueryWrapper<Dish> dishWrapper = new QueryWrapper<>();
        dishWrapper.eq("DishID", DishID);
        Dish dish = dishMapper.selectOne(dishWrapper);
        return dish;
    }

    public void insertDish(Dish dish) {
        dishMapper.insert(dish);
    }

    public Dish getDishExceptDishID(String dishName, BigDecimal dishValue, Long SchoolID) {
        QueryWrapper<Dish> dishWrapper = new QueryWrapper<>();
        dishWrapper
                .eq("dishName", dishName)
                .eq("dishValue", dishValue)
                .eq("SchoolID", SchoolID);
        Dish dish = dishMapper.selectOne(dishWrapper);
        return dish;
    }

    public void updateDishInfo(Long BoxID, String dishName, BigDecimal dishValue) {
        Dish dish = new Dish();
        dish.setDishName(dishName)
                .setDishValue(dishValue)
                .setSchoolID((long) 1);
        Dish resDish = getDishExceptDishID(dishName, dishValue, (long)1);
        if (resDish == null) {
            insertDish(dish);
            resDish = getDishExceptDishID(dishName, dishValue, (long)1);
        }
        dish.setDishID(resDish.getDishID());
        containService.insertContain(dish, BoxID, new Date(), 1);
    }

    public List<Dish> getDishInfo(Long UnionID) {
        Long SchoolID = schoolService.getSchoolIDByStuffID(UnionID);
        QueryWrapper<Dish> dishWrapper = new QueryWrapper<>();
        dishWrapper.eq("SchoolID", SchoolID);
        List<Dish> DishList = dishMapper.selectList(dishWrapper);
        return DishList;
    }

    public Dish getDishValueByName(Long UnionID, String name) {
        Long SchoolID = schoolService.getSchoolIDByStuffID(UnionID);
        QueryWrapper<Dish> dishWrapper = new QueryWrapper<>();
        dishWrapper.eq("SchoolID", SchoolID);
        dishWrapper.eq("DishName", name);
        dishWrapper.orderByDesc("DishID");
        Dish dish = dishMapper.selectOne(dishWrapper);
        return dish;
    }

    public void updateDishValue(Long UnionID, Dish dish) {
        Long SchoolID = schoolService.getSchoolIDByStuffID(UnionID);
        dish.setSchoolID(SchoolID);
        insertDish(dish);
    }
}

