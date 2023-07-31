package io.github.javenue.jvmlibs.autovalidator;

import java.lang.reflect.Executable;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;
import io.github.javenue.jvmlibs.autovalidator.annotation.AutoValidated;

abstract class AutoValidationAspect {

  protected Validator validator;

  protected AutoValidationAspect() {
    this(Validation.buildDefaultValidatorFactory());
  }

  protected AutoValidationAspect(ValidatorFactory validatorFactory) {
    this(validatorFactory.getValidator());
  }

  protected AutoValidationAspect(Validator validator) {
    this.validator = validator;
  }

  public void setValidator(Validator validator) {
    this.validator = validator;
  }

  /**
   * {@link AutoValidated}アノテーションが付与されているクラス内のメソッド, コンストラクター実行時の join
   * pointを検出するためのpointcut。AutoValidation機能に含まれるaspectが共通でadviseするべきpointcut。 また、{@link
   *
   * @NonAutoValidated}アノテーションが付与されているクラス内のメソッド，コンストラクタや、 {@link @NonAutoValidated}アノテーションが付与されているメソッド，コンストラクタ実行下のjoin pointは 無視される。
   */
  @Pointcut("""
      @within(io.github.javenue.jvmlibs.autovalidator.annotation.AutoValidated)
      && (execution(!static * *.*(..)) || execution(* .new(..)))
      && !cflow(@within(io.github.javenue.jvmlibs.autovalidator.annotation.NonAutoValidated))
      && !cflow(execution(@io.github.javenue.jvmlibs.autovalidator.annotation.NonAutoValidated * *.*(..)))
      && !cflow(execution(@io.github.javenue.jvmlibs.autovalidator.annotation.NonAutoValidated * .new(..)))
      """)
  public void pointcutForAutoValidation() {
  }

  /**
   * targetオブジェクトから、クラス，メソッドに付与されている{@link @AutoValidated}アノテーションから、 指定バリデーショングループを取得するためのメソッド。
   *
   * @param target    advise対象オブジェクト。
   * @param signature advise対象メソッド, コンストラクタのsignatureオブジェクト。
   * @return 取得された指定バリデーショングループ。
   */
  protected Class<?>[] determineValidationGroups(Object target, Signature signature) {
    var AutoValidatedAnnotationClass = AutoValidated.class;

    Executable executable = null;
    if (signature instanceof ConstructorSignature constructorSignature) {
      executable = (constructorSignature.getConstructor());
    } else if (signature instanceof MethodSignature methodSignature) {
      executable = methodSignature.getMethod();
    }

    var annotation = (executable != null)
        ? executable.getAnnotation(AutoValidatedAnnotationClass) : null;
    if (annotation == null) {
      annotation = target.getClass().getAnnotation(AutoValidatedAnnotationClass);
    }

    return (annotation == null
        ? new Class<?>[0] : AutoValidatedAnnotationClass.cast(annotation).value());
  }

}
