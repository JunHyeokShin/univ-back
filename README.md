# Univ (DDD + Spring Boot + JWT)

대학교 학사 관리 시스템을 DDD로 구현한 Spring Boot 프로젝트입니다.

## 패키지 구조 (DDD)

```
com.university
├── common
│   ├── config        # SecurityConfig, DataInitializer
│   ├── exception     # BusinessException, ErrorCode, GlobalExceptionHandler
│   └── security      # JwtProvider, JwtAuthenticationFilter, AuthUser
├── user              # 사용자 (학생/교수/관리자, 인증)
├── course            # 과목 개설/폐강
├── enrollment        # 수강신청 (제약 도메인 서비스)
├── grade             # 성적·출석 (F학점 알고리즘 포함)
└── schedule          # 학사일정 모드 (@RequireMode AOP)
```

각 모듈은 `domain → application → infrastructure → api` 4계층입니다.
모듈 간 호출은 항상 다른 모듈의 **application 서비스**를 통해서만 합니다.

## API 사용 흐름

### 1. 로그인 → JWT 발급

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"loginId":"admin","password":"admin1234"}'
```

응답:

```json
{
    "token": "eyJhbGciOi...",
    "role": "ADMIN",
    "userId": 1,
    "name": "기본 관리자"
}
```

이후 모든 요청에 `Authorization: Bearer {token}` 헤더를 포함합니다.

### 2. 관리자가 학생/교수 계정 생성

```bash
# 학생 - 학번은 자동 생성 (예: 20260001)
curl -X POST http://localhost:8080/api/admin/users/students \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"password":"pass1234","name":"홍길동","contact":"010-1111-2222","department":"컴퓨터공학과"}'

# 교수
curl -X POST http://localhost:8080/api/admin/users/professors \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"professorNumber":"P001","password":"pass1234","name":"김교수","contact":"010-3333-4444","department":"컴퓨터공학과"}'
```

### 3. 관리자가 과목 개설 + 학사일정 모드를 ENROLLMENT 로 변경

```bash
curl -X POST http://localhost:8080/api/courses \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name":"자바프로그래밍","type":"MAJOR","credit":3,"capacity":30,"professorId":2,"semester":"2026-1"}'

curl -X PUT http://localhost:8080/api/schedule/mode \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"mode":"ENROLLMENT"}'
```

### 4. 학생이 수강 신청 (ENROLLMENT 모드일 때만)

```bash
curl -X POST http://localhost:8080/api/enrollments \
  -H "Authorization: Bearer $STUDENT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"courseId":1}'
```

다른 모드에서 시도하면 `WRONG_ACADEMIC_MODE` 에러가 반환됩니다.

### 5. 모드를 GRADING으로 → 교수 성적 입력 → VIEWING → 학생 조회

```bash
# 관리자가 모드 변경
PUT /api/schedule/mode  body: {"mode":"GRADING"}

# 교수가 성적 입력
POST /api/grades/scores
{ "studentId":3, "courseId":1, "attendanceScore":90, "assignmentScore":85,
  "midtermScore":80, "finalScore":75 }

# 교수가 출석 입력 (결석 4회 이상이면 F 자동 부여)
POST /api/grades/attendance
{ "studentId":3, "courseId":1, "presentCount":12, "lateCount":2, "absentCount":1 }

