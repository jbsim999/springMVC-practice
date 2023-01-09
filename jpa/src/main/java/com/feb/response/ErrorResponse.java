package com.feb.response;

import com.feb.exception.BusinessLogicException;
import com.feb.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ErrorResponse {
    private List<FieldError> fieldErrors;
    private List<ConstraintViolationError> violationErrors;

    private int status;
    private String message;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ErrorResponse(List<FieldError> fieldErrors, List<ConstraintViolationError> violationErrors){
        this.fieldErrors = fieldErrors;
        this.violationErrors = violationErrors;
    }

    public static ErrorResponse of(BindingResult bindingResult){
        return new ErrorResponse(FieldError.of(bindingResult),null);
    }

    public static ErrorResponse of(Set<ConstraintViolation<?>> violations){
        return new ErrorResponse(null, ConstraintViolationError.of(violations));
    }

    public static ErrorResponse of(ExceptionCode e){
        return new ErrorResponse(e.getStatus(),e.getMessage());
    }

    public static ErrorResponse of(HttpStatus httpStatus){
        return new ErrorResponse(httpStatus.value(),httpStatus.getReasonPhrase());
    }



    @Getter
    public static class FieldError{
        private String field;
        private Object rejectedValue;
        private String reason;

        public FieldError(String field, Object rejectedValue, String reason) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.reason = reason;
        }

        public static List<FieldError> of(BindingResult bindingResult){
            final List<org.springframework.validation.FieldError> fieldErrors =
                    bindingResult.getFieldErrors();

            return fieldErrors.stream()
                    .map(error-> new FieldError(
                            error.getField(),
                            error.getRejectedValue()==null ?
                            "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @AllArgsConstructor
    public static class ConstraintViolationError{
        private String propertyPath;
        private Object rejectedValue;
        private String reason;

        public static List<ConstraintViolationError> of(
                Set<ConstraintViolation<?>> constraintViolations){
            return constraintViolations.stream()
                    .map(constraintViolation -> new ConstraintViolationError(
                            constraintViolation.getPropertyPath().toString(),
                            constraintViolation.getInvalidValue().toString(),
                            constraintViolation.getMessage()))
                    .collect(Collectors.toList());
        }

    }
}
