package com.github.shiverawe.scoptions

trait Property[P] {

  val key: String
  val default: P
  var value: Option[P] = None
  val target: Option[Scoptions]
  val contentType: String

  /**
    * Get the value
    *
    * @return effective value of property
    *         "effective value" = `value`, or `default` if `value` is empty
    */
  def get(): P =
    value.getOrElse(default)

  /**
    * Alias for `get` method
    *
    * @see `get`
    */
  def apply(): P = get()

  /**
    * Sets new value of property
    *
    * @param value to be set
    */
  def set(value: P): Unit =
    this.value = Some(value)

  /**
    * Alias for `set` method
    *
    * @see `set`
    */
  def :=(value: P): Unit =
    set(value)

  override def toString: String =
    serialize(apply())

  def fromString(value: String): Unit =
    set(deserialize(value))

  def serialize(value: P): String =
    value.toString

  def deserialize(string: String): P

  /**
    * Sets effective value to default
    */
  def setDefault(): Unit =
    this.value = None

  /**
    * Lets you see if value is default
    *
    * @return `true` if effective value equals default
    */
  def isDefault(): Boolean =
    apply() == default
}
