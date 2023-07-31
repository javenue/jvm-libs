package io.github.javenue.jvmlibs.generallibs.beanvalidation.annotaion;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;

/**
 * The annotated string must only contain Japanese letters and alphabets excluding numerics, symbols
 * and spaces.
 */

@Documented
@Constraint(validatedBy = {})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@ReportAsSingleViolation
@Pattern(regexp = "[一-龠ぁ-ゔァ-ヴｱ-ｳﾞーa-zA-Zａ-ｚＡ-Ｚ々〆〤]*")
public @interface AlphaJapanese {

  @Documented
  @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
  @Retention(RUNTIME)
  @interface List {

    AlphaJapanese[] value();

  }

  String message() default "{io.github.javenue.jvmlibs.generallibs.beanvalidation.JapaneseOrAlphabet.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
