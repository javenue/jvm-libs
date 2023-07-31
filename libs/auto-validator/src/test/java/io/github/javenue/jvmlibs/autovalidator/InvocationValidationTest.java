package io.github.javenue.jvmlibs.autovalidator;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import io.github.javenue.jvmlibs.autovalidator.annotation.AutoValidated;
import io.github.javenue.jvmlibs.autovalidator.annotation.NonAutoValidated;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings({"unused", "ConstantConditions", "SameParameterValue", "UnusedReturnValue"})
class InvocationValidationTest {

  @Nested
  class WhenConstructorCalled {

    @Nested
    class WithoutGroup {

      @AutoValidated
      static class MyBean {

        MyBean(@Max(0) int param) {
        }

      }

      @Test
      void givenParamFollowingConstraintThenNoExceptionThrown() {
        assertThatNoException()
            .isThrownBy(() -> new MyBean(0));
      }

      @Test
      void givenParamViolatingConstraintThenExceptionThrown() {
        assertThatExceptionOfType(ConstraintViolationException.class)
            .isThrownBy(() -> new MyBean(1))
            .withMessage("MyBean.param: must be less than or equal to 0");
      }

    }

    @Nested
    class WithGroupOnClass {

      @AutoValidated(Group1.class)
      static class MyBean {

        MyBean(
            @Max(0)
            @Max(value = 1, groups = Group1.class)
            int param
        ) {
        }

      }

      @Test
      void givenParamViolatingConstraintOfDefaultGroupThenNoExceptionThrown() {
        assertThatNoException()
            .isThrownBy(() -> new MyBean(1));
      }

      @Test
      void givenParamViolatingConstraintOfGroup1ThenExceptionThrown() {
        assertThatExceptionOfType(ConstraintViolationException.class)
            .isThrownBy(() -> new MyBean(2))
            .withMessage("MyBean.param: must be less than or equal to 1");
      }

    }

    @Nested
    class WithGroupOnConstructor {

      @AutoValidated
      static class MyBean {

        @AutoValidated(Group1.class)
        MyBean(
            @Max(0)
            @Max(value = 1, groups = Group1.class)
            int param
        ) {
        }

      }

      @Test
      void givenParamViolatingConstraintOfDefaultGroupThenNoExceptionThrown() {
        assertThatNoException()
            .isThrownBy(() -> new MyBean(1));
      }

      @Test
      void givenParamViolatingConstraintOfGroup1ThenExceptionThrown() {
        assertThatExceptionOfType(ConstraintViolationException.class)
            .isThrownBy(() -> new MyBean(2))
            .withMessage("MyBean.param: must be less than or equal to 1");
      }

    }

    @Nested
    class WithGroupsOnClassAndConstructor {

      @AutoValidated(Group1.class)
      static class MyBean {

        @AutoValidated(Group2.class)
        MyBean(
            @Max(value = 1, groups = Group1.class)
            @Max(value = 2, groups = Group2.class)
            int param
        ) {
        }

      }

      @Test
      void givenParamViolatingConstraintOfGroup1ThenNoExceptionThrown() {
        assertThatNoException()
            .isThrownBy(() -> new MyBean(2));
      }

      @Test
      void givenParamViolatingConstraintOfGroup2ThenExceptionThrown() {
        assertThatExceptionOfType(ConstraintViolationException.class)
            .isThrownBy(() -> new MyBean(3))
            .withMessage("MyBean.param: must be less than or equal to 2");
      }

    }

    @Nested
    class WithNonAutoValidatedOnClass {

      @NonAutoValidated
      @AutoValidated
      static class MyBean {

        MyBean(@Max(0) int param) {
        }

      }

      @Test
      void givenParamViolatingConstraintThenNoExceptionThrown() {
        assertThatNoException()
            .isThrownBy(() -> new MyBean(1));
      }

    }

    @Nested
    class WithNonAutoValidatedOnConstructor {

      @AutoValidated
      static class MyBean {

        @NonAutoValidated
        MyBean(@Max(0) int param) {
        }

      }

      @Test
      void givenParamViolatingConstraintThenNoExceptionThrown() {
        assertThatNoException()
            .isThrownBy(() -> new MyBean(1));
      }

    }

  }

  @Nested
  class MethodTests {

    @Nested
    class WithoutGroup {

      @AutoValidated
      static class MyBean {

        @Max(0)
        int method(@Max(0) int param, int returnValue) {
          return returnValue;
        }

      }

      @Test
      void givenParamAndReturnValueFollowingConstraintThenNoExceptionThrown() {
        assertThatNoException()
            .isThrownBy(() -> new MyBean().method(0, 0));
      }

      @Test
      void givenParamViolatingConstraintThenExceptionThrown() {
        assertThatExceptionOfType(ConstraintViolationException.class)
            .isThrownBy(() -> new MyBean().method(1, 0))
            .withMessage("method.param: must be less than or equal to 0");
      }

      @Test
      void givenReturnValueViolatingConstraintThenExceptionThrown() {
        assertThatExceptionOfType(ConstraintViolationException.class)
            .isThrownBy(() -> new MyBean().method(0, 1))
            .withMessage("method.<return value>: must be less than or equal to 0");
      }

      @Test
      void givenParamAndReturnValueViolatingConstraintThenExceptionThrown() {
        assertThatExceptionOfType(ConstraintViolationException.class)
            .isThrownBy(() -> new MyBean().method(1, 0))
            .withMessage("method.param: must be less than or equal to 0");
      }

    }

    @Nested
    class WithGroupOnClass {

      @AutoValidated(Group1.class)
      static class MyBean {

        @Max(0)
        @Max(value = 1, groups = Group1.class)
        int method(
            @Max(0)
            @Max(value = 1, groups = Group1.class)
            int param,
            int returnValue
        ) {
          return returnValue;
        }

      }

