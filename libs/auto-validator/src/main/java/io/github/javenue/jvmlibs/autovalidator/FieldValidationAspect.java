package io.github.javenue.jvmlibs.autovalidator;

import java.util.Stack;
import javax.validation.ConstraintViolationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@SuppressWarnings("unused")
@Aspect
public class FieldValidationAspect extends AutoValidationAspect {

  /**
   * 自動バリデーション対象インスタンスに所属するメソッド（=フィールドの値を更新する可能性のあるメソッド）の呼出を記録し、
   * そのメソッドからreturnするときにフィールドバリデーションを実行すべきか否かを記録するためのStack.
   */
  protected static Stack<Boolean> fieldSetRecords = new Stack<>();

  /**
   * 自動バリデーション対象インスタンスに所属するメソッドからreturnするときに、
   * そのメソッドが所属するインスタンスのフィールドバリデーションが必要か否かを、
   * fieldSetRecordsスタックから判定するためのPointcut.
   */
  @Pointcut("if()")
  public static boolean shouldValidateFields() {
    return fieldSetRecords.peek() == Boolean.TRUE;
  }

  /**
   * 自動バリデーション対象インスタンスに所属するメソッドが呼び出されたことを記録するAdvice.
   */
  @Before("pointcutForAutoValidation()")
  public void recordAdvising() {
    fieldSetRecords.push(Boolean.FALSE);
  }

  /**
   * 自動バリデーション対象インスタンスに所属するメソッド内でフィールドへの書込を検出し、
   * このメソッドからreturnする際に所属インスタンスのフィールドバリデーションが必要なことをスタックに記録するAdvice.
   */
  @AfterReturning("""
      @within(io.github.javenue.jvmlibs.autovalidator.annotation.AutoValidated)
      && cflowbelow(pointcutForAutoValidation())
      && set(* *)
      && !shouldValidateFields()
      """)
  public void recordFieldSet() {
    fieldSetRecords.pop();
    fieldSetRecords.push(Boolean.TRUE);
  }

  /**
   * 自動バリデーション対象インスタンスのフィールドへ書込を行ったメソッドからreturnするときに、
   * 所属インスタンスのフィールドをバリデーションするAdvice.
   */
  @AfterReturning("pointcutForAutoValidation() && shouldValidateFields()")
  public void validateFields(JoinPoint joinPoint) {
    var target = joinPoint.getTarget();
    var signature = joinPoint.getSignature();
    var groups = this.determineValidationGroups(target, signature);

    var constraintViolations = this.validator.validate(target, groups);
    if (!constraintViolations.isEmpty()) {
      throw new ConstraintViolationException(constraintViolations);
    }
  }

  /**
   * 自動バリデーション対象のインスタンスに所属するメソッドからreturnするとき、Stack内の要素を1つ取り除くAdvice.
   */
  @After("pointcutForAutoValidation()")
  public void removeRecode() {
    fieldSetRecords.pop();
  }

}
