package com.auroralibrary.library.exception;

import java.util.List;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponse {
  private Error error;

  @Data
  public static class Error {
    private int status;
    private String title;
    private List<Issue> issues;
    private HttpStatus httpStatus;
  }

  @Data
  public static class Issue {
    private String field;
    private String message;

    public Issue(String field, String message) {
      this.field = field;
      this.message = message;
    }
  }

  public ErrorResponse(int status, String title, String field, String message) {
    this.error = new Error();
    this.error.setStatus(status);
    this.error.setTitle(title);
    this.error.setIssues(List.of(new Issue(field, message)));
  }

  public ErrorResponse(HttpStatus httpStatus, String title, String field, String message) {
    this.error = new Error();
    this.error.setStatus(httpStatus.value());
    this.error.setTitle(title);
    this.error.setIssues(List.of(new Issue(field, message)));
    this.error.setHttpStatus(httpStatus);
  }
}
