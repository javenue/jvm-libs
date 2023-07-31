package io.github.javenue.jvmlibs.generallibs.beanvalidation;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.Validation;
import javax.validation.Validator;
import io.github.javenue.jvmlibs.generallibs.beanvalidation.annotaion.AlphaJapanese;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AlphaJapaneseConstraintTest {

  @Nested
  class WhenConstrainedBeanValidated {

    final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    static class MyBean {

      @AlphaJapanese
      final String value;

      public MyBean(String value) {
        this.value = value;
      }

    }

    @ParameterizedTest
    @ValueSource(strings = {"あ", "ア", "ｱ", "亜", "a", "ａ", ""})
    void givenAlphabetOrJapaneseCharacterThenNoViolationDetected(String validValue) {
      assertThat(validator.validate(new MyBean(validValue))).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "１", "@", "＠", " ", "　"})
    void givenNumericOrSymbolThenViolationDetected(String invalidValue) {
      assertThat(validator.validate(new MyBean(invalidValue)))
          .extracting("message")
          .containsOnly("use only Japanese letters or alphabets");
    }

    @Test
    void givenNullThenNoViolationDetected() {
      assertThat(validator.validate(new MyBean(null))).isEmpty();
    }

  }

}
