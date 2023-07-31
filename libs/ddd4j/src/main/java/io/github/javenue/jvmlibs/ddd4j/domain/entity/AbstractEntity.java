package io.github.javenue.jvmlibs.ddd4j.domain.entity;

/**
 * DDDのエンティティを表す抽象クラス。
 */
public abstract class AbstractEntity {

  /**
   * エンティティクラスのIDを用いてオブジェクトの同値性を判定するためのメソッド。
   */
  @Override
  public abstract boolean equals(Object object);

  /**
   * エンティティクラスのIDを用いてハッシュ値を生成するためのメソッド。
   */
  @Override
  public abstract int hashCode();

}
