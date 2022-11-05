package br.com.wallace.rafael;

import br.com.wallace.rafael.payload.Order;
import br.com.wallace.rafael.payload.OrderType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@Slf4j
public class SimpleStoreKafkaProducerApplication implements CommandLineRunner {

    private final StreamBridge streamBridge;

    @Autowired
    public SimpleStoreKafkaProducerApplication(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static void main(String[] args) {
        SpringApplication.run(SimpleStoreKafkaProducerApplication.class, args);
    }

    /**
     * responsible for sending the quantity of products to replenish stock.
     */
    List<Order> buyOrders = Arrays.asList(
            new Order(UUID.randomUUID().toString(), 1, LocalDate.now(), OrderType.BUY, 50),
            new Order(UUID.randomUUID().toString(), 3, LocalDate.now(), OrderType.BUY, 50),
            new Order(UUID.randomUUID().toString(), 4, LocalDate.now(), OrderType.BUY, 15),
            new Order(UUID.randomUUID().toString(), 1, LocalDate.now(), OrderType.BUY, 2),
            new Order(UUID.randomUUID().toString(), 2, LocalDate.now(), OrderType.BUY, 22),
            new Order(UUID.randomUUID().toString(), 1, LocalDate.now(), OrderType.BUY, 7)
    );

    /**
     * responsible for sending the quantity of products sold.
     */
    List<Order> sellOrders = Arrays.asList(
            new Order(UUID.randomUUID().toString(), 2, LocalDate.now(), OrderType.SELL, 12),
            new Order(UUID.randomUUID().toString(), 1, LocalDate.now(), OrderType.SELL, 23),
            new Order(UUID.randomUUID().toString(), 3, LocalDate.now(), OrderType.SELL, 39),
            new Order(UUID.randomUUID().toString(), 4, LocalDate.now(), OrderType.SELL, 3),
            new Order(UUID.randomUUID().toString(), 1, LocalDate.now(), OrderType.SELL, 13)
    );

    @Override
    public void run(String... args) throws Exception {
        sendOrders();
    }

    public void sendOrders() throws JsonProcessingException {
        log.info("[SimpleStoreKafkaProducerApplication] - Start sending messages");
        val map = new HashMap<String, Object>();
        map.put("service-origin", "simple-store-kafka-producer");
        val headers = new MessageHeaders(map);
        Message<String> message = MessageBuilder
                .createMessage(mapper.writeValueAsString(mountPayload(buyOrders)), headers);

        log.info("[SimpleStoreKafkaProducerApplication] - Sending orders type: {}", OrderType.BUY);
        streamBridge.send("ordersbuy-out-0", message);
        log.info("[SimpleStoreKafkaProducerApplication] - Orders sended");

        message = MessageBuilder
                .createMessage(mapper.writeValueAsString(mountPayload(sellOrders)), headers);
        log.info("[SimpleStoreKafkaProducerApplication] - Sending orders type: {}", OrderType.SELL);
        streamBridge.send("orderssell-out-0", message);
        log.info("[SimpleStoreKafkaProducerApplication] - Orders sended");
    }

    private Message<List<Order>> mountPayload(List<Order> orders) {
        return MessageBuilder
                .withPayload(orders)
                .build();
    }
}
