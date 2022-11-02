package com.demo.dao.entity;

import java.io.Serializable;

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
@TableName("student")
public class Student implements Serializable{
    @TableId
    private Long StudentID;
    private String StudentName;
    private String CardID;

    private Long SchoolID;
    @TableField(exist = false)
    private String SchoolName;

    private Long ParentID;
}
