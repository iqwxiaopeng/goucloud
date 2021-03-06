package com.goucloud.controller;

import com.common.entities.Product;
import com.goucloud.service.ProductService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RefreshScope //刷新配置 可以动态更新配置
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/product/add", method = RequestMethod.POST)
    public boolean add(@RequestBody Product product) {
        return productService.add(product);
    }

    //fallbackMethod 指定当get方法出现异常时,将要调用的处理方法
    //处理方法的返回值和参数类型要一致
    @HystrixCommand(fallbackMethod = "getFallback")
    @RequestMapping(value = "/product/get/{id}", method = RequestMethod.GET)
    public Product get(@PathVariable("id") Long id) {
        Product product = productService.get(id);
        //如果product为空,则模拟一个异常,
        if(product == null) {
            throw new RuntimeException("ID=" + id + "无效");
        }
        return product;
    }

    public Product getFallback(@PathVariable("id") Long id) {
        return new Product(id, "ID=" + id +"无效----@HystrixCommand",
                "熔断机制返回:无法找到对应数据库");
    }


    @RequestMapping(value = "/product/list", method = RequestMethod.GET)
    public List<Product> list() {
        return productService.list();
    }

    //以下代码测试动态配置更新
    @Value("${emp.name}")
    private String name;
    @GetMapping("/hello")
    public String hello() {
        return name;
    }

}
