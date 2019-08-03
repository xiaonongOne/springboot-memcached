package com.example.memcached;

import com.example.memcached.config.SpymemcachedManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Spymemcached方式
 * 感谢：https://blog.csdn.net/qq_39211866/article/details/84312170
 * @Version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpymemcachedTests {

    @Resource
    private SpymemcachedManager memcachedManager;

    @Test
    public void testSetGet() {
        //set的方法签名为，第二个参数是过期时间，单位是秒
        Boolean result = memcachedManager.set("Spymemcached", 10000, "码农新锐，这是Java客户端Spymemcached方式！");
        if (result) {
            System.out.println("Spymemcached 值：" + memcachedManager.get("Spymemcached").toString());
            return;
        }
        System.out.println("***********  操作失败!  ***********");
    }

    @Test
    public void testAsyncGet2() {
        //获取值，如果在5秒内没有返回值，将取消
        Object myObj = null;
        Object result = memcachedManager.ascynGet("someKey");
        System.out.println(result);
    }

    @Test
    public void testReplace() {
        Boolean flag = memcachedManager.replace("someKey", "dashuai", 10000);
        if (flag) {
            System.out.println("更新替换键值成功!");
            System.out.println("最终结果为:" + memcachedManager.get("someKey").toString());
            return;
        }
        System.out.println("更新键值失败!");
    }

    @Test
    public void testAdd() {
        Boolean flag = memcachedManager.add("someKey", "dashuai", 10000);
        if (flag) {
            System.out.println("最终结果为:" + memcachedManager.get("someKey").toString());
            return;
        }
        System.out.println("添加键值失败!");
    }

    @Test
    public void delete() {
        Boolean f = memcachedManager.delete("someKey");
        System.out.println("删除" + (f ? "成功!" : "失败!"));
    }

    @Test
    public void incrementTest() {
        long result = memcachedManager.increment("increment", 5, 20, 10000);
        System.out.println(result);
    }

    @Test
    public void decrementTest() {
        long result = memcachedManager.decrement("increment", 5, 20, 10000);
        System.out.println(result);
    }

    @Test
    public void asyncIncrement() {
        Long result = memcachedManager.asyncIncrement("increment", 5);
        System.out.println(result);
    }

    @Test
    public void asyncGetMultiTest() {
        memcachedManager.set("aa", 100000, "大帅");
        memcachedManager.set("bb", 100000, "大傻");
        List<String> list = new ArrayList<>();
        list.add("aa");
        list.add("bb");
        Map map = memcachedManager.asyncGetMulti(list);
        System.out.println(map.toString());
    }

    @Test
    public void flushTest() {
        memcachedManager.flush();
        Object result = memcachedManager.get("aa");
        System.out.println(result);
    }
}
