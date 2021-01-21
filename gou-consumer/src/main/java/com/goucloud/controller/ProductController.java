package com.goucloud.controller;


import com.common.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 不使用Eureka也可以通过转掉服务接口实现调用其他服务 但是就必须知道服务地址
 */
@RestController
public class ProductController {
    //非Eureka版本 直接写死地址
    //private static final String REST_URL_PREFIX = "http://localhost:8001";

    //Eureka版本 修改为商品提供者向Eureka服务器中注册的地址
    private static final String REST_URL_PREFIX = "http://gou-provider";

    @Autowired //使用restful模板调用
    private RestTemplate restTemplate;

    @RequestMapping(value = "/consumer/product/add")
    public boolean add(Product product) {
        //(url, requestMap, ResponseBean.class)这三个参数分别代表：
        //REST请求地址、请求参数、HTTP响应转换被转换成的对象类型。
        return restTemplate.postForObject(REST_URL_PREFIX + "/product/add", product, Boolean.class);
    }

    @RequestMapping(value = "/consumer/product/get/{id}")
    public Product get(@PathVariable("id") Long id) {
        //注意 要求Product必须有个空构造方法,因为底层要反序列化这个对象
        return restTemplate.getForObject(REST_URL_PREFIX + "/product/get/" + id, Product.class);
    }

    @RequestMapping(value = "/consumer/product/list")
    public List<Product> list() {
        return restTemplate.getForObject(REST_URL_PREFIX + "/product/list", List.class);
    }


}