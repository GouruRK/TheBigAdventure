package game;

import java.util.Objects;

import game.entity.item.Item;
import util.Position;

public class Inventory {

  public static final int NB_ROWS = 2;
  public static final int NB_COLS = 9;
  public static final int SIZE = Inventory.NB_COLS * Inventory.NB_ROWS;
  
  private final Item[] inventory;
  private int nbItems;
  
  public Inventory() {
    this.inventory = new Item[Inventory.NB_COLS * Inventory.NB_ROWS];
    nbItems = 0;
  }
  
  public Item[] get() {
    return inventory;
  }
  
  public Item get(int index) {
    if (Inventory.isIndexValid(index)) {
      return inventory[index];
    }
    return null;
  }
  
  public Item get(int x, int y) {
    if (Inventory.isIndexValid(x, y)) {
      return inventory[Inventory.transformIndex(x, y)];
    }
    return null;
  }
  
  public Item get(Position pos) {
    return get((int)pos.x(), (int)pos.y());
  }
 
 public boolean isFull() {
   return nbItems == Inventory.SIZE;
 }
 
 public boolean add(Item item) {
   Objects.requireNonNull(item);
   if (isFull()) {
     return false;
   }
   int index = getFirstFreeSlot();
   inventory[index] = item;
   nbItems++;
   return true;
 }
 
 public Item remove(int index) {
   Item item = null;
   if (Inventory.isIndexValid(index)) {
     item = inventory[index];
     inventory[index] = null;
   }
   return item;
 }
 
 public Item remove(int x, int y) {
   Item item = null;
   if (Inventory.isIndexValid(x, y)) {
     int index = Inventory.transformIndex(x, y);
     item = inventory[index];
     inventory[index] = null;
   }
   return item;
 }
 
 public Item remove(Position pos) {
   return remove((int)pos.x(), (int)pos.y());
 }
 
 private int getFirstFreeSlot() {
   for (int i = 0; i < Inventory.SIZE; i++) {
     if (inventory[i] == null) {
       return i;
     }
   }
   return -1;
 }
 
 private static int transformIndex(int x, int y) {
   return x + y*Inventory.NB_ROWS;
 }
 
 private static boolean isIndexValid(int index) {
   return index >= 0 && index < Inventory.SIZE;
 }
 
 private static boolean isIndexValid(int x, int y) {
   return x >= 0 && x < NB_ROWS && y >= 0 && y < NB_COLS;
 }
 
}
