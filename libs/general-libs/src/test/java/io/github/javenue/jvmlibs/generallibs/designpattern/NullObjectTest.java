package io.github.javenue.jvmlibs.generallibs.designpattern;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class NullObjectTest {

  @Test
  void whenIsNullCalledThenReturnTrue() {
    assertThat(new NullObjectImpl().isNull()).isTrue();
  }

  @Test
  @SuppressWarnings("ConstantConditions")
  void givenNullWhenIsNullishCalledThenReturnTrue() {
    assertThat(NullObject.isNullish(null)).isTrue();
  }

  @Test
  void givenNullObjectWhenIsNullishCalledThenReturnTrue() {
    assertThat(NullObject.isNullish(new NullObjectImpl())).isTrue();
  }

  @Test
  @SuppressWarnings("ConstantConditions")
  void givenNullWhenIsNonNullishCalledThenReturnFalse() {
    assertThat(NullObject.nonNullish(null)).isFalse();
  }

  @Test
  void givenNullObjectWhenIsNonNullishCalledThenReturnFalse() {
    assertThat(NullObject.nonNullish(new NullObjectImpl())).isFalse();
  }

}

class NullObjectImpl implements NullObject {

}
