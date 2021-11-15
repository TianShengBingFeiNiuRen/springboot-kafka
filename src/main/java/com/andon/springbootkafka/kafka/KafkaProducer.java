package com.andon.springbootkafka.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Andon
 * 2021/11/12
 * <p>
 * 生产者
 */
@Slf4j
@Component
public class KafkaProducer {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, String json) {
        kafkaTemplate.send(topic, json);
    }
}
