package org.pickly.service.bookmark.services

import org.pickly.service.bookmark.BookmarkFactory
import org.pickly.service.bookmark.entity.Bookmark
import org.pickly.service.bookmark.repository.interfaces.BookmarkRepository
import org.pickly.service.bookmark.service.interfaces.BookmarkService
import org.pickly.service.category.CategoryFactory
import org.pickly.service.category.repository.interfaces.CategoryRepository
import org.pickly.service.member.MemberFactory
import org.pickly.service.member.repository.interfaces.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import spock.lang.Specification

@Transactional
@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class BookmarkServiceSpec extends Specification {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpassword")

    @Autowired
    private BookmarkService bookmarkService

    @Autowired
    private BookmarkRepository bookmarkRepository

    @Autowired
    private CategoryRepository categoryRepository

    @Autowired
    private MemberRepository memberRepository

    private BookmarkFactory bookmarkFactory = new BookmarkFactory()
    private CategoryFactory categoryFactory = new CategoryFactory()
    private MemberFactory memberFactory = new MemberFactory()

    def setupSpec() {
        postgreSQLContainer.start()
        System.setProperty("spring.datasource.url", postgreSQLContainer.jdbcUrl)
        System.setProperty("spring.datasource.username", postgreSQLContainer.username)
        System.setProperty("spring.datasource.password", postgreSQLContainer.password)
    }

    def cleanupSpec() {
        postgreSQLContainer.stop()
    }

    def "유저가 좋아하는 북마크 수 조회"() {
        given:
        var member = memberFactory.testMember()
        memberRepository.save(member)
        var category = categoryFactory.testCategory(member)
        categoryRepository.save(category)
        List<Bookmark> bookmarkList = bookmarkFactory.testBookmarks(3, member, category)
        bookmarkList.each { entity ->
            bookmarkRepository.save(entity)
        }

        when:
        def count = bookmarkService.countMemberLikes(member.id)

        then:
        count == 3
    }
}
