package com.cyx.upload.controller;

import com.cyx.common.resp.RespBean;
import com.cyx.upload.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Api("文件上传相关")
@RestController
@RequestMapping("/upload/img")
public class ImageController {
    @Autowired
    ImageService imageService;

    @ApiOperation("保存图片到minio")
    @PostMapping("/save")
    public RespBean save(@RequestParam("photo") MultipartFile photo, HttpServletRequest request, HttpSession session) {
        String path = imageService.save(photo, session);
        return RespBean.success(path);
    }

    @ApiOperation("检查图片是否存在于minio")
    @GetMapping("/check")
    public RespBean check(String bucketName, String path) {
        boolean res = imageService.check(bucketName, path);
        return RespBean.success(res);
    }

    @ApiOperation("删除minio里的图片文件")
    @DeleteMapping("/delete")
    public RespBean delete(String path) {
        boolean res = imageService.delete(path);
        return RespBean.success(res);
    }

    //    获取默认图片
    @ApiOperation("获取默认图片的url")
    @GetMapping("/default")
    public RespBean defaultImg() {
        String res = imageService.defaultImg();
        return RespBean.success(res);
    }


}
