package com.demo.controller;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dao.entity.Dish;
import com.demo.service.DishService;

@RestController
@SpringBootApplication
@CrossOrigin
public class DishController {
    @Autowired
    private DishService dishService;

    public HashMap<String, Object> getAllInfo(HttpCookie httpCookie) {
        HashMap<String, Object> returns = new HashMap<>();
        int state = 1;
        Long UnionID = Long.valueOf(httpCookie.getValue());
        List<Dish> DishList = dishService.getDishInfo(UnionID);
        returns.put("state", state);
        returns.put("list", DishList);
        return returns;
    }

    public HashMap<String, Object> getDishValue(HttpCookie httpCookie, String Name) {
        HashMap<String, Object> returns = new HashMap<>();
        int state = 1;
        Long UnionID = Long.valueOf(httpCookie.getValue());
        Dish dish = dishService.getDishValueByName(UnionID, Name);
        returns.put("state", state);
        returns.put("dish", dish);
        return returns;
    }

    public int updateDishValue(HttpCookie httpCookie, Dish dish) {
        int state = 1;
        Long UnionID = Long.valueOf(httpCookie.getValue());
        dishService.updateDishValue(UnionID, dish);

        return state;
    }
}
