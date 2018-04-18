package eu.eoscpilot.schema2jsonld.web.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApplicationExceptionHandler {

    public class ResponseInfo {
        private String message;

        public ResponseInfo() {}

        public ResponseInfo(String message) {
            this.setMessage(message);
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @JsonIgnore
        public Boolean isEmpty() {
            if (this.getMessage() != null && this.getMessage().length() > 0) return false;
            return true;
        }
    }

    @ExceptionHandler(ApplicationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseInfo processException(ApplicationException ex) {
        ResponseInfo nfo = new ResponseInfo();
        nfo.setMessage(ex.getMessage());
        return nfo;
    }
}
