package com.unla.users_service.repository;

import com.unla.users_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String username);
    List<User> findByIdTienda(long idTienda);
    void deleteByUserName(String username);

}