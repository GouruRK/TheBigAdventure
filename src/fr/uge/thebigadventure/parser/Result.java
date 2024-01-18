package fr.uge.thebigadventure.parser;

import java.util.Objects;

/**
 * Triplet of token, its content and line number the content originally was
 */
public record Result(Token token, String content, int line) {
  public Result {
    Objects.requireNonNull(token);
    Objects.requireNonNull(content);
  }
}
