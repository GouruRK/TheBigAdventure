package parser;

import java.util.Objects;

import game.GameObjectID;

public record EncodingRow(String skin, String code, GameObjectID id) {

  public EncodingRow {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(code);
  }
  
}
