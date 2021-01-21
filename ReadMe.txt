要启动服务需要先将 cloudconfig.zip拷贝到E:\java_work\cloudconfig\

在mysql(用户名root，密码root)中建表

DROP DATABASE IF EXISTS springcloud_db01;
CREATE DATABASE springcloud_db01 CHARACTER SET UTF8;
USE springcloud_db01;
CREATE TABLE product
(
pid BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
product_name VARCHAR(50),
db_source VARCHAR(50)
);
INSERT INTO product(product_name,db_source) VALUES('格力空调',DATABASE());
INSERT INTO product(product_name,db_source) VALUES('海尔冰箱',DATABASE());
INSERT INTO product(product_name,db_source) VALUES('小短裙',DATABASE());
INSERT INTO product(product_name,db_source) VALUES('羽绒服',DATABASE());
INSERT INTO product(product_name,db_source) VALUES('韩版休闲鞋',DATABASE());
INSERT INTO product(product_name,db_source) VALUES('高贵鞋',DATABASE());
SELECT * FROM product;



1. 服务启动顺序
   gou-config-->gou-register-->gou-gateway-->gou-provider-->gou-consumer

   gou-config : 统一管理配置文件 端口号为5001 访问 http://localhost:5001/gou-register-dev.yml 就可以获取到 E:\java_work\cloudconfig\gou-register.yml
   文件中 dev块配置信息

   gou-register: 服务注册与发现

   gou-gateway: 服务网关 访问 http://localhost:17001/product/get/100?token=aa  就会实际访问到 gou-provider服务去

2. 关于使用到的技术：
   gou-config: spring-cloud-config， config-bus
   分服务端和客户端。服务端主要配置配置来源比如 git，svn，本地；客户端主要配置服务端地址，使用的配置文件和对应的块如 dev，prod；
   如果使用动态配置，客户端需要引入rabbitmq，和 actuator；因配置变更消息是通过rabbitmq传递，因此需要配置mq地址，暴露刷新端口。
   刷新配置操作为  post http://localhost:18001/actuator/bus-refresh

   gou-register: eureka
   分服务端和客户端。服务端主要配置eureka服务相关信息，分单机和集群配置，集群需要把自己注册给其他eureka服务；
   客户端主要配置eureka服务器地址和自己在eureka服务器上展示的一些信息。

   gou-gateway: zuul
   只有zuul服务端，他的信息是从eureka获取,因此只需要配置eureka地址，以及zuul相关代理映射就可以。zuul主要是集中统一管理接口，提供集中登陆验证黑白名单等工作

   gou-dashboard: hytrix
   只有服务端，他只是提供了界面化的监控，没有他也可以监控。要输入被监控的服务器监控地址 eg: http://localhost:18001/actuator/hystrix.stream。他就能把这些信息
   界面化输出，方便查看。  要求就是被监控服务必须引入 hystrix和 actuator,同时在yaml文件中暴露监控接口hystrix.stream。

   其他：
   gou-provider使用技术：
   hytrix： 服务器熔断  参考ProductController的get方法

   gou-consumer： openfeign
   ribbon： 服务端负载均衡  参考ConfigBean {Ribbon 相关依赖,eureka会自动引入Ribbon因此无需再引入}, @LoadBalanced 注解就是生成一个负载均衡的restfultemplate
   只要使用这个带有负载均衡标识的restTemplate访问像eureka注册的微服务，eureka就会帮我们进行负载均衡访问。

   feign:   默认继承了ribbon, 客户端负载均衡和熔断
   feign的 负载均衡： 通过代理实现  代码上( 新建 ProductClientService 接口，使用 @FeignClient("服务名称") 注解标识，来指定调用哪个服务,并且主启动类也加FeignClient代理的路径）
   然后，客户端的访问本应该是直接找gou-provider的ProductService，现在就是通过feign的ProductClientService代理访问过去了。
   原理：因为ProductClientService使用 @FeignClient("gou-provider")指定了服务器名，因此Feign会从Eureka注册中心获取 product 服务列表，并通过负载均衡算法进行服务调用。

   feign的 熔断机制：客户端熔断主要针对服务器异常无法返回情况做处理。feign自带了hystrix,因此需要先在yaml中配置启用feign的hystrix。
   然后在 ProductClientService 接口上的 @FeignClient 注解中，加上 fallback 指定熔断处理类即可： ProductClientServiceFallBack.class


相关访问：
rabbitmq服务地址：http://127.0.0.1:15672/#/queues
Eureka服务地址： http://127.0.0.1:16001/

配置中心获取地址: http://localhost:5001/gou-gateway-dev.yml

通过zuul网关访问provider服务： http://localhost:17001/product/get/1?token=aa
通过zuul网关访问consumer服务： http://localhost:17001/consumer/product/get/1?token=aa

provider服务测试动态配置更新地址：http://localhost:18001/hello

consumer服务测试feign地址： http://localhost:1800/feignconsumer/product/get/300

provider监控地址：http://localhost:18001/actuator/hystrix.stream

通过provider服务刷新配置: http://localhost:18001/actuator/bus-refresh

dashboard监控服务地址：
http://localhost:9001/hystrix

dashboard服务监控provider服务地址：
http://localhost:9001/hystrix/monitor?stream=http%3A%2F%2Flocalhost%3A18001%2Factuator%2Fhystrix.stream&delay=2000&title=provider-18001




