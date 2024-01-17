package fr.uge.thebigadventure.game;

import java.util.Objects;

import fr.uge.thebigadventure.game.entity.item.Item;
import fr.uge.thebigadventure.util.Position;

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
  
  /**
   * Get array of item
   * @return
   */
  public Item[] get() {
    return inventory;
  }
  
  /**
   * Get item stored at index
   * @param index
   * @return
   */
  public Item get(int index) {
    if (Inventory.isIndexValid(index)) {
      return inventory[index];
    }
    return null;
  }
  
  /**
   * Get item stored at given coordinates
   * @param x
   * @param y
   * @return
   */
  public Item get(int x, int y) {
    if (isIndexValid(x, y)) {
      return inventory[transformIndex(x, y)];
    }
    return null;
  }
  
  /**
   * Get item stored at given position
   * @param pos
   * @return
   */
  public Item get(Position pos) {
    Objects.requireNonNull(pos);
    return get((int)pos.x(), (int)pos.y());
  }
 
 /**
  * Check if inventory's full
  * @return
  */
 public boolean isFull() {
   return nbItems == SIZE;
 }
 
 /**
  * Check if the inventory contains the given item
  * @param item
  * @return
  */
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
 
 /**
  * Add given item in the inventory at the first free index
  * @param item
  * @return if the item has been added
  */
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
 
 /**
  * Remove item stored at given index and return it
  * @param index
  * @return the removed item
  */
 public Item remove(int index) {
   Item item = null;
   if (isIndexValid(index)) {
     item = inventory[index];
     inventory[index] = null;
   }
   return item;
 }
 
 /**
  * Remove item stored at given coordinates and return it
  * @param x
  * @param y
  * @return the removed item
  */
 public Item remove(int x, int y) {
   Item item = null;
   if (isIndexValid(x, y)) {
     int index = transformIndex(x, y);
     item = inventory[index];
     inventory[index] = null;
   }
   return item;
 }
 
 /**
  * Remove item at given position and return it
  * @param pos
  * @return the removed item
  */
 public Item remove(Position pos) {
   Objects.requireNonNull(pos);
   
   return remove((int)pos.x(), (int)pos.y());
 }
 
 /**
  * Remove the given item from the inventory
  * @param item
  * @return the removed item
  */
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
 
 /**
  * Get the first free slot of the inventory that store no items
  * @return free index, else -1 if the inventory's full
  */
 private int getFirstFreeSlot() {
   for (int i = 0; i < SIZE; i++) {
     if (inventory[i] == null) {
       return i;
     }
   }
   return -1;
 }
 
 /**
  * Transform coordinates to index
  * @param x
  * @param y
  * @return
  */
 private static int transformIndex(int x, int y) {
   return x + y*NB_COLS;
 }
 
 /**
  * Check if index's valid
  * @param index
  * @return
  */
 private static boolean isIndexValid(int index) {
   return index >= 0 && index < SIZE;
 }
 
 /**
  * Check if coordinates are valid
  * @param x
  * @param y
  * @return
  */
 private static boolean isIndexValid(int x, int y) {
   return x >= 0 && x < NB_COLS && y >= 0 && y < NB_ROWS;
 }
 
}
