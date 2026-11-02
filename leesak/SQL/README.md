# 기능별 실제 SQL 실행 로그

2026년 11월 2일 13:09:38~39 (KST), 제출 프로젝트에서 빌드한 JAR에 Postman 컬렉션을 실행하면서 기록한 실제 Hibernate 로그입니다. 별도 포트 `18082`와 H2 메모리 DB를 사용했습니다.

- [전체 실행 로그](execution.log): HTTP 요청 21개·응답 상태, SQL 30개 및 바인딩 값. 서버 시작·종료 정보는 제외했습니다.
- [Postman CLI 실행 기록](../Postman/postman-cli-output.txt): 요청 21개·검증 32개 모두 통과, 실패 0개.
- 같은 제출 소스에서 `./gradlew test bootJar`가 성공했고 HTTP 통합 테스트 5개가 모두 통과했습니다.

## 직접 촬영한 콘솔 이미지

| 내용 | 실제 이미지 |
|---|---|
| 장르 생성 INSERT와 외래 키 제약 | [콘솔 전체](01_create_genre_sql_console.png) |
| 장르 생성 INSERT 두 건 | [생성 SQL](02_create_genres_sql.png) |
| 장르와 도서의 LEFT JOIN 조회 | [조회 SQL](03_genres_books_left_join_sql.png) |

위 이미지는 수동 테스트 당시의 콘솔 캡처입니다. 아래 기능별 발췌와 전체 로그는 별도 DB에서 전체 컬렉션을 재검증한 실행 기록으로, 서로 다른 실행의 증빙입니다. 캡처에 보이지 않는 도서 CRUD와 장르 수정·삭제도 전체 로그와 아래 발췌에 포함했습니다.

## 기능별 주요 SQL

| 기능 | API | 응답 | 주요 SQL |
|---|---|---|---|
| 장르 생성 | `POST /genres` | 201 | `INSERT` |
| 장르 전체 조회 | `GET /genres` | 200 | `SELECT` |
| 장르 단건 조회 | `GET /genres/1` | 200 | `SELECT` |
| 장르 수정 | `PATCH /genres/1` | 200 | `UPDATE` |
| 장르 삭제 | `DELETE /genres/1` | 204 | `DELETE` |
| 도서 생성 | `POST /books` | 201 | `INSERT` |
| 도서 전체 조회 | `GET /books` | 200 | `SELECT` |
| 도서 단건 조회 | `GET /books/1` | 200 | `SELECT` |
| 도서 제목·장르 수정 | `PATCH /books/1` | 200 | `UPDATE` |
| 도서 삭제 | `DELETE /books/1` | 204 | `DELETE` |

`?`는 JDBC 준비 문장의 바인딩 자리이며, 이어지는 `binding parameter` 로그에서 실제 값을 확인할 수 있습니다. 관계 확인을 위해 함께 실행되는 SELECT도 발췌에 포함했습니다.

PATCH는 트랜잭션 내 엔티티 변경 감지로 UPDATE를 실행합니다. 장르에 도서가 남아 있으면 409를 반환하고 DELETE를 실행하지 않습니다. 잘못된 필수 입력은 INSERT 없이 400, 없는 리소스는 조회 후 404를 반환합니다. 이 오류 사례도 전체 로그에 포함됩니다.

## API별 실제 로그 발췌

### 장르 생성: `POST /genres`

```text
2026-11-02T13:09:38.560+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-1] o.s.web.servlet.DispatcherServlet        : POST "/genres", parameters={}
2026-11-02T13:09:38.628+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-1] org.hibernate.SQL                        :
    insert
    into
        genres
        (name, id)
    values
        (?, default)
2026-11-02T13:09:38.630+09:00 TRACE 33484 --- [submission-validation] [io-18082-exec-1] org.hibernate.orm.jdbc.bind              : binding parameter (1:VARCHAR) <- [문학]
2026-11-02T13:09:38.676+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-1] o.s.web.servlet.DispatcherServlet        : Completed 201 CREATED
```

### 장르 전체 조회: `GET /genres`

