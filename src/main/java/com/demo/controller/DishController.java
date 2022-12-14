package com.demo.controller;

import java.math.BigDecimal;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONObject;
import com.demo.dao.entity.Dish;
import com.demo.service.DishService;
import com.demo.service.SchoolService;

@RestController
@SpringBootApplication
@CrossOrigin
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private SchoolService schoolService;
    
    @RequestMapping(value = "/dish/geyAllInfo", method = RequestMethod.POST)
    public HashMap<String, Object> getAllInfo(HttpCookie httpCookie) {
        HashMap<String, Object> returns = new HashMap<>();
        int state = 1;
        Long UnionID = Long.valueOf(httpCookie.getValue());
        Long SchoolID = schoolService.getSchoolIDByStuffID(UnionID);
        List<Dish> DishList = dishService.getDishInfo(SchoolID);
        returns.put("state", state);
        returns.put("list", DishList);
        return returns;
    }

    @RequestMapping(value = "/dish/getDishValue", method = RequestMethod.POST)
    public HashMap<String, Object> getDishValue(HttpCookie httpCookie, @RequestBody String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        String Name = jsonObject.getString("name");  // 菜品名

        HashMap<String, Object> returns = new HashMap<>();
        int state = 1;
        Long UnionID = Long.valueOf(httpCookie.getValue());
        Long SchoolID = schoolService.getSchoolIDByStuffID(UnionID);
        Dish dish = dishService.getDishValueByName(SchoolID, Name);
        returns.put("state", state);
        returns.put("dish", dish);
        return returns;
    }

    @RequestMapping(value = "/dish/updateDishValue", method = RequestMethod.POST)
    public int updateDishValue(HttpCookie httpCookie, @RequestBody String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        Integer id = jsonObject.getInteger("id");  // 菜品id
        String name = jsonObject.getString("name");  // 菜品名
        Number value = jsonObject.getDouble("value");  // 菜品价格

        Dish dish = new Dish();
        dish.setDishID(id.longValue())
                .setDishName(name)
                .setDishValue((BigDecimal)value);

        int state = 1;
        Long UnionID = Long.valueOf(httpCookie.getValue());
        Long SchoolID = schoolService.getSchoolIDByStuffID(UnionID);
        dishService.updateDishValue(SchoolID, dish);

        return state;
    }
} 
