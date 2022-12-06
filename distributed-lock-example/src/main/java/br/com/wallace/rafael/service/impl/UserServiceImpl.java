package br.com.wallace.rafael.service.impl;

import br.com.wallace.rafael.document.UserDocument;
import br.com.wallace.rafael.repository.UserRepository;
import br.com.wallace.rafael.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDocument> findAllUsers() {
        return userRepository.findAll();
    }
}
