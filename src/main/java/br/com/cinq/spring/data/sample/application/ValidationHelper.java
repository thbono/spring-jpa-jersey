package br.com.cinq.spring.data.sample.application;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ValidationHelper {

    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    private ValidationHelper() {
    }

    public static <T> List<ValidationError> validateList(List<T> objects) {
        final Validator validator = validatorFactory.getValidator();
        final List<ValidationError> errors = Lists.newArrayList();

        IntStream.range(BigInteger.ZERO.intValue(), objects.size()).forEach(index ->
                errors.addAll(validator.validate(objects.get(index)).stream().map(c -> new ValidationError(index, c))
                        .collect(Collectors.toList()))
        );

        return errors;
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class ValidationError {
        private int index;

        private String message;

        protected ValidationError() {
        }

        ValidationError(final int index, final ConstraintViolation violation) {
            this.index = index;
            this.message = String.format("%s %s", violation.getPropertyPath(), violation.getMessage());
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("index", index)
                    .add("message", message)
                    .toString();
        }
    }

}
