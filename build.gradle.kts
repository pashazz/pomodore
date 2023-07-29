plugins {
  java
  id("org.springframework.boot") version "3.1.1"
  id("io.spring.dependency-management") version "1.1.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
  sourceCompatibility = JavaVersion.VERSION_20
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-jdbc")
  implementation("org.postgresql:postgresql")
  implementation("org.springframework.boot:spring-boot-starter-quartz")

  compileOnly("org.liquibase:liquibase-core")
  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")


  testImplementation("org.springframework.boot:spring-boot-starter-test")


  // https://mvnrepository.com/artifact/org.telegram/telegrambots
  implementation("org.telegram:telegrambots:6.7.0")

}

tasks.withType<Test> {
  useJUnitPlatform()
}
