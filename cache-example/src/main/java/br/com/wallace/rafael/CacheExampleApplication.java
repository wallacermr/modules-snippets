package br.com.wallace.rafael;

import br.com.wallace.rafael.document.User;
import br.com.wallace.rafael.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@Slf4j
public class CacheExampleApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main( String[] args ) {
        SpringApplication.run(CacheExampleApplication.class, args);
    }

    @Override
    public void run(String... args) {
        save();
//        getOrders();
    }

    private void save() {

        User user1 = User.builder()
                .birthDate(LocalDate.of(1988, 5, 15))
                .name("joana")
                .build();

        User user2 = User.builder()
                .birthDate(LocalDate.of(1972, 6, 23))
                .build();

        User user3 = User.builder()
                .birthDate(LocalDate.of(1995, 1, 1))
                .name("mongo")
                .build();

        User user4 = User.builder()
                .birthDate(LocalDate.of(2004, 11, 25))
                .name("john")
                .build();

        User user5 = User.builder()
                .birthDate(LocalDate.of(2015, 12, 3))
                .name("mark")
                .build();

        List<User> users = Arrays.asList(user1, user2, user3, user4, user5);
        userRepository.saveAll(users);
    }

    @Transactional(readOnly = true)
    @Cacheable("orders")
    private List<User> getOrders() {
        List<User> users = userRepository.findAll();
        users.forEach(System.out::println);
        return users;
    }
}
