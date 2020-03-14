package org.llinbo.demo.redis;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class RedisDemoApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    private long startTime;

    @Before
    public void doBefore() {
        startTime = System.currentTimeMillis();
    }

    @After
    public void doAfter() throws InterruptedException {
        System.out.println("耗时: " + (System.currentTimeMillis() - startTime) + " 毫秒");
        Thread.sleep(3000L);
    }

    @Test
    public void testPieline() {
        int count = 10000;
        Integer index = 0;
        while (index++ < count) {
            Integer finalIndex = index;
            List list = redisTemplate.executePipelined(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    redisConnection.set(("test:" + finalIndex).getBytes(), "1".getBytes());
                    System.out.println(finalIndex);
                    return null;
                }
            });
        }
    }

}
