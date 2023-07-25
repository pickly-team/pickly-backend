package org.pickly.service.comment.service

import org.pickly.service.bookmark.BookmarkFactory
import org.pickly.service.bookmark.repository.interfaces.BookmarkRepository
import org.pickly.service.category.CategoryFactory
import org.pickly.service.category.repository.interfaces.CategoryRepository
import org.pickly.service.comment.CommentFactory
import org.pickly.service.comment.repository.interfaces.CommentRepository
import org.pickly.service.comment.service.dto.CommentUpdateDTO
import org.pickly.service.comment.service.interfaces.CommentService
import org.pickly.service.member.MemberFactory
import org.pickly.service.member.repository.interfaces.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.junit.jupiter.Testcontainers
import spock.lang.Specification

@Transactional
@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CommentServiceSpec extends Specification {

    @Autowired
    private CommentService commentService

    @Autowired
    private BookmarkRepository bookmarkRepository

    @Autowired
    private CategoryRepository categoryRepository

    @Autowired
    private MemberRepository memberRepository

    @Autowired
    private CommentRepository commentRepository

    private BookmarkFactory bookmarkFactory = new BookmarkFactory()
    private CategoryFactory categoryFactory = new CategoryFactory()
    private MemberFactory memberFactory = new MemberFactory()
    private CommentFactory commentFactory = new CommentFactory()

    def "특정 댓글 내용 수정"() {
        given:
        var member = memberFactory.testMember()
        memberRepository.save(member)
        var category = categoryFactory.testCategory(member)
        categoryRepository.save(category)
        var bookmark = bookmarkFactory.testBookmark(member, category)
        bookmarkRepository.save(bookmark)
        var comment = commentFactory.testComment(member, bookmark, true, "첫번째 내용")
        commentRepository.save(comment)
        var memberId = member.id

        when:
        CommentUpdateDTO request = new CommentUpdateDTO("두번째 내용")
        def updateComment = commentService.update(comment.id, memberId, request)

        then:
        updateComment.isOwnerComment == true
        updateComment.content == "두번째 내용"
    }

}