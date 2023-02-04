package org.pickly.service.spock

import org.pickly.service.test.utils.CustomStringUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class CustomStringUtilTest extends Specification {

    @Autowired
    CustomStringUtil customStringUtil

    def "문자열을 마지막 '/' 까지 자른다"() {
        setup:
        def defaultStr = "테스트1 / 테스트2 / 테스트3"
        def blankStr = ""
        def nullStr = null
        when:
        def defaultResult = customStringUtil.inputSubString(defaultStr)
        def blankResult = customStringUtil.inputSubString(blankStr)
        def nullResult = customStringUtil.inputSubString(nullStr)
        then:
        defaultResult == "테스트1 / 테스트2 "
        blankResult == ""
        nullResult == null
    }


}