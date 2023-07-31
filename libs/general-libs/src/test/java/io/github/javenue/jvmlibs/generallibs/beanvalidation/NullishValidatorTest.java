package io.github.javenue.jvmlibs.generallibs.beanvalidation;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.Validation;
import javax.validation.Validator;
import io.github.javenue.jvmlibs.generallibs.beanvalidation.annotaion.Nullish;
import io.github.javenue.jvmlibs.generallibs.designpattern.NullObject;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class NullishValidatorTest {

  static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  static class MyValueObject {

  }

  static class MyNullObject extends MyValueObject implements NullObject {

  }

  @Nested
  class WhenConstraintBeanValidated {

    static class MyBean {

      @Nullish
      MyValueObject object;

      MyBean(MyValueObject object) {
        this.object = object;
      }

    }

    @Test
    void givenNullThenNoViolationDetected() {
      assertThat(validator.validate(new MyBean(null))).isEmpty();
    }

    @Test
    void givenNullishObjectThenNoViolationDetected() {
      assertThat(validator.validate(new MyBean(new MyNullObject()))).isEmpty();
    }

    @Test
    void givenNonNullishObjectThenViolationDetected() {
      assertThat(validator.validate(new MyBean(new MyValueObject())))
          .extracting("message")
          .containsOnly("must be null or NullObject");
    }

  }

}
