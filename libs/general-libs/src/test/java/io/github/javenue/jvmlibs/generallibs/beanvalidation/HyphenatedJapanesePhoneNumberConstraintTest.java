package io.github.javenue.jvmlibs.generallibs.beanvalidation;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.Validation;
import javax.validation.Validator;
import io.github.javenue.jvmlibs.generallibs.beanvalidation.annotaion.HyphenatedJapanesePhoneNumber;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class HyphenatedJapanesePhoneNumberConstraintTest {

  @Nested
  class WhenConstrainedBeanValidated {

    final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    static class MyBean {

      @HyphenatedJapanesePhoneNumber
      final String value;

      public MyBean(String value) {
        this.value = value;
      }

    }

    @Nested
    class ForLandLinePhoneNumber {

      @ParameterizedTest
      @ValueSource(strings = {"01-2345-6789", "012-345-6789", "0123-45-6789", "01234-5-6789"})
      void givenValidLandLinePhoneNumberThenNoViolationDetected(String param) {
        assertThat(validator.validate(new MyBean(param))).isEmpty();
      }

      @Test
      void givenLandLinePhoneNumberNotPrecededBy0ThenViolationDetected() {
        assertThat(validator.validate(new MyBean("12-3456-7890")))
            .extracting("message")
            .containsOnly("not a well-formed hyphenated phone number");
      }

      @Test
      void givenLandLinePhoneNumberWithoutHyphenThenViolationDetected() {
        assertThat(validator.validate(new MyBean("012345678")))
            .extracting("message")
            .containsOnly("not a well-formed hyphenated phone number");
      }

      @ParameterizedTest
      @ValueSource(strings = {"0-12345-6789", "012345-6-789"})
      void givenLandLinePhoneNumberWithInvalidHyphenThenViolationDetected(String param) {
        assertThat(validator.validate(new MyBean(param)))
            .extracting("message")
            .containsOnly("not a well-formed hyphenated phone number");
      }

    }

    @Nested
    class ForCellPhoneNumber {

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

}
