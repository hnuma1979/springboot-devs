package jp.mirageworld.spring.common.exception;

import java.util.Collections;
import java.util.Map;

/**
 * 入力エラーを表す。
 */
public class ValidationException extends RuntimeException {

        private final Map<String, String> errors;

        public  ValidationException (String key, String value) {
            this(Map.of(key, value));
        }

        public ValidationException(Map<String, String> errors) {
            super(errors.toString());
            this.errors = errors;
        }

        public Map<String, String> getErrors() {
            return Collections.unmodifiableMap(this.errors);
        }
}
