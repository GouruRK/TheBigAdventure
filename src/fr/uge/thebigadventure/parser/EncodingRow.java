package fr.uge.thebigadventure.parser;

import java.util.Locale;
import java.util.Objects;

import fr.uge.thebigadventure.game.GameObjectID;

public record EncodingRow(String skin, Character code, GameObjectID id) {

  public EncodingRow(String skin, Character code, GameObjectID id) {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(code);
    
    this.skin = skin.toUpperCase(Locale.ROOT);
    this.code = code;
    this.id = id;
  }
  
}
