package ru.khehelk.practice;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@SuppressWarnings("java:S2187")
public class AbstractDatabaseIntegrationTests {

    @Container
    protected static PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:18")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("test");

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.postgres.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.postgres.username", POSTGRES::getUsername);
        registry.add("spring.datasource.postgres.password", POSTGRES::getPassword);

        registry.add("spring.liquibase.url", POSTGRES::getJdbcUrl);
        registry.add("spring.liquibase.user", POSTGRES::getUsername);
        registry.add("spring.liquibase.password", POSTGRES::getPassword);
    }

}
