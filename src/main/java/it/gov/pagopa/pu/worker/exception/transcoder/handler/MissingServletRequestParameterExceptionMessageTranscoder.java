package it.gov.pagopa.pu.worker.exception.transcoder.handler;

import it.gov.pagopa.pu.worker.dto.generated.WorkerErrorDTO;
import it.gov.pagopa.pu.worker.dto.generated.ErrorFieldDTO;
import it.gov.pagopa.pu.worker.exception.transcoder.ExceptionMessageTranscoded;
import it.gov.pagopa.pu.worker.exception.transcoder.ExceptionMessageTranscoder;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.List;

public class MissingServletRequestParameterExceptionMessageTranscoder implements ExceptionMessageTranscoder<MissingServletRequestParameterException> {

  @Override
  public ExceptionMessageTranscoded transcode(MissingServletRequestParameterException missingServletRequestParameterException) {
    return new ExceptionMessageTranscoded(
      WorkerErrorDTO.CategoryEnum.WORKER_BAD_REQUEST.getValue(),
      missingServletRequestParameterException.getMessage(),
      List.of(new ErrorFieldDTO(missingServletRequestParameterException.getParameterName(), "NotNull", missingServletRequestParameterException.getMessage())));
  }
}