# 모드 VIEWING으로 변경 후 학생이 본인 성적 조회
GET /api/grades/me
```

## 주요 도메인 규칙

### 수강신청 제약 (`EnrollmentPolicy`)

- **중복 신청 금지**: `(student_id, course_id)` 유니크 제약 + 사전 검사
- **학기당 최대 18학점**: 현재 학기 누적 학점 + 신규 학점 > 18 → 에러
- **정원 초과 / 폐강 검사**: `Course` 애그리거트가 자체 검증 (`increaseEnrollment()`)

### 성적 산출 알고리즘 (`GradeCalculator`)

가중치: 출석 10% + 과제 20% + 중간 35% + 기말 35%

1. **결석 4회 이상** → 무조건 `F` (총점 무관)
2. **총점 60점 미만** → `F`
3. 그 외:
    - 95↑ A+, 90↑ A, 85↑ B+, 80↑ B, 75↑ C+, 70↑ C, 65↑ D+, 60↑ D

### 과목 폐강 조건 (`Course.close()`)

- 수강신청 인원이 3명 미만일 때만 폐강 가능. 그 이상이면 `CANNOT_CLOSE_COURSE`.

### 학사일정 모드 (`@RequireMode` AOP)

- `ENROLLMENT`: 수강신청/취소만 허용
- `GRADING`: 성적 입력만 허용
- `VIEWING`: 조회만 허용 (기본값)

`@RequireMode(AcademicMode.X)`가 붙은 메서드는 AOP가 현재 모드를 확인하고, 일치하지 않으면 `WRONG_ACADEMIC_MODE` 예외를 발생시킵니다.
조회 메서드에는 어노테이션이 없으므로 모든 모드에서 동작합니다.

## 역할별 접근 가능 API 요약

| Endpoint                             | 역할        | 설명                    |
|--------------------------------------|-----------|-----------------------|
| `POST /api/auth/login`               | -         | 로그인                   |
| `GET /api/users/me`                  | 전체        | 내 정보 조회               |
| `PATCH /api/users/me`                | 전체        | 비밀번호/연락처 수정           |
| `POST /api/admin/users/students`     | ADMIN     | 학생 생성 (학번 자동)         |
| `POST /api/admin/users/professors`   | ADMIN     | 교수 생성                 |
| `POST /api/admin/users/admins`       | ADMIN     | 관리자 생성                |
| `DELETE /api/admin/users/{id}`       | ADMIN     | 계정 삭제                 |
| `GET /api/courses?type=MAJOR`        | 전체        | 개설 과목 조회 (필터링)        |
| `GET /api/courses/mine`              | PROFESSOR | 내 담당 강의               |
| `POST /api/courses`                  | ADMIN     | 과목 개설                 |
| `POST /api/courses/{id}/close`       | ADMIN     | 과목 폐강 (3명 미만)         |
| `POST /api/enrollments`              | STUDENT   | 수강 신청 (ENROLLMENT 모드) |
| `DELETE /api/enrollments/{id}`       | STUDENT   | 수강 취소 (ENROLLMENT 모드) |
| `GET /api/enrollments/me`            | STUDENT   | 내 수강 목록               |
| `GET /api/enrollments/courses/{id}`  | PROF/ADM  | 과목 수강생 명단             |
| `POST /api/grades/scores`            | PROFESSOR | 성적 입력 (GRADING 모드)    |
| `POST /api/grades/attendance`        | PROFESSOR | 출석부 입력                |
| `GET /api/grades/me?semester=2026-1` | STUDENT   | 성적 조회 (학기별/전체)        |
| `GET /api/grades/courses/{id}`       | PROFESSOR | 과목 수강생 성적             |
| `GET /api/schedule/mode`             | 전체        | 현재 모드 조회              |
| `PUT /api/schedule/mode`             | ADMIN     | 모드 변경                 |

## DDD 설계 결정 요약

1. **단일 User 애그리거트 + Role + Profile VO**: Student/Professor/Admin을 별도 애그리거트로 나누는 대신, Role enum과 역할별
   VO(`StudentProfile`, `ProfessorProfile`)로 단순화.

2. **JPA Entity = 도메인 Entity**: 학습/과제 목적이므로 도메인 모델과 영속화 모델을 분리하지 않음. 매핑 코드 절반 감소.

3. **Repository 인터페이스는 도메인에, JPA 구현은 인프라에**: 도메인 계층이 JPA를 알지 못하도록 분리.

4. **도메인 서비스 두 곳**:
    - `EnrollmentPolicy`: 다중 애그리거트 검증 (Enrollment 목록 + Course)
    - `GradeCalculator`: F학점 알고리즘. 외부 의존이 없어 순수 도메인 로직.

5. **학사일정 제어는 AOP**: 컨트롤러나 서비스에 if-else를 흩뿌리지 않고 `@RequireMode` 어노테이션 + Aspect로 횡단 관심사 분리.

6. **모듈 간 결합**: enrollment → course, grade → course 만 존재. 단방향 의존을 유지.
