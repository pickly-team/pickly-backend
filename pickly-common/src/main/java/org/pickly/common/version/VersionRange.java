package org.pickly.common.version;

import java.util.HashSet;
import java.util.Set;
import org.springframework.util.StringUtils;

/*
 * 지정된 from 버전과 to 버전 사이의 버전 범위를 나타내는 버전 범위 클래스입니다.
 * 클래스는 주어진 버전이 지정된 범위 내에 있는지 확인하는 데 사용됩니다.
 */
public class VersionRange {

  private final Version from;

  private final Version to;

  public VersionRange(String from, String to) {
    this.from = new Version(from);
    this.to = new Version(to);
  }

  public static Set<VersionRange> versionRange(String from, String to) {
    Set<VersionRange> ranges = new HashSet<>();

    addVersionRange(ranges, from, to);

    return ranges;
  }

  private static void addVersionRange(Set<VersionRange> ranges, String from, String to) {
    if (StringUtils.hasText(from)) {
      String toVersion = (StringUtils.hasText(to) ? to : Version.MAX_VERSION);
      VersionRange versionRange = new VersionRange(from, toVersion);

      ranges.add(versionRange);
    }
  }

  public boolean includes(String other) {
    Version version = new Version(other);

    return from.compareTo(version) <= 0 && to.compareTo(version) >= 0;
  }

  @Override
  public String toString() {
    return "range[" + from + "-" + to + "]";
  }

}
