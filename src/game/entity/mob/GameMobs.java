package game.entity.mob;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import game.GameObject;
import game.GameObjectID;

public enum GameMobs implements GameObject {  
  
  BABA(GameObjectID.MOB),
  BADBAD(GameObjectID.MOB),
  BAT(GameObjectID.MOB),
  BEE(GameObjectID.MOB),
  BIRD(GameObjectID.MOB),
  BUG(GameObjectID.MOB),
  BUNNY(GameObjectID.MOB),
  CAT(GameObjectID.MOB),
  CRAB(GameObjectID.MOB),
  DOG(GameObjectID.MOB),
  FISH(GameObjectID.MOB),
  FOFO(GameObjectID.MOB),
  FROG(GameObjectID.MOB),
  GHOST(GameObjectID.MOB),
  IT(GameObjectID.MOB),
  JELLY(GameObjectID.MOB),
  JIJI(GameObjectID.MOB),
  KEKE(GameObjectID.MOB),
  LIZARD(GameObjectID.MOB),
  ME(GameObjectID.MOB),
  MONSTER(GameObjectID.MOB),
  ROBOT(GameObjectID.MOB),
  SNAIL(GameObjectID.MOB),
  SKULL(GameObjectID.MOB),
  TEETH(GameObjectID.MOB),
  TURTLE(GameObjectID.MOB),
  WORM(GameObjectID.MOB);

  private final GameObjectID id;
  
  private static final List<GameMobs> MOBS = List.of(GameMobs.values());
  private static final Map<String, GameObjectID> MOBSNAMES = MOBS.stream()
      .collect(Collectors.toUnmodifiableMap(GameMobs::name, GameMobs::id));
  
  public GameObjectID id() {
    return id;
  }

  public static GameObjectID getId(String name) {
    return MOBSNAMES.getOrDefault(name, GameObjectID.UNKNOWN);
  }
  
  GameMobs(GameObjectID id) {
    this.id = id;
  }
  
}
