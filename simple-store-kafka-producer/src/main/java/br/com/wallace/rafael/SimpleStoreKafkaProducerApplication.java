package br.com.wallace.rafael;

import br.com.wallace.rafael.payload.Order;
import br.com.wallace.rafael.payload.OrderType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
import java.util.HashMap;
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
            .configure(SerializationFeature.FLUSH_AFTER_WRITE_VALUE, false);

    public static void main(String[] args) {
        SpringApplication.run(SimpleStoreKafkaProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        sendOrders();
    }

    public void sendOrders() throws JsonProcessingException {
        log.info("[SimpleStoreKafkaProducerApplication] - Start sending messages");
        val map = new HashMap<String, Object>();
        map.put("service-origin", "simple-store-kafka-producer");
        val headers = new MessageHeaders(map);
        val buyOrder =
                new Order(UUID.randomUUID().toString(), 2, LocalDate.now(), OrderType.BUY, 134);
        Message<String> message = MessageBuilder.createMessage(mapper.writeValueAsString(buyOrder), headers);

        log.info("[SimpleStoreKafkaProducerApplication] - Sending orders type: {}", OrderType.BUY);
        streamBridge.send("ordersbuy-out-0", message);
        log.info("[SimpleStoreKafkaProducerApplication] - Orders sended");

        val sellOrder =
                new Order(UUID.randomUUID().toString(), 1, LocalDate.now(), OrderType.SELL, 70);
        message = MessageBuilder
                .createMessage(mapper.writeValueAsString(sellOrder), headers);
        log.info("[SimpleStoreKafkaProducerApplication] - Sending orders type: {}", OrderType.SELL);
        streamBridge.send("orderssell-out-0", message);
        log.info("[SimpleStoreKafkaProducerApplication] - Orders sended");
    }
}
