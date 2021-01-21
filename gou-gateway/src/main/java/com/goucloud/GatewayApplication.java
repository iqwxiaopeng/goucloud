package com.goucloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 一.Zuul 包含了对请求路由和校验过滤两个最主要的功能：
 * 1.其中路由功能负责将外部请求转发到具体的微服务实例上，是实现外部访问统一入口的基础，
 * 客户端请求网关/api/product，通过路由转发到 product 服务
 * 客户端请求网关/api/order，通过路由转发到 order 服务
 * 2.而过滤功能则负责对请求的处理过程进行干预，是实现请求校验等功能的基础.
 * 二.Zuul 和 Eureka 进行整合，将 Zuul 自身注册为 Eureka 服务治理中的服务，同时从 Eureka 中获得其他微服
 * 务的消息，也即以后的访问微服务都是通过Zuul跳转后获得。
 */
@EnableZuulProxy //开启zuul的功能
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
