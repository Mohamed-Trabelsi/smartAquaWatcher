package com.example.projectapi.Repository;

import com.example.projectapi.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.jws.soap.SOAPBinding;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByLogin(String login);
    User findByEmail(String email);
    User findByid(Long id);
}
