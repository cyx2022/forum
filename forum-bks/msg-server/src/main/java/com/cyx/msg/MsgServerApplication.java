package com.cyx.msg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class MsgServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsgServerApplication.class, args);
    }

}