      @Test
      void givenParamAndReturnValueViolatingConstraintOfDefaultGroupThenNoExceptionThrown() {
        assertThatNoException()
            .isThrownBy(() -> new MyBean().method(1, 1));
      }

      @Test
      void givenParamViolatingConstraintOfGroup1ThenExceptionThrown() {
        assertThatExceptionOfType(ConstraintViolationException.class)
            .isThrownBy(() -> new MyBean().method(2, 0))
            .withMessage("method.param: must be less than or equal to 1");
      }

      @Test
      void givenReturnValueViolatingConstraintOfGroup1ThenExceptionThrown() {
        assertThatExceptionOfType(ConstraintViolationException.class)
            .isThrownBy(() -> new MyBean().method(0, 2))
            .withMessage("method.<return value>: must be less than or equal to 1");
      }

    }

    @Nested
    class WithGroupOnMethod {

      @AutoValidated
      static class MyBean {

        @AutoValidated(Group1.class)
        @Max(0)
        @Max(value = 1, groups = Group1.class)
        int method(
            @Max(0)
            @Max(value = 1, groups = Group1.class)
            int param,
            int returnValue
        ) {
          return returnValue;
        }

      }

      @Test
      void givenParamAndReturnValueViolatingConstraintOfDefaultGroupThenNoExceptionThrown() {
        assertThatNoException()
            .isThrownBy(() -> new MyBean().method(1, 1));
      }

      @Test
      void givenParamViolatingConstraintOfGroup1ThenExceptionThrown() {
        assertThatExceptionOfType(ConstraintViolationException.class)
            .isThrownBy(() -> new MyBean().method(2, 0))
            .withMessage("method.param: must be less than or equal to 1");
      }

      @Test
      void givenReturnValueViolatingConstraintOfGroup1ThenExceptionThrown() {
        assertThatExceptionOfType(ConstraintViolationException.class)
            .isThrownBy(() -> new MyBean().method(0, 2))
            .withMessage("method.<return value>: must be less than or equal to 1");
      }

    }

    @Nested
    class WithGroupsOnClassAndMethod {

      @AutoValidated(Group1.class)
      static class MyBean {

        @AutoValidated(Group2.class)
        @Max(value = 1, groups = Group1.class)
        @Max(value = 2, groups = Group2.class)
        int method(
            @Max(value = 1, groups = Group1.class)
            @Max(value = 2, groups = Group2.class)
            int param,
            int returnValue
        ) {
          return returnValue;
        }

      }

      @Test
      void givenParamViolatingConstraintOfGroup1ThenNoExceptionThrown() {
        assertThatNoException()
            .isThrownBy(() -> new MyBean().method(2, 0));
      }

      @Test
      void givenParamViolatingConstraintOfGroup1ThenExceptionThrown() {
        assertThatExceptionOfType(ConstraintViolationException.class)
            .isThrownBy(() -> new MyBean().method(3, 0))
            .withMessage("method.param: must be less than or equal to 2");
      }

      @Test
      void givenReturnValueViolatingConstraintOfGroup1ThenNoExceptionThrown() {
        assertThatNoException()
            .isThrownBy(() -> new MyBean().method(0, 2));
      }

      @Test
      void givenReturnValueViolatingConstraintOfGroup1ThenExceptionThrown() {
        assertThatExceptionOfType(ConstraintViolationException.class)
            .isThrownBy(() -> new MyBean().method(0, 3))
            .withMessage("method.<return value>: must be less than or equal to 2");
      }

    }

    @Nested
    class WithNonAutoValidatedOnClass {

      @NonAutoValidated
      @AutoValidated
      static class MyBean {

        MyBean(@Max(0) int param) {
        }

      }

      @Test
      void givenParamViolatingConstraintThenNoExceptionThrown() {
        assertThatNoException()
            .isThrownBy(() -> new MyBean(1));
      }

    }

    @Nested
    class WithNonAutoValidatedOnMethod {

      @AutoValidated
      static class MyBean {

        @NonAutoValidated
        MyBean(@Max(0) int param) {
        }

      }

      @Test
      void givenParamViolatingConstraintThenNoExceptionThrown() {
        assertThatNoException()
            .isThrownBy(() -> new MyBean(1));
      }

    }

  }

  @Nested
  class WhenOverridingMethodCalled {

    static abstract class BaseBean {

      @Max(0)
      abstract int method(@Max(0) int param, int returnValue);

    }

    @AutoValidated
    static class SubBean extends BaseBean {

      @Override
      int method(int param, int returnValue) {
        return returnValue;
      }

    }

    @Test
    void givenParamViolatingConstraintOfBaseClassThenExceptionThrown() {
      assertThatExceptionOfType(ConstraintViolationException.class)
          .isThrownBy(() -> new SubBean().method(1, 0))
          .withMessage("method.param: must be less than or equal to 0");
    }

    @Test
    void givenReturnValueViolatingConstraintOfBaseClassThenExceptionThrown() {
      assertThatExceptionOfType(ConstraintViolationException.class)
          .isThrownBy(() -> new SubBean().method(0, 1))
          .withMessage("method.<return value>: must be less than or equal to 0");
    }

  }

  @Nested
  class WhenStaticMethodCalled {

    @AutoValidated
    static class MyBean {

      @Max(0)
      static int method(@Max(0) int param, int returnValue) {
        return returnValue;
      }

    }

    @Test
    void givenParamViolatingConstraintButNoExceptionThrown() {
      assertThatNoException().isThrownBy(() -> MyBean.method(1, 1));
    }

  }

  interface Group1 {

  }

  interface Group2 {

  }

}
