package graphic.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import game.Inventory;
import game.entity.item.Item;
import util.Direction;

public class TradeController {

  private final InventoryController inventory;
  private Map<Item, List<Item>> trade = null;
  private boolean isTradeInterfaceShow = false;
  private int cursor = 0;
  private int totalLength = 0;
  
  public TradeController(InventoryController inventory) {
    Objects.requireNonNull(inventory);
    this.inventory = inventory;
  }
  
  public Map<Item, List<Item>> trade() {
    return trade;
  }
  
  public int cursor() {
    return cursor;
  }
  
  public int totalLength() {
    return totalLength;
  }
  
  public boolean hasTrade() {
    return trade != null;
  }
  
  public boolean isTradeInterfaceShow() {
    return isTradeInterfaceShow;
  }
  
  public void setTrade(Map<Item, List<Item>> trade) {
    Objects.requireNonNull(trade);
    this.trade = trade;
    this.totalLength = updateTotalLength();
  }
  
  private int updateTotalLength() {
    if (trade == null) {
      return 0;
    }
    int res = 0;
    for (var entry: trade.entrySet()) {
      res += entry.getValue().size();
    }
    return res;
  }
  
  public void toggleIsTradeInterfaceShow() {
    isTradeInterfaceShow = !isTradeInterfaceShow;
    inventory.toggleInventoryDisplay();
    if (!isTradeInterfaceShow) {
      trade = null;
    } else {
      cursor = 0;
    }
  }
  
  public void moveCursor(Direction dir) {
    if (!(dir == Direction.NORTH || dir == Direction.SOUTH)) return;
    if (cursor == 0 && dir == Direction.NORTH) return;
    if (cursor == trade.size() && dir == Direction.SOUTH) return;

    if (dir == Direction.SOUTH) {
      cursor++;
    } else {
      cursor--;
    }
  }
  
  public void tradeItem() {
   int index = 0;
   Inventory inv = inventory.inventory();
   for (var entry: trade.entrySet()) {
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
