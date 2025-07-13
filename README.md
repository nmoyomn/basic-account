# 🔐 회원가입 및 로그인 기능 구현 프로젝트

간단한 인증 기반의 회원가입/로그인 기능을 구현한 Spring Boot 프로젝트입니다.  
JWT 기반 인증 처리와 비밀번호 유효성 검사, 이메일 중복 검사 등의 기능을 포함하고 있으며, HTML + JS 기반의 프론트엔드와 연동됩니다.

---

### 🛠 기술 스택

#### 🧑‍💻 Backend
* Java 8
* Spring Boot 2.7.18
* Spring Security 5.7.x
* Spring Data JPA (Hibernate)
* JWT (JSON Web Token)
* H2 Database
* Gradle
* Lombok

#### 🌐 Frontend
* HTML5
* Vanilla JavaScript
* Axios

#### 🧪 Test
* JUnit5
* MockMvc

#### ⚙️ 기타
* Bean Validation (`@Valid`, `@NotBlank`, `@Pattern`, `@Email`, `@Size`)
* Global Exception Handling (`@ControllerAdvice`)
* Bcrypt 암호화
* Favicon, 정적 CSS 지원

---

### ✅ 주요 기능

* 회원가입
    - 이메일 형식 및 최대 길이(32자) 제한
    - 비밀번호 유효성 검사 (대소문자/숫자/특수문자 각 1개 이상 포함)
    - 비밀번호 확인 일치 여부 체크
    - 중복 이메일 검증
    - 비밀번호 암호화 저장

* 로그인
    - 이메일/비밀번호 입력
    - 유효 시 JWT 발급 및 localStorage 저장
    - 실패 시 에러 메시지 출력

* JWT 인증
    - 로그인 이후 요청에 대해 JWT 필터를 통해 검증 수행
    - `/signup`, `/login` 및 정적 자원 등은 필터 제외 처리

---

### 📌 제약 사항 및 유효성 규칙

* **아이디 (userId)**
    - 이메일 형식 필수
    - 최대 32자
    - 중복 불가 → `아이디가 중복되었습니다` alert 표시

* **비밀번호 (password)**
    - 최소 8자 이상
    - 대문자, 소문자, 숫자, 특수문자 각각 1개 이상 포함
    - 불일치 시 → `비밀번호가 일치하지 않습니다` alert 표시
    - 유효성 실패 시 → `비밀번호는 대소문자, 숫자, 특수문자를 포함해야 합니다` alert 표시

* **기타**
    - 모든 유효성 실패는 alert 메시지로 사용자에게 표시
    - 가입 완료 시 `"가입되셨습니다"` 알림 후 `/login` 이동

---
