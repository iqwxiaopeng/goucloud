package com.goucloud.feign.controller;


import com.common.entities.Product;
import com.goucloud.feign.service.ProductClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ProductController_Feign {

   @Autowired
   ProductClientService service;

    @RequestMapping(value = "/feignconsumer/product/add")
    public boolean add(Product product) {
        return service.add(product);
    }

    @RequestMapping(value = "/feignconsumer/product/get/{id}")
    public Product get(@PathVariable("id") Long id) {
        return service.get(id);
    }

    @RequestMapping(value = "/feignconsumer/product/list")
    public List<Product> list() {
        return service.list();
    }



}
