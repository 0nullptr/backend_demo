package com.demo.service;

import java.util.ArrayList;
import java.util.Calendar;
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

    public ArrayList<Number> getLastWeekSpendByStudentID(Long StudentID) {
        QueryWrapper<Buy> BuyWrapper = new QueryWrapper<>();
        BuyWrapper.eq("StudentID", StudentID);
        ArrayList<Number> lists = new ArrayList<>();

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = c1;
        c1.add(Calendar.DATE, -1);
        long spend = 0;
        List<Buy> buyList;
        for(int i = 0; i < 7; i++) {
            BuyWrapper.between("buyDate", c1.getTime(), c2.getTime());
            buyList = buyMapper.selectList(BuyWrapper);
            for (int j = 0; j < buyList.size(); j++) {
                spend += buyList.get(j).getDishValue().longValue();
            }
            lists.add(i, spend);

            spend = 0;
            c1.add(Calendar.DATE, -1);
            c2.add(Calendar.DATE, -1);
        }

        return lists;
    }
}
