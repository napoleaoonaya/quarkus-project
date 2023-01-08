package br.com.onaya.rest.dto;

import lombok.Data;

import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ResponseError {
    private String error;
    private Collection<FieldError> fieldErrorCollection;

    public ResponseError(String error, Collection<FieldError> fieldErrorCollection) {
        this.error = error;
        this.fieldErrorCollection = fieldErrorCollection;
    }

    public static <T>ResponseError createFromValidation(Set<ConstraintViolation<T>> violations){
        List<FieldError> errors = violations
                .stream()
                .map(cv -> new FieldError(cv.getPropertyPath().toString(), cv.getMessage()))
                .collect(Collectors.toList());
        String message = "Validation Error";
        return new ResponseError(message,errors);
    }

    public Response withStatusCode(int code){
        return Response.status(code).entity(this).build();
    }

}
