package com.goucloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;



/**
 * Feign通过接口的方法调用Rest服务（之前是Ribbon+RestTemplate），请求发送给 Eureka 服务器（http://MICR
 * OSERVICE-PRODUCT/product/list）, 通过Feign直接找到服务接口 ，因为集成了 Ribbon 技术，Feign 自带负载均
 * 衡配置功能。
 * 1、 启动类添加@EnableFeignClients注解，Spring会扫描标记了@FeignClient注解的接口，并生成此接口的代理
 * 对象
 * 2、 @FeignClient("服务名称 ") 即指定了 product 服务名称，Feign会从Eureka注册中心获取 product 服务列表，
 * 并通过负载均衡算法进行服务调用。
 * 3、在接口方法中使用注解 @RequestMapping(value = "/product/list",method = RequestMethod.GET)，指定调
 * 用的url，Feign 会根据url进行远程调用。
 *
 * Feign 注意事项
 * 1、@FeignClient接口方法有基本类型参数在参数必须加@PathVariable("XXX") 或 @RequestParam("XXX")
 * 2、@FeignClient接口方法返回值为复杂对象时，此类型必须有无参构造方法。
 */

//会扫描标记了指定包下@FeignClient注解的接口，并生成此接口的代理对象
@EnableFeignClients(basePackages = "com.goucloud.feign.service") //使用feign客户端负载均衡技术
@EnableEurekaClient //标识 是一个Eureka客户端 为了使用ribbon负载均衡
@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}
