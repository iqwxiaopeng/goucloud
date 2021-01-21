package com.goucloud.feign.service;


import com.common.entities.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/** 这个包下都是演示要使用feign客户端负载均衡需要做的内容
 */
//@FeignClient(value = "gou-provider") //指定调用的微服务名称 就是创建了一个代理对象来访问
//客户端熔断是解决服务器故障比如停机之类的无法返回问题  服务器熔断是为了解决服务器出错拥塞问题
@FeignClient(value = "gou-provider", fallback = ProductClientServiceFallBack.class) //这个是演示使用feign的客户端熔断 参数fallback是熔断类
public interface ProductClientService {

    @RequestMapping(value = "/product/add", method = RequestMethod.POST)
    boolean add(@RequestBody Product product);

    @RequestMapping(value = "/product/get/{id}", method = RequestMethod.GET)
    Product get(@PathVariable("id") Long id);

    @RequestMapping(value = "/product/list", method = RequestMethod.GET)
    List<Product> list();

}
