package util;

import java.util.Objects;

public record Zone(Position topLeft, Position bottomRight) {

  public Zone {
    Objects.requireNonNull(topLeft);
    Objects.requireNonNull(bottomRight);
  }
  
  public boolean isInside(Position pos) {
    Objects.requireNonNull(topLeft);
    return (topLeft.x() <= pos.x() && pos.x() < bottomRight.x())
        && (topLeft.y() <= pos.y() && pos.y() < bottomRight.y());
  }
  
}
