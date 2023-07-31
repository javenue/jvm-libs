package io.github.javenue.jvmlibs.generallibs.beanvalidation;

import io.github.javenue.jvmlibs.generallibs.beanvalidation.annotaion.ChangingPasswords;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class ChangingPasswordsValidator implements
    ConstraintValidator<ChangingPasswords, Object[]> {

  @Override
  public void initialize(ChangingPasswords constraintAnnotation) {
  }

  @Override
  public boolean isValid(Object[] value, ConstraintValidatorContext context) {
    var currentPassword = value[0];
    var newPassword = value[1];
    var confirmPassword = value[2];

    if (Objects.equals(currentPassword, newPassword)) {
      return false;
    } else {
      return Objects.equals(newPassword, confirmPassword);
    }
  }

}
