package com.hyk.univ.user.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hyk.univ.user.domain.User;

public interface JpaUserRepository extends JpaRepository<User, Long> {

  Optional<User> findByLoginId(String loginId);

  boolean existsByLoginId(String loginId);

  @Query("SELECT MAX(u.studentProfile.studentNumber) FROM User u " +
      "WHERE u.studentProfile.studentNumber LIKE CONCAT(:prefix, '%')")
  String findMaxStudentNumberByYearPrefix(@Param("prefix") String prefix);

}
