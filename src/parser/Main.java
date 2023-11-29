package parser;

import java.io.IOException;

import game.Game;

public class Main {
  public static void main(String[] args) {
    try {
      // game = Parser.parseMap("map/badGridDataEncodingDefinedTwice.map");
      // game = Parser.parseMap("map/badGridDataEncodingNotALetter.map");
      // game = Parser.parseMap("map/monster_house.map");
      Parser.parseMap("map/default.map");
    } catch (IOException e) {
      e.printStackTrace();
    } catch (TokenException e) {
      e.printStackTrace();
    }
  }
}
