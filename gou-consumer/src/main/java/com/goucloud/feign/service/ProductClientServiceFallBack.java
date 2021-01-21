package com.goucloud.feign.service;

import com.common.entities.Product;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 基于feign 的熔断处理类
 */

@Component //一定要加上它,将它纳入到容器中
public class ProductClientServiceFallBack implements ProductClientService{


    @Override
    public boolean add(Product product) {
        return false;
    }

    @Override
    public Product get(Long id) {
        return new Product(id, "id=" + id + "无数据--@feignclient&hystrix", "无有效provider服务");
    }

    @Override
    public List<Product> list() {
        return null;
    }
}