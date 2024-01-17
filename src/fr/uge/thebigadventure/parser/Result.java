package fr.uge.thebigadventure.parser;

import java.util.Objects;

public record Result(Token token, String content, int line) {
  public Result {
    Objects.requireNonNull(token);
    Objects.requireNonNull(content);
  }
}
