package ru.khehelk.practice.app;

import java.sql.Connection;
import javax.sql.DataSource;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.Configuration;
import org.jooq.meta.jaxb.Generate;
import org.jooq.meta.jaxb.Generator;
import org.jooq.meta.jaxb.Jdbc;
import org.jooq.meta.jaxb.Target;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.khehelk.practice.AbstractDatabaseIntegrationTests;

class JooqGenerator extends AbstractDatabaseIntegrationTests {

    @Test
    void generateJooqClasses() throws Exception {
        DataSource dataSource = createDataSource();

        runLiquibaseMigrations(dataSource);

        generateJooq();
    }

    private DataSource createDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(POSTGRES.getJdbcUrl());
        dataSource.setUsername(POSTGRES.getUsername());
        dataSource.setPassword(POSTGRES.getPassword());
        return dataSource;
    }

    private void runLiquibaseMigrations(DataSource dataSource) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase(
                "db/master.yml",
                new ClassLoaderResourceAccessor(),
                database
            );

            liquibase.update("");
        }
    }

    private void generateJooq() throws Exception {
        Configuration configuration = new Configuration()
            .withJdbc(new Jdbc()
                .withDriver("org.postgresql.Driver")
                .withUrl(POSTGRES.getJdbcUrl())
                .withUser(POSTGRES.getUsername())
                .withPassword(POSTGRES.getPassword()))
            .withGenerator(new Generator()
                .withName("org.jooq.codegen.JavaGenerator")
                .withDatabase(new org.jooq.meta.jaxb.Database()
                    .withName("org.jooq.meta.postgres.PostgresDatabase")
                    .withIncludes(".*")
                    .withExcludes("databasechangelog|databasechangeloglock|outbox")
                    .withInputSchema("public")
                    .withForcedTypes())
                .withGenerate(new Generate()
                    .withPojos(true)
                    .withDaos(true)
                    .withSpringAnnotations(true)
                    .withFluentSetters(true)
                    .withValidationAnnotations(true))
                .withTarget(new Target()
                    .withPackageName("ru.khehelk.practice.jooq")
                    .withDirectory("src/main/java")));

        GenerationTool.generate(configuration);
    }

}
