package com.example.memcached;

import com.example.memcached.config.MemcachedJavaClientManager;
import com.whalin.MemCached.MemCachedClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * Memcached-Java-Client方式
 * 感谢：https://blog.csdn.net/kye055947/article/details/83692466
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemcachedJaveClientTests {

    @Autowired
    private MemcachedJavaClientManager memCachedClient;

    @Test
    public void contextLoads() throws InterruptedException{
        // 放入缓存
        boolean flag = memCachedClient.set("Memcached-Java-Client", "码农新锐，这是Java客户端Memcached-Java-Client方式！");
        // 取出缓存
        Object name = memCachedClient.get("Memcached-Java-Client");
        System.out.println(name);
        // 3s后过期
        memCachedClient.set("url", "https://www.jianshu.com/u/3d0829501918", new Date(3000));
        Object url = memCachedClient.get("url");
        System.out.println(url);
        Thread.sleep(3000);

        url = memCachedClient.get("url");
        System.out.println(name);
        System.out.println(url);
    }

    /**
     * 常用方法 add set get
     *
     * set方法
     *      将数据保存到cache服务器，如果保存成功则返回true
     *      如果cache服务器存在同样的key，则替换之
     *      set有5个重载方法，key和value是必须的参数，还有过期时间，hash码，value是否字符串三个可选参数
     *
     * add方法
     *      将数据添加到cache服务器,如果保存成功则返回true
     *      如果cache服务器存在同样key，则返回false
     *      add有4个重载方法，key和value是必须的参数，还有过期时间，hash码两个可选参数
     *
     * replace方法
     *      将数据替换cache服务器中相同的key,如果保存成功则返回true
     *      如果cache服务器不存在同样key，则返回false
     *      replace有4个重载方法，key和value是必须的参数，还有过期时间，hash码两个可选参数
     */
}
