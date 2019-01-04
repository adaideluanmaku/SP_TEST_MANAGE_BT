package com.ch.redis.v53;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;


/**
 * @author：周应强
 * @创建日期:2018-09-27 10:46
 * @描述：redis的加载的配置文件的方法
 **/
@Configuration
@PropertySource(value= {"classpath:config.properties"})
public class RedisServiceConfig {

	private static Logger logger = LoggerFactory.getLogger(RedisServiceConfig.class);

    public RedisServiceConfig(){
    	logger.info("redis初始化开始..........");
        //System.out.println("Redis 初始化。。。。");
    }
    
    
    @Value("${redis.host}")
    private String hostname;
    @Value("${redis.port}")
    private int port;
    @Value("${redis.pass}")
    private String password;
    @Value("${redis.maxTotal}")
    private int maxTotal;
    @Value("${redis.maxIdle}")
    private int maxIdle;
    @Value("${redis.minIdle}")
    private int minIdle;
    @Value("${redis.blockWhenExhausted}")
    private boolean isBlockWhenExhausted;
    @Value("${redis.maxWait}")
    private int maxWait;
    @Value("${redis.testOnBorrow}")
    private boolean isTestOnBorrow;
    @Value("${redis.testOnReturn}")
    private boolean isTestOnReturn;
    @Value("${redis.testWhileIdle}")
    private boolean isTestWhileIdle;
    @Value("${redis.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${redis.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${redis.default.db}")
    private int dbIndex;



    /**
     *
     * <ul>
     * <li>方法名：  fstRedisSerializer </li>
     * <li>功能描述：fst序列化和反序列化的工具 </li>
     * <li>创建人：  周应强 </li>
     * <li>创建时间：2018年9月17日 </li>
     * </ul>
     * @return FstRedisSerializer
     */
    @Bean
    public FstRedisSerializer fstRedisSerializer() {
        return new FstRedisSerializer();
    }






    /**
     * 配置连接池参数
     *
     * @return GenericObjectPool
     */
    @Bean
    public GenericObjectPoolConfig genericObjectPoolConfig() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMaxTotal(maxTotal);
        genericObjectPoolConfig.setMinIdle(minIdle);
        //连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        genericObjectPoolConfig.setBlockWhenExhausted(isBlockWhenExhausted);
        genericObjectPoolConfig.setMaxWaitMillis(maxWait);
        //在borrow一个实例时，是否提前进行alidate操作；如果为true，则得到的实例均是可用的
        genericObjectPoolConfig.setTestOnBorrow(isTestOnBorrow);
        //调用returnObject方法时，是否进行有效检查
        genericObjectPoolConfig.setTestOnReturn(isTestOnReturn);
        //在空闲时检查有效性, 默认false
        genericObjectPoolConfig.setTestWhileIdle(isTestWhileIdle);
        //表示idle object evitor两次扫描之间要sleep的毫秒数；
        genericObjectPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        //表示一个对象至少停留在idle状态的最短时间，
        //然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义；
        genericObjectPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        return genericObjectPoolConfig;
    }


    /**
     *
     * <ul>
     * <li>方法名：  lettuceClientConfiguration </li>
     * <li>功能描述：lettuce链接客户端的方式，原来用的jedis现在全部修改成lettuce的方式 </li>
     * <li>创建人：  周应强 </li>
     * <li>创建时间：2018年9月17日 </li>
     * </ul>
     * @param genericObjectPoolConfig 连接池的配置
     * @return LettuceClientConfiguration
     */
    @Bean
    public LettucePoolingClientConfiguration lettucePoolingClientConfiguration(GenericObjectPoolConfig genericObjectPoolConfig) {
    	return LettucePoolingClientConfiguration.builder().poolConfig(genericObjectPoolConfig).build();

    }



    /**
     *
     * <ul>
     * <li>方法名：  redisStandaloneConfiguration </li>
     * <li>功能描述： redis链接账号和密码的获取对象</li>
     * <li>创建人：  周应强 </li>
     * <li>创建时间：2018年9月17日 </li>
     * </ul>
     * @return RedisStandaloneConfiguration
     */
    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(this.hostname, this.port);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(this.password));
        redisStandaloneConfiguration.setDatabase(this.dbIndex);
        return redisStandaloneConfiguration;
    }


    /**
     *
     * <ul>
     * <li>方法名：  redisConnectionFactory </li>
     * <li>功能描述： 通过lettuce获取到链接redis的工厂类</li>
     * <li>创建人：  周应强 </li>
     * <li>创建时间：2018年9月17日 </li>
     * </ul>
     * @param redisStandaloneConfiguration 客户端链接的配置工具类
     * @param lettuceClientConfiguration 客户端一些基本配置类
     * @return LettuceConnectionFactory
     */
    @Bean
    public LettuceConnectionFactory redisConnectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration, LettucePoolingClientConfiguration lettuceClientConfiguration) {
        return new LettuceConnectionFactory(redisStandaloneConfiguration,lettuceClientConfiguration);
    }

    /**
     *
     * <ul>
     * <li>方法名：  redisTemplateObject </li>
     * <li>功能描述：redis读取的对象的 的方式 </li>
     * <li>创建人：  周应强 </li>
     * <li>创建时间：2018年9月17日 </li>
     * </ul>
     * @param redisConnectionFactory redis链接的工厂类
     * @param fstRedisSerializer 序列化的工具。
     * @return redisTemplate
     */
    @Bean(value="redisTemplateObject")
    public RedisTemplate<String,Object> redisTemplateObject(RedisConnectionFactory redisConnectionFactory
            , FstRedisSerializer fstRedisSerializer) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setKeySerializer(fstRedisSerializer);
        redisTemplate.setValueSerializer(fstRedisSerializer);
        redisTemplate.setHashValueSerializer(fstRedisSerializer);
        redisTemplate.setHashKeySerializer(fstRedisSerializer);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //todo 定制化配置
        return redisTemplate;
    }
    /**
     *
     * <ul>
     * <li>方法名：  redisStrTemplateObject </li>
     * <li>功能描述：redis读取的对象的 的方式 </li>
     * <li>创建人：  周应强 </li>
     * <li>创建时间：2018年9月17日 </li>
     * </ul>
     * @param redisConnectionFactory redis链接的工厂类
     * @param fstRedisSerializer 序列化的工具。
     * @return redisTemplate
     */
    @Bean(value = "stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory
            , FstRedisSerializer fstRedisSerializer){
        StringRedisTemplate redisStrTemplate = new StringRedisTemplate();
        redisStrTemplate.setKeySerializer(fstRedisSerializer);
        redisStrTemplate.setValueSerializer(fstRedisSerializer);
        redisStrTemplate.setHashValueSerializer(fstRedisSerializer);
        redisStrTemplate.setHashKeySerializer(fstRedisSerializer);
        redisStrTemplate.setConnectionFactory(redisConnectionFactory);
        return redisStrTemplate;
    }

}
