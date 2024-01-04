package graphic.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import game.Inventory;
import game.entity.item.Item;
import game.entity.mob.Friend;
import util.Direction;

public class TradeController {

  private final InventoryController inventory;
  private Friend friend;
  private boolean isTradeInterfaceShow = false;
  private int cursor = 0;
  private int visualCursor = 0;
  private int totalLength = 0;
  private int minIndex = 0;
  private int maxIndex;
  private final int consecutiveTradeLength = 10;
  
  public TradeController(InventoryController inventory) {
    Objects.requireNonNull(inventory);
    this.inventory = inventory;
  }
  
  public Map<Item, List<Item>> trade() {
    return friend.trade();
  }
  
  public List<String> text() {
    return friend.text();
  }
  
  public boolean hasText() {
    return friend.hasText();
  }
  
  public int cursor() {
    return cursor;
  }
  
  public int visualCursor() {
    return visualCursor;
  }
  
  public int consecutiveTradeLength() {
    return consecutiveTradeLength;
  }
  
  public int minIndex() {
    return minIndex;
  }
  
  public int maxIndex() {
    return maxIndex;
  }
  
  public int totalLength() {
    return totalLength;
  }
  
  public boolean hasTrade() {
    return friend.trade() != null;
  }
  
  public boolean isTradeInterfaceShow() {
    return isTradeInterfaceShow;
  }
  
  public void setFriend(Friend friend) {
    Objects.requireNonNull(friend);
    this.friend = friend;
    this.totalLength = updateTotalLength();
    this.maxIndex = Math.min(totalLength, consecutiveTradeLength);
    this.minIndex = 0;
  }
  
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
  
  public void toggleIsTradeInterfaceShow() {
    isTradeInterfaceShow = !isTradeInterfaceShow;
    inventory.toggleInventoryDisplay();
    if (!isTradeInterfaceShow) {
      friend = null;
    } else {
      cursor = 0;
      visualCursor = 0;
    }
  }
  
  public void moveCursor(Direction dir) {
    if (!(dir == Direction.NORTH || dir == Direction.SOUTH)) return;

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
}
