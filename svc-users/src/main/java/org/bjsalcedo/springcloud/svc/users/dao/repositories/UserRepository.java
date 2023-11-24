package org.bjsalcedo.springcloud.svc.users.dao.repositories;

import org.bjsalcedo.springcloud.svc.users.dao.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
