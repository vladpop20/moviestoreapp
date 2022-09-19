package com.stackroute.AuthenticationService.repository;

import com.stackroute.AuthenticationService.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, String> {

	Optional<UserAuth> findUserByEmailAndPassword(String email, String pass);
}
