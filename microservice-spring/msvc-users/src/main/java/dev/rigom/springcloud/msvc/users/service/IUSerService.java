package dev.rigom.springcloud.msvc.users.service;

import dev.rigom.springcloud.msvc.users.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUSerService {

    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    void deleteById(Long id);
    Optional<User> findByEmail(String email);

    List<User> findAllByIds(Iterable<Long> ids);
}
