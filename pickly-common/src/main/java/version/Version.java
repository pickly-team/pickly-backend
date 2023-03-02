package version;

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
    } else if (this.major < version.major) {
      return -1;
    } else if (this.minor > version.minor) {
      return 1;
    } else if (this.minor < version.minor) {
      return -1;
    } else {
      return 0;
    }
  }

  @Override
  public String toString() {
    return "v" + major + "." + minor;
  }
}
