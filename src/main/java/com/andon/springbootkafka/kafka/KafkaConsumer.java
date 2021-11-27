package com.andon.springbootkafka.kafka;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
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

    private static CopyOnWriteArrayList<JSONObject> messageList = new CopyOnWriteArrayList<>();

    @KafkaListener(topicPartitions = { //配置topic和分区：监听两个topic: test02、topic04
            @TopicPartition(topic = "${kafka.topic.test02}", partitions = {"0", "2"}), //test02只接收分区0，2的消息
            @TopicPartition(topic = "topic04", partitions = "0", partitionOffsets = @PartitionOffset(partition = "2", initialOffset = "5")) //topic04接收分区0和分区2的消息，但是分区1的消费者初始位置为5
    })
    public void consume(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {
        if (!ObjectUtils.isEmpty(consumerRecord)) {
            String message = consumerRecord.value();
            log.info("consume message:{}", message);
            messageList.add(JSONObject.parseObject(message));
        }
        if (messageList.size() >= 5) {
            log.info("messageList:{}", JSONObject.toJSONString(messageList));
            log.info("messageList.size:{}", messageList.size());
            acknowledgment.acknowledge(); //手动提交offset
            messageList.clear();
        }
    }

    @KafkaListener(topicPartitions = { //配置topic和分区：监听两个topic: test02、topic04
            @TopicPartition(topic = "${kafka.topic.test02}", partitions = {"1", "3"}), //test02只接收分区1，3的消息
            @TopicPartition(topic = "topic04", partitions = "1", partitionOffsets = @PartitionOffset(partition = "3", initialOffset = "4")) //topic04接收分区1和分区3的消息，但是分区1的消费者初始位置为5
    })
    public void consume2(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {
        if (!ObjectUtils.isEmpty(consumerRecord)) {
            String message = consumerRecord.value();
            log.info("consume2 message:{}", message);
            messageList.add(JSONObject.parseObject(message));
        }
        if (messageList.size() >= 5) {
            log.info("messageList:{}", JSONObject.toJSONString(messageList));
            log.info("messageList.size:{}", messageList.size());
            acknowledgment.acknowledge(); //手动提交offset
            messageList.clear();
        }
    }

    @KafkaListener(topics = {"${kafka.topic.test01}", "test03"}) //监听的topic：test01、test03
    public void consumeTopicTest01(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {
        if (!ObjectUtils.isEmpty(consumerRecord)) {
            String message = consumerRecord.value();
            log.info("consumeTopicTest01 message:{}", message);
            messageList.add(JSONObject.parseObject(message));
        }
        if (messageList.size() >= 5) {
            log.info("messageList:{}", JSONObject.toJSONString(messageList));
            log.info("messageList.size:{}", messageList.size());
            acknowledgment.acknowledge(); //手动提交offset
            messageList.clear();
        }
    }
}
