package game.environnement;

import java.util.Objects;

import util.Direction;
import util.Zone;

public record Wind(Zone zone, Direction dir) {

  //------- Constructor -------
  
  public Wind {
    Objects.requireNonNull(zone);
  }
  
  //------- Getter -------
  
  public String skin() {
    return "WIND";
  }
}
