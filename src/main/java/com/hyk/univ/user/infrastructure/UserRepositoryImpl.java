package com.hyk.univ.user.infrastructure;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.hyk.univ.user.domain.User;
import com.hyk.univ.user.domain.UserRepository;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

  private final JpaUserRepository jpaUserRepository;

  @Override
  public User save(User user) {
    return this.jpaUserRepository.save(user);
  }

  @Override
  public Optional<User> findById(Long id) {
    return this.jpaUserRepository.findById(id);
  }

  @Override
  public Optional<User> findByLoginId(String loginId) {
    return this.jpaUserRepository.findByLoginId(loginId);
  }

  @Override
  public List<User> findAll() {
    return this.jpaUserRepository.findAll();
  }

  @Override
  public boolean existsByLoginId(String loginId) {
    return this.jpaUserRepository.existsByLoginId(loginId);
  }

  @Override
  public void deleteById(Long id) {
    this.jpaUserRepository.deleteById(id);
  }

  @Override
  public String findMaxStudentNumberByYearPrefix(String yearPrefix) {
    return this.jpaUserRepository.findMaxStudentNumberByYearPrefix(yearPrefix);
  }

}
