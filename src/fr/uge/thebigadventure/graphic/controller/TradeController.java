package fr.uge.thebigadventure.graphic.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import fr.uge.thebigadventure.game.Inventory;
import fr.uge.thebigadventure.game.entity.item.Item;
import fr.uge.thebigadventure.game.entity.mob.Friend;
import fr.uge.thebigadventure.util.Direction;

public class TradeController {

  //------- Constants -------
  
  /**
   * Maximum number of trade to display at the same time
   */
  private final int consecutiveTradeLength = 10;

  //------- Fields -------
  
  /**
   * Controller for inventory
   */
  private final InventoryController inventory;
  
  /**
   * Controller for text
   */
  private final TextController text;
  
  /**
   * Current friend the player is talking to
   * Not final because the player can talk and trade with different friends
   */
  private Friend friend;
  
  /**
   * Intels if the trade interface is currently display
   */
  private boolean isTradeInterfaceShow = false;
  
  /**
   * Index of selected trade
   */
  private int cursor = 0;
  
  /**
   * Position of the cursor on the window
   * It's equals to cursor except all trades
   * cannot be displayed at the same time 
   * (number of trade greater then consecutiveTradeLength)
   */
  private int visualCursor = 0;
  
  /**
   * Number of trades
   */
  private int totalLength = 0;
  
  /**
   * Smallest trade index displayed
   */
  private int minIndex = 0;
  
  /**
   * Greatest trade index displayd
   */
  private int maxIndex;
  
  //------- Constructor -------
  
  public TradeController(InventoryController inventory, TextController text) {
    Objects.requireNonNull(inventory);
    Objects.requireNonNull(text);
    this.inventory = inventory;
    this.text = text;
  }
  
  //------- Getter -------
  
  /**
   * Get trade map
   * @return
   */
  public Map<Item, List<Item>> trade() {
    return friend.trade();
  }
  
  /**
   * Get text controller if friend has text
   * @return
   */
  public TextController textController() {
    return text;
  }
  
  /**
   * Get friend's text 
   * @return
   */
  public boolean hasText() {
    return friend.hasText();
  }
  
  /**
   * Get current trade cursor
   * @return
   */
  public int cursor() {
    return cursor;
  }
  
  /**
   * Get current visual trade cursor
   * @return
   */
  public int visualCursor() {
    return visualCursor;
  }
  
  /**
   * Get maximum number of trade displayed at the same time
   * @return
   */
  public int consecutiveTradeLength() {
    return consecutiveTradeLength;
  }
  
  /**
   * Get lowest trade index
   * @return
   */
  public int minIndex() {
    return minIndex;
  }
  
  /**
   * Get greatest trade index
   * @return
   */
  public int maxIndex() {
    return maxIndex;
  }
  
  /**
   * Get number of trades
   * @return
   */
  public int totalLength() {
    return totalLength;
  }
  
  /**
   * Check if a friend can trade things
   * @return
   */
  public boolean hasTrade() {
    return friend.trade() != null;
  }
  
  /**
   * Give intel if the trade interface is currently displayed
   * @return
   */
  public boolean isTradeInterfaceShow() {
    return isTradeInterfaceShow;
  }
  
  //------- Modifiers -------
  
  public void setFriend(Friend friend) {
    Objects.requireNonNull(friend);
    
    this.friend = friend;
    this.totalLength = updateTotalLength();
    this.maxIndex = Math.min(totalLength, consecutiveTradeLength);
    this.minIndex = 0;
    if (friend.hasText()) {
      text.setText(friend.text());
    }
  }
  
  
  /**
   * Toggle trade interface
   */
  public void toggleIsTradeInterfaceShow() {
    isTradeInterfaceShow = !isTradeInterfaceShow;
    inventory.toggleIsInventoryInterfaceShow();
    text.toggleIsTextInterfaceShow();
    
    if (!isTradeInterfaceShow) {
      friend = null;
    } else {
      cursor = 0;
      visualCursor = 0;
    }
  }
  
  /**
   * Key controller while on the trade interface
   * @param dir
   */
  public void moveCursor(Direction dir) {
    Objects.requireNonNull(dir);
    if (!(dir == Direction.NORTH || dir == Direction.SOUTH)) {
      text.changePage(dir);
      return;
    }

    if (dir == Direction.NORTH) {
      if (cursor == 0) return;
      cursor--;
      if (cursor >= minIndex) {
        visualCursor--;
      } else {
        minIndex--;
        maxIndex--;
      }
    } else {
      if (cursor == totalLength - 1) return;
      cursor++;
      if (cursor != maxIndex) {
        visualCursor++;
      } else {
        minIndex++;
        maxIndex++;
      }
    }
  }
  
  public void tradeItem() {
   int index = 0;
   Inventory inv = inventory.inventory();
   for (var entry: friend.trade().entrySet()) {
     for (Item item: entry.getValue()) {
       if (index == cursor) {
         if (inventory.player().hold() != null && inventory.player().hold().equals(entry.getKey())) {
           inventory.player().removeHeldItem();
           inventory.player().setHold(item);
           toggleIsTradeInterfaceShow();
         } else if (inv.contains(entry.getKey())) {
           inv.remove(entry.getKey());
           inv.add(item);
           toggleIsTradeInterfaceShow();
           return;
         }
       }
       index++;
     }
   }
  }

  /**
   * Calculate the number of trades
   * @return
   */
  private int updateTotalLength() {
    if (friend.trade() == null) {
      return 0;
    }
    int res = 0;
    for (var entry: friend.trade().entrySet()) {
      res += entry.getValue().size();
    }
    return res;
  }
}
