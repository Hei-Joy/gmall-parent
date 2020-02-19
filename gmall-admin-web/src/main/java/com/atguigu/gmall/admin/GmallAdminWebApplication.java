package com.atguigu.gmall.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * VO:(View Object/Value Object)：视图对象；
 *      1）、List<User>：把专门交给前端的数据封装成VO；（user100个字段，userVo 3个）
 *      2）、User { }；用户给我提交的封装成vo往下传。
 *             用户注册：
 *                  用户提交一个手机号;user=100个字段；内存中创建大对象
 *                  userService.register(user)
 *      request--->提交的vo；
 *      response--->返回的vo；
 *
 *
 *
 * DAO:(Database Access Object)：数据库访问对象；专门用来对数据库进行crud的对象。XxxMapper
 * POJO:(Plain Old Java Object)：古老的单纯的java对象。javaBean（封装数据的）
 * DO:(Data Object)：数据对象---POJO  (Database Object)：数据库对象（专门用来封装数据库表的实体类）
 * TO:(Transfer Object)：传输对象；（aController(user[100个])== aService.a(userTo[3个])）
 *      1）、服务之间互调，为了数据传输封装对象
 *      2）、aService(){
 *              user,
 *              movie
 *      }
 *      bService(用户名和电影名  UserMovieTo(userName,movieName)){
 *
 *      }
 *
 * DTO:(Data Transfer Object)：
 *
 * @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
 *  1、不进行数据源的自动配置
 *
 *  如果导入的依赖，引入一个自动配置场景：
 *  1）、这个场景自动配置默认生效，我们就必须配置他
 *  2）、不想配置；
 *      1）、引入的时候排除这个场景依赖
 *      2）、排除掉这个场景的自动配置类
 *
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GmallAdminWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallAdminWebApplication.class, args);
    }

}
