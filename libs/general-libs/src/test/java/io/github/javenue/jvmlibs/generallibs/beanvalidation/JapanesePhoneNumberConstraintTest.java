package io.github.javenue.jvmlibs.generallibs.beanvalidation;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.Validation;
import javax.validation.Validator;
import io.github.javenue.jvmlibs.generallibs.beanvalidation.annotaion.JapanesePhoneNumber;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class JapanesePhoneNumberConstraintTest {

  @Nested
  class WhenConstrainedBeanValidated {

    final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    static class MyBean {

      @JapanesePhoneNumber
      final String value;

      public MyBean(String value) {
        this.value = value;
      }

    }

    @Nested
    class ForLandLinePhoneNumber {

      @Test
      void givenValidLandLinePhoneNumber() {
        assertThat(validator.validate(new MyBean("0312345678"))).isEmpty();
      }

      @Test
      void givenLandLinePhoneNumberNotPrecededBy0() {
        assertThat(validator.validate(new MyBean("1234567890")))
            .extracting("message")
            .containsOnly("not a well-formed phone number");
      }

      @Test
      void givenLandLinePhoneNumberLessThan10Digits() {
        assertThat(validator.validate(new MyBean("012345678")))
            .extracting("message")
            .containsOnly("not a well-formed phone number");
      }

      @Test
      void givenLandLinePhoneNumberMoreThan10Digits() {
        assertThat(validator.validate(new MyBean("01234567890")))
            .extracting("message")
            .containsOnly("not a well-formed phone number");
      }

    }

    @Nested
    class ForCellPhoneNumber {

      @Test
      void givenValidCellPhoneNumber() {
        assertThat(validator.validate(new MyBean("09012345678"))).isEmpty();
      }

      @ParameterizedTest
      @ValueSource(strings = {"04012345678", "06012345678", "00012345678"})
      void givenCellPhoneNumberWithInvalidPrefix(String param) {
        assertThat(validator.validate(new MyBean(param)))
            .extracting("message")
            .containsOnly("not a well-formed phone number");
      }

      @Test
      void givenCellPhoneNumberLessThan11Digits() {
        // 携帯電話番号としては正しくないが、固定電話番号としては正しいので不正検出されない
        assertThat(validator.validate(new MyBean("0904567890"))).isEmpty();
      }

      @Test
      void givenCellPhoneNumberMoreThan11Digits() {
        assertThat(validator.validate(new MyBean("090456789012")))
            .extracting("message")
            .containsOnly("not a well-formed phone number");
      }

      @Test
      void givenNull() {
        assertThat(validator.validate(new MyBean(null))).isEmpty();
      }

      @Test
      void givenEmpty() {
        assertThat(validator.validate(new MyBean("")))
            .extracting("message")
            .containsOnly("not a well-formed phone number");
      }

    }

  }

}
