# 실제 SQL 실행 로그

2026년 11월 1일 23:48:38 (KST), 실제 서버에 Postman CLI 컬렉션을 실행하면서 기록한 Hibernate 로그입니다. DB는 H2 2.4.240, Hibernate는 7.4.13.Final입니다. 모든 SQL과 바인딩 값은 실행 결과에서 발췌했으며 줄 끝 공백만 정리했습니다.

- [execution.log](execution.log): Postman 요청 21개의 HTTP 메서드·경로, 실제 SQL, 바인딩 값, HTTP 응답 상태 전체. 서버 시작 정보와 로컬 파일 경로는 제외했습니다.
- [Postman CLI 원본 출력](../Postman/postman-cli-output.txt): 요청 21개 및 검증 32개 통과, 실패 0개.
- HTTP 통합 테스트도 `./gradlew test`에서 5개 통과했습니다(실패 0, 오류 0).

SQL의 `?`는 JDBC 준비 문장의 바인딩 자리입니다. 바로 이어지는 `binding parameter` 로그로 실제 값을 확인할 수 있습니다. 아래는 각 API 요청 구간을 그대로 발췌한 것입니다. 엔티티 조회와 관계 조회가 함께 실행되는 경우 부가 SELECT도 모두 포함했습니다.

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
| 도서 수정 및 장르 이동 | `PATCH /books/1` | 200 | `UPDATE` |
| 도서 삭제 | `DELETE /books/1` | 204 | `DELETE` |

## 트랜잭션과 연관관계

`PATCH`는 트랜잭션 안에서 관리 상태 엔티티의 필드를 변경합니다. 별도 수정 쿼리를 직접 작성하지 않아도 변경 감지(dirty checking)에 의해 커밋 시 UPDATE가 발생합니다. 도서를 다른 장르로 옮기면 실제 `books.genre_id` 값이 바뀝니다.

장르에 도서가 남아 있으면 존재 확인 SELECT 후 409를 반환하며 DELETE는 실행하지 않습니다. 잘못된 필수 입력은 400으로 거절하고 INSERT를 실행하지 않습니다. 없는 도서·장르는 조회 SELECT 후 404를 반환합니다. 해당 실패 사례도 `execution.log`에 포함되어 있습니다.

## API별 실제 로그 발췌

### 장르 생성: `POST /genres`

```text
2026-11-01T23:48:38.331+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-3] o.s.web.servlet.DispatcherServlet        : POST "/genres", parameters={}
2026-11-01T23:48:38.364+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-3] org.hibernate.SQL                        :
    insert
    into
        genres
        (name, id)
    values
        (?, default)
2026-11-01T23:48:38.365+09:00 TRACE 22128 --- [books-genres-api] [nio-8080-exec-3] org.hibernate.orm.jdbc.bind              : binding parameter (1:VARCHAR) <- [문학]
2026-11-01T23:48:38.376+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-3] o.s.web.servlet.DispatcherServlet        : Completed 201 CREATED
```

### 장르 전체 조회: `GET /genres`

```text
2026-11-01T23:48:38.409+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-5] o.s.web.servlet.DispatcherServlet        : GET "/genres", parameters={}
2026-11-01T23:48:38.410+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-5] org.hibernate.SQL                        :
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
2026-11-01T23:48:38.413+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-5] o.s.web.servlet.DispatcherServlet        : Completed 200 OK
```

### 장르 단건 조회: `GET /genres/1`

```text
2026-11-01T23:48:38.423+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-6] o.s.web.servlet.DispatcherServlet        : GET "/genres/1", parameters={}
2026-11-01T23:48:38.428+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-6] org.hibernate.SQL                        :
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
2026-11-01T23:48:38.429+09:00 TRACE 22128 --- [books-genres-api] [nio-8080-exec-6] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [1]
2026-11-01T23:48:38.430+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-6] o.s.web.servlet.DispatcherServlet        : Completed 200 OK
```

### 장르 수정: `PATCH /genres/1`

