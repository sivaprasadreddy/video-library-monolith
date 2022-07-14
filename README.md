# video-library-monolith
Video Library application following monolithic architecture using SpringBoot 

## Backend Tech Stack
* Java
* SpringBoot 
* H2(Dev) / Postgres (Prod)
* Spring Data JPA
* Spring Security 
* Thymeleaf
* Flyway
* Gradle
* JUnit 5, Mockito, Testcontainers

```shell
./gradlew :video-library:bootRun
```

## Gatling Performance Testing

```shell
./gradlew :video-library-gatling-tests:gatlingRun
```