```text
2026-11-02T13:09:38.710+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-4] o.s.web.servlet.DispatcherServlet        : GET "/genres", parameters={}
2026-11-02T13:09:38.739+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-4] org.hibernate.SQL                        :
    select
        g1_0.id,
        b1_0.genre_id,
        b1_0.id,
        b1_0.author,
        b1_0.title,
        g1_0.name
    from
        genres g1_0
    left join
        books b1_0
            on g1_0.id=b1_0.genre_id
    order by
        g1_0.id,
        b1_0.id
2026-11-02T13:09:38.747+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-4] o.s.web.servlet.DispatcherServlet        : Completed 200 OK
```

### 장르 단건 조회: `GET /genres/1`

```text
2026-11-02T13:09:38.760+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-5] o.s.web.servlet.DispatcherServlet        : GET "/genres/1", parameters={}
2026-11-02T13:09:38.765+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-5] org.hibernate.SQL                        :
    select
        g1_0.id,
        g1_0.name,
        b1_0.genre_id,
        b1_0.id,
        b1_0.author,
        b1_0.title
    from
        genres g1_0
    left join
        books b1_0
            on g1_0.id=b1_0.genre_id
    where
        g1_0.id=?
    order by
        b1_0.id
2026-11-02T13:09:38.765+09:00 TRACE 33484 --- [submission-validation] [io-18082-exec-5] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [1]
2026-11-02T13:09:38.766+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-5] o.s.web.servlet.DispatcherServlet        : Completed 200 OK
```

### 장르 수정: `PATCH /genres/1`

```text
2026-11-02T13:09:38.778+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-6] o.s.web.servlet.DispatcherServlet        : PATCH "/genres/1", parameters={}
2026-11-02T13:09:38.780+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-6] org.hibernate.SQL                        :
    select
        g1_0.id,
        g1_0.name,
        b1_0.genre_id,
        b1_0.id,
        b1_0.author,
        b1_0.title
    from
        genres g1_0
    left join
        books b1_0
            on g1_0.id=b1_0.genre_id
    where
        g1_0.id=?
    order by
        b1_0.id
2026-11-02T13:09:38.780+09:00 TRACE 33484 --- [submission-validation] [io-18082-exec-6] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [1]
2026-11-02T13:09:38.782+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-6] org.hibernate.SQL                        :
    update
        genres
    set
        name=?
    where
        id=?
2026-11-02T13:09:38.783+09:00 TRACE 33484 --- [submission-validation] [io-18082-exec-6] org.hibernate.orm.jdbc.bind              : binding parameter (1:VARCHAR) <- [한국 문학]
2026-11-02T13:09:38.783+09:00 TRACE 33484 --- [submission-validation] [io-18082-exec-6] org.hibernate.orm.jdbc.bind              : binding parameter (2:BIGINT) <- [1]
2026-11-02T13:09:38.785+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-6] o.s.web.servlet.DispatcherServlet        : Completed 200 OK
```

### 장르 삭제: `DELETE /genres/1`

```text
2026-11-02T13:09:39.022+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-1] o.s.web.servlet.DispatcherServlet        : DELETE "/genres/1", parameters={}
2026-11-02T13:09:39.023+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-1] org.hibernate.SQL                        :
    select
        g1_0.id,
        g1_0.name,
        b1_0.genre_id,
        b1_0.id,
        b1_0.author,
        b1_0.title
    from
        genres g1_0
    left join
        books b1_0
            on g1_0.id=b1_0.genre_id
    where
        g1_0.id=?
    order by
        b1_0.id
2026-11-02T13:09:39.023+09:00 TRACE 33484 --- [submission-validation] [io-18082-exec-1] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [1]
2026-11-02T13:09:39.024+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-1] org.hibernate.SQL                        :
    select
        b1_0.id
    from
        books b1_0
    where
        b1_0.genre_id=?
    fetch
        first ? rows only
2026-11-02T13:09:39.024+09:00 TRACE 33484 --- [submission-validation] [io-18082-exec-1] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [1]
2026-11-02T13:09:39.024+09:00 TRACE 33484 --- [submission-validation] [io-18082-exec-1] org.hibernate.orm.jdbc.bind              : binding parameter (2:INTEGER) <- [1]
2026-11-02T13:09:39.025+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-1] org.hibernate.SQL                        :
    delete
    from
        genres
    where
        id=?
2026-11-02T13:09:39.025+09:00 TRACE 33484 --- [submission-validation] [io-18082-exec-1] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [1]
2026-11-02T13:09:39.026+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-1] o.s.web.servlet.DispatcherServlet        : Completed 204 NO_CONTENT
```

