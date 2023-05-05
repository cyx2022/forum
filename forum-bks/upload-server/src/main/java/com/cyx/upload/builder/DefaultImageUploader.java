package com.cyx.upload.builder;

import com.cyx.upload.utils.MinioUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;

//上传默认图片
@Component
public class DefaultImageUploader implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    MinioUtil minioUtil;

    @Value("${minio.default}")
    String defaultImg;
    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        File file= ResourceUtils.getFile("classpath:static/img/default.jpg");
//        File file = new File("src/main/resources/static/img/default.jpg");
        byte[] bytes= Files.readAllBytes(file.toPath());
        MultipartFile multipartFile=new MockMultipartFile(file.getName(),defaultImg,"image/jpg", bytes);
        minioUtil.uploadDefault(multipartFile);
        System.out.println("默认图片上传成功");
    }
}
