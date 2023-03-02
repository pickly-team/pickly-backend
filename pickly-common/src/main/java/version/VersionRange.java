package version;

public class VersionRange {

  private final Version from;

  private final Version to;

  public VersionRange(String from, String to) {
    this.from = new Version(from);
    this.to = new Version(to);
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
