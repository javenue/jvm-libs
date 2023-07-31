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
 * The annotated string must be a hyphenated well-formed Japanese land-line phone or cellphone
 * number.
 */
@Documented
@Constraint(validatedBy = {})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@ReportAsSingleViolation
@Pattern(regexp = "^((0((\\d-\\d{4})|(\\d{2}-\\d{3})|(\\d{3}-\\d{2})|(\\d{4}-\\d))-\\d{4})|(0[5789]0-[0-9]{4}-[0-9]{4}))$")
public @interface HyphenatedJapanesePhoneNumber {

  @Documented
  @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
  @Retention(RUNTIME)
  @interface List {

    HyphenatedJapanesePhoneNumber[] value();

  }

  String message() default "{io.github.javenue.jvmlibs.generallibs.beanvalidation.HyphenatedPhoneNumber.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
