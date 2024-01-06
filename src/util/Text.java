package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Text {

  public static final int LINELENGTH = 80;
  
  private final List<String> text;
  
  public Text(String text, boolean data) {
    Objects.requireNonNull(text);
    String newText = text.replaceAll("\\\"\\\"\\\"", "").stripIndent();
    if (data) {
      this.text = refactorText(newText);
    } else {
      this.text = setReadable(newText);
    }
  }
  
  public Text(String text) {
    this(text, false);
  }
  
  
  public List<String> text() {
    return text;
  }
  
  private List<String> refactorText(String text) {
    String[] lines = text.split("\n");
        
    List<String> res = Arrays.stream(lines)
        .map(line -> line.replaceAll("\n", ""))
        .collect(Collectors.toList());
    
    res.removeIf(line -> line.isEmpty());
    return res;
  }
  
  private List<String> setReadable(String text) {
    ArrayList<String> newText = new ArrayList<String>();
    String[] lines = text.split("\n");
    
    for (int i = 1; i < lines.length; i++) { // set i to 1 to prevent empty line at begining
      if (lines[i].length() < LINELENGTH) {
        newText.add(lines[i]);
      } else {
        newText.addAll(cutLine(lines[i]));
      }
    }
    return List.copyOf(newText);
  }
  
  private List<String> cutLine(String line) {
    ArrayList<String> newLine = new ArrayList<String>();
    String[] words = line.split(" ");
    StringBuilder builder = new StringBuilder();
    
    int wordIndex = 0;
    int currentLength = 0;
    while (wordIndex < words.length) {
      if (currentLength + words[wordIndex].length() < LINELENGTH) {
        builder.append(words[wordIndex]).append(" ");
        currentLength += words[wordIndex].length();
        wordIndex++;
      } else {
        newLine.add(builder.toString());
        builder = new StringBuilder();
        currentLength = 0;
      }
    }

    if (builder.length() != 0) {
      newLine.add(builder.toString());
    }
    
    return List.copyOf(newLine);
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
