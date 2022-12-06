package br.com.wallace.rafael;

import br.com.wallace.rafael.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@AllArgsConstructor
@SpringBootApplication
public class DistributedLockApplication implements CommandLineRunner {

    private final UserRepository userRepository;

    public static void main( String[] args ) {
        SpringApplication.run(DistributedLockApplication.class, args);
    }



    @Override
    public void run(String... args) throws Exception {

    }
}