### 도서 생성: `POST /books`

```text
2026-11-02T13:09:38.795+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-7] o.s.web.servlet.DispatcherServlet        : POST "/books", parameters={}
2026-11-02T13:09:38.800+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-7] org.hibernate.SQL                        :
    select
        g1_0.id,
        g1_0.name,
        b1_0.genre_id,
        b1_0.id,
        b1_0.author,
        b1_0.title
    from
        genres g1_0
    left join
        books b1_0
            on g1_0.id=b1_0.genre_id
    where
        g1_0.id=?
    order by
        b1_0.id
2026-11-02T13:09:38.800+09:00 TRACE 33484 --- [submission-validation] [io-18082-exec-7] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [1]
2026-11-02T13:09:38.801+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-7] org.hibernate.SQL                        :
    insert
    into
        books
        (author, genre_id, title, id)
    values
        (?, ?, ?, default)
2026-11-02T13:09:38.802+09:00 TRACE 33484 --- [submission-validation] [io-18082-exec-7] org.hibernate.orm.jdbc.bind              : binding parameter (1:VARCHAR) <- [한강]
2026-11-02T13:09:38.802+09:00 TRACE 33484 --- [submission-validation] [io-18082-exec-7] org.hibernate.orm.jdbc.bind              : binding parameter (2:BIGINT) <- [1]
2026-11-02T13:09:38.802+09:00 TRACE 33484 --- [submission-validation] [io-18082-exec-7] org.hibernate.orm.jdbc.bind              : binding parameter (3:VARCHAR) <- [소년이 온다]
2026-11-02T13:09:38.804+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-7] o.s.web.servlet.DispatcherServlet        : Completed 201 CREATED
```

### 도서 전체 조회: `GET /books`

```text
2026-11-02T13:09:38.815+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-8] o.s.web.servlet.DispatcherServlet        : GET "/books", parameters={}
2026-11-02T13:09:38.820+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-8] org.hibernate.SQL                        :
    select
        b1_0.id,
        b1_0.author,
        b1_0.genre_id,
        g1_0.id,
        g1_0.name,
        b1_0.title
    from
        books b1_0
    join
        genres g1_0
            on g1_0.id=b1_0.genre_id
    order by
        b1_0.id
2026-11-02T13:09:38.822+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-8] o.s.web.servlet.DispatcherServlet        : Completed 200 OK
```

### 도서 단건 조회: `GET /books/1`

```text
2026-11-02T13:09:38.833+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-9] o.s.web.servlet.DispatcherServlet        : GET "/books/1", parameters={}
2026-11-02T13:09:38.835+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-9] org.hibernate.SQL                        :
    select
        b1_0.id,
        b1_0.author,
        b1_0.genre_id,
        g1_0.id,
        g1_0.name,
        b1_0.title
    from
        books b1_0
    join
        genres g1_0
            on g1_0.id=b1_0.genre_id
    where
        b1_0.id=?
2026-11-02T13:09:38.836+09:00 TRACE 33484 --- [submission-validation] [io-18082-exec-9] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [1]
2026-11-02T13:09:38.837+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-9] o.s.web.servlet.DispatcherServlet        : Completed 200 OK
```

### 도서 제목·장르 수정: `PATCH /books/1`

