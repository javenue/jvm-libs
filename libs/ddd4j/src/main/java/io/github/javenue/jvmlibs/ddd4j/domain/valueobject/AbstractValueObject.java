package io.github.javenue.jvmlibs.ddd4j.domain.valueobject;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * DDDの値オブジェクトを表す抽象クラス。 equalsメソッドは、全フィールドの値が一致する場合にtrueを返す。このクラスの具象クラスが他のオブジェクトへの参照をフィールドに持つ場合、
 * そのオブジェクトはequalsメソッドを適切にオーバーライドしている必要がある。
 */
public abstract class AbstractValueObject {

  @Override
  public final boolean equals(Object object) {
    return EqualsBuilder.reflectionEquals(this, object);
  }

  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

}
