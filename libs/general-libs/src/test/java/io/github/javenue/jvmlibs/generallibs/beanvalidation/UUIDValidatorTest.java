package io.github.javenue.jvmlibs.generallibs.beanvalidation;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.Validation;
import javax.validation.Validator;
import io.github.javenue.jvmlibs.generallibs.beanvalidation.annotaion.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class UUIDValidatorTest {

  @Nested
  class WhenConstrainedBeanValidated {

    static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    static class MyBean {

      @UUID
      String value;

      public MyBean(String value) {
        this.value = value;
      }

    }

    @Test
    void givenValidUuidThenNoViolationDetected() {
      assertThat(validator.validate(new MyBean("f81d4fae-7dec-11d0-a765-00a0c91e6bf6"))).isEmpty();
    }

    @Test
    void givenInvalidUuidThenViolationDetected() {
      assertThat(validator.validate(new MyBean("Invalid UUID.")))
          .extracting("message")
          .containsOnly("invalid UUID");
    }

    @Test
    void givenNullThenNoViolationDetected() {
      assertThat(validator.validate(new MyBean(null))).isEmpty();
    }

  }

}
