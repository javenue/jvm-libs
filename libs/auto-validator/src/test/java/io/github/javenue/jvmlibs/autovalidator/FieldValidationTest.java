package io.github.javenue.jvmlibs.autovalidator;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import io.github.javenue.jvmlibs.autovalidator.annotation.AutoValidated;
import io.github.javenue.jvmlibs.autovalidator.annotation.NonAutoValidated;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings({"WriteOnlyObject", "SameParameterValue"})
class FieldValidationTest {

  @Nested
  class WhenConstructorCalled {

    @Nested
    class WithoutGroup {

      @AutoValidated
      static class MyBean {

        @Max(0)
        int field;

        MyBean(int param) {
          field = param;
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
            .withMessage("field: must be less than or equal to 0");
      }

    }

    @Nested
    class WithGroupOnClass {

      @AutoValidated(Group1.class)
      static class MyBean {

        @Max(0)
        @Max(value = 1, groups = Group1.class)
        int field;

        MyBean(int param) {
          field = param;
        }

      }

      @Test
      void givenParamViolatingConstraintOfNoGroupThenNoExceptionThrown() {
        assertThatNoException()
            .isThrownBy(() -> new MyBean(1));
      }

      @Test
      void givenParamViolatingConstraintOfGroup1ThenExceptionThrown() {
        assertThatExceptionOfType(ConstraintViolationException.class)
            .isThrownBy(() -> new MyBean(2))
            .withMessage("field: must be less than or equal to 1");
      }

    }

    @Nested
    class WithGroupOnConstructor {

      @AutoValidated
      static class MyBean {

        @Max(0)
        @Max(value = 1, groups = Group1.class)
        int field;

        @AutoValidated(Group1.class)
        MyBean(int param) {
          field = param;
        }

      }

      @Test
      void givenParamViolatingConstraintOfNoGroupThenNoExceptionThrown() {
        assertThatNoException()
            .isThrownBy(() -> new MyBean(1));
      }

      @Test
      void givenParamViolatingConstraintOfGroup1ThenExceptionThrown() {
        assertThatExceptionOfType(ConstraintViolationException.class)
            .isThrownBy(() -> new MyBean(2))
            .withMessage("field: must be less than or equal to 1");
      }

    }

    @Nested
    class WithGroupsOnClassAndConstructor {

      @AutoValidated(Group1.class)
      static class MyBean {

        @Max(value = 1, groups = Group1.class)
        @Max(value = 2, groups = Group2.class)
        int field;

        @AutoValidated(Group2.class)
        MyBean(int param) {
          field = param;
        }

      }

      @Test
      void givenParamViolatingConstraintOfNoGroup1ThenNoExceptionThrown() {
        assertThatNoException()
            .isThrownBy(() -> new MyBean(2));
      }

      @Test
      void givenParamViolatingConstraintOfGroup2ThenExceptionThrown() {
        assertThatExceptionOfType(ConstraintViolationException.class)
            .isThrownBy(() -> new MyBean(3))
            .withMessage("field: must be less than or equal to 2");
      }

    }

    @Nested
    class WithNonAutoValidatedOnClass {

      @NonAutoValidated
      @AutoValidated
      static class MyBean {

        @Max(0)
        int field;

