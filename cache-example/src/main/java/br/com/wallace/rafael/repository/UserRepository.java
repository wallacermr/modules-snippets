package br.com.wallace.rafael.repository;

import br.com.wallace.rafael.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
