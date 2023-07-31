package io.github.javenue.jvmlibs.autovalidator;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;

/*
https://stackoverflow.com/a/64619293のバグにより、finalフィールドにアクセスするコンストラクタ呼出を
Around adviceでadviseするとIllegalAccessErrorが発生してしまうので、処理をBefore, After adviceに分割して対応。
*/

@SuppressWarnings("unused")
@Aspect
public class InvocationValidationAspect extends AutoValidationAspect {

  /**
   * 自動バリデーション対象インスタンス内のメソッドが呼び出されるとき、仮引数に付与されている制約アノテーションに基づき
   * メソッドに渡される実引数をバリデーションするAdvice.
   */
  @Before("pointcutForAutoValidation()")
  public void validateArgs(JoinPoint joinPoint) {
    var target = joinPoint.getTarget();
    var signature = joinPoint.getSignature();
    var args = joinPoint.getArgs();
    var groups = this.determineValidationGroups(target, signature);

    Set<? extends ConstraintViolation<?>> constraintViolations = new HashSet<>();
    if (signature instanceof ConstructorSignature constructorSignature) {
      Constructor<?> constructor = constructorSignature.getConstructor();
      constraintViolations = this.validator.forExecutables()
          .validateConstructorParameters(constructor, args, groups);
    } else if (signature instanceof MethodSignature methodSignature) {
      var method = methodSignature.getMethod();
      constraintViolations = this.validator.forExecutables()
          .validateParameters(target, method, args, groups);
    }

    if (!constraintViolations.isEmpty()) {
      throw new ConstraintViolationException(constraintViolations);
    }
  }

  /**
   * 自動バリデーション対象インスタンス内のメソッドからreturnするとき、
   * メソッド定義に付与されている制約アノテーションに基づき戻り値をバリデーションするAdvice.
   */
  @AfterReturning(pointcut = "pointcutForAutoValidation()", returning = "returnValue")
  public void validateReturnValue(JoinPoint joinPoint, Object returnValue) {
    var target = joinPoint.getTarget();
    var signature = joinPoint.getSignature();
    var groups = this.determineValidationGroups(target, signature);

    Set<? extends ConstraintViolation<?>> constraintViolations = new HashSet<>();
    if (signature instanceof ConstructorSignature constructorSignature) {
      Constructor<?> constructor = constructorSignature.getConstructor();
      constraintViolations = this.validator.forExecutables()
          .validateConstructorReturnValue(constructor, returnValue, groups);
    } else if (signature instanceof MethodSignature methodSignature) {
      var method = methodSignature.getMethod();
      constraintViolations = this.validator.forExecutables()
          .validateReturnValue(target, method, returnValue, groups);
    }

    if (!constraintViolations.isEmpty()) {
      throw new ConstraintViolationException(constraintViolations);
    }
  }

}
