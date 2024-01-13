package game;

import game.entity.item.GameItems;
import game.entity.mob.GameMobs;
import game.environment.GameEnvironment;

public interface GameObject {
 
	/**
	 * Precise the type of the Name (item, mob, environment)
	 * 
	 * @param name
	 * @return gameObjectID
	 */
  public static GameObjectID fromName(String name) {
    GameObjectID id;
    if ((id = GameMobs.getId(name)) != GameObjectID.UNKNOWN) {
      return id;
    }
    if ((id = GameItems.getId(name)) != GameObjectID.UNKNOWN) {
      return id;
    }
    if ((id = GameEnvironment.getId(name)) != GameObjectID.UNKNOWN) {
      return id;
    }
    return GameObjectID.UNKNOWN;
  }
}
