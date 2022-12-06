package br.com.wallace.rafael.service;

import br.com.wallace.rafael.document.UserDocument;

import java.util.List;

public interface UserService {

    List<UserDocument> findAllUsers();
}
