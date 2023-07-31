package io.github.javenue.jvmlibs.generallibs.beanvalidation;

import io.github.javenue.jvmlibs.generallibs.beanvalidation.annotaion.NotNullish;
import io.github.javenue.jvmlibs.generallibs.designpattern.NullObject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotNullishValidator implements ConstraintValidator<NotNullish, Object> {

  @Override
  public void initialize(NotNullish constraintAnnotation) {
  }

  @Override
  public boolean isValid(Object object, ConstraintValidatorContext context) {
    if (object == null) {
      return false;
    } else {
      return !(object instanceof NullObject);
    }
  }

}