        MyBean(int param) {
          field = param;
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

        @Max(0)
        int field;

        @NonAutoValidated
        MyBean(int param) {
          field = param;
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
  class WhenMethodCalled {

    @Nested
    class WithoutGroup {

      @AutoValidated
      static class MyBean {

        @Max(0)
        int field;

        void setField(int param) {
          field = param;
        }

      }

      @Test
      void givenParamFollowingConstraintThenNoExceptionThrown() {
        assertThatNoException()
            .isThrownBy(() -> new MyBean().setField(0));
      }

      @Test
      void givenParamViolatingConstraintThenExceptionThrown() {
        assertThatExceptionOfType(ConstraintViolationException.class)
            .isThrownBy(() -> new MyBean().setField(1))
            .withMessage("field: must be less than or equal to 0");
      }

    }

    @Nested
    class WithGroupOnClass {

      @AutoValidated(Group1.class)
      static class MyBean {

        @Max(0)
        @Max(value = 1, groups = Group1.class)
        int field;

        void setField(int param) {
          field = param;
        }

      }

      @Test
      void givenParamViolatingConstraintOfNoGroupThenNoExceptionThrown() {
        assertThatNoException()
            .isThrownBy(() -> new MyBean().setField(1));
      }

      @Test
      void givenParamViolatingConstraintOfGroup1ThenExceptionThrown() {
        assertThatExceptionOfType(ConstraintViolationException.class)
            .isThrownBy(() -> new MyBean().setField(2))
            .withMessage("field: must be less than or equal to 1");
      }

    }

    @Nested
    class WithGroupOnMethod {

      @AutoValidated
      static class MyBean {

        @Max(0)
        @Max(value = 1, groups = Group1.class)
        int field;

        @AutoValidated(Group1.class)
        void setField(int param) {
          field = param;
        }

      }

      @Test
      void givenParamViolatingConstraintOfNoGroupThenNoExceptionThrown() {
        assertThatNoException()
            .isThrownBy(() -> new MyBean().setField(1));
      }

      @Test
      void givenParamViolatingConstraintOfGroup1ThenExceptionThrown() {
        assertThatExceptionOfType(ConstraintViolationException.class)
            .isThrownBy(() -> new MyBean().setField(2))
            .withMessage("field: must be less than or equal to 1");
      }

    }

    @Nested
    class WithGroupsOnClassAndMethod {

      @AutoValidated(Group1.class)
      static class MyBean {

        @Max(value = 1, groups = Group1.class)
        @Max(value = 2, groups = Group2.class)
        int field;

        @AutoValidated(Group2.class)
        void setField(int param) {
          field = param;
        }

      }

      @Test
      void givenParamViolatingConstraintOfGroup1ThenExceptionThrown() {
        assertThatNoException()
            .isThrownBy(() -> new MyBean().setField(2));
      }

      @Test
      void givenParamViolatingConstraintOfGroup2ThenExceptionThrown() {
        assertThatExceptionOfType(ConstraintViolationException.class)
            .isThrownBy(() -> new MyBean().setField(3))
            .withMessage("field: must be less than or equal to 2");
      }

    }

    @Nested
    class WithNonAutoValidatedOnClass {

      @NonAutoValidated
      @AutoValidated
      static class MyBean {

        @Max(0)
        int field;

        @SuppressWarnings("SameParameterValue")
        void setField(int param) {
          field = param;
        }

      }

      @Test
      void givenParamViolatingConstraintThenNoExceptionThrown() {
        assertThatNoException()
            .isThrownBy(() -> new MyBean().setField(1));
      }

    }

    @Nested
    class WithNonAutoValidatedOnMethod {

      @AutoValidated
      static class MyBean {

        @Max(0)
        int field;

        @SuppressWarnings("SameParameterValue")
        @NonAutoValidated
        void setField(int param) {
          field = param;
        }

      }

      @Test
      void givenParamViolatingConstraintThenNoExceptionThrown() {
        assertThatNoException()
            .isThrownBy(() -> new MyBean().setField(1));
      }

    }

  }

  @Nested
  class WhenConstructorOfNestedBeansCalled {

    @AutoValidated
    static class OuterBean {

      @Max(0)
      int outerField;

      OuterBean(int outerParam, int innerParam) {
        outerField = outerParam;
        new InnerBean(innerParam);
      }

    }

    @AutoValidated
    static class InnerBean {

      @Max(0)
      int innerField;

      InnerBean(int innerParam) {
        innerField = innerParam;
      }

    }

    @Test
    void givenOuterParamViolatingConstraintThenExceptionThrown() {
      assertThatExceptionOfType(ConstraintViolationException.class)
          .isThrownBy(() -> new OuterBean(1, 0))
          .withMessage("outerField: must be less than or equal to 0");
    }

    @Test
    void givenInnerParamViolatingConstraintThenExceptionThrown() {
      assertThatExceptionOfType(ConstraintViolationException.class)
          .isThrownBy(() -> new OuterBean(0, 1))
          .withMessage("innerField: must be less than or equal to 0");
    }

    @Test
    void givenOuterAndInnerParamViolatingConstraintThenExceptionThrown() {
      assertThatExceptionOfType(ConstraintViolationException.class)
          .isThrownBy(() -> new OuterBean(1, 1))
          .withMessage("innerField: must be less than or equal to 0");
    }

  }

  @Nested
  class WhenStaticMethodCalled {

    @AutoValidated
    static class MyBean {

      @Max(0)
      static int field;

      static void method(int param) {
        field = param;
      }

    }

    @Test
    void givenParamViolatingConstraintButNoExceptionThrown() {
      assertThatNoException().isThrownBy(() -> MyBean.method(1));
    }

  }

  interface Group1 {

  }

  interface Group2 {

  }

}
