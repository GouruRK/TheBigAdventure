package fr.uge.thebigadventure.parser.commandline;

/**
 * Exception when parsing command line arguments
 */
public class CommandLineException extends Exception {
  /**
   * Serial class number
   */
  private static final long serialVersionUID = 1L;

  //------- Constructor -------
  
  public CommandLineException(String errorMessage) {
    super(errorMessage);
  }
}
