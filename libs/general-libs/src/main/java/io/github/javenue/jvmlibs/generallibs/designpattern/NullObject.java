package io.github.javenue.jvmlibs.generallibs.designpattern;

/**
 * A marker interface that indicates the implement class is a null object of Null Object Pattern.
 */
public interface NullObject {

  static boolean isNullish(Object obj) {
    return obj == null || obj instanceof NullObject;
  }

  static boolean nonNullish(Object obj) {
    return !isNullish(obj);
  }

  default boolean isNull() {
    return true;
  }

}
