package game.entity.mob;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
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
  
  private static final Set<GameMobs> MOBS = Set.of(GameMobs.values());
  private static final Map<String, GameObjectID> MOBSNAMES = MOBS.stream()
      .collect(Collectors.toUnmodifiableMap(GameMobs::name, GameMobs::id));
  private static final Map<String, GameMobs> TYPEMOBS = MOBS.stream()
      .collect(Collectors.toUnmodifiableMap(GameMobs::name, Function.identity()));
  
  
  public GameObjectID id() {
    return id;
  }

  public static GameObjectID getId(String name) {
    return MOBSNAMES.getOrDefault(name, GameObjectID.UNKNOWN);
  }
  
  public static GameMobs getMob(String name) {
    return TYPEMOBS.get(name);
  }
  
  GameMobs(GameObjectID id) {
    this.id = id;
  }
  
}
