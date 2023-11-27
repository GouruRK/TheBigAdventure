package parser;

import java.io.IOException;

import game.Game;

public class Main {
  public static void main(String[] args) {
    try {
      // Game game = Parser.parseMap("map/badGridDataEncodingDefinedTwice.map");
      // Game game = Parser.parseMap("map/badGridDataEncodingNotALetter.map");
      Game game = Parser.parseMap("map/default.map");
    } catch (IOException e) {
      e.printStackTrace();
    } catch (TokenException e) {
      e.printStackTrace();
    }
  }
}
