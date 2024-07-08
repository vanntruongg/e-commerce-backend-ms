package identityservice.exception;

public class AccountUnAvailableException extends RuntimeException {
  private final transient ErrorDetail errorDetail;
  public AccountUnAvailableException(int errorCode, String message) {
    super(message);
    this.errorDetail = ErrorDetail.builder()
            .errorCode(errorCode)
            .errorMessage(message)
            .build();
  }

  public AccountUnAvailableException(int errorCode, String message, Throwable cause) {
    super(message, cause);
    this.errorDetail = ErrorDetail.builder()
            .errorCode(errorCode)
            .errorMessage(message)
            .build();
  }
}
