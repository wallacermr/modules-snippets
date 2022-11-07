package br.com.wallace.rafael;

import br.com.wallace.rafael.payload.Order;
import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@SpringBootApplication
public class SimpleStoreKafkaConsumerApplication {

    public static void main( String[] args ) {
        SpringApplication.run(SimpleStoreKafkaConsumerApplication.class, args);
    }

    @Bean
    public Consumer<Message<Order>> receiveOrderBuy() {
        return message -> {
            val order = message.getPayload();
            System.out.println(order);
        };
    }

    @Bean
    public Consumer<Message<Order>> receiveOrderSell() {
        return message -> {
            val order = message.getPayload();
            System.out.println(order);
        };
    }

}
