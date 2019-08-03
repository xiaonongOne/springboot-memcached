package com.example.memcached.config;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * Memcached-Java-Clientf方式
 * @Version: 1.0
 */
@Configuration
public class MemcachedJavaClientManager {

    private Logger logger = LoggerFactory.getLogger(MemcachedJavaClientManager.class);
    @Value("${memcache.servers}")
    private String[] servers;
    @Value("${memcache.failover}")
    private boolean failover;
    @Value("${memcache.initConn}")
    private int initConn;
    @Value("${memcache.minConn}")
    private int minConn;
    @Value("${memcache.maxConn}")
    private int maxConn;
    @Value("${memcache.maintSleep}")
    private int maintSleep;
    @Value("${memcache.nagel}")
    private boolean nagel;
    @Value("${memcache.socketTO}")
    private int socketTO;
    @Value("${memcache.aliveCheck}")
    private boolean aliveCheck;

    private  MemCachedClient memCachedClient;
    /**
     * SockIOPool建立通信的连接池
     * 这个类用来创建管理客户端和服务器通讯连接池，客户端主要的工作包括数据通讯、服务器定位、hash码生成等都是由这个类完成的。
     * 获得连接池的单态方法。这个方法有一个重载方法getInstance( String poolName )，每个poolName只构造一个SockIOPool实例。
     * 缺省构造的poolName是default。
     */
    @Bean
    public SockIOPool sockIOPool () {
        SockIOPool pool = SockIOPool.getInstance();
        pool.setServers(servers);
        pool.setFailover(failover);
        pool.setInitConn(initConn);
        pool.setMinConn(minConn);
        pool.setMaxConn(maxConn);
        pool.setMaintSleep(maintSleep);
        pool.setNagle(nagel);
        pool.setSocketTO(socketTO);
        pool.setAliveCheck(aliveCheck);
        pool.initialize();
        return pool;
    }

    @Bean
    public MemCachedClient memCachedClient(){
        if (memCachedClient == null)
            memCachedClient = new MemCachedClient();
        return memCachedClient;
    }

    //--------------------------------以下是方法---------------------------------------------------
    /**
     * 向缓存添加新的键值对。如果键已经存在，则之前的值将被替换.
     * @param key 键
     * @param value 值
     * @return boolean
     */
    public  boolean set(String key, Object value) {
        return setExp(key, value, null);
    }
    /**
     * 向缓存添加新的键值对。如果键已经存在，则之前的值将被替换.
     * @param key 键
     * @param value 值
     * @param expire 过期时间 New Date(1000*10)：十秒后过期
     * @return boolean
     */
    public  boolean set(String key, Object value, Date expire) {
        return setExp(key, value, expire);
    }
    /**
     * 向缓存添加新的键值对。如果键已经存在，则之前的值将被替换.
     * @param key 键
     * @param value 值
     * @param expire 过期时间 New Date(1000*10)：十秒后过期
     * @return boolean
     */
    private  boolean setExp(String key, Object value, Date expire) {
        boolean flag = false;
        try {
            flag = memCachedClient.set(key, value, expire);
        } catch (Exception e) {
            logger.info("Memcached set方法报错，key值：" + key + "\r\n" + exceptionWrite(e));
        }
        return flag;
    }
    /**
     * 仅当缓存中不存在键时，add 命令才会向缓存中添加一个键值对.
     * @param key 键
     * @param value 值
     * @return boolean
     */
    public  boolean add(String key, Object value) {
        return addExp(key, value, null);
    }
    /**
     * 仅当缓存中不存在键时，add 命令才会向缓存中添加一个键值对.
     * @param key 键
     * @param value 值
     * @param expire 过期时间 New Date(1000*10)：十秒后过期
     * @return boolean
     */
    public  boolean add(String key, Object value, Date expire) {
        return addExp(key, value, expire);
    }
    /**
     * 仅当缓存中不存在键时，add 命令才会向缓存中添加一个键值对.
     * @param key 键
     * @param value 值
     * @param expire 过期时间 New Date(1000*10)：十秒后过期
     * @return boolean
     */
    private  boolean addExp(String key, Object value, Date expire) {
        boolean flag = false;
        try {
            flag = memCachedClient.add(key, value, expire);
        } catch (Exception e) {
            logger.info("Memcached add方法报错，key值：" + key + "\r\n" + exceptionWrite(e));
        }
        return flag;
    }
    /**
     * 仅当键已经存在时，replace 命令才会替换缓存中的键.
     * @param key 键
     * @param value 值
     * @return boolean
     */
    public  boolean replace(String key, Object value) {
        return replaceExp(key, value, null);
    }
    /**
     * 仅当键已经存在时，replace 命令才会替换缓存中的键.
     * @param key 键
     * @param value 值
     * @param expire 过期时间 New Date(1000*10)：十秒后过期
     * @return boolean
     */
    public  boolean replace(String key, Object value, Date expire) {
        return replaceExp(key, value, expire);
    }
    /**
     * 仅当键已经存在时，replace 命令才会替换缓存中的键.
     * @param key 键
     * @param value 值
     * @param expire 过期时间 New Date(1000*10)：十秒后过期
     * @return boolean
     */
    private  boolean replaceExp(String key, Object value, Date expire) {
        boolean flag = false;
        try {
            flag = memCachedClient.replace(key, value, expire);
        } catch (Exception e) {
            logger.info("Memcached replace方法报错，key值：" + key + "\r\n" + exceptionWrite(e));
        }
        return flag;
    }
    /**
     * get 命令用于检索与之前添加的键值对相关的值.
     * @param key 键
     * @return boolean
     */
    public  Object get(String key) {
        Object obj = null;
        try {
            obj = memCachedClient.get(key);
        } catch (Exception e) {
            logger.info("Memcached get方法报错，key值：" + key + "\r\n" + exceptionWrite(e));
        }
        return obj;
    }
    /**
     * 删除 memcached 中的任何现有值.
     * @param key 键
     * @return boolean
     */
    public  boolean delete(String key) {
        return deleteExp(key, null);
    }
    /**
     * 删除 memcached 中的任何现有值.
     * @param key 键
     * @param expire 过期时间 New Date(1000*10)：十秒后过期
     * @return boolean
     */
    public  boolean delete(String key, Date expire) {
        return deleteExp(key, expire);
    }
    /**
     * 删除 memcached 中的任何现有值.
     * @param key 键
     * @param expire 过期时间 New Date(1000*10)：十秒后过期
     * @return boolean
     */
    @SuppressWarnings("deprecation")
    private  boolean deleteExp(String key, Date expire) {
        boolean flag = false;
        try {
            flag = memCachedClient.delete(key, expire);
        } catch (Exception e) {
            logger.info("Memcached delete方法报错，key值：" + key + "\r\n" + exceptionWrite(e));
        }
        return flag;
    }
    /**
     * 清理缓存中的所有键/值对.
     * @return boolean
     */
    public  boolean flashAll() {
        boolean flag = false;
        try {
            flag = memCachedClient.flushAll();
        } catch (Exception e) {
            logger.info("Memcached flashAll方法报错\r\n" + exceptionWrite(e));
        }
        return flag;
    }
    /**
     * 返回异常栈信息，String类型.
     * @param e Exception
     * @return boolean
     */
    private  String exceptionWrite(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }
}
