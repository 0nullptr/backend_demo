package com.demo.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

@SpringBootApplication
@Service
public class DateService {
    public String DateToString(Date date) {
        String string = null;
        try {
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
            string = parser.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }
}
