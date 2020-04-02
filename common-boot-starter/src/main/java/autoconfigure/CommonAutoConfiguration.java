package autoconfigure;

import aaron.common.aop.FullCommonFieldAspect;
import aaron.common.data.common.CommonExceptionHandler;
import aaron.common.data.common.CommonState;
import aaron.common.logging.LoggingAspect;
import aaron.common.utils.RedisUtil;
import aaron.common.utils.SnowFlake;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @author xiaoyouming
 * @version 1.0
 * @since 2020-03-04
 */
@Configuration
@ConfigurationProperties(prefix = "aaron")
public class CommonAutoConfiguration {
    private String version;
    private Integer dataCenterId;
    private Integer machineId;

    public Integer getDataCenterId() {
        return dataCenterId;
    }

    public void setDataCenterId(Integer dataCenterId) {
        this.dataCenterId = dataCenterId;
    }

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Bean
    public SnowFlake getSnowFlake(){
        return new SnowFlake(dataCenterId,machineId);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        Jackson2JsonRedisSerializer<Object> serializer = jackson2JsonRedisSerializer();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);
        return redisTemplate;
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        //初始化一个RedisCacheWriter输出流
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        //采用Jackson2JsonRedisSerializer序列化机制
        Jackson2JsonRedisSerializer<Object> serializer = jackson2JsonRedisSerializer();
        //创建一个RedisSerializationContext.SerializationPair给定的适配器pair
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(serializer);
        //创建CacheConfig
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);

        RedisCacheManager redisCacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig);

        return redisCacheManager;
    }


    @Bean
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        final Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        final ObjectMapper objectMapper = Jackson2ObjectMapperBuilder
                .json().build();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }

    @Bean
    public FullCommonFieldAspect fullCommonFieldAspect(){
        return new FullCommonFieldAspect();
    }

    @Bean
    public LoggingAspect loggingAspect(){
        LoggingAspect a = new LoggingAspect();
        a.setVersion(version);
        return a;
    }

    @Bean
    public CommonState commonState(){
        CommonState state = new CommonState();
        state.setVersion(version);
        return state;
    }

    @Bean
    public RedisUtil redisUtil(RedisTemplate<String,Object> redisTemplate){
        return new RedisUtil(redisTemplate);
    }

    @Bean
    public CommonExceptionHandler commonExceptionHandler(){
        return new CommonExceptionHandler();
    }

}
