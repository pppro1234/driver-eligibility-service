package Registration.IO;

/**
 * Thrown when the prospective driver file cannot be parsed.
 *
 * <p>Used by {@link ProspectiveDriverFileParser} for JSON parsing and validation errors.</p>
 */
public class FileParseException extends Exception {

  public FileParseException(String message) {
    super(message);
  }

  public FileParseException(String message, Throwable cause) {
    super(message, cause);
  }
}
