package graphic.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import game.entity.item.Item;

public class TradeController {

  private final InventoryController inventory;
  private Map<String, List<Item>> trade = null;
  private boolean isTradeInterfaceShow = false;
  private int index = 0;
  
  public TradeController(InventoryController inventory) {
    Objects.requireNonNull(inventory);
    this.inventory = inventory;
  }
  
  public Map<String, List<Item>> trade() {
    return trade;
  }
  
  public int index() {
    return index;
  }
  
  public boolean hasTrade() {
    return trade != null;
  }
  
  public boolean isTradeInterfaceShow() {
    return isTradeInterfaceShow;
  }
  
  public void setTrade(Map<String, List<Item>> trade) {
    Objects.requireNonNull(trade);
    this.trade = trade;
  }
  
  public void toggleIsTradeInterfaceShow() {
    isTradeInterfaceShow = !isTradeInterfaceShow;
    inventory.toggleInventoryDisplay();
    if (!isTradeInterfaceShow) {
      trade = null;
    } else {
      index = 0;
    }
  }
  
  
}
