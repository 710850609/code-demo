package org.llinbo.demo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class RedisDemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RedisDemoApplication.class, args);
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        long startTime = System.currentTimeMillis();
        test1();
        System.out.println("1");
//        test2();
//        test3();
        System.out.println("耗时： " + (System.currentTimeMillis() - startTime));
    }

    private void test1() {
        int count = 89741;
        int batch = 1000;
        Integer index = 0;
        while (index < count + batch) {
            Integer finalIndex = index;
            try {
                redisTemplate.executePipelined(new SessionCallback<Integer>() {
                    @Override
                    public Integer execute(RedisOperations redisOperations) throws DataAccessException {
                        int sum = finalIndex;
                        while (sum < count && sum - finalIndex < batch) {
                            redisOperations.opsForHash().put("base:message:unreadcache:1-:" + UUID.randomUUID().toString().replace("-", ""), "count", "" + sum);
                            sum++;
                        }
                        return null;
                    }
                });
            } catch (Exception e) {
                System.out.println(index);
                throw e;
            }
            index += batch;
        }
    }

    private void test2() {
        int count = 10000;
        Integer index = 0;
        while (index < count) {
            redisTemplate.opsForHash().put("test:" + index, "count", "" + index);
            index++;
        }
    }


    private void test3() {
        List list = redisTemplate.executePipelined(new SessionCallback<String>() {
            @Override
            public String execute(RedisOperations redisOperations) throws DataAccessException {
                int count = 100;
                int index = 0;
                while (index++ < count) {
                    redisOperations.opsForHash().get("test:" + index, "count");
                }
                return null;
            }
        });
        System.out.println(list);
    }

    @Value("${spring.redis.cluster.nodes}")
    private List<String> nodes;

//    private void testt() {
//        RedisClusterConfiguration configuration = new RedisClusterConfiguration(nodes);
//
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(configuration);
//
//        RedisTemplate redisTemplate = new RedisTemplate();
//        redisTemplate.setConnectionFactory(jedisConnectionFactory);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
//
//        int count = 8000;
//        int batch = 1000;
//        Integer index = 6673;
//        while (index < count + batch) {
//            Integer finalIndex = index;
//            try {
//                redisTemplate.executePipelined(new SessionCallback<Integer>() {
//                    @Override
//                    public Integer execute(RedisOperations redisOperations) throws DataAccessException {
//                        int sum = finalIndex;
//                        while (sum < count && sum - finalIndex < batch) {
//                            redisOperations.opsForHash().put("test:" + sum, "count", "" + sum);
//                            sum++;
//                        }
//                        return null;
//                    }
//                });
//            } catch (Exception e) {
//                System.out.println(index);
//                throw e;
//            }
//            index += batch;
//        }
//    }
}
