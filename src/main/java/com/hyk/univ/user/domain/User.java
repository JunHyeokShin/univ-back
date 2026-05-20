package com.hyk.univ.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String loginId;

  @Column(nullable = false)
  private String password;

  private String name;

  private String contact;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role;

  @Embedded
  private StudentProfile studentProfile;

  @Embedded
  private ProfessorProfile professorProfile;

  private User(String loginId, String password, String name, String contact, Role role) {
    this.loginId = loginId;
    this.password = password;
    this.name = name;
    this.contact = contact;
    this.role = role;
  }

  public static User createStudent(
      String studentNumber,
      String password,
      String name,
      String contact,
      String department
  ) {
    User user = new User(studentNumber, password, name, contact, Role.STUDENT);
    user.studentProfile = new StudentProfile(studentNumber, department);
    return user;
  }

  public static User createProfessor(
      String professorNumber,
      String password,
      String name,
      String contact,
      String department
  ) {
    User user = new User(professorNumber, password, name, contact, Role.PROFESSOR);
    user.professorProfile = new ProfessorProfile(professorNumber, department);
    return user;
  }

  public static User createAdmin(
      String username,
      String password,
      String name,
      String contact
  ) {
    return new User(username, password, name, contact, Role.ADMIN);
  }

  public void updateContact(String contact) {
    this.contact = contact;
  }

  public void updatePassword(String encodedPassword) {
    this.password = encodedPassword;
  }

}
