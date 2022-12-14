package com.demo.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.dao.entity.Contain;
import com.demo.dao.entity.Dish;
import com.demo.dao.mapper.ContainMapper;
import com.demo.dao.mapper.DishMapper;

@SpringBootApplication
@Service
public class DishService {
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private ContainMapper containMapper;

    @Autowired
    private DateService dateService;

    @Autowired
    private ContainService containService;

    public Dish getDish(Long DishID) {
        QueryWrapper<Dish> dishWrapper = new QueryWrapper<>();
        dishWrapper.eq("DishID", DishID);
        List<Dish> dishList = dishMapper.selectList(dishWrapper);
        Dish dish;
        if (dishList.size() == 0) {
            dish = null;
        } else {
            dish = dishList.get(dishList.size() - 1);
        }
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
        List<Dish> dishList = dishMapper.selectList(dishWrapper);
        Dish dish;
        if (dishList.size() == 0) {
            dish = null;
        } else {
            dish = dishList.get(dishList.size() - 1);
        }
        return dish;
    }

    public void updateDishInfo(Long BoxID, String dishName, BigDecimal dishValue) {
        Dish dish = new Dish();
        dish.setDishName(dishName)
                .setDishValue(dishValue)
                .setSchoolID((long) 1);
        Dish resDish = getDishExceptDishID(dishName, dishValue, (long) 1);
        if (resDish == null) {
            insertDish(dish);
            resDish = getDishExceptDishID(dishName, dishValue, (long) 1);
        }
        dish.setDishID(resDish.getDishID());
        containService.insertContain(dish, BoxID, new Date(), 1);
    }

    public List<Dish> getDishInfo(Long SchoolID) {
        QueryWrapper<Dish> dishWrapper = new QueryWrapper<>();
        dishWrapper.eq("SchoolID", SchoolID);
        List<Dish> DishList = dishMapper.selectList(dishWrapper);
        for (int i = 0; i < DishList.size(); i++) {
            Long DishID = DishList.get(i).getDishID();
            DishList.get(i).setTimes(getDishTimesByDishID(DishID));
        }
        return DishList;
    }

    public Dish getDishValueByName(Long SchoolID, String name) {
        QueryWrapper<Dish> dishWrapper = new QueryWrapper<>();
        dishWrapper.eq("SchoolID", SchoolID);
        dishWrapper.eq("DishName", name);
        List<Dish> DishList = dishMapper.selectList(dishWrapper);
        Dish dish;
        if (DishList.size() > 0) {
            dish = DishList.get(DishList.size() - 1);
        } else {
            dish = new Dish();
        }
        return dish;
    }

    public void updateDishValue(Long SchoolID, Dish dish) {
        dish.setSchoolID(SchoolID);
        insertDish(dish);
    }

    public int getDishTimesByDishID(Long DishID) {
        int times;
        Date dateNow = new Date();
        Date dateFormer = new Date(dateNow.getTime() - 7 * 24 * 60 * 60 * 1000);

        QueryWrapper<Contain> containWrapper = new QueryWrapper<>();
        containWrapper.eq("DishID", DishID);
        containWrapper.between(
                "SellTime",
                dateService.DateToString(dateFormer),
                dateService.DateToString(dateNow));
        times = containMapper.selectCount(containWrapper);
        return times;
    }
}
