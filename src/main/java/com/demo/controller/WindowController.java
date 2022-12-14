package com.demo.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.demo.dao.entity.Window;
import com.demo.service.DishService;
import com.demo.service.WindowService;
import com.demo.service.StaffService;

@RestController
@SpringBootApplication
@CrossOrigin
public class WindowController {
    @Autowired
    private DishService dishService;

    @Autowired
    private WindowService windowService;

    @Autowired
    private StaffService staffService;

    @RequestMapping(value = "/window/getWindowsInfo", method = RequestMethod.POST)
    public List<Window> getWindowsInfo(@CookieValue(value = "unionid", required = false) String UnionID) {
        Long SchoolID = staffService.getSchoolIDByUnionID(UnionID);
        List<Window> windowList = windowService.getWindowListBySchoolID(SchoolID);
        return windowList;
    }

    @RequestMapping(value = "/window/updateDishInfo", method = RequestMethod.POST)
    public String updateDishInfo(@RequestBody String json) {
        boolean success = true;
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONArray NowWindows = JSONArray.parseArray(jsonObject.getString("dishWindows"));
        for (int i = 0; i < NowWindows.size(); i++) {
            JSONObject WindowsInfo = NowWindows.getJSONObject(i);
            JSONArray NowBox = WindowsInfo.getJSONArray("inTable");
            for (int j = 0; j < NowBox.size(); j++) {
                JSONObject box = NowBox.getJSONObject(j);
                Long BoxID = box.getLong("id");
                JSONObject dish = box.getJSONObject("dish");
                String dishName = dish.getString("name");
                BigDecimal dishValue = dish.getBigDecimal("value");
                dishService.updateDishInfo(BoxID, dishName, dishValue);
            }
        }
        if (!success) {
            return "FAIL";
        }
        return "SUCCESS";
    }
}
