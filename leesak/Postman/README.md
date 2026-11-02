# Postman API 테스트 결과

실제 Postman 앱에서 촬영한 결과 이미지 15장, 추가 API의 실제 CLI 출력으로 만든 결과 이미지 3장과 재실행 가능한 [컬렉션](books-genres.postman_collection.json), [전체 CLI 실행 기록](postman-cli-output.txt)을 첨부했습니다. 이미지 파일명은 컬렉션의 요청 번호·기능·HTTP 응답 코드로 구성했습니다.

## 전체 컬렉션 재검증

2026년 11월 2일 13:09 (KST), 제출 소스에서 빌드한 JAR를 별도 H2 메모리 DB와 `18082` 포트로 실행하여 검증했습니다. 요청 21개·검증 32개 모두 통과했고 실패는 0개입니다. 기존 `8080` 서버의 데이터는 사용하지 않았습니다.

```bash
postman collection run books-genres.postman_collection.json \
  --env-var baseUrl=http://localhost:18082 \
  --no-report-events
```

일반 실행 시에는 컬렉션 기본 주소인 `http://localhost:8080`을 사용합니다. 01번부터 순서대로 실행하면 생성된 ID가 컬렉션 변수에 저장되며, 17·19·20번에서 테스트 데이터를 삭제합니다. 실제 요청별 SQL과 바인딩 값은 [전체 SQL 로그](../SQL/execution.log)에 있습니다.

## 기능별 결과

모든 행의 실제 요청·응답과 검증 결과는 [CLI 실행 기록](postman-cli-output.txt)에 포함됩니다. 추가 결과 이미지 3장은 이번 실행 기록의 해당 구간을 텍스트 보고서 형태로 렌더링한 것이며, Postman 앱 스크린샷과 구분했습니다. 별도 이미지가 없는 행도 전체 기록에서 확인할 수 있습니다.

| 번호 | 기능 | API | 응답 | 결과 이미지 / 기록 |
|---|---|---|---|---|
| 01 | 장르 생성 | `POST /genres` | 201 | [이미지](01_create_genre_201.png) |
| 02 | 두 번째 장르 생성 | `POST /genres` | 201 | [이미지](02_create_second_genre_201.png) |
| 03 | 장르 전체 조회 | `GET /genres` | 200 | [이미지](03_list_genres_200.png) |
| 04 | 장르 단건 조회 | `GET /genres/{id}` | 200 | [이미지](04_get_genre_200.png) |
| 05 | 장르 수정 | `PATCH /genres/{id}` | 200 | [이미지](05_update_genre_200.png) |
| 06 | 도서 생성 | `POST /books` | 201 | [이미지](06_create_book_201.png) |
| 07 | 도서 전체 조회 | `GET /books` | 200 | [CLI 결과 이미지](07_list_books_200_cli.png) |
| 08 | 도서 단건 조회 | `GET /books/{id}` | 200 | [CLI 결과 이미지](08_get_book_200_cli.png) |
| 09 | 장르별 도서 조회 | `GET /genres/{id}` | 200 | [이미지](09_get_genre_with_books_200.png) |
| 10 | 도서가 있는 장르 삭제 거절 | `DELETE /genres/{id}` | 409 | [이미지](10_delete_genre_with_books_409.png) |
| 11 | 빈 도서 제목 거절 | `POST /books` | 400 | [이미지](11_create_book_blank_title_400.png) |
| 12 | 없는 장르로 도서 생성 거절 | `POST /books` | 404 | [이미지](12_create_book_unknown_genre_404.png) |
| 13 | 빈 장르 이름 거절 | `POST /genres` | 400 | [이미지](13_create_genre_blank_name_400.png) |
| 14 | 없는 도서 조회 | `GET /books/{id}` | 404 | [이미지](14_get_unknown_book_404.png) |
| 15 | 도서 제목·장르 수정 | `PATCH /books/{id}` | 200 | [이미지](15_update_book_title_and_genre_200.png) |
| 16 | 장르 이동 후 이전 장르 확인 | `GET /genres/{id}` | 200 | [CLI 기록](postman-cli-output.txt) |
| 17 | 도서 삭제 | `DELETE /books/{id}` | 204 | [이미지](17_delete_book_204.png) |
| 18 | 없는 도서 조회 / 컬렉션의 삭제 후 조회 | `GET /books/{id}` | 404 | [이미지](18_get_missing_book_404.png) |
| 19 | 첫 번째 장르 삭제 | `DELETE /genres/{id}` | 204 | [CLI 결과 이미지](19_21_delete_genres_and_verify_cli.png) |
| 20 | 두 번째 장르 삭제 | `DELETE /genres/{id}` | 204 | [CLI 결과 이미지](19_21_delete_genres_and_verify_cli.png) |
| 21 | 삭제한 장르 조회 | `GET /genres/{id}` | 404 | [CLI 결과 이미지](19_21_delete_genres_and_verify_cli.png) |

## 수동 캡처의 테스트 데이터

수동 캡처는 컬렉션 기본값과 다른 장르명·도서 제목·저자를 사용했습니다. 01·05·06·15번에는 고정 예상값과 다른 값으로 일부 assertion이 실패한 표시가 있습니다. 이미지의 HTTP 응답과 JSON은 원본 그대로 첨부했습니다. 제출 컬렉션을 새 DB에서 기본 데이터로 재실행한 결과는 위 CLI 기록의 요청 21개·검증 32개 통과입니다.

17번 수동 캡처는 도서 4를 삭제하고, 18번은 도서 3을 조회하므로 두 사진은 같은 도서의 삭제 전후를 나타내지 않습니다. 같은 ID의 삭제 후 404는 CLI 기록의 17·18번에서 확인할 수 있습니다. 15번 수동 캡처의 기존 저자 유지와 장르 변경도 전체 컬렉션에서 별도로 재검증했습니다.

삭제 성공의 `204 No Content`는 응답 본문이 비어 있는 것이 정상입니다.

CLI 기록은 실제 실행 출력을 보존하고 줄 끝 공백만 정리했습니다. CLI 결과 이미지는 이 기록에서 발췌했습니다.
