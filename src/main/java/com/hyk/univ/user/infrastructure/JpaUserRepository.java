package com.hyk.univ.user.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyk.univ.user.domain.User;

public interface JpaUserRepository extends JpaRepository<User, Long> {

  Optional<User> findByLoginId(String loginId);

  boolean existsByLoginId(String loginId);

}
