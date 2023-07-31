package io.github.javenue.jvmlibs.generallibs.beanvalidation;

import io.github.javenue.jvmlibs.generallibs.beanvalidation.annotaion.Nullish;
import io.github.javenue.jvmlibs.generallibs.designpattern.NullObject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NullishValidator implements ConstraintValidator<Nullish, Object> {

  @Override
  public void initialize(Nullish constraintAnnotation) {
  }

  @Override
  public boolean isValid(Object object, ConstraintValidatorContext context) {
    if (object == null) {
      return true;
    } else {
      return object instanceof NullObject;
    }
  }

}