```text
2026-11-01T23:48:38.442+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-7] o.s.web.servlet.DispatcherServlet        : PATCH "/genres/1", parameters={}
2026-11-01T23:48:38.444+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-7] org.hibernate.SQL                        :
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
2026-11-01T23:48:38.444+09:00 TRACE 22128 --- [books-genres-api] [nio-8080-exec-7] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [1]
2026-11-01T23:48:38.446+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-7] org.hibernate.SQL                        :
    update
        genres
    set
        name=?
    where
        id=?
2026-11-01T23:48:38.447+09:00 TRACE 22128 --- [books-genres-api] [nio-8080-exec-7] org.hibernate.orm.jdbc.bind              : binding parameter (1:VARCHAR) <- [한국 문학]
2026-11-01T23:48:38.447+09:00 TRACE 22128 --- [books-genres-api] [nio-8080-exec-7] org.hibernate.orm.jdbc.bind              : binding parameter (2:BIGINT) <- [1]
2026-11-01T23:48:38.448+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-7] o.s.web.servlet.DispatcherServlet        : Completed 200 OK
```

### 장르 삭제: `DELETE /genres/1`

```text
2026-11-01T23:48:38.712+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-3] o.s.web.servlet.DispatcherServlet        : DELETE "/genres/1", parameters={}
2026-11-01T23:48:38.714+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-3] org.hibernate.SQL                        :
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
2026-11-01T23:48:38.714+09:00 TRACE 22128 --- [books-genres-api] [nio-8080-exec-3] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [1]
2026-11-01T23:48:38.715+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-3] org.hibernate.SQL                        :
    select
        b1_0.id
    from
        books b1_0
    where
        b1_0.genre_id=?
    fetch
        first ? rows only
2026-11-01T23:48:38.715+09:00 TRACE 22128 --- [books-genres-api] [nio-8080-exec-3] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [1]
2026-11-01T23:48:38.715+09:00 TRACE 22128 --- [books-genres-api] [nio-8080-exec-3] org.hibernate.orm.jdbc.bind              : binding parameter (2:INTEGER) <- [1]
2026-11-01T23:48:38.716+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-3] org.hibernate.SQL                        :
    delete
    from
        genres
    where
        id=?
2026-11-01T23:48:38.716+09:00 TRACE 22128 --- [books-genres-api] [nio-8080-exec-3] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [1]
2026-11-01T23:48:38.717+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-3] o.s.web.servlet.DispatcherServlet        : Completed 204 NO_CONTENT
```

### 도서 생성: `POST /books`

```text
2026-11-01T23:48:38.463+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-8] o.s.web.servlet.DispatcherServlet        : POST "/books", parameters={}
2026-11-01T23:48:38.471+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-8] org.hibernate.SQL                        :
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
2026-11-01T23:48:38.472+09:00 TRACE 22128 --- [books-genres-api] [nio-8080-exec-8] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [1]
2026-11-01T23:48:38.473+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-8] org.hibernate.SQL                        :
    insert
    into
        books
        (author, genre_id, title, id)
    values
        (?, ?, ?, default)
2026-11-01T23:48:38.474+09:00 TRACE 22128 --- [books-genres-api] [nio-8080-exec-8] org.hibernate.orm.jdbc.bind              : binding parameter (1:VARCHAR) <- [한강]
2026-11-01T23:48:38.474+09:00 TRACE 22128 --- [books-genres-api] [nio-8080-exec-8] org.hibernate.orm.jdbc.bind              : binding parameter (2:BIGINT) <- [1]
2026-11-01T23:48:38.474+09:00 TRACE 22128 --- [books-genres-api] [nio-8080-exec-8] org.hibernate.orm.jdbc.bind              : binding parameter (3:VARCHAR) <- [소년이 온다]
2026-11-01T23:48:38.479+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-8] o.s.web.servlet.DispatcherServlet        : Completed 201 CREATED
```

### 도서 전체 조회: `GET /books`

