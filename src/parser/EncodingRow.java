package parser;

import java.util.Objects;

import game.GameObjectID;

public record EncodingRow(String skin, Character code, GameObjectID id) {

  public EncodingRow {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(code);
  }
  
}
