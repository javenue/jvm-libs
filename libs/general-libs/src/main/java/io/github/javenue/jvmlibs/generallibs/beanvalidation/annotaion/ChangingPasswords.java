package io.github.javenue.jvmlibs.generallibs.beanvalidation.annotaion;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.github.javenue.jvmlibs.generallibs.beanvalidation.ChangingPasswordsValidator;
import io.github.javenue.jvmlibs.generallibs.beanvalidation.annotaion.ChangingPasswords.List;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 現在のパスワード，新しいパスワード，確認用パスワードを引数にとるパスワード変更メソッドに対し、<br> ・現在のパスワードと新しいパスワードが異なること<br>
 * ・新しいパスワードと確認用パスワードが等しいこと<br> を検証するバリデーションアノテーション。 メソッドの引数は、先頭から「現在のパスワード，新しいパスワード，確認用パスワード」の順であり、
 * 各パスワードのクラスはequalsメソッドを適切に実装していること。
 */
@Documented
@Constraint(validatedBy = {ChangingPasswordsValidator.class})
@Target({METHOD})
@Retention(RUNTIME)
@Repeatable(List.class)
public @interface ChangingPasswords {

  @Documented
  @Target({METHOD})
  @Retention(RUNTIME)
  @interface List {

    ChangingPasswords[] value();

  }

  String message() default "{io.github.javenue.jvmlibs.generallibs.beanvalidation.ChangingPasswords.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
