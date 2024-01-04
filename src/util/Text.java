package util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Text {

  private final List<String> text;
  
  public Text(String text) {
    Objects.requireNonNull(text);
    this.text = refactorText(text);
  }
  
  public List<String> text() {
    return text;
  }
  
  private List<String> refactorText(String text) {
    String temp = text.replaceAll("\\\"\\\"\\\"", "").stripIndent();
    String[] lines = temp.split("\n");
        
    List<String> res = Arrays.stream(lines)
        .map(line -> line.replaceAll("\n", ""))
        .collect(Collectors.toList());
    
    res.removeIf(line -> line.isEmpty());
    return res;
  }
  
  public String longestLine() {
    return text.stream().max(Comparator.comparingInt(String::length)).orElse("");
  }
  
  public int longestLineLength() {
    return longestLine().length();
  }
  
  public int numberOfLines() {
    return text.size();
  }
  
  public String get(int index) {
    return text.get(index);
  }
  
}
