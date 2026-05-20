package com.hyk.univ.user.domain;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

  User save(User user);

  Optional<User> findById(Long id);

  Optional<User> findByLoginId(String loginId);

  List<User> findAll();

  boolean existsByLoginId(String loginId);

  void deleteById(Long id);

  String findMaxStudentNumberByYearPrefix(String yearPrefix);

}
