package org.pickly.service

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import spock.lang.Specification

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class TestConfig extends Specification {

    @Container
    private static final PostgreSQLContainer postgreSQLContainer =
            new PostgreSQLContainer("postgres:15")
                    .withDatabaseName("testdb")
                    .withUsername("testuser")
                    .withPassword("testpassword")
                    .withInitScript("schema.sql")

    def setupSpec() {
        postgreSQLContainer.start()
        System.setProperty("spring.datasource.url", postgreSQLContainer.jdbcUrl)
        System.setProperty("spring.datasource.username", postgreSQLContainer.username)
        System.setProperty("spring.datasource.password", postgreSQLContainer.password)
    }

    def cleanupSpec() {
        postgreSQLContainer.stop()
    }

}