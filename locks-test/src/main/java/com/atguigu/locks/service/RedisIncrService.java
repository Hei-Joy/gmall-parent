package com.atguigu.locks.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class RedisIncrService {

    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    JedisPool jedisPool;



    AtomicInteger integer = new AtomicInteger(1);
    int i = 1;

    private Object obj = new Object();
    ReentrantLock lock = new ReentrantLock();

    Map<String,Object> map = new HashMap<>();

    @Autowired
    RedissonClient redisson;

    /**
     * Map cache = new Map();
     * a(){
     *     cache.put(k,v)
     * }
     *
     *
     * 缓存污染。
     * b(){
     *     V v = cache.get(k);
     *     v.setAge(19);
     *     if(v.age==18){
     *         //吃饭
     *     }
     *
     * }
     *
     * c(){
     *     V v = cache.get(k);
     *     if(v.age==18){
     *         //睡觉
     *     }
     * }
     *
     *
     *
     */


    public void useRedissonForLock(){
        //map.put(k,v)
        //integer.compareAndSet(1,2);

        //利用CAS算法可以结算。ABA；
        int i1 = integer.incrementAndGet();


//        synchronized (){
//            System.out.println("");
//        }



        lock.lock();

        //
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        //

        
        

        
        
        lock.unlock();
        //1、获取一把锁。只要各个代码，用的锁名一样即可
        RLock lock = redisson.getLock("lock");
        try {
            //lock.lock();//一直等待。阻塞住
            //感知别人删锁。发布订阅模式（实时感知）。   lock监听redis，redis一旦删锁。赶紧尝试去加锁。

            lock.lock(3,TimeUnit.SECONDS);//加锁带自动解锁
            Jedis jedis = jedisPool.getResource();
            String num = jedis.get("num");
            Integer i = Integer.parseInt(num);
            i = i+1;
            jedis.set("num",i.toString());
            jedis.close();
        }finally {
            lock.unlock();//解锁
        }
    }


    public void chaMap(){
        map.put("hello","123");
    }
    
    public void incrDistribute(){
        //Object hello = map.get("hello");

        //1、占坑。（原子性）
        //1）、先判断没有，2）、再给里面放值


        /**
         *
         * setnx->set if not exist：原子操作。判断带保存。
         *
         *1）、代码第一阶段；
         * public void hello(){
         *
         * //获取和设置值必须是原子的
         *   String lock =  getFromRedis("lock");//get("lock")
         *   if(lock == null){
         *       setRedisKey("lock","1");
         *       //执行业务
         *       delRedisKey("lock")
         *       return ;
         *   }else{
         *      hello();//自旋
         *   }
         * }
         * //问题：加锁的原子性
         *
         * 2、代码第二阶段
         * public void hello(){
         *     //1、获取到锁
         *     Integer lock = setnx("lock',"111"); //0代表没有保存数据，说明已经有人占了。1代表占可坑成功
         *     if(lock!=0){
         *         //执行业务逻辑
         *         //释放锁、删除锁
         *         del("lock")
         *     }else{
         *         //等待重试
         *         hello();
         *     }
         * }
         * //问题：如果由于各种问题（未捕获的异常、断电等）导致锁没释放。其他人永远获取不到锁。
         * //解决：加个过期时间。
         *
         * 3、代码第三阶段
         * public void hello(){
         *    //超时和加锁必须原子
         *     Integer lock = setnx("lock',"111");
         *     if(lock!=null){
         *         expire("lock",10s);
         *         //执行业务逻辑
         *         //释放锁
         *         del("lock')
         *     }else{
         *         hello();
         *     }
         *
         * }
         * 问题：刚拿到锁，机器炸了，没来得及设置超时。
         * 解决：加锁和加超时也必须是原子的。
         *
         *
         * 4、代码第四阶段：
         * public void hello(){
         *     String result = setnxex("lock","111",10s);
         *     if(result=="ok"){
         *         //加锁成功
         *         //执行业务逻辑
         *         del("lock")
         *     }else{
         *         hello();
         *     }
         * }
         * 问题：如果业务逻辑超时，导致锁自动删除，业务执行完又删除一遍。至少多个人都获取到了锁。
         *
         * 5、代码第五阶段。
         * public void hello(){
         *    String token = UUID;
         *    String result = setnxex("lock",token,10s);
         *    if(result == "ok"){
         *        //执行业务
         *
         *        //删锁，保证删除自己的锁
         *        if(get("lock")==token){
         *            del("lock")
         *        }
         *    }else{
         *        hello();
         *    }
         * }
         * 问题？：我们获取锁的时候，锁的值正在给我们返回。锁过期。redis删除了锁。
         * 但是我们拿到了值，而且对比成功（此时此刻正好有人又获取）。我们还删除了锁。至少两个线程又进入同一个代码。
         *  原因：？删锁不是原子。d
         *      lua脚本。
         *
         *  解决：
         *  String script =
         *      "if redis.call('get', KEYS[1]) == ARGV[1] then
         *              return redis.call('del', KEYS[1])
         *       else
         *              return 0
         *       end";
         *
         * jedis.eval(script, Collections.singletonList(key), Collections.singletonList(token));
         *
         *   lua脚本进行删除。
         *
         *
         * 1）、分布式锁的核心（保证原子性）
         *      1）、加锁。占坑一定要是原子的。（判断如果没有，就给redis中保存值）
         *      2）、锁要自动超时。
         *      3）、解锁也要原子。
         *
         *
         *  最终的分布式锁的代码：大家都去redis中占同一个坑。
         *
         *
         *
         *  @Lock
         *  public void hello(){
         *      String token = uuid;
         *      String lock = redis.setnxex("lock",token,10s);
         *      if(lock=="ok"){
         *          //执行业务逻辑
         *          //脚本删除锁
         *      }else{
         *          hello();//自旋。
         *      }
         *  }
         *
         *  AOP;
         *
         *  RedisTemplate和Jedis客户端2选一
         *
         */
        //

        //1、加锁
//        String token = UUID.randomUUID().toString();
//        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", token, 3, TimeUnit.SECONDS);
//        if(lock){
//            ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
//            String num = stringStringValueOperations.get("num");
//            if (num != null) {
//                Integer i = Integer.parseInt(num);
//                i = i + 1;
//                stringStringValueOperations.set("num", i.toString());
//            }
//
//            //删除锁。
//            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
//            DefaultRedisScript<String> script1 = new DefaultRedisScript<>(script);
//            redisTemplate.execute(script1, Arrays.asList("lock"),token);
//            System.out.println("删除锁完成...");
//        }else {
//            incrDistribute();
//        }


        /**
         * 1、锁的更多考虑
         *   1）、自旋。
         *      自旋次数。
         *      自旋超时。
         *   2）、锁设置
         *      锁粒度；细；记录级别；
         *          1）、各自服务各自锁
         *          2）、分析好粒度，不要锁住无关数据。一种数据一种锁，一条数据一个锁。
         *   3）、锁类型：
         *
         *
         *
         * 查询商品详情；进缓存-->击穿，穿透，雪崩。
         *
         *   查商品
         *   public Product productInfo(String productId){
         *
         *
         *      Product cache = jedis.get(productId);
         *      if(cache!=null){
         *          return cache;
         *      }else{
         *          //各自数据各自锁。
         *          String lock = jedis.set("lock-"+productId, token, SetParams.setParams().ex(3).nx());
         *          if(lock!=null){
         *             //查数据库
         *              Product product = getFromDb();
         *              jedis.set(productId,product);
         *          }else{
         *              return productInfo(productId);//自旋。
         *          }
         *      }
         *   }
         *
         *   查询1号商品，2,3,4,5，6；
         *   1号商品缓存没有，2号有，3号没有，4没有，5有。
         *
         *
         *
         *
         *      jedis.set("lock", token, SetParams.setParams().ex(3).nx());
         *      if(){
         *          //拿到锁
         *      }
         *
         *
         *
         */


        Jedis jedis = jedisPool.getResource();

        try {
            String token = UUID.randomUUID().toString();
            String lock = jedis.set("lock", token, SetParams.setParams().ex(3).nx());
            if(lock!=null&&lock.equalsIgnoreCase("OK")){
                //ok
                String num = jedis.get("num");
                Integer i = Integer.parseInt(num);
                i = i+1;
                jedis.set("num",i.toString());


                //删除锁
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                jedis.eval(script, Collections.singletonList("lock"),Collections.singletonList(token));
                System.out.println("删除锁ok....");

            }else {
                try {
                    Thread.sleep(1000);
                    incrDistribute();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }finally {
            jedis.close();
        }



    }



    /**
     * 进程内肯定好使，单机跑。分布式肯定 不好使
     */
    public void incr() {

        /**
         *  ReentrantLock lock = new ReentrantLock();应该在成员变量位置才锁得住
         */

        //锁得住？
        lock.lock();
        ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
        String num = stringStringValueOperations.get("num");
        if (num != null) {
            Integer i = Integer.parseInt(num);
            i = i + 1;
            stringStringValueOperations.set("num", i.toString());
        }

        lock.unlock();

        //这个锁大家都用一个。
        //this当前对象。当前service对象。spring的组件是单例的。this一个。
        //this相同，锁相同，锁ok
        //RedisIncrService对象一个。自动注入；StringRedisTemplate，redisTemplate也只能注入唯一一个。
        //RedisIncrService对象创建的时候赋值，RedisIncrService一个   private Object obj = new Object();

        //1）、synchronized(this)：能
        //2）、synchronized (redisTemplate)：能
        //3）、synchronized (new Object())：锁不住
        //4）、synchronized (obj)：锁得住？锁得住
        //5）、synchronized (obj())；锁的住
        //6）、synchronized (RedisIncrService.class)；锁得住
//        synchronized (this){
//            ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
//            String num = stringStringValueOperations.get("num");
//            if(num!=null){
//                Integer i = Integer.parseInt(num);
//                i = i+1;
//                stringStringValueOperations.set("num",i.toString());
//            }
//        }

    }


    //肯定锁不住
    public Object obj() {
        Object o = new Object();
        BeanUtils.copyProperties(obj, o);
        return o;
    }
}
