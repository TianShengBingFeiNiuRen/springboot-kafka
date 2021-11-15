package com.andon.springbootkafka.kafka;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Andon
 * 2021/11/12
 * <p>
 * 消费者
 */
@Slf4j
@Component
public class KafkaConsumer {

    private static CopyOnWriteArrayList<String> messageList = new CopyOnWriteArrayList<>();

    @KafkaListener(topics = {"test01", "test02"}) //监听的topic：test01、test02
    public void consume(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {
        if (!ObjectUtils.isEmpty(consumerRecord)) {
            String message = consumerRecord.value();
            messageList.add(message);
        }
        if (messageList.size() >= 5) {
            log.info("messageList:{}", JSONObject.toJSONString(messageList));
            log.info("messageList.size:{}", messageList.size());
            acknowledgment.acknowledge(); //手动提交offset
            messageList.clear();
        }
    }
}
