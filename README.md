# 🗄️ 2주차 과제 - SQL & JPA

## 과제 요구사항

1. 본인이 원하는 주제로 **JPA를 활용한 CRUD API**를 구현합니다.
2. `@OneToMany`, `@ManyToOne`을 사용하여 엔티티 간 연관관계를 설정합니다.
3. 본인이 설계한 **ERD 이미지**를 첨부합니다.
4. **Postman**을 통해 각 API를 테스트하고 결과를 첨부합니다.
5. 각 기능 실행 시 발생하는 **SQL 쿼리 로그**를 첨부합니다.
6. 기본적인 자바 컨벤션을 준수합니다.

### SQL 쿼리 로그 예시

| 기능 | API | 발생 SQL 쿼리 예시 |
|---|---|---|
| 데이터베이스 생성 | - | `CREATE DATABASE student_db;` |
| 강의 생성 | `POST /courses` | `INSERT INTO course (course_name, professor) VALUES ("데이터베이스", "김석환");` |
| 강의 전체 조회 | `GET /courses` | `SELECT * FROM course;` |
| 학생 생성 | `POST /students` | `INSERT INTO student (name, major, course_id) VALUES ("안정민", "소프트융합학과", 1);` |
| 학생 단건 조회 | `GET /students/1` | `SELECT * FROM student WHERE id = 1;` |
| 학생 전체 조회 | `GET /students` | `SELECT * FROM student;` |
| 학생 수정 | `PATCH /students/1` | `UPDATE student SET name = "이길수", major = "소프트웨어융합학과", course_id = 1 WHERE id = 1;` |
| 학생 삭제 | `DELETE /students/1` | `DELETE FROM student WHERE id = 1;` |
| 강의 삭제 | `DELETE /courses/1` | `DELETE FROM course WHERE id = 1;` |

---

## 제출 형식

1. 과제 레포지토리를 Fork합니다.
2. Fork한 개인 레포지토리를 Git Clone합니다.
3. 본인의 영문 이름으로 폴더를 생성합니다.
   - ex. `kimseokhwan`
4. 이름 폴더 아래에 `Jpa` 이름으로 Spring Boot 프로젝트를 생성합니다.
5. 이름 폴더 아래에 다음 폴더를 생성하고 결과물을 첨부합니다.
   - `Postman`: API 테스트 결과 이미지
   - `ERD`: 설계한 ERD 이미지
   - `SQL`: CRUD 실행 시 발생한 SQL 쿼리 로그
6. 과제 레포지토리로 PR을 생성합니다.

---

## PR 요구사항

- **PR 제목:** `26-27-Backend-Assignment-02_본인이름`
- **PR 본문:** 본인이 구현한 SQL & JPA 프로젝트에 대한 소개를 작성합니다.

---

## 과제 마감 기한

- **제출 기한:** 11월 1일 (일) 23:59:59
- **리팩토링 제출:** 11월 4일 (수) 23:59:59
