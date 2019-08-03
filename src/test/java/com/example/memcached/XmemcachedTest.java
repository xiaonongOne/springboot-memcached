package com.example.memcached;

import net.rubyeye.xmemcached.MemcachedClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeoutException;

/**
 * Xmemcached方式
 * 感谢：https://www.cnblogs.com/devise/p/10146917.html
 * Xmemcached官网的地址，里面的文档很详细  https://github.com/killme2008/xmemcached/wiki
 * @Version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class XmemcachedTest {
    @Autowired
    private MemcachedClient memcachedClient;

    @Test
    public void testSetGet() {
        Boolean result = null;
        try {
            result = memcachedClient.set("Xmemcached",0,"码农新锐，这是Java客户端Xmemcached方式！");
            if (result) {
                System.out.println("Xmemcached值：" + memcachedClient.get("Xmemcached").toString());
                return;
            }
            System.out.println(result);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
