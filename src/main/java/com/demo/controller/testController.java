package com.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController
@SpringBootApplication
@CrossOrigin
public class testController {
    @RequestMapping(value="/setMsg", method=RequestMethod.GET)
    public String test1(HttpSession session, String msg) {
        session.setAttribute("msg", msg);
        return "ok";
    }

    @RequestMapping(value="/getMsg", method=RequestMethod.GET)
    public String test2(HttpSession session) {
        String msg = (String) session.getAttribute("msg");
        return msg;
    }
}
