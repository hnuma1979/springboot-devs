package jp.mirageworld.spring.common.controller.adviser;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;


import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

import jp.mirageworld.spring.common.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@ControllerAdvice
public class AppControllerAdvice {
    
    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<Map<String,String>> errors(WebExchangeBindException exception) {
        log.debug("START（errors：BindException）");
        try {
            List<FieldError> fieldErrors = exception.getFieldErrors();
            Map<String,Set<String>> errors = new HashMap<>();
            fieldErrors .forEach(error->{
                String key = error.getField();
                if (!errors.containsKey(key))
                    errors.put(key, new TreeSet<>());
                errors.get(key).add(error.getDefaultMessage());
            });

            return Mono.just(errors.entrySet().stream().collect(
                    Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> String.join("<br>", entry.getValue())
                    )
            ));
        } finally {
            log.warn(exception.getMessage());
            log.debug("END（errors：BindException）");
        }
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<Map<String,String>> errors(ValidationException exception) {
        log.debug(exception.getLocalizedMessage());
        return Mono.just(exception.getErrors());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Flux<String> errors(Exception exception) {
        log.error(exception.getLocalizedMessage(), exception);
        return Flux.error(exception);
    }
}
