package org.pickly.common.version;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.condition.AbstractRequestCondition;

@Slf4j
public class VersionResourceRequestCondition extends
    AbstractRequestCondition<VersionResourceRequestCondition> {

  private final Set<VersionRange> versions;

  private final String acceptedMediaType;

  /*
   * @VersionResource 어노테이션의 media, from, to 속성을  받아서 VersionRange 객체를 생성한다.
   */
  public VersionResourceRequestCondition(String acceptedMediaType, String from, String to) {
    this(acceptedMediaType, VersionRange.versionRange(from, to));
  }


  public VersionResourceRequestCondition(String acceptedMediaType,
      Collection<VersionRange> versions) {
    this.acceptedMediaType = acceptedMediaType;
    this.versions = Set.copyOf(versions);
  }

  /*
   * combine 메소드는 두 개의 VersionResourceRequestCondition 객체를 합쳐서 새로운 VersionResourceRequestCondition 객체를 생성한다.
   */
  @Override
  public VersionResourceRequestCondition combine(VersionResourceRequestCondition other) {
    log.info("Combining:\n{}\n{}", this, other);
    Set<VersionRange> newVersions = new LinkedHashSet<VersionRange>(this.versions);
    newVersions.addAll(other.versions);

    return new VersionResourceRequestCondition(combineMediaTypes(other), newVersions);
  }

  /*
   * getMatchingCondition 메소드는 요청에 맞는 VersionResourceRequestCondition 객체를 반환한다.
   * 요청에 맞는 VersionResourceRequestCondition 객체를 찾는 과정에서 Accept 헤더와 version 파라미터를 확인한다.
   * Accept 헤더와 version 파라미터가 모두 매칭되지 않는다면 null을 반환한다.
   * Accept 헤더와 version 파라미터가 모두 매칭된다면 현재 VersionResourceRequestCondition 객체를 반환한다.
   */
  @Override
  public VersionResourceRequestCondition getMatchingCondition(HttpServletRequest request) {

    String accept = Optional.ofNullable(request.getHeader("version")).orElse("");
    Pattern pattern = Pattern.compile("(.*)-(\\d+\\.\\d+)");
    Matcher matcher = pattern.matcher(accept);
    if (isAcceptMatched(matcher)) {
      return this;
    } else {
      Map<String, String[]> params = request.getParameterMap();
      if (params.entrySet().stream().anyMatch(param -> Objects.equals(param.getKey(), "version"))) {
        String version = params.get("version")[0];
        if (isVersionMatched(version)) {
          return this;
        }
      }
      log.info("No matching version found for request: {}", request);
      return null;
    }
  }

  /*
   * compareTo 메소드는 두 VersionResourceRequestCondition 객체를 비교한다.
   */
  @Override
  public int compareTo(VersionResourceRequestCondition other, HttpServletRequest request) {
    return 0;
  }

  /*
   * toString 메소드는 VersionResourceRequestCondition 객체의 내용을 문자열로 반환한다.
   * VersionResourceRequestCondition 객체의 내용은 media, from, to 속성이다.
   * VersionResourceRequestCondition 객체의 내용을 문자열로 반환하는 과정에서 media 속성은 무시한다.
   * VersionResourceRequestCondition 객체의 내용을 문자열로 반환하는 과정에서 from, to 속성은 VersionRange 객체의 toString 메소드를 호출한다.
   * VersionRange 객체의 toString 메소드는 from, to 속성을 문자열로 반환한다.
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("version={");
    builder.append("media=").append(acceptedMediaType).append(",");
    for (VersionRange range : versions) {
      builder.append(range).append(",");
    }
    builder.append("}");

    return builder.toString();

  }

  @Override
  protected Collection<?> getContent() {
    return versions;
  }

  @Override
  protected String getToStringInfix() {
    return " && ";
  }

  private String combineMediaTypes(VersionResourceRequestCondition other) {

    String newMediaType;

    if (StringUtils.hasText(this.acceptedMediaType) && StringUtils.hasText(other.acceptedMediaType)
        &&
        !this.acceptedMediaType.equals(other.acceptedMediaType)) {
      throw new IllegalArgumentException(
          "Cannot combine media types: " + this.acceptedMediaType + " and "
              + other.acceptedMediaType);
    }

    if (StringUtils.hasText(this.acceptedMediaType)) {
      newMediaType = this.acceptedMediaType;

    } else {
      newMediaType = other.acceptedMediaType;
    }
    return newMediaType;
  }

  private boolean isVersionMatched(String version) {
    for (VersionRange range : versions) {
      if (range.includes(version)) {
        return true;
      }
    }
    return false;
  }

  private boolean isAcceptMatched(Matcher matcher) {
    if (matcher.matches()) {
      String actualMediaType = matcher.group(1);
      String version = matcher.group(2);

      log.info("Version = {}", version);
      if (acceptedMediaType.startsWith(actualMediaType)) {
        return isVersionMatched(version);
      }
    }
    return false;
  }
}
