package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import com.demo.dao.entity.Image;
import com.demo.dao.mapper.ImageMapper;

@SpringBootApplication
@Service
public class ImageService {
    @Autowired
    private ImageMapper imageMapper;

    public void insertImage(String Path, Long DishID) {
        Image image = new Image();
        image.setDishID(DishID)
                .setPath(Path);
        imageMapper.insert(image);
    }
}
