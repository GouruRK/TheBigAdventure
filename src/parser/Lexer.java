package parser;

import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Lexer {
  private static final List<Token> TOKENS = List.of(Token.values());
  private static final Pattern PATTERN = Pattern.compile(
      TOKENS.stream()
          .map(token -> "(" + token.regex + ")")
          .collect(Collectors.joining("|")));

  private final String text;
  private final Matcher matcher;
  
  private final Stack<Result> stack;
  
  public Lexer(String text) {
    this.text = Objects.requireNonNull(text);
    this.matcher = PATTERN.matcher(text);
    this.stack = new Stack<Result>();;
  }

  public boolean hasNext() {
    if (!stack.isEmpty()) {
      return true;
    }
    Result next = nextResult();
    if (next != null) {
      stack.add(next);
      return true;
    }
    return false;
  }
  
  public void addNext(Result res) {
    Objects.requireNonNull(res);
    stack.add(res);
  }

  
  public Result nextResult() {
    if (!stack.isEmpty()) {
      return stack.pop();
    }
    var matches = matcher.find();
    if (!matches) {
       return null; 
     }
    for (var group = 1; group <= matcher.groupCount(); group++) {   
      var start = matcher.start(group);
      if (start != -1) {
        var end = matcher.end(group);
        var content = text.substring(start, end);
        
//        line += content.chars().mapToObj(obj -> (char)obj).filter(c -> c == '\n').count();
//        content.replaceAll("\n", "");
        
        return new Result(TOKENS.get(group - 1), content);
      }
    }
    throw new AssertionError();
  }

  
//  public static void main(String[] args) throws IOException {
//    var path = Path.of("map/default.map");
//    var text = Files.readString(path);
//    var lexer = new Lexer(text);
//    Result result;
//    while((result = lexer.nextResult()) != null) {
//      System.out.println(result);
//    }
//  }
}


// Experimentation with number of line
//public class Lexer {
//  private static final List<Token> TOKENS = List.of(Token.values());
//  private static final Pattern PATTERN = Pattern.compile(
//      TOKENS.stream()
//          .map(token -> "(" + token.regex + ")")
//          .collect(Collectors.joining("|")));
//
//  private final String[] text;
//  private final int textSize;
//  
//  private Matcher matcher;
//  private Result next = null;
//  private Result last = null;
//  private int line = 0;
//  
//  public Lexer(String text) {
//    this.text = Objects.requireNonNull(text).split("\n");
//    this.matcher = PATTERN.matcher(this.text[line]);
//    this.textSize = text.length();
//  }
//
//  public boolean hasNext() {
//    if (next != null) {
//      return true;
//    }
//    next = nextResult();
//    return next != null;
//  }
//  
//  public void addNext(Result res) {
//    next = res;
//  }
//
//  public Result getLast() {
//    return last;
//  }
//  
//  public Result nextResult() {
//    if (next != null) {
//      Result res = next;
//      next = null;
//      last = res;
//      return res;
//    }
//    var matches = matcher.find();
//    if (!matches) {
//      if (line == textSize) {
//        return null; 
//      }
//      this.line++;
//      this.matcher = PATTERN.matcher(this.text[this.line]);
//      return nextResult();
//    }
//    for (var group = 1; group <= matcher.groupCount(); group++) {
//      var start = matcher.start(group);
//      if (start != -1) {
//        var end = matcher.end(group);
//        var content = text[line].substring(start, end);
//        Result res = new Result(TOKENS.get(group - 1), content, line + 1);
//        last = res;
//        return res;
//      }
//    }
//    throw new AssertionError();
//  }
//
//  
//  public static void main(String[] args) throws IOException {
//    var path = Path.of("map/default.map");
//    var text = Files.readString(path);
//    var lexer = new Lexer(text);
//    Result result;
//    while((result = lexer.nextResult()) != null) {
//      System.out.println(result);
//    }
//  }
//}

