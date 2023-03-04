package org.pickly.service.bookmark.services

import org.pickly.service.bookmark.BookmarkFactory
import org.pickly.service.bookmark.entity.Bookmark
import org.pickly.service.member.MemberFactory
import org.pickly.service.bookmark.repository.interfaces.BookmarkRepository
import org.pickly.service.bookmark.service.interfaces.BookmarkService
import org.pickly.service.member.entity.Member
import org.pickly.service.member.repository.interfaces.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class BookmarkServiceSpec extends Specification {

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    private BookmarkFactory bookmarkFactory = new BookmarkFactory();

    def "유저가 좋아하는 북마크 수 조회"() {
        given:
        List<Bookmark> bookmarkList = bookmarkFactory.testBookmarks(3);
        bookmarkList.each { entity ->
            bookmarkRepository.save(entity)
        }

        when:
        def count = bookmarkService.countMemberLikes(member)

        then:
        count == 3
    }


}