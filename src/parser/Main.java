package parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
  public static void main(String[] args) throws IOException {
    var path = Path.of("map/default.map");
    var text = Files.readString(path);
    var parser = new Parser(text);
    
    try {
      System.out.println(parser.parseMap());
      
    } catch (TokenException e) {
      e.printStackTrace();
    } 
  }
}
