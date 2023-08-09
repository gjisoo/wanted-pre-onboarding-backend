# 지원자 정보
### 김지수
</br>

# 애플리케이션의 실행 방법
#### 1.프로젝트를 클론합니다.
#### git clone https://github.com/your_username/your_project.git
#### 2.프로젝트 디렉토리로 이동합니다.
#### cd your_project
#### 3.Maven을 사용하여 프로젝트를 빌드합니다.
#### mvn clean install
#### 4. Spring Boot 애플리케이션을 실행합니다.
#### mvn spring-boot:run

#### 데이터베이스 테이블구조
</br>
#### 스키마명: myproject

#### User테이블
|Column|Type|
|---|---|
|id(PK)|bigint/auto_increment|
|email|varchar(255)|
|password(암호화 저장)|varchar(64)|
|created_dt|datetime|

#### Post테이블
|Column|Type|
|---|---|
|id(PK)|bigint/auto_increment|
|title|varchar(255)|
|content|text|
|author_id(FK)|bigint|
|created_dt|datetime|
|updated_dt|datetime|


</br></br>

#### 구현 방법 및 이유에 대한 간략한 설명
1.회원가입 엔드포인트:
사용자의 이메일과 비밀번호를 받아서 새로운 사용자를 등록합니다.
이메일과 비밀번호 유효성 검사를 통해 올바른 형식인지 확인하고, 비밀번호는 암호화하여 저장합니다.
</br></br>
2.로그인 엔드포인트:
사용자의 이메일과 비밀번호를 받아서 사용자를 인증하고, JWT를 생성하여 반환합니다.
이메일과 비밀번호의 유효성을 확인하고, 인증이 완료되면 JWT를 생성하여 반환합니다.
</br></br>
3.새로운 게시글 생성 엔드포인트:
게시글 제목과 내용을 받아서 새로운 게시글을 생성합니다.
</br></br>
4.게시글 목록 조회 엔드포인트:
Pagination 기능을 통해 게시글 목록을 페이지 단위로 조회합니다.
</br></br>
5.특정 게시글 조회 엔드포인트:
게시글의 ID를 받아서 해당 게시글을 조회합니다.
</br></br>
6.특정 게시글 수정 엔드포인트:
게시글의 ID와 수정 내용을 받아서 해당 게시글을 수정합니다.
게시글 작성자만 수정 가능합니다.
</br></br>
7.특정 게시글 삭제 엔드포인트:
게시글의 ID를 받아서 해당 게시글을 삭제합니다.
게시글 작성자만 삭제 가능합니다.

</br></br>
#### 엔드포인트 호출방법
|엔드포인트|HTTP 메서드|요청 예시 (Request Body)|설명
|---|---|---|---|
|/join|POST|{"email": "user@example.com", "password": "securepassword"}|사용자 회원가입|
|/login|POST|{"email": "user@example.com", "password": "securepassword"}|사용자 로그인|
|/write/post|POST|{"title": "New Post", "content": "This is a new post content."}|새로운 게시글 생성|
/view/posts|GET|-|게시글 목록 조회 (Pagination 기능 포함) 1페이지부터 최신글순으로 보여짐|
/view/posts/{postId}|GET|-|특정 게시글 조회|
/edit/post/{postId}|PUT|{"title": "Updated Title", "content": "Updated content."}|특정 게시글 수정(작성자만 수정가능)|
/delete/post/{postId}|DELETE|-|특정 게시글 삭제(작성자만 삭제가능)|
