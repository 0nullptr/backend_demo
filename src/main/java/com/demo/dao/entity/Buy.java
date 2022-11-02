package com.demo.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("buy")
public class Buy implements Serializable{
    @TableId(type = IdType.AUTO)
    private Long BuyID;

    private Long StudentID;

    private Long ContainID;

    @TableField(exist = false)
    private Long DishID;
    @TableField(exist = false)
    private String DishName;
    @TableField(exist = false)
    private BigDecimal DishValue;

    @TableField(exist = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date SellDate;
    @TableField(exist = false)
    private Integer SellMeal;
}
