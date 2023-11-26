package parser;

import java.util.Objects;

import game.GameObjectID;

public record EncodingCouple(String code, GameObjectID id) {

  public EncodingCouple {
    Objects.requireNonNull(code);
  }
  
}
