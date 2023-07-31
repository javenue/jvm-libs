package io.github.javenue.jvmlibs.generallibs.beanvalidation;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.Validation;
import javax.validation.Validator;
import io.github.javenue.jvmlibs.generallibs.beanvalidation.annotaion.JapaneseLandLinePhoneNumber;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class JapaneseLandLinePhoneNumberConstraintTest {

  @Nested
  class WhenConstrainedBeanValidated {

    final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    static class MyBean {

      @JapaneseLandLinePhoneNumber
      final String value;

      public MyBean(String value) {
        this.value = value;
      }

    }

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
