package com.cyx.upload.listener;

import com.cyx.common.commonConstant.ImageConstant;
import com.cyx.upload.utils.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

// TODO 监听redis暂存图片的key被删除的事件
@Component
public class KeyExpirationEventListener extends KeyExpirationEventMessageListener {

    @Value("${minio.bucketName}")
    private String bucketName;

    @Autowired
    MinioUtil minioUtil;
    public KeyExpirationEventListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = message.toString();
        if(key.startsWith(ImageConstant.IMAGE_CACHE_HEAD)){
            String path=key.substring(ImageConstant.IMAGE_CACHE_HEAD.length());
            minioUtil.removeSingle(bucketName, path);
        }
    }
}
