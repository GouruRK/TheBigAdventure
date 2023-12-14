package game;

import java.util.List;
import java.util.Objects;

import game.entity.item.DroppedItem;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.environnement.Environnement;
import util.Direction;
import util.Position;

public class Game {

  private final Position size;
  private final Environnement[][] field;
  private final List<Mob> mobs;
  private final List<DroppedItem> items;
  private final Player player;

  
  /***
   * define a Game
   * 
   * @param size
   * @param field
   * @param mobs
   * @param items
   * @param player
   */
  public Game(Position size, Environnement[][] field, List<Mob> mobs, List<DroppedItem> items, Player player) {
    Objects.requireNonNull(size);
    Objects.requireNonNull(field);
    Objects.requireNonNull(mobs);
    Objects.requireNonNull(items);
    Objects.requireNonNull(player);
    this.size = size;
    this.field = field;
    this.mobs = mobs;
    this.items = items;
    this.player = player;
  }

  @Override
  public String toString() {
    return size.toString() + "\n"
        + player.toString() + "\n"
        + "Mobs: " + mobs + "\n"
        + "Items: " + items;
  }

  /**
   * define a Player
   * @return player
   */
  public Player player() {
    return player;
  }

  /***
   * Define an Environnement
   * @return Environnement[][]
   */
  public Environnement[][] field() {
    return field;
  }

  /***
   * Define a List of Mob
   * @return List<Mob>
   */
  public List<Mob> mobs() {
    return mobs;
  }

  /***
   * Define a List of DroppedItem
   * @return List<DroppedItem>
   */
  public List<DroppedItem> items() {
    return items;
  }

  /***
   * Allows to move a Mob in one direction (if it's possible)
   * 
   * @param mob
   * @param dir
   * @param step
   */
  public void move(Mob mob, Direction dir, double step) {
    // ici, faire un switch ou je ne sais quoi pour savoir dans quelle dir tu vas
    Position nextPos = switch (dir) {
    case NORTH -> mob.pos().addY(-step);
    case WEST -> mob.pos().addX(-step);
    case SOUTH -> mob.pos().addY(step);
    case EAST -> mob.pos().addX(step);
    default -> null;
    };
    if (nextPos == null) {
      return;
    }
    if (mob.isMoveInZonePossible(nextPos) && isMoveInGamePossible(nextPos)) {
      mob.setPos(nextPos);
    }
  }

  /***
   * Search a Mob with a certain Pos in the List of Mob
   * 
   * @param pos
   * @return Mob
   */
  public Mob searchMob(Position pos) {
    for (Mob mob : mobs) {
        if (mob.pos().equals(pos)) {
            return mob;
        }
    }
    return null;
}
  
  /***
   * Search an Environnement with a certain Pos in the List of Environnement
   * 
   * @param pos
   * @return Environnement
   */
  public Environnement searchEnvironnement(Position pos) {
    return this.field[(int)pos.y()][(int)pos.x()];
  }

  /**
   * Check if the move is possible to execute
   * 
   * @param pos
   * @return boolean
   */
  public boolean isMoveInGamePossible(Position pos) {
    Environnement env = searchEnvironnement(pos);
    Mob mob = searchMob(pos);
    
    return (env == null || env.standable() || env.isOpen()) && mob == null;
  }

}
