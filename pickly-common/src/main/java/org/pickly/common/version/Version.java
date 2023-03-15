package org.pickly.common.version;

/*
 * 버전 정보를 표현하기 위한 클래스
 * Compareable 인터페이스를 구현하여 버전을 비교할 수 있도록 합니다.
 * MAX_VERSION은 버전 범위의 최대값을 나타냅니다.
 * compareTo 메서드는 주어진 버전이 현재 버전보다 큰지, 작은지, 같은지를 비교합니다.
 * toString 메서드는 버전을 문자열로 표현합니다.
 */
public class Version implements Comparable<Version> {

  public static final String MAX_VERSION = "99.99";

  private final int major;

  private final int minor;

  public Version(String version) {
    String[] tokens = version.split("\\.");

    if (tokens.length != 2) {
      throw new IllegalArgumentException("Invalid version format: " + version);
    }

    major = Integer.parseInt(tokens[0]);
    minor = Integer.parseInt(tokens[1]);
  }


  @Override
  public int compareTo(Version version) {
    if (this.major > version.major) {
      return 1;
    }

    if (this.major < version.major) {
      return -1;
    }

    if (this.minor > version.minor) {
      return 1;
    }

    if (this.minor < version.major) {
      return -1;
    }

    return 0;
  }

  @Override
  public String toString() {
    return "v" + major + "." + minor;
  }
}
