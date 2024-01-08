package parser.commandline;

@SuppressWarnings("serial")
public class CommandLineException extends Exception {
  public CommandLineException(String errorMessage) {
    super(errorMessage);
  }
}
