package io.github.javenue.jvmlibs.generallibs.beanvalidation;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import javax.validation.Validation;
import javax.validation.executable.ExecutableValidator;
import io.github.javenue.jvmlibs.generallibs.beanvalidation.annotaion.ChangingPasswords;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ChangingPasswordsValidatorTest {

  @Nested
  class WhenConstrainedMethodCalled {

    ExecutableValidator executableValidator = Validation
        .buildDefaultValidatorFactory()
        .getValidator()
        .forExecutables();

    MyClass myClass = new MyClass();

    Method method = myClass
        .getClass()
        .getDeclaredMethod("changePassword", String.class, String.class, String.class);

    Object[] args = new String[3];

    WhenConstrainedMethodCalled() throws NoSuchMethodException {
    }

    class MyClass {

      @SuppressWarnings("unused")
      @ChangingPasswords
      void changePassword(
          String currentPassword,
          String newPassword,
          String confirmPassword
      ) {
      }

    }

    @Test
    void givenValidArgsThenNoViolationDetected() {
      args[0] = "current";
      args[1] = "new";
      args[2] = "new";
      assertThat(executableValidator.validateParameters(myClass, method, args)).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({"current, current, new", "current,new,confirm"})
    void givenInvalidValuesThenNoViolationDetected(
        String currentPassword,
        String newPassword,
        String confirmPassword
    ) {
      args[0] = currentPassword;
      args[1] = newPassword;
      args[2] = confirmPassword;
      assertThat(executableValidator.validateParameters(myClass, method, args))
          .extracting("message")
          .containsOnly("any of [new password][confirm password] are invaild");
    }

    @Test
    void givenNullAsCurrentPasswordThenNoViolationDetected() {
      args[0] = null;
      args[1] = "new";
      args[2] = "new";
      assertThat(executableValidator.validateParameters(myClass, method, args)).isEmpty();
    }

    @Test
    void givenNullAsNewPasswordThenNoViolationDetected() {
      args[0] = "current";
      args[1] = null;
      args[2] = null;
      assertThat(executableValidator.validateParameters(myClass, method, args)).isEmpty();
    }

  }

}


