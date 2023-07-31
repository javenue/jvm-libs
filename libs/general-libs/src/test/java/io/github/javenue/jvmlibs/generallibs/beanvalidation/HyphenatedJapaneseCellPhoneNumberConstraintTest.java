package io.github.javenue.jvmlibs.generallibs.beanvalidation;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.Validation;
import javax.validation.Validator;
import io.github.javenue.jvmlibs.generallibs.beanvalidation.annotaion.HyphenatedJapaneseCellPhoneNumber;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class HyphenatedJapaneseCellPhoneNumberConstraintTest {

  @Nested
  class WhenConstrainedBeanValidated {

    final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    static class MyBean {

      @HyphenatedJapaneseCellPhoneNumber
      final String hyphenatedPhoneNumber;

      public MyBean(String hyphenatedPhoneNumber) {
        this.hyphenatedPhoneNumber = hyphenatedPhoneNumber;
      }

    }

    @Test
    void givenValidCellPhoneNumberThenNoViolationDetected() {
      assertThat(validator.validate(new MyBean("090-1234-5678"))).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"040-1234-5678", "060-1234-5678", "000-1234-5678"})
    void givenCellPhoneNumberWithInvalidPrefixThenViolationDetected(String param) {
      assertThat(validator.validate(new MyBean(param)))
          .extracting("message")
          .containsOnly("not a well-formed hyphenated phone number");
    }

    @Test
    void givenCellPhoneNumberWithoutHyphenThenViolationDetected() {
      assertThat(validator.validate(new MyBean("0904567890")))
          .extracting("message")
          .containsOnly("not a well-formed hyphenated phone number");
    }

    @Test
    void givenCellPhoneNumberWithInvalidHyphenThenViolationDetected() {
      assertThat(validator.validate(new MyBean("0904-5678-901")))
          .extracting("message")
          .containsOnly("not a well-formed hyphenated phone number");
    }

    @Test
    void givenNullThenNoViolationDetected() {
      assertThat(validator.validate(new MyBean(null))).isEmpty();
    }

    @Test
    void givenEmptyThenNoViolationDetected() {
      assertThat(validator.validate(new MyBean("")))
          .extracting("message")
          .containsOnly("not a well-formed hyphenated phone number");
    }

  }

}
