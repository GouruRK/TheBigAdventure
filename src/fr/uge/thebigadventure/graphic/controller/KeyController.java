package fr.uge.thebigadventure.graphic.controller;

import java.util.Objects;

import fr.umlv.zen5.KeyboardKey;

/**
 * Associate a key with an action
 */
public class KeyController {
  
  /**
   * Get action from keys
   * @param key
   * @return
   */
  public static KeyOperation getOperation(KeyboardKey key) {
    Objects.requireNonNull(key);
    return switch (key) {
      case KeyboardKey.UP, KeyboardKey.Z -> KeyOperation.UP;
      case KeyboardKey.RIGHT, KeyboardKey.D -> KeyOperation.RIGHT;
      case KeyboardKey.DOWN, KeyboardKey.S -> KeyOperation.DOWN;
      case KeyboardKey.LEFT, KeyboardKey.Q -> KeyOperation.LEFT;
      case KeyboardKey.I -> KeyOperation.INVENTORY;
      case KeyboardKey.A -> KeyOperation.DROP;
      case KeyboardKey.SPACE -> KeyOperation.ACTION;
      case KeyboardKey.UNDEFINED -> KeyOperation.EXIT;
      default -> KeyOperation.NONE;
    };
  }
}
