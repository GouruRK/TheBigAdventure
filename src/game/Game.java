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
   * Get the player instance
   * @return player
   */
  public Player player() {
    return player;
  }

  /***
   * Get the environnement that contains all obstacles and scenery of the map
   * @return Environnement[][]
   */
  public Environnement[][] field() {
    return field;
  }

  /***
   * Get the list of all mobs of the game
   * @return List<Mob>
   */
  public List<Mob> mobs() {
    return List.copyOf(mobs);
  }

  /***
   * Get a list of DroppedItem
   * @return List<DroppedItem>
   */
  public List<DroppedItem> items() {
    return List.copyOf(items);
  }
  
  /**
   * Get the inventory instance
   * @return
   */
  public Inventory inventory() {
    return inventory;
  }
  
  /**
   * Search if there is an item on the ground at given position
   * @param pos position
   * @return the item if its on the ground, else null
   */
  public DroppedItem searchItem(Position pos) {
    Objects.requireNonNull(pos);
    return items.stream().filter(item -> item.pos().equals(pos)).findFirst().orElse(null);
  }
  
  /**
   * Search if there is a mob at given position
   * @param pos
   * @return the mob if its at the position, else null
   */
  public Mob searchMob(Position pos) {
    Objects.requireNonNull(pos);
    return mobs.stream().filter(mob -> mob.pos().equals(pos)).findFirst().orElse(null);
  }
  
  /***
   * Search an Environnement with a certain Pos in the List of Environnement
   * 
   * @param pos
   * @return Environnement
   */
  public Environnement searchEnvironnement(Position pos) {
    Objects.requireNonNull(pos);
    return field[(int)pos.y()][(int)pos.x()];
  }
  
  /**
   * Remove an item from the DroppedItem list instance
   * @param item to remove
   */
  public void removeDroppedItem(DroppedItem item) {
    Objects.requireNonNull(item);
    items.removeIf(i -> i.equals(item));
  }


  // ------- Movement related -------
  
  /**
   * Move all mobs of the map
   */
  public void moveMobs() {
    // here, all mobs moved of one square
    mobs.forEach(mob -> moveMob(mob));
  }

  private void moveMob(Mob mob) {
    switch (mob.behaviour()) {
    case Behaviour.AGRESSIVE -> moveAgressiveMob(mob);
    case Behaviour.SHY -> move(mob, player.facing(), 1);
    default -> move(mob, Direction.randomDirection(), 1);
    }
  }
  
  /**
   * Agressive mobs attack the player if he is next to a mob
   */
  private void moveAgressiveMob(Mob mob) {
    move(mob, Direction.randomDirection(), 1);
    if (Math.distance(player.pos(), mob.pos()) == 1 && Math.randomBoolean()) {
      attackMob(mob, player);
    }
  }
  
  /**
   * Move the given mob one step on the given direction if the movement is possible
   * @param mob mob to move. If mob is the player, it can pick up item from the ground
   * @param dir direction to move
   * @param step number (or proportion) of square to move
   */
  public void move(Mob mob, Direction dir, double step) {
    Objects.requireNonNull(mob);
    Objects.requireNonNull(dir);
    
    Position nextPos = mob.pos().computeDirection(dir, step);
    if (nextPos == null) {
      return;
    }
    
    if (mob.isMoveInZonePossible(nextPos) && isMoveInGamePossible(mob, nextPos)) {
      mob.setPos(nextPos);
      
      switch (mob) {
        case Player player -> pickUpItem(player);
        default -> {}
      }
      
    }
    mob.setFacing(dir);
  }
  
  /**
   * Check if the move is possible
   * 
   * @param pos
   * @return boolean
   */  
  private boolean isMoveInGamePossible(Mob mobToMove, Position pos) {
    Environnement env = searchEnvironnement(pos);
    Mob mob = searchMob(pos);
    
    return switch (mobToMove) {
      case Player p -> (env == null || env.standable() || env.isOpen()) && mob == null;
      default -> (env == null || env.standable() || env.isOpen()) && !pos.equals(player.pos());
    }; 
  }
  
  //------- Modifiers -------
  
  /**
   * Remove all dead mobs
   */
  private void removeDeadMob() {
    mobs.removeIf(Mob::isDead);
  }
  
  /**
   * If there is an item on the ground at the same position of the player and
   * the player doesn't already hold an item, it can pick it up and put it as
   * a hold item
   * @param player
   */
  private void pickUpItem(Player player) {
    if (player.hold() == null) {
      DroppedItem item = searchItem(player.pos());
      if (item != null) {
        player.setHold(item.item());
        removeDroppedItem(item);
        System.out.println(item);
      }
    }
  }
  
  /**
   * Add a dropped item on the map
   * @param item
   */
  public void addDroppedItem(DroppedItem item) {
    Objects.requireNonNull(item);
    items.add(item);
  }
  
  /**
   * Create a dropped item at given position and add it to the map
   * @param item
   * @param pos
   */
  public void addDroppedItem(Item item, Position pos) {
    Objects.requireNonNull(item);
    Objects.requireNonNull(pos);
    items.add(new DroppedItem(pos, item));
  }
  
  /**
   * Manage conflict between two mobs
   * @param attacker
   * @param victim
   */
  public void attackMob(Mob attacker, Mob victim) {
    // we manually check if instances aren't null because this method is 
    // generic, and be called directly after a 'searchMob' which can return null
    if (attacker != null && victim != null) {
      victim.takeDamage(attacker.damage());
      removeDeadMob();      
    }
  }
  
  public void removeEnvironnement(Position pos) {
    Objects.requireNonNull(pos);
    if ((0 <= pos.x() && pos.x() < size.x()) && (0 <= pos.y() && pos.y() < size.y())) {
      field[(int)pos.y()][(int)pos.x()] = null;
    }
  }

}
