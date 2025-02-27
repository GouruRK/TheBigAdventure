package fr.uge.thebigadventure.game.entity.item;

import java.util.Objects;

import fr.uge.thebigadventure.game.GameObjectID;

public record Food(String skin, String name) implements Item {

  //------- Constructors -------
  
  public Food {
    Objects.requireNonNull(skin);
  }
  
  public Food(String skin) {
    this(skin, null);
  }
  
  // ------- Getter -------
  
  public GameObjectID id() {
    return GameObjectID.FOOD;
  }
  
}