```text
2026-11-01T23:48:38.492+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-9] o.s.web.servlet.DispatcherServlet        : GET "/books", parameters={}
2026-11-01T23:48:38.496+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-9] org.hibernate.SQL                        :
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
2026-11-01T23:48:38.502+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-9] o.s.web.servlet.DispatcherServlet        : Completed 200 OK
```

### 도서 단건 조회: `GET /books/1`

```text
2026-11-01T23:48:38.517+09:00 DEBUG 22128 --- [books-genres-api] [io-8080-exec-10] o.s.web.servlet.DispatcherServlet        : GET "/books/1", parameters={}
2026-11-01T23:48:38.519+09:00 DEBUG 22128 --- [books-genres-api] [io-8080-exec-10] org.hibernate.SQL                        :
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
2026-11-01T23:48:38.519+09:00 TRACE 22128 --- [books-genres-api] [io-8080-exec-10] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [1]
2026-11-01T23:48:38.520+09:00 DEBUG 22128 --- [books-genres-api] [io-8080-exec-10] o.s.web.servlet.DispatcherServlet        : Completed 200 OK
```

### 도서 수정 및 장르 이동: `PATCH /books/1`

```text
2026-11-01T23:48:38.653+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-9] o.s.web.servlet.DispatcherServlet        : PATCH "/books/1", parameters={}
2026-11-01T23:48:38.654+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-9] org.hibernate.SQL                        :
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
2026-11-01T23:48:38.655+09:00 TRACE 22128 --- [books-genres-api] [nio-8080-exec-9] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [1]
2026-11-01T23:48:38.656+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-9] org.hibernate.SQL                        :
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
2026-11-01T23:48:38.656+09:00 TRACE 22128 --- [books-genres-api] [nio-8080-exec-9] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [2]
2026-11-01T23:48:38.657+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-9] org.hibernate.SQL                        :
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
2026-11-01T23:48:38.657+09:00 TRACE 22128 --- [books-genres-api] [nio-8080-exec-9] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [1]
2026-11-01T23:48:38.659+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-9] org.hibernate.SQL                        :
    update
        books
    set
        author=?,
        genre_id=?,
        title=?
    where
        id=?
2026-11-01T23:48:38.659+09:00 TRACE 22128 --- [books-genres-api] [nio-8080-exec-9] org.hibernate.orm.jdbc.bind              : binding parameter (1:VARCHAR) <- [한강]
2026-11-01T23:48:38.659+09:00 TRACE 22128 --- [books-genres-api] [nio-8080-exec-9] org.hibernate.orm.jdbc.bind              : binding parameter (2:BIGINT) <- [2]
2026-11-01T23:48:38.660+09:00 TRACE 22128 --- [books-genres-api] [nio-8080-exec-9] org.hibernate.orm.jdbc.bind              : binding parameter (3:VARCHAR) <- [채식주의자]
2026-11-01T23:48:38.660+09:00 TRACE 22128 --- [books-genres-api] [nio-8080-exec-9] org.hibernate.orm.jdbc.bind              : binding parameter (4:BIGINT) <- [1]
2026-11-01T23:48:38.660+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-9] o.s.web.servlet.DispatcherServlet        : Completed 200 OK
```

### 도서 삭제: `DELETE /books/1`

```text
2026-11-01T23:48:38.684+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-2] o.s.web.servlet.DispatcherServlet        : DELETE "/books/1", parameters={}
2026-11-01T23:48:38.686+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-2] org.hibernate.SQL                        :
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
2026-11-01T23:48:38.686+09:00 TRACE 22128 --- [books-genres-api] [nio-8080-exec-2] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [1]
2026-11-01T23:48:38.689+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-2] org.hibernate.SQL                        :
    delete
    from
        books
    where
        id=?
2026-11-01T23:48:38.689+09:00 TRACE 22128 --- [books-genres-api] [nio-8080-exec-2] org.hibernate.orm.jdbc.bind              : binding parameter (1:BIGINT) <- [1]
2026-11-01T23:48:38.690+09:00 DEBUG 22128 --- [books-genres-api] [nio-8080-exec-2] o.s.web.servlet.DispatcherServlet        : Completed 204 NO_CONTENT
```
