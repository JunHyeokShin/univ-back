package com.hyk.univ.grade.application;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.univ.common.exception.BusinessException;
import com.hyk.univ.common.exception.ErrorCode;
import com.hyk.univ.course.domain.Course;
import com.hyk.univ.course.domain.CourseRepository;
import com.hyk.univ.grade.application.dto.GradeResponse;
import com.hyk.univ.grade.application.dto.InputGradeRequest;
import com.hyk.univ.grade.application.dto.UpdateAttendanceRequest;
import com.hyk.univ.grade.domain.Attendance;
import com.hyk.univ.grade.domain.Grade;
import com.hyk.univ.grade.domain.GradeCalculator;
import com.hyk.univ.grade.domain.GradeRepository;
import com.hyk.univ.grade.domain.Score;
import com.hyk.univ.schedule.application.RequireMode;
import com.hyk.univ.schedule.domain.AcademicMode;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GradeService {

  private final GradeRepository gradeRepository;
  private final CourseRepository courseRepository;
  private final GradeCalculator gradeCalculator;

  public List<GradeResponse> findMyGrades(Long studentId) {
    return this.gradeRepository.findByStudentId(studentId).stream()
        .map(GradeResponse::from).collect(Collectors.toList());
  }

  public List<GradeResponse> findMyGradesBySemester(Long studentId, String semester) {
    return this.gradeRepository.findByStudentIdAndSemester(studentId, semester).stream()
        .map(GradeResponse::from).collect(Collectors.toList());
  }

  public List<GradeResponse> findByCourse(Long professorId, Long courseId) {
    ensureOwnCourse(professorId, courseId);
    return this.gradeRepository.findByCourseId(courseId).stream()
        .map(GradeResponse::from).collect(Collectors.toList());
  }

  @Transactional
  @RequireMode(AcademicMode.GRADING)
  public GradeResponse inputGrade(Long professorId, InputGradeRequest request) {
    Course course = ensureOwnCourse(professorId, request.courseId());
    Grade grade = this.gradeRepository
        .findByStudentIdAndCourseId(request.studentId(), request.courseId())
        .orElseGet(() -> this.gradeRepository.save(
            Grade.initial(request.studentId(), request.courseId(), course.getSemester())));
    Score newScore = new Score(request.attendanceScore(), request.assignmentScore(),
        request.midtermScore(), request.finalScore());
    grade.updateScore(newScore, this.gradeCalculator);
    return GradeResponse.from(this.gradeRepository.save(grade));
  }

  @Transactional
  @RequireMode(AcademicMode.GRADING)
  public GradeResponse updateAttendance(Long professorId, UpdateAttendanceRequest request) {
    Course course = ensureOwnCourse(professorId, request.courseId());
    Grade grade = this.gradeRepository
        .findByStudentIdAndCourseId(request.studentId(), request.courseId())
        .orElseGet(() -> this.gradeRepository.save(
            Grade.initial(request.studentId(), request.courseId(), course.getSemester())));
    Attendance newAttendance = new Attendance(request.presentCount(), request.lateCount(), request.absentCount());
    grade.updateAttendance(newAttendance, this.gradeCalculator);
    return GradeResponse.from(this.gradeRepository.save(grade));
  }

  private Course ensureOwnCourse(Long professorId, Long courseId) {
    Course course = this.courseRepository.findById(courseId)
        .orElseThrow(() -> new BusinessException(ErrorCode.COURSE_NOT_FOUND));
    if (!course.getProfessorId().equals(professorId)) {
      throw new BusinessException(ErrorCode.NOT_OWN_COURSE);
    }
    return course;
  }

}
