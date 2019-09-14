package org.oza.ego.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer  //启动Eureka注册中心
public class EgoEureka1Application  {

    public static void main(String[] args) {
        SpringApplication.run(EgoEureka1Application.class, args);
    }

}
