package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import game.entity.item.DroppedItem;
import game.entity.item.Item;
import game.entity.mob.Behaviour;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.environnement.Environnement;
import util.Direction;
import util.Math;
import util.Position;

public class Game {

  //------- Fields -------
  
  private final Position size;
  private final Environnement[][] field;
  private final ArrayList<Mob> mobs;
  private final ArrayList<DroppedItem> items;
  private final Player player;
  private final Inventory inventory;

  
  // -------- Constructor --------
  
  /***
   * define a Game
   * 
   * @param size
   * @param field
   * @param mobs
   * @param items
   * @param player
   */
  public Game(Position size, Environnement[][] field, ArrayList<Mob> mobs, ArrayList<DroppedItem> items, Player player) {
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
    this.inventory = new Inventory();
  }

  @Override
  public String toString() {
    return size.toString() + "\n"
        + player.toString() + "\n"
        + "Mobs: " + mobs + "\n"
        + "Items: " + items;
  }

  //------- Getter -------
  
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
    return List.copyOf(mobs);
  }

  /***
   * Define a List of DroppedItem
   * @return List<DroppedItem>
   */
  public List<DroppedItem> items() {
    return List.copyOf(items);
  }
  
  public Inventory inventory() {
    return inventory;
  }
  
  public DroppedItem searchItem(Position pos) {
    return items.stream().filter(item -> item.pos().equals(pos)).findFirst().orElse(null);
  }
  
  public Mob searchMob(Position pos) {
    return mobs.stream().filter(mob -> mob.pos().equals(pos)).findFirst().orElse(null);
  }
  
  /***
   * Search an Environnement with a certain Pos in the List of Environnement
   * 
   * @param pos
   * @return Environnement
   */
  public Environnement searchEnvironnement(Position pos) {
    return field[(int)pos.y()][(int)pos.x()];
  }
  
  public void removeDroppedItem(DroppedItem item) {
    items.removeIf(i -> i.equals(item));
  }


  // ------- Movement related -------
  
  public void moveMobs() {
    mobs.forEach(mob -> move(mob, Direction.randomDirection(), 1));
  }
  
  public void agressiveMob() {
    mobs.stream()
      .filter(mob -> mob.behaviour() == Behaviour.AGRESSIVE 
        && Math.distance(player.pos(), mob.pos()) == 1 
        && Math.randomBoolean())
      .forEach(m -> player.takeDamage(m.damage()));
  }
  
  public void moveEnemies() {
    mobs.stream()
      .filter(mob -> mob.id() == GameObjectID.ENEMY)
      .forEach(mob -> move(mob, Direction.randomDirection(), 1));
  }
  
  public void move(Mob mob, Direction dir, double step) {
    Position nextPos = mob.pos().computeDirection(dir, step);
    if (nextPos == null) {
      return;
    }
    
    if (mob.isMoveInZonePossible(nextPos) && isMoveInGamePossible(mob, nextPos)) {
      mob.setPos(nextPos);
      pickUpItem(mob);
    }
    mob.setFacing(dir);
  }
  
  /**
   * Check if the move is possible to execute
   * 
   * @param pos
   * @return boolean
   */  
  public boolean isMoveInGamePossible(Mob mobToMove, Position pos) {
    Environnement env = searchEnvironnement(pos);
    Mob mob = searchMob(pos);
    
    return switch (mobToMove) {
      case Player p -> (env == null || env.standable() || env.isOpen()) && mob == null;
      default -> (env == null || env.standable() || env.isOpen()) && !pos.equals(player.pos());
    }; 
  }
  
  //------- Modifiers -------
  
  private void removeDeadMob() {
    mobs.removeIf(Mob::isDead);
  }
  
  public void pickUpItem(Mob mob) {
    switch (mob) {
    case Player p -> {
      if (p.hold() == null) {
        DroppedItem item = searchItem(p.pos());
        if (item != null) {
          p.setHold(item.item());
          removeDroppedItem(item);
        }
      }
    }
    default -> {}
    }
  }
  
  public void addDroppedItem(DroppedItem item) {
    Objects.requireNonNull(item);
    items.add(item);
  }
  
  public void addDroppedItem(Item item, Position pos) {
    Objects.requireNonNull(item);
    Objects.requireNonNull(pos);
    items.add(new DroppedItem(pos, item));
  }
  
  public void attackMob(Mob attacker, Mob victim) {
    if (attacker != null && victim != null) {
      victim.takeDamage(attacker.damage());
      removeDeadMob();      
    }
  }

}
