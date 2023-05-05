package com.cyx.upload.service;

import com.cyx.common.utils.UUIDUtil;
import com.cyx.common.commonConstant.ImageConstant;
import com.cyx.upload.exception.ImageTimeOutException;
import com.cyx.upload.utils.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ImageService {
    @Autowired
    StringRedisTemplate redisTemplate;


    @Value("${minio.endpoint}")
    String endpoint;
    @Value("${minio.bucketName}")
    String bucketName;
    @Value("${minio.default}")
    String defaultImg;

    @Autowired
    MinioUtil minioUtil;

    public String save(MultipartFile photo, HttpSession session) {
        String path = minioUtil.uploadSingle(photo);
//        设置图片的缓存
        redisTemplate.opsForValue().set(ImageConstant.IMAGE_CACHE_HEAD + path, "1", ImageConstant.IMAGE_TIME_LIMIT);
        return ImageConstant.IMAGE_ACCESS_HEAD + path;
    }

    public String transform(MultipartFile file, HttpSession session) {
        // 给文件重命名
        String fileName = UUIDUtil.uuid() + "." + file.getContentType()
                .substring(file.getContentType().lastIndexOf("/") + 1);
        try {
            // 获取保存路径
            String path = getSavePath();
            File files = new File(path, fileName);
            File parentFile = files.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdir();
            }
            file.transferTo(files);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "http://127.0.0.1:8005/img/" + fileName;

    }

    public String getSavePath() {
        // 这里需要注意的是ApplicationHome是属于SpringBoot的类
        // 获取项目下resources/static/img路径
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());

        // 保存目录位置根据项目需求可随意更改
        return applicationHome.getDir().getParentFile()
                .getParentFile().getAbsolutePath() + "\\src\\main\\resources\\static\\img";
    }

    public boolean delete(String path) {
        String key = ImageConstant.IMAGE_CACHE_HEAD + path;
        Boolean res = redisTemplate.delete(key);
        //TODO 删除文件

        try {
            Files.delete(Paths.get(path));
        } catch (IOException e) {
            //文件过期被删除
            throw new ImageTimeOutException();
        }
        return res;
    }

    public boolean check(String bucketName, String path) {
        // 检查指定URL是否存在文件
        boolean exist = minioUtil.exist(bucketName, path);
        return exist;
    }

    public String defaultImg() {
        return endpoint+"/"+bucketName+"/"+defaultImg;
    }
}
