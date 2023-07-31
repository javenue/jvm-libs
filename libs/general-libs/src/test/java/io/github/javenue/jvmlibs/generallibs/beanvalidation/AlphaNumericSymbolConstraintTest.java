package io.github.javenue.jvmlibs.generallibs.beanvalidation;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.Validation;
import javax.validation.Validator;
import io.github.javenue.jvmlibs.generallibs.beanvalidation.annotaion.AlphaNumericSymbol;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AlphaNumericSymbolConstraintTest {

  @Nested
  class WhenConstrainedBeanValidated {

    final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    static class MyBean {

      @AlphaNumericSymbol
      final String value;

      public MyBean(String value) {
        this.value = value;
      }

    }

    @Test
    void givenAlphanumericSymbolThenNoViolationDetected() {
      assertThat(
          validator.validate(new MyBean("A1!@#$%^&*-_=+{[()]}|:;,<>.?"))).isEmpty();
    }

    @Test
    void givenSpaceThenViolationDetected() {
      assertThat(validator.validate(new MyBean(" ")))
          .extracting("message")
          .containsOnly("use only alphabets, numerics or symbols");
    }

    @Test
    void givenUnacceptedSymbolThenViolationDetected() {
      assertThat(validator.validate(new MyBean("¥")))
          .extracting("message")
          .containsOnly("use only alphabets, numerics or symbols");
    }

    @Test
    void givenMultibyteCharThenViolationDetected() {
      assertThat(validator.validate(new MyBean("あ")))
          .extracting("message")
          .containsOnly("use only alphabets, numerics or symbols");
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
