package it.gov.pagopa.pu.worker.exception;

import it.gov.pagopa.pu.worker.exception.common.CommonExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WorkerExceptionHandler extends CommonExceptionHandler {
}
