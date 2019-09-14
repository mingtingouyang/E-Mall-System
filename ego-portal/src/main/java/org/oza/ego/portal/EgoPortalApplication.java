package org.oza.ego.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient //开启发现实例支持
@EnableFeignClients //开启feign支持
public class EgoPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(EgoPortalApplication.class, args);
    }

}
