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
          .map(token -> "(" + token.regex() + ")")
          .collect(Collectors.joining("|")));

  private final String text;
  private final Matcher matcher;
  
  private final Stack<Result> stack;
  
  private Result last;
  private int line;
  
  public Lexer(String text) {
    this.text = Objects.requireNonNull(text);
    this.matcher = PATTERN.matcher(text);
    this.stack = new Stack<Result>();;
    this.line = 1;
  }
  
  public Result last() {
    return last;
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
    Result next = filteredNextResult();
    while (next != null && next.token() == Token.NEWLINE) {
      next = filteredNextResult();
      line++;
    }
    last = next;
    if (next != null) {
      return new Result(next.token(), next.content(), line);
    }
    return next;
  }
  
  public Result filteredNextResult() {
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
        
        Result res = new Result(TOKENS.get(group - 1), content, line);
        if (res.token() == Token.QUOTE) {
          line += content.chars().mapToObj(obj -> (char)obj).filter(c -> c == '\n').count();
        }
        
        return res;
      }
    }
    throw new AssertionError();
  }
}
