package it.gov.pagopa.pu.worker.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import it.gov.pagopa.pu.worker.dto.generated.WorkerErrorDTO;
import it.gov.pagopa.pu.worker.util.Utilities;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WorkerExceptionHandler {

  @ExceptionHandler({ValidationException.class, HttpMessageNotReadableException.class, MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class})
  public ResponseEntity<WorkerErrorDTO> handleViolationException(Exception ex, HttpServletRequest request) {
    return handleException(ex, request, HttpStatus.BAD_REQUEST, WorkerErrorDTO.CodeEnum.WORKER_BAD_REQUEST);
  }

  @ExceptionHandler({ServletException.class, ErrorResponseException.class})
  public ResponseEntity<WorkerErrorDTO> handleServletException(Exception ex, HttpServletRequest request) {
    HttpStatusCode httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    WorkerErrorDTO.CodeEnum errorCode = WorkerErrorDTO.CodeEnum.WORKER_GENERIC_ERROR;
    if (ex instanceof ErrorResponse errorResponse) {
      httpStatus = errorResponse.getStatusCode();
      if (httpStatus.isSameCodeAs(HttpStatus.NOT_FOUND)) {
        errorCode = WorkerErrorDTO.CodeEnum.WORKER_NOT_FOUND;
      } else if (httpStatus.is4xxClientError()) {
        errorCode = WorkerErrorDTO.CodeEnum.WORKER_BAD_REQUEST;
      }
    }
    return handleException(ex, request, httpStatus, errorCode);
  }

  @ExceptionHandler({RuntimeException.class})
  public ResponseEntity<WorkerErrorDTO> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
    return handleException(ex, request, HttpStatus.INTERNAL_SERVER_ERROR, WorkerErrorDTO.CodeEnum.WORKER_GENERIC_ERROR);
  }

  static ResponseEntity<WorkerErrorDTO> handleException(Exception ex, HttpServletRequest request, HttpStatusCode httpStatus, WorkerErrorDTO.CodeEnum errorEnum) {
    logException(ex, request, httpStatus);

    String message = buildReturnedMessage(ex);

    return ResponseEntity
      .status(httpStatus)
      .contentType(MediaType.APPLICATION_JSON)
      .body(new WorkerErrorDTO(errorEnum, message, Utilities.getTraceId()));
  }

  private static void logException(Exception ex, HttpServletRequest request, HttpStatusCode httpStatus) {
    boolean printStackTrace = httpStatus.is5xxServerError();
    Level logLevel = printStackTrace ? Level.ERROR : Level.INFO;
    log.makeLoggingEventBuilder(logLevel)
      .log("A {} occurred handling request {}: HttpStatus {} - {}",
        ex.getClass(),
        getRequestDetails(request),
        httpStatus.value(),
        ex.getMessage(),
        printStackTrace ? ex : null
      );
    if (!printStackTrace && log.isDebugEnabled() && ex.getCause() != null) {
      log.debug("CausedBy: ", ex.getCause());
    }
  }

  private static String buildReturnedMessage(Exception ex) {
    switch (ex) {
      case HttpMessageNotReadableException httpMessageNotReadableException -> {
        if (httpMessageNotReadableException.getCause() instanceof JsonMappingException jsonMappingException) {
          return "Cannot parse body. " +
            jsonMappingException.getPath().stream()
              .map(JsonMappingException.Reference::getFieldName)
              .collect(Collectors.joining(".")) +
            ": " + jsonMappingException.getOriginalMessage();
        }
        return "Required request body is missing";
      }
      case MethodArgumentNotValidException methodArgumentNotValidException -> {
        return "Invalid request content." +
          methodArgumentNotValidException.getBindingResult()
            .getAllErrors().stream()
            .map(e -> " " +
              (e instanceof FieldError fieldError ? fieldError.getField() : e.getObjectName()) +
              ": " + e.getDefaultMessage())
            .sorted()
            .collect(Collectors.joining(";"));
      }
      case ConstraintViolationException constraintViolationException -> {
        return "Invalid request content." +
          constraintViolationException.getConstraintViolations()
            .stream()
            .map(e -> " " + e.getPropertyPath() + ": " + e.getMessage())
            .sorted()
            .collect(Collectors.joining(";"));
      }
      default -> {
        return ex.getMessage();
      }
    }
  }

  static String getRequestDetails(HttpServletRequest request) {
    return "%s %s".formatted(request.getMethod(), request.getRequestURI());
  }
}
