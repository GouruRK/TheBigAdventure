package parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
  public static void main(String[] args) throws IOException, TokenException {
    var path = Path.of("map/default.map");
    var text = Files.readString(path);
    var parser = new Parser(text, "map/default.map");
    
    System.out.println(parser.parseMap()); 
  }
}
