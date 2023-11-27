package parser;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import game.Game;
import util.Position;

public class GameAttributes {

  private String data = null;
  private Position size = null;
  private Map<String, EncodingRow> encodings = null;
  
  private static boolean isSizeValid(Position size) {
    return size.x() > 0 && size.y() > 0;
  }
  
  public void addData(String data) throws TokenException {
    Objects.requireNonNull(data);
    if (this.data != null) {
      throw new TokenException("Data already register");
    }
    this.data = data;
  }
  
  public void addSize(Position size) throws TokenException {
    Objects.requireNonNull(size);
    if (this.size != null) {
      throw new TokenException("Size already register");
    }
    if (GameAttributes.isSizeValid(size)) {      
      this.size = size;
    }
    throw new TokenException("Invalid given size");
  }
  
  public void addEncodings(Map<String, EncodingRow> encodings) throws TokenException {
    Objects.requireNonNull(encodings);
    if (this.encodings != null) {
      throw new TokenException("Encodings already register");
    }
    this.encodings = encodings;
  }
  
  private List<char[]> checkData() throws TokenException {
    int width = (int) size.x(), height = (int) size.y();
    String tempData = data.stripIndent();
    String[] lineArray = tempData.split("\n");

    List<char[]> field = Arrays.stream(lineArray)
        .map(line -> line.toCharArray())
        .collect(Collectors.toList());
    
    if (field.size() != height) {
      throw new TokenException("Invalid map size");
    }
    
    if (!field.stream().allMatch(line -> line.length == width)) {
      throw new TokenException("Invalid map size");
    }
    return field;
  }
  
  public boolean isFull() {
    return (data != null) && (size != null) && (encodings != null);
  }
  
  public Game createGame() throws TokenException {
    if (!isFull()) {
      throw new TokenException("Missing datas to create game object");
    }
    
    return new Game(size, encodings, checkData());
  }
  
}
