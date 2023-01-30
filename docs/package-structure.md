# Package Structure

## Multi Module
```
├── pickly-common
│   ├── build.gradle
│   └── src
├── pickly-push
│   ├── build.gradle
│   └── src
├── pickly-scheduler
│   ├── build.gradle
│   └── src
├── pickly-service
│   ├── build.gradle
│   └── src
├── build.gradle
├── docs
├── gradle
├── gradlew
├── gradlew.bat
├── settings.gradle
└── README.md 

```

#### Modules
- pickly-common
  - common 모듈압나다. 
- pickly-push
  - push 알림 모듈입니다. 
- pickly-scheduler
  - scheduler 모듈입니다. 
- pickly-services
  - service 모듈입니다. 

<br />

## Domain Directory Structure
```
├── pickly-service
│   ├── build.gradle
│   └── src
│       └── main
│           ├── java
│           │   └── org
│           │       └── pickly
│           │           └── service
│           │               ├── ServiceApplication.java
│           │               ├── auth
│           │               │   ├── controller
│           │               │   ├── dto
│           │               │   ├── entity
│           │               │   ├── exception
│           │               │   ├── repository
│           │               │   └── service
│           │               ├── common
│           │               │   ├── config
│           │               │   ├── filter
│           │               │   └── utils
│           │               ├── infra
│           │               │   ├── firebase
│           │               │   ├── s3
│           │               │   └── slack
│           │               └──scrap
│           │                   ├── controller
│           │                   ├── dto
│           │                   ├── entity
│           │                   ├── exception
│           │                   ├── repository
│           │                   └── service
│           └── resources
│               ├── application-dev.yml
│               ├── application-local.yml
│               ├── application-prod.yml
│               └── application.yml
```



####  Directory 
- auth
  - 사용자 도메인이며, 아래와 같이 계층형으로 구성되어 있습니다. 
    - controller 
    - dto
    - entity
    - exception
    - repository
    - service
- scrap 
  - 스크랩 도메인이며, auth와 동일한 계층형 구조입니다. 
- common
  - 프로젝트 전반적으로 사용되는 객체들로 구성되어 있는 common 디렉터리입니다. 
- infra
  - Infrastructure 관련 코드를 구성하는 infra 디렉터리입니다. 
