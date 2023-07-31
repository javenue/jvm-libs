package io.github.javenue.jvmlibs.generallibs.beanvalidation;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.Validation;
import javax.validation.Validator;
import io.github.javenue.jvmlibs.generallibs.beanvalidation.annotaion.AlphaNumeric;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AlphaNumericConstraintTest {

  @Nested
  class WhenConstrainedBeanValidated {

    final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    MyBean bean;

    static class MyBean {

      @AlphaNumeric
      final String value;

      public MyBean(String value) {
        this.value = value;
      }

    }

    @Test
    void givenAlphanumericThenNoViolationDetected() {
      assertThat(validator.validate(new MyBean("ValidAlphanumeric"))).isEmpty();
    }

    @Test
    void givenSpaceThenViolationDetected() {
      assertThat(validator.validate(new MyBean(" ")))
          .extracting("message")
          .containsOnly("use only alphabets or numerics");
    }

    @Test
    void givenSymbolThenViolationDetected() {
      bean = new MyBean("@");
      assertThat(validator.validate(bean))
          .extracting("message")
          .containsOnly("use only alphabets or numerics");
    }

    @Test
    void givenMultibyteCharThenViolationDetected() {
      assertThat(validator.validate(new MyBean("あ")))
          .extracting("message")
          .containsOnly("use only alphabets or numerics");
    }

    @Test
    void givenNullThenNoViolationDetected() {
      assertThat(validator.validate(new MyBean(null))).isEmpty();
    }

    @Test
    void givenEmptyThenNoViolationDetected() {
      assertThat(validator.validate(new MyBean(""))).isEmpty();
    }

  }

}
