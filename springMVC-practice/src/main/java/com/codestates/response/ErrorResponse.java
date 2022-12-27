package com.codestates.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private List<FieldError> fieldErrors;
    private List<ConstraintViolationError> constraintViolationErrors;

    public static ErrorResponse of(BindingResult bindingResult){
        return new ErrorResponse(FieldError.of(bindingResult), null);
    }

    public static ErrorResponse of(Set<ConstraintViolation<?>> violations){
        return new ErrorResponse(null, ConstraintViolationError.of(violations));
    }

    @Getter
    @AllArgsConstructor
    public static class FieldError{
        private String field;
        private Object rejectedValue;
        private String reason;

        public static List<FieldError> of(BindingResult bindingResult){
            final List<org.springframework.validation.FieldError> fieldErrors =
                    bindingResult.getFieldErrors();

            return fieldErrors.stream()
                    .map(fieldError -> new FieldError(
                            fieldError.getField(),
                            fieldError.getRejectedValue() == null ?
                                    "" : fieldError.getRejectedValue().toString(),
                            fieldError.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }

    public static class ConstraintViolationError{
        private String propertyPath;
        private Object rejectedValue;
        private String reason;

        private ConstraintViolationError(String propertyPath, Object rejectedValue, String reason) {
            this.propertyPath = propertyPath;
            this.rejectedValue = rejectedValue;
            this.reason = reason;
        }

        public static List<ConstraintViolationError> of(Set<ConstraintViolation<?>> constraintViolations){
            return constraintViolations.stream()
                    .map(constraintViolation -> new ConstraintViolationError(
                            constraintViolation.getPropertyPath().toString(),
                            constraintViolation.getInvalidValue().toString(),
                            constraintViolation.getMessage()))
                    .collect(Collectors.toList());
        }
    }
}
