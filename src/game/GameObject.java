package game;

import game.entity.item.GameItems;
import game.entity.mob.GameMobs;
import game.environnement.GameEnvironnement;

public interface GameObject {
 
  public static GameObjectID fromName(String name) {
    GameObjectID id;
    if ((id = GameMobs.getId(name)) != GameObjectID.UNKNOWN) {
      return id;
    }
    if ((id = GameItems.getId(name)) != GameObjectID.UNKNOWN) {
      return id;
    }
    if ((id = GameEnvironnement.getId(name)) != GameObjectID.UNKNOWN) {
      return id;
    }
    return GameObjectID.UNKNOWN;
  }
  
  
  
}
