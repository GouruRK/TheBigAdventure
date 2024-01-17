package fr.uge.thebigadventure.game.environment;

import java.util.Objects;

import fr.uge.thebigadventure.util.Direction;
import fr.uge.thebigadventure.util.Zone;

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
