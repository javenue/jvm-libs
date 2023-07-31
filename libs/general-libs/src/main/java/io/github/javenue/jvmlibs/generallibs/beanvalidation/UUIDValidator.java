package io.github.javenue.jvmlibs.generallibs.beanvalidation;

import io.github.javenue.jvmlibs.generallibs.beanvalidation.annotaion.UUID;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UUIDValidator implements ConstraintValidator<UUID, String> {

  @Override
  public void initialize(UUID constraintAnnotation) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    } else {
      try {
        java.util.UUID.fromString(value);
        return true;
      } catch (IllegalArgumentException e) {
        return false;
      }
    }
  }

}
