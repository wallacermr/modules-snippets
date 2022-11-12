package br.com.wallace.rafael.repository;

import br.com.wallace.rafael.document.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Cacheable("users")
    Optional<User> findByName(String name);
}
