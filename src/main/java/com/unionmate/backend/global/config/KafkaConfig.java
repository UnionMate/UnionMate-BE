package com.unionmate.backend.global.config;

import com.unionmate.backend.global.kafka.event.JwtGenerateEvent;
import com.unionmate.backend.global.kafka.event.JwtTokenEvent;
import com.unionmate.backend.global.kafka.event.JwtUserIdEvent;
import com.unionmate.backend.global.kafka.event.JwtVerifyEvent;
import com.unionmate.backend.global.kafka.event.MailSendEvent;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConfig {

  @Value("${kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Value("${kafka.consumer.group-id}")
  private String consumerGroupId;

  @Value("${kafka.topics.jwt-verify-reply}")
  private String jwtVerifyReplyTopic;

  @Value("${kafka.topics.jwt-generate-reply}")
  private String jwtGenerateReplyTopic;

  @Bean
  public ProducerFactory<String, JwtVerifyEvent> jwtVerifyProducerFactory() {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    configProps.put(ProducerConfig.ACKS_CONFIG, "all");
    configProps.put(ProducerConfig.RETRIES_CONFIG, 3);
    return new DefaultKafkaProducerFactory<>(configProps);
  }

  @Bean
  public ConsumerFactory<String, JwtUserIdEvent> jwtVerifyConsumerFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.unionmate.backend.global.kafka.event");
    props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
    props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, JwtUserIdEvent.class.getName());
    return new DefaultKafkaConsumerFactory<>(props);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, JwtUserIdEvent> jwtVerifyKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, JwtUserIdEvent> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(jwtVerifyConsumerFactory());
    return factory;
  }

  @Bean
  public ReplyingKafkaTemplate<String, JwtVerifyEvent, JwtUserIdEvent> jwtVerifyReplyingKafkaTemplate(
      ProducerFactory<String, JwtVerifyEvent> jwtVerifyProducerFactory,
      ConcurrentKafkaListenerContainerFactory<String, JwtUserIdEvent> jwtVerifyKafkaListenerContainerFactory
  ) {
    ConcurrentMessageListenerContainer<String, JwtUserIdEvent> replyContainer =
        jwtVerifyKafkaListenerContainerFactory.createContainer(jwtVerifyReplyTopic);
    replyContainer.getContainerProperties().setGroupId(consumerGroupId + "-jwt-verify-replies");

    return new ReplyingKafkaTemplate<>(jwtVerifyProducerFactory, replyContainer);
  }

  @Bean
  public ProducerFactory<String, JwtGenerateEvent> jwtGenerateProducerFactory() {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    configProps.put(ProducerConfig.ACKS_CONFIG, "all");
    configProps.put(ProducerConfig.RETRIES_CONFIG, 3);
    return new DefaultKafkaProducerFactory<>(configProps);
  }

  @Bean
  public ConsumerFactory<String, JwtTokenEvent> jwtGenerateConsumerFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.unionmate.backend.global.kafka.event");
    props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
    props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, JwtTokenEvent.class.getName());
    return new DefaultKafkaConsumerFactory<>(props);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, JwtTokenEvent> jwtGenerateKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, JwtTokenEvent> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(jwtGenerateConsumerFactory());
    return factory;
  }

  @Bean
  public ReplyingKafkaTemplate<String, JwtGenerateEvent, JwtTokenEvent> jwtGenerateReplyingKafkaTemplate(
      ProducerFactory<String, JwtGenerateEvent> jwtGenerateProducerFactory,
      ConcurrentKafkaListenerContainerFactory<String, JwtTokenEvent> jwtGenerateKafkaListenerContainerFactory
  ) {
    ConcurrentMessageListenerContainer<String, JwtTokenEvent> replyContainer =
        jwtGenerateKafkaListenerContainerFactory.createContainer(jwtGenerateReplyTopic);
    replyContainer.getContainerProperties().setGroupId(consumerGroupId + "-jwt-generate-replies");

    return new ReplyingKafkaTemplate<>(jwtGenerateProducerFactory, replyContainer);
  }

  @Bean
  public ProducerFactory<String, MailSendEvent> mailSendProducerFactory() {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    configProps.put(ProducerConfig.ACKS_CONFIG, "all");
    configProps.put(ProducerConfig.RETRIES_CONFIG, 3);
    return new DefaultKafkaProducerFactory<>(configProps);
  }

  @Bean
  public KafkaTemplate<String, MailSendEvent> mailSendKafkaTemplate(
      ProducerFactory<String, MailSendEvent> mailSendProducerFactory
  ) {
    return new KafkaTemplate<>(mailSendProducerFactory);
  }
}