```text
2026-11-02T13:09:38.962+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-8] o.s.web.servlet.DispatcherServlet        : PATCH "/books/1", parameters={}
2026-11-02T13:09:38.963+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-8] org.hibernate.SQL                        :
    select
        b1_0.id,
        b1_0.author,
        b1_0.genre_id,
        g1_0.id,
        g1_0.name,
        b1_0.title
    from
        books b1_0
    join
        genres g1_0
            on g1_0.id=b1_0.genre_id
    where
        b1_0.id=?
2026-11-02T13:09:38.964+09:00 TRACE 33484 --- [submission-validation] [io-18082-exec-8] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [1]
2026-11-02T13:09:38.965+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-8] org.hibernate.SQL                        :
    select
        g1_0.id,
        g1_0.name,
        b1_0.genre_id,
        b1_0.id,
        b1_0.author,
        b1_0.title
    from
        genres g1_0
    left join
        books b1_0
            on g1_0.id=b1_0.genre_id
    where
        g1_0.id=?
    order by
        b1_0.id
2026-11-02T13:09:38.965+09:00 TRACE 33484 --- [submission-validation] [io-18082-exec-8] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [2]
2026-11-02T13:09:38.966+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-8] org.hibernate.SQL                        :
    select
        b1_0.genre_id,
        b1_0.id,
        b1_0.author,
        b1_0.title
    from
        books b1_0
    where
        b1_0.genre_id=?
    order by
        b1_0.id
2026-11-02T13:09:38.966+09:00 TRACE 33484 --- [submission-validation] [io-18082-exec-8] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [1]
2026-11-02T13:09:38.968+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-8] org.hibernate.SQL                        :
    update
        books
    set
        author=?,
        genre_id=?,
        title=?
    where
        id=?
2026-11-02T13:09:38.968+09:00 TRACE 33484 --- [submission-validation] [io-18082-exec-8] org.hibernate.orm.jdbc.bind              : binding parameter (1:VARCHAR) <- [한강]
2026-11-02T13:09:38.968+09:00 TRACE 33484 --- [submission-validation] [io-18082-exec-8] org.hibernate.orm.jdbc.bind              : binding parameter (2:BIGINT) <- [2]
2026-11-02T13:09:38.968+09:00 TRACE 33484 --- [submission-validation] [io-18082-exec-8] org.hibernate.orm.jdbc.bind              : binding parameter (3:VARCHAR) <- [채식주의자]
2026-11-02T13:09:38.968+09:00 TRACE 33484 --- [submission-validation] [io-18082-exec-8] org.hibernate.orm.jdbc.bind              : binding parameter (4:BIGINT) <- [1]
2026-11-02T13:09:38.969+09:00 DEBUG 33484 --- [submission-validation] [io-18082-exec-8] o.s.web.servlet.DispatcherServlet        : Completed 200 OK
```

### 도서 삭제: `DELETE /books/1`

```text
2026-11-02T13:09:38.993+09:00 DEBUG 33484 --- [submission-validation] [o-18082-exec-10] o.s.web.servlet.DispatcherServlet        : DELETE "/books/1", parameters={}
2026-11-02T13:09:38.994+09:00 DEBUG 33484 --- [submission-validation] [o-18082-exec-10] org.hibernate.SQL                        :
    select
        b1_0.id,
        b1_0.author,
        b1_0.genre_id,
        g1_0.id,
        g1_0.name,
        b1_0.title
    from
        books b1_0
    join
        genres g1_0
            on g1_0.id=b1_0.genre_id
    where
        b1_0.id=?
2026-11-02T13:09:38.994+09:00 TRACE 33484 --- [submission-validation] [o-18082-exec-10] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [1]
2026-11-02T13:09:38.997+09:00 DEBUG 33484 --- [submission-validation] [o-18082-exec-10] org.hibernate.SQL                        :
    delete
    from
        books
    where
        id=?
2026-11-02T13:09:38.997+09:00 TRACE 33484 --- [submission-validation] [o-18082-exec-10] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [1]
2026-11-02T13:09:38.998+09:00 DEBUG 33484 --- [submission-validation] [o-18082-exec-10] o.s.web.servlet.DispatcherServlet        : Completed 204 NO_CONTENT
```
