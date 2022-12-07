package com.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.alibaba.fastjson2.JSONObject;
import com.demo.dao.entity.Box;
import com.demo.dao.entity.Buy;
import com.demo.dao.entity.Contain;
import com.demo.dao.entity.Dish;
import com.demo.dao.entity.Student;
import com.demo.dao.entity.Window;
import com.demo.service.BoxService;
import com.demo.service.BuyService;
import com.demo.service.ContainService;
import com.demo.service.DateService;
import com.demo.service.DishService;
import com.demo.service.StudentService;
import com.demo.service.WindowService;

@RestController
@SpringBootApplication
@CrossOrigin
public class BuyController {
    @Autowired
    private BoxService boxService;

    @Autowired
    private BuyService buyService;

    @Autowired
    private ContainService containService;

    @Autowired
    private DateService dateService;

    @Autowired
    private DishService dishService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private WindowService windowService;

    SseEmitter sseCache;

    Student studentCache;

    @GetMapping(value = "/buy/SSEInit")
    public SseEmitter SSEInit() {
        SseEmitter sseEmitter = new SseEmitter(5 * 60 * 1000L);
        sseCache = sseEmitter;
        return sseEmitter;
    }

    @RequestMapping(value = "/buy/studentArrive/{CardID}", method = RequestMethod.GET)
    public HashMap<String, Object> studentArrive(@PathVariable String CardID) {
        HashMap<String, Object> returns = new HashMap<>();
        String state = "SUCCESS";
        Student student = new Student();
        student = studentService.getStudentByCardIDAndSchoolID(CardID, (long) 1);
        do {
            if (student == null) {
                state = "FAIL";
                break;
            }
            HashMap<String, Object> json = new HashMap<>();
            json.put("state", 1);
            json.put("name", student.getStudentName());
            returns.put("name", student.getStudentName());
            try {
                sseCache.send(JSONObject.toJSONString(json));
                studentCache = student;
            } catch (IOException e) {
                e.printStackTrace();
                state = "ERROR";
            }
        } while (false);
        returns.put("STATE", state);
        return returns;
    }

    @RequestMapping(value = "/buy/selectDish/{PositionNumber}", method = RequestMethod.GET)
    public String selectDish(@PathVariable Long PositionNumber) {
        String state = "SUCCESS";
        Student student = studentCache;
        do {
            if (student == null) {
                state = "ERROR";
                break;
            }
            Box box = boxService.getBoxByPositionNumberAndWinodwID(PositionNumber, (long) 1);
            if (box == null) {
                state = "ERROR";
                break;
            }
            Date date = new Date();
            String dateString = dateService.DateToString(date);
            Contain contain = containService.getContainByBoxIDAndSellInfo(
                    box.getBoxID(),
                    dateString,
                    1);
            if (contain == null) {
                state = "ERROR";
                break;
            }
            Dish dish = dishService.getDish(contain.getDishID());
            if (dish == null) {
                state = "ERROR";
                break;
            }
            HashMap<String, Object> json = new HashMap<>();
            json.put("state", 2);
            json.put("value", dish.getDishValue());
            try {
                sseCache.send(JSONObject.toJSONString(json));
                buyService.insertBuy(
                        studentCache.getStudentID(),
                        contain.getContainID());
            } catch (IOException e) {
                e.printStackTrace();
                state = "FAIL";
            }
        } while (false);
        return state;
    }

    @RequestMapping(value = "/buy/getBoxNumber", method = RequestMethod.GET)
    public HashMap<String, Object> getBoxNumber() {
        HashMap<String, Object> json = new HashMap<>();
        List<Window> windowList = windowService.getWindowListBySchoolID((long) 1);
        if (windowList == null || windowList.size() == 0) {
            json.put("state", 0);
        } else {
            json.put("state", 1);
            Window window = windowList.get(0);
            json.put("number", window.getBoxCount());
        }
        return json;
    }

    @RequestMapping(value = "/buy/queryInfo", method = RequestMethod.POST)
    public ArrayList<Buy> queryInfo(@RequestBody String json) {
        JSONObject info = JSONObject.parseObject(json);
        String start = info.getString("start");
        String end = info.getString("end");
        Long StudentID = info.getLong("studentID");
        List<Contain> ContainList = containService.getContainsByTimeFrame(start, end);
        List<Long> ContainIDs = new ArrayList<>();
        HashMap<Long, Contain> hashMap = new HashMap<>();
        ContainList.forEach(item -> {
            ContainIDs.add(item.getContainID());
            hashMap.put(item.getContainID(), item);
        });
        List<Buy> buyList = buyService.getDishListByStudentIDAndContainIDs(StudentID, ContainList);
        ArrayList<Buy> buys = new ArrayList<>();
        buyList.forEach(item -> {
            Dish dish = dishService.getDish(hashMap.get(item.getContainID()).getDishID());
            item.setDishID(dish.getDishID())
                    .setDishName(dish.getDishName())
                    .setDishValue(dish.getDishValue())
                    .setSellDate(hashMap.get(item.getContainID()).getSellDate())
                    .setSellMeal(hashMap.get(item.getContainID()).getSellMeal());
            buys.add(item);
        });
        return buys;
    }

    @RequestMapping(value = "/buy/lastWeekSpend", method = RequestMethod.POST)
    public HashMap<String , Object> lastWeekSpend(@RequestBody String json) {
        HashMap<String, Object> returns = new HashMap<>();
        int state = 1;
        JSONObject jsonObject = JSONObject.parseObject(json);
        Long id = Long.parseLong(jsonObject.getString("id"));  // 学生id
        ArrayList<Number> lists = buyService.getLastWeekSpendByStudentID(id);
        returns.put("state", state);
        returns.put("lists", lists);
        return returns;
    }
}
