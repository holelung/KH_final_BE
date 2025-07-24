

# Saintra Backend

## Project Overview

Saintra 백엔드 애플리케이션은 KH Final 프로젝트의 핵심으로, 인증·인가, 실시간 채팅, CRUD API, 이메일 알림 등 다양한 기능을 제공합니다.

## Features

- **JWT 기반 인증 & 인가** 
- **역할 기반 접근 제어 (Spring Security)** 
- **RESTful API**를 통한 사용자·상품·주문 관리  
- **실시간 통신**: STOMP 프로토콜 기반 WebSocket 지원 
- **이메일 알림** (Spring Boot Mail) 
- **AWS S3 파일 업로드/다운로드** 
- **AES 암호화** (Bouncy Castle) 
- **AOP 기반 로깅**로 세부 로그 기록 
- **데이터 영속성**: Spring Data JDBC & MyBatis 
- **데이터 검증** (Spring Validation) 

## Tech Stack

- **Java 21** 
- **Spring Boot 3.4.6** 
- **Spring Security / Spring AOP / Spring Validation / Spring Web / Spring WebSocket** 
- **MyBatis 3.0.4** 
- **JJWT 0.12.3** 
- **Bouncy Castle 1.78.1** 
- **Spring Cloud AWS 2.2.6.RELEASE** 
- **Oracle JDBC Driver (ojdbc11)** 
- **Lombok** 
- **Gradle** 

## Prerequisites

- JDK 21 이상  
- Gradle (또는 제공된 Wrapper 사용)  
- Oracle Database (접속 정보 필요)  
- AWS S3 버킷 (AWS 자격 증명 필요)  
- SMTP 이메일 서버 정보  

## Configuration

`src/main/resources/application.yml`에 아래 설정을 추가하세요:

```yaml
spring:
  application:
    name: saintra
  datasource:
    url: jdbc:oracle:thin:@//<HOST>:<PORT>/<SERVICE>
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    driver-class-name: oracle.jdbc.OracleDriver
  mail:
    host: ${MAIL_HOST}
    port: ${MAIL_PORT}
    username: ${MAIL_USERNAME}
    password: ${MAIL_PASSWORD}
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true

server:
  port: 8080

mybatis:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
    jdbc-type-for-null: VARCHAR
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: 
    com.kh.saintra.user.model.vo
    com.kh.saintra.user.model.dto
    com.kh.saintra.global.util.token.model.vo
    com.kh.saintra.auth.model.vo
    com.kh.saintra.auth.model.dto
    com.kh.saintra.mail.model.dto
    com.kh.saintra.global.logging.model.dto
    com.kh.saintra.global.logging.model.vo


logging:
  level:
    org.apache.ibatis: DEBUG
    org.apache.ibatis.excutor: TRACE
    org.apache.ibatis.executor.SimpleExecutor: TRACE
    org.apache.ibatis.executor.statement.RoutingStatementHandler: TRACE
    java.sql: DEBUG
    jdbc.sqlonly: DEBUG
    jdbc.resultset: DEBUG
    jdbc.audit: DEBUG

jwt:
  secret: ${JWT_SECRETKEY}

url:
  find-password: "http://${DOMAIN}/authenticator/password-reset?key="
  origin-patterns: "*"


cloud:
  aws:
    s3:
      bucket: ${S3_BUCKET_NAME}
    stack.auto: false
    region.static: ap-northeast-2
    credentials:
      accessKey: ${AWS_ACCESSKEY}
      secretKey: ${AWS_SECRETKEY}

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
