package org.bjsalcedo.springcloud.svc.users.services;

import lombok.RequiredArgsConstructor;
import org.bjsalcedo.springcloud.svc.users.clients.CourseClientRest;
import org.bjsalcedo.springcloud.svc.users.dao.entities.User;
import org.bjsalcedo.springcloud.svc.users.dao.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CourseClientRest courseClientRest;

    @Transactional(readOnly = true)
    public List<User> list() {
        return (List<User>) userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> byId(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
        courseClientRest.deleteCourseUserById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> byEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<User> byIds(List<Long> ids) {
        return (List<User>) userRepository.findAllById(ids);
    }
}
