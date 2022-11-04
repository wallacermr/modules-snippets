package br.com.wallace.rafael;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.javac.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

@SpringBootApplication
@Slf4j
public class SampleKafkaProducerApplication implements CommandLineRunner {

    private static long orderId = 0;
    private static final Random r = new Random();

    @Autowired
    private StreamBridge streamBridge;

    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static void main(String[] args) {
        SpringApplication.run(SampleKafkaProducerApplication.class, args);
    }

//    LinkedList<Order> buyOrders = new LinkedList<>(List.of(
//            new Order(++orderId, 1, 1, 100, LocalDateTime.now(), OrderType.BUY, 1000),
//            new Order(++orderId, 2, 1, 200, LocalDateTime.now(), OrderType.BUY, 1050),
//            new Order(++orderId, 3, 1, 100, LocalDateTime.now(), OrderType.BUY, 1030),
//            new Order(++orderId, 4, 1, 200, LocalDateTime.now(), OrderType.BUY, 1050),
//            new Order(++orderId, 5, 1, 200, LocalDateTime.now(), OrderType.BUY, 1000),
//            new Order(++orderId, 11, 1, 100, LocalDateTime.now(), OrderType.BUY, 1050)
//    ));
//
//    LinkedList<Order> sellOrders = new LinkedList<>(List.of(
//            new Order(++orderId, 6, 1, 200, LocalDateTime.now(), OrderType.SELL, 950),
//            new Order(++orderId, 7, 1, 100, LocalDateTime.now(), OrderType.SELL, 1000),
//            new Order(++orderId, 8, 1, 100, LocalDateTime.now(), OrderType.SELL, 1050),
//            new Order(++orderId, 9, 1, 300, LocalDateTime.now(), OrderType.SELL, 1000),
//            new Order(++orderId, 10, 1, 200, LocalDateTime.now(), OrderType.SELL, 1020)
//    ));

    @Override
    public void run(String... args) throws Exception {
        sendMessage();
    }

    public void sendMessage() throws JsonProcessingException {
        Order order = new Order(1L, 1, 1, 100, LocalDateTime.now(), OrderType.BUY, 1000);
        Message<Order> message = MessageBuilder
                .withPayload(order)
                .setHeader(KafkaHeaders.CORRELATION_ID, UUID.randomUUID().toString())
                .build();
        log.info("Object {}", message);
        streamBridge.send("orderBuy-out-0", mapper.writeValueAsString(message));
    }

//    @Bean
//    public Supplier<Message<Order>> orderBuy() {
//        return () -> {
//            if (buyOrders.peek() != null) {
//                Message<Order> o = MessageBuilder
//                        .withPayload(buyOrders.peek())
//                        .setHeader(KafkaHeaders.MESSAGE_KEY, Objects.requireNonNull(buyOrders.poll()).getId())
//                        .build();
//                log.info("Order: {}", o.getPayload());
//                return o;
//            } else {
//                return null;
//            }
//        };
//    }
//
//    @Bean
//    public Supplier<Message<Order>> orderSell() {
//        return () -> {
//            if (sellOrders.peek() != null) {
//                Message<Order> o = MessageBuilder
//                        .withPayload(sellOrders.peek())
//                        .setHeader(KafkaHeaders.MESSAGE_KEY, Objects.requireNonNull(sellOrders.poll()).getId())
//                        .build();
//                log.info("Order: {}", o.getPayload());
//                return o;
//            } else {
//                return null;
//            }
//        };
//    }

}
