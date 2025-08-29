package dev.rigom.springcloud.msvc.users.service;

import dev.rigom.springcloud.msvc.users.client.CourseFeingClient;
import dev.rigom.springcloud.msvc.users.models.entity.User;
import dev.rigom.springcloud.msvc.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements IUSerService{

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CourseFeingClient courseFeingClient;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
        courseFeingClient.deleteCourseByUserId(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllByIds(Iterable<Long> ids) {
        return (List<User>) userRepository.findAllById(ids);
    }
}
