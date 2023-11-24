package org.bjsalcedo.springcloud.svc.users.dao.repositories;

import org.bjsalcedo.springcloud.svc.users.dao.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
