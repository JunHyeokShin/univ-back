package com.hyk.univ.user.application.dto;

import com.hyk.univ.user.domain.Role;
import com.hyk.univ.user.domain.User;

public record UserResponse(
    Long id,
    String loginId,
    String name,
    String contact,
    Role role,
    String studentNumber,
    String professorNumber,
    String department
) {

  public static UserResponse from(User user) {
    String dept = null;
    String studentNo = null;
    String profNo = null;
    if (user.getStudentProfile() != null) {
      studentNo = user.getStudentProfile().getStudentNumber();
      dept = user.getStudentProfile().getDepartment();
    }
    else if (user.getProfessorProfile() != null) {
      profNo = user.getProfessorProfile().getProfessorNumber();
      dept = user.getProfessorProfile().getDepartment();
    }
    return new UserResponse(user.getId(), user.getLoginId(), user.getName(),
        user.getContact(), user.getRole(), studentNo, profNo, dept);
  }

}
