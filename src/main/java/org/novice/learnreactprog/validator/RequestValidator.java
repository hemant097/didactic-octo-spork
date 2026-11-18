package org.novice.learnreactprog.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.novice.learnreactprog.dto.UserRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class RequestValidator {

    private final Validator validator;

    public <T> Mono<T> validate(T request) {
        Set<ConstraintViolation<T>> violations = validator.validate(request);
        if(!violations.isEmpty()){
            Map<String,String> errors = violations.stream()
                    .collect(Collectors.toMap(
                            k -> k.getPropertyPath().toString(),
                            v-> v.getMessage(),
                            (k,v) -> k
                    ));
            throw new ValidationException(errors.toString());
        }
        return Mono.just(request);
    }
}
