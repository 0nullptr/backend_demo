package com.demo.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("dish")
public class Dish implements Serializable{
    @TableId(type = IdType.AUTO)
    private Long DishID;
    private String DishName;
    private BigDecimal DishValue;

    private Long SchoolID;

    @TableField(exist = false)
    private int Times;
}
