package com.andon.springbootkafka;

import com.alibaba.fastjson.JSONObject;
import com.andon.springbootkafka.kafka.KafkaProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Andon
 * 2021/11/12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseTest {

    @Resource
    private KafkaProducer kafkaProducer;

    @Test
    public void test02() {
        String topic = "test02";
        String message;
        for (int i = 0; i < 100; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(String.valueOf(i), new Date().toString());
            message = JSONObject.toJSONString(jsonObject);
            kafkaProducer.send(topic, message);
        }
    }

    @Test
    public void test01() {
        String topic = "test01";
        String message;
        for (int i = 0; i < 100; i++) {
            message = i + new Date().toString();
            kafkaProducer.send(topic, message);
        }
    }
}
