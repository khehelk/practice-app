plugins {
    java
    id("org.springframework.boot") version "4.0.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.khehelk"
version = "0.0.1-SNAPSHOT"
description = "practice-app"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
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
    implementation("org.springframework.boot:spring-boot-starter-liquibase")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("de.siegmar:fastcsv:4.1.0")
    implementation("org.apache.poi:poi-ooxml:5.5.1")
    implementation("org.dhatim:fastexcel:0.20.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.2")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:4.0.1")
    implementation("org.postgresql:postgresql:42.7.10")
    implementation("com.clickhouse:clickhouse-jdbc:0.9.6")
    compileOnly("org.projectlombok:lombok")
    testImplementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-liquibase-test")
    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:4.0.1")
    testImplementation("org.testcontainers:junit-jupiter:1.21.4")
    testImplementation("org.testcontainers:postgresql:1.21.4")
    testImplementation("org.jooq:jooq-meta:3.19.30")
    testImplementation("org.jooq:jooq-codegen:3.19.30")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
