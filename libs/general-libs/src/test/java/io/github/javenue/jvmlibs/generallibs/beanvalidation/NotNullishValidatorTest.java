package io.github.javenue.jvmlibs.generallibs.beanvalidation;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.Validation;
import javax.validation.Validator;
import io.github.javenue.jvmlibs.generallibs.beanvalidation.annotaion.NotNullish;
import io.github.javenue.jvmlibs.generallibs.designpattern.NullObject;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class NotNullishValidatorTest {

  @Nested
  class WhenConstraintBeanValidated {

    static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private static class MyValueObject {

    }

    private static class MyNullObject extends MyValueObject implements NullObject {

    }

    static class MyBean {

      @NotNullish
      MyValueObject object;

      MyBean(MyValueObject object) {
        this.object = object;
      }

    }

    @Test
    void givenNonNullishObjectThenNoViolationDetected() {
      assertThat(validator.validate(new MyBean(new MyValueObject()))).isEmpty();
    }

    @Test
    void givenNullThenViolationDetected() {
      assertThat(validator.validate(new MyBean(null)))
          .extracting("message")
          .containsOnly("must not be null nor NullObject");
    }

    @Test
    void givenNullishObjectThenViolationDetected() {
      assertThat(validator.validate(new MyBean(new MyNullObject())))
          .extracting("message")
          .containsOnly("must not be null nor NullObject");
    }

  }

}
