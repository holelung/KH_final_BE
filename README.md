

# Saintra Backend

## Project Overview

Saintra 백엔드 애플리케이션은 KH Final 프로젝트의 핵심으로, 인증·인가, 실시간 채팅, CRUD API, 이메일 알림 등 다양한 기능을 제공합니다.

## Features

- **JWT 기반 인증 & 인가** :contentReference[oaicite:0]{index=0}  
- **역할 기반 접근 제어 (Spring Security)** :contentReference[oaicite:1]{index=1}  
- **RESTful API**를 통한 사용자·상품·주문 관리  
- **실시간 통신**: STOMP 프로토콜 기반 WebSocket 지원 :contentReference[oaicite:2]{index=2}  
- **이메일 알림** (Spring Boot Mail) :contentReference[oaicite:3]{index=3}  
- **AWS S3 파일 업로드/다운로드** :contentReference[oaicite:4]{index=4}  
- **AES 암호화** (Bouncy Castle) :contentReference[oaicite:5]{index=5}  
- **AOP 기반 로깅**로 세부 로그 기록 :contentReference[oaicite:6]{index=6}  
- **데이터 영속성**: Spring Data JDBC & MyBatis :contentReference[oaicite:7]{index=7}  
- **데이터 검증** (Spring Validation) :contentReference[oaicite:8]{index=8}  

## Tech Stack

- **Java 21** :contentReference[oaicite:9]{index=9}  
- **Spring Boot 3.4.6** :contentReference[oaicite:10]{index=10}  
- **Spring Security / Spring AOP / Spring Validation / Spring Web / Spring WebSocket** :contentReference[oaicite:11]{index=11}  
- **MyBatis 3.0.4** :contentReference[oaicite:12]{index=12}  
- **JJWT 0.12.3** :contentReference[oaicite:13]{index=13}  
- **Bouncy Castle 1.78.1** :contentReference[oaicite:14]{index=14}  
- **Spring Cloud AWS 2.2.6.RELEASE** :contentReference[oaicite:15]{index=15}  
- **Oracle JDBC Driver (ojdbc11)** :contentReference[oaicite:16]{index=16}  
- **Lombok** :contentReference[oaicite:17]{index=17}  
- **Gradle** :contentReference[oaicite:18]{index=18}  

## Prerequisites

- JDK 21 이상  
- Gradle (또는 제공된 Wrapper 사용)  
- Oracle Database (접속 정보 필요)  
- AWS S3 버킷 (AWS 자격 증명 필요)  
- SMTP 이메일 서버 정보  

## Configuration

`src/main/resources/application.yml` (또는 `application.properties`)에 아래 설정을 추가하세요:

```yaml
spring:
  datasource:
    url: jdbc:oracle:thin:@//<HOST>:<PORT>/<SERVICE>
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  mail:
    host: ${MAIL_HOST}
    port: ${MAIL_PORT}
    username: ${MAIL_USERNAME}
    password: ${MAIL_PASSWORD}

jwt:
  secret: ${JWT_SECRET}
  expiration-ms: 3600000

cloud:
  aws:
    credentials:
      access-key: ${AWS_ACCESS_KEY}
      secret-key: ${AWS_SECRET_KEY}
    region:
      static: ${AWS_REGION}
    s3:
      bucket: ${AWS_S3_BUCKET}
```

## Build & Run

1. 레포지토리 클론

   ```bash
   git clone https://github.com/holelung/KH_final_BE.git
   cd KH_final_BE
   ```

2. 애플리케이션 실행

   ```bash
   ./gradlew bootRun
   ```

   또는 빌드 후 실행

   ```bash
   ./gradlew build
   java -jar build/libs/saintra-1.0.0.jar
   ```

## Project Structure

```
KH_final_BE
├── .github/workflows/
├── build.gradle
├── gradlew / gradlew.bat
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.kh.saintra
│   │   │       ├── controller
│   │   │       ├── service
│   │   │       ├── mapper
│   │   │       ├── model
│   │   │       ├── config
│   │   │       └── global
│   │   └── resources
│   │       ├── application.yml
│   │       └── ...
│   └── test
│       └── java
│           └── com.kh.saintra
├── .gitignore
└── README.md
```

## Testing

```bash
./gradlew test
```

```
::contentReference[oaicite:19]{index=19}
```
