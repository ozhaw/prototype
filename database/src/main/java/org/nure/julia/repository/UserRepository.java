package org.nure.julia.repository;

import org.nure.julia.entity.user.WebUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<WebUser, Long> {
    Optional<WebUser> findByEmail(String email);

    Optional<WebUser> findByEmailAndPassword(String email, String password);
}
