package game.environnement;

import java.util.Objects;

import util.Direction;
import util.Zone;

public record Wind(Zone zone, Direction dir) {

  public Wind {
    Objects.requireNonNull(zone);
  }
  
}
