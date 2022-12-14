package com.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.service.ImageService;

@RestController
@CrossOrigin
@SpringBootApplication
public class ImageController {
    @Value("${file.save.path}")
    String fileSavePath;

    @Autowired
    private ImageService imageService;

    @RequestMapping("/image/upImgs")
    public String upImgs(HttpServletRequest request,
            @RequestParam("file") MultipartFile imageFile,
            @RequestParam("desc") String desc,
            @RequestParam("index") Long index) throws IOException {
        System.out.println(index);
        File fir = new File(fileSavePath);
        if (!fir.exists()) {
            fir.mkdirs();
        }
        String originalFilename = imageFile.getOriginalFilename();
        if (originalFilename == null) {
            return "fail";
        }
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
        File newFile = new File(fileSavePath + newFileName);
        System.out.println(newFileName);
        imageService.insertImage(newFileName, index);
        imageFile.transferTo(newFile);
        // url路径=http + :// + server名字 + port端口 + /images/ + newFileName
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/images/"
                + newFileName;
        return url;
    }
}
