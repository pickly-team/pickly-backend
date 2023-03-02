package version;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
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

  public VersionResourceRequestCondition(String acceptedMediaType, String from, String to) {
    this(acceptedMediaType, versionRange(from, to));
  }

  public VersionResourceRequestCondition(String acceptedMediaType,
      Collection<VersionRange> versions) {
    this.acceptedMediaType = acceptedMediaType;
    this.versions = Collections.unmodifiableSet(new HashSet<VersionRange>(versions));
  }

  private static Set<VersionRange> versionRange(String from, String to) {
    Set<VersionRange> ranges = new HashSet<>();

    if (StringUtils.hasText(from)) {
      String toVersion = (StringUtils.hasText(to) ? to : Version.MAX_VERSION);
      VersionRange versionRange = new VersionRange(from, toVersion);

      ranges.add(versionRange);
    }

    return ranges;
  }

  @Override
  public VersionResourceRequestCondition combine(VersionResourceRequestCondition other) {
    log.info("Combining:\n{}\n{}", this, other);
    Set<VersionRange> newVersions = new LinkedHashSet<VersionRange>(this.versions);
    newVersions.addAll(other.versions);
    String newMediaType;

    if (StringUtils.hasText(this.acceptedMediaType) && StringUtils.hasText(other.acceptedMediaType)
        &&
        !this.acceptedMediaType.equals(other.acceptedMediaType)) {
      throw new IllegalArgumentException(
          "Cannot combine media types: " + this.acceptedMediaType + " and "
              + other.acceptedMediaType);
    } else if (StringUtils.hasText(this.acceptedMediaType)) {
      newMediaType = this.acceptedMediaType;
    } else {
      newMediaType = other.acceptedMediaType;
    }
    return new VersionResourceRequestCondition(newMediaType, newVersions);
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

  @Override
  public VersionResourceRequestCondition getMatchingCondition(HttpServletRequest request) {

    String accept = Optional.ofNullable(request.getHeader("Accept")).orElse("");
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

  @Override
  protected Collection<?> getContent() {
    return versions;
  }


  @Override
  public int compareTo(VersionResourceRequestCondition other, HttpServletRequest request) {
    return 0;
  }

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
  protected String getToStringInfix() {
    return " && ";
  }
}
