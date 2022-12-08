# 작은 스프링 API 제작프로젝트

## Use case
![유스케이스](./usecase.png)

## API 명세
| 기능           | Method | URL             | Request              | Response     | 요구사항                                      |
|--------------|--------|-----------------|----------------------|--------------|-------------------------------------------|
| 전체 게시글 목록 조회 | GET    | /api/posts      |                      | 게시글 리스트      | 제목, 작성자명, 작성내용, 작성날짜 조회 + 작성날짜 기준 내림차순 정렬 |
| 게시글 작성       | POST   | /api/posts      | 제목, 작성자명, 비밀번호, 작성내용 | 게시글          | 제목, 작성자명, 비밀번호, 작성내용을 저장하고, 저장된 게시글을 반환하기 |
| 선택한 게시글 조회   | GET    | /api/posts/{id} |                      | 게시글          | 선택한 게시글의 제목, 작성자명, 작성날짜, 작성 내용            |
| 선택한 게시글 삭제   | DELETE | /api/posts/{id} | 비밀번호                 | 200 Response | 서버에서 비밀번호 여부를 판단한 뒤, 성공응답 받기              |
| 선택한 게시글 수정   | PUT    | /api/posts/{id} | 수정 데이터, 비밀번호         | 수정된 게시글      | 서버에서 비밀번호 여부를 판단한 뒤, 수정된 게시글을 반환하기        |


