package com.atguigu.gmall.pms;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.DigestUtils;


/**
 * 1、配置整合dubbo
 * 2、配置整合MyBatisPlus
 *
 * logstash整合，
 * 1）、导jar
 * 2）、导日志配置
 * 3）、在kibana里面建立好日志的索引，就可以可视化检索
 *
 *
 * 1、给内存中存一个值。另外一个类获取出来使用？
 *
 * HelloUtils{
 *     private static Map  map = new HashMap();
 *     {
 *         map.put
 *         map.get
 *     }
 * }
 *
 * 2、请问你们项目怎么联调？
 *      联调？（前后端测写的接口）
 *      接口？（interface）【我们用Controller暴露出来的请求】
 * 3、dubbo原理是什么？
 *      1）、rpc原理：两个不同的服务（不同机器【不同进程】），建立连接，传输数据。
 *      2）、那张图。
 *
 * 0925：
 *     1）、学的很多....
 *     2）、3年经验（核心主流会）。。。  6年经验+（掌握和精通）；
 *          ssm，springboot；ssh；
 *          Spring：设计模式、装饰模式；
 *     3）、学习能力和读文档...
 *
 * 4、联调；
 *      前端Vue，后台开发Server；（接口文档【swagger】）
 *      接口文档：研讨每一个功能的设计（数据库，接口的设计，业务逻辑的设计）；
 *          1）、后台程序员，知道前端需要什么数据，能传来数据数据；
 *          2）、原型；（原型设计师（UE））
 *                 原型：
 *                      1）、UI：设计界面（Android、IOS、Web）
 *                      2）、Server：按照开发功能
 *
 *                 需求分析（设计）---编码---测试---上线
 *         联调发现，前后端设计的问题。
 *
 *
 * 缓存的使用场景：
 * 一些固定的数据，不太变化的数据，高频访问的数据（基本不变），变化频率低的都可以入缓存，加速系统的访问。
 * 缓存的目的：提高系统查询效率，提供性能
 *
 *
 * 1）、将菜单缓存起来，以后查询直接去缓存中拿即可；
 *
 *
 *
 * 设计模式：模板模式：
 * 操作xxx都有对应的xxxTemplate；
 * JdbcTemplate、RestTemplate、RedisTemplate、MongoTemplate
 *
 * RedisTemplate<Object, Object>；  k-v；
 * v有五种类型、String、V
 * StringRedisTemplate: k-v都是String的。
 *
 * 引入一个场景，猜这个场景的xxxAutoConfiguration，
 * 帮我们注入能操作这个技术的组件，这个场景的配置信息都在xxxProperties中说明了(prefix = "spring.redis")使用哪种前缀配置
 *
 * 2)、整合Redis两大步
 *   1）、导入starter-data-redis
 *   2）、application.properties配置与 spring.redis相关的
 *   注意：
 *      RedisTemplate；存数据默认使用jdk的方式序列化存过去。
 *      我们推荐都应该存成json；
 *      做法：
 *          将默认的序列化器改为json的
 *
 *
 * 2、如果发现事务加不上。开启基于注解的事务功能  @EnableTransactionManagement
 *  如果要真的开启什么功能就显式的加上这个注解。。。。
 *
 * 3、事务的最终解决方案；
 *    1）、普通加事务。导入jdbc-starter，@EnableTransactionManagement，加@Transactional
 *    2）、方法自己调自己类里面的加不上事务。
 *          1）、导入aop包，开启代理对象的相关功能
 *               <dependency>
*                   <groupId>org.springframework.boot</groupId>
*                   <artifactId>spring-boot-starter-aop</artifactId>
 *               </dependency>
 *          2）、获取到当前类真正的代理对象，去掉方法即可
 *                 1）、@EnableAspectJAutoProxy(exposeProxy = true):暴露代理对象
 *                 2）、获取代理对象；
 *
 *
 *
 *
 *
 */
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableDubbo
@MapperScan(basePackages = "com.atguigu.gmall.pms.mapper")
@EnableTransactionManagement
@SpringBootApplication
public class GmallPmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallPmsApplication.class, args);
    }

}
