<<<<<<< HEAD:src/main/java/org/g9project4/global/configs/RedisConfig.java
package org.g9project4.global.configs;
=======
package org.g9project4.admin.global.configs;
>>>>>>> 0694ea017b72880143ea0b0b2a75758e79b2496d:src/main/java/org/g9project4/admin/global/configs/RedisConfig.java

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setEnableTransactionSupport(true);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
<<<<<<< HEAD:src/main/java/org/g9project4/global/configs/RedisConfig.java
}
=======
}
>>>>>>> 0694ea017b72880143ea0b0b2a75758e79b2496d:src/main/java/org/g9project4/admin/global/configs/RedisConfig.java
