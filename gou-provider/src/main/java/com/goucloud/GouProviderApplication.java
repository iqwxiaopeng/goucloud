package com.goucloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;


@EnableHystrix //开启Hystrix的熔断机制  这里展示的是服务器的熔断
//@EnableEurekaClient 注解，表示它是一个Eureka的客户端，本服务启动后会自动注册进Eureka Sever服务列表中
@EnableEurekaClient //将此服务注册到Eureka 服务注册中心
@MapperScan("com.goucloud.mapper") //扫描包下面所有Mapper接口
@SpringBootApplication
public class GouProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(GouProviderApplication.class, args);
    }

}