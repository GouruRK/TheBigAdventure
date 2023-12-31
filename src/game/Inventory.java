package game;

import java.util.Objects;

import game.entity.item.Item;
import util.Position;

public class Inventory {

  //------- Fields -------
  
  public static final int NB_ROWS = 2;
  public static final int NB_COLS = 9;
  public static final int SIZE = Inventory.NB_COLS * Inventory.NB_ROWS;
  
  private final Item[] inventory;
  private int nbItems;
  
  //------- Constructor -------
  
  public Inventory() {
    this.inventory = new Item[Inventory.NB_COLS * Inventory.NB_ROWS];
    nbItems = 1;
  }

  //------- Getter -------
  
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
    Objects.requireNonNull(pos);
    return get((int)pos.x(), (int)pos.y());
  }
 
 public boolean isFull() {
   return nbItems == SIZE;
 }
 
 public boolean contains(Item item) {
   Objects.requireNonNull(item);
   for (int i = 0; i < SIZE; i++) {
     if (item.equals(get(i))) {
       return true;
     }
   }
   return false;
 }
 
 //------- Setter -------
 
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
 
 public Item remove(Item item) {
   Objects.requireNonNull(item);
   for (int i = 0; i < SIZE; i++) {
     if (item.equals(get(i))) {
       return remove(i);
     }
   }
   return null;
 }
 
 //------- Other -------
 
 private int getFirstFreeSlot() {
   for (int i = 0; i < SIZE; i++) {
     if (inventory[i] == null) {
       return i;
     }
   }
   return -1;
 }
 
 private static int transformIndex(int x, int y) {
   return x + y*NB_COLS;
 }
 
 private static boolean isIndexValid(int index) {
   return index >= 0 && index < SIZE;
 }
 
 private static boolean isIndexValid(int x, int y) {
   return x >= 0 && x < NB_COLS && y >= 0 && y < NB_ROWS;
 }
 
}
