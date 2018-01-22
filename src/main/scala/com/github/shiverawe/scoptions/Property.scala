package com.github.shiverawe.scoptions

abstract class Property[P] extends PropertyLike[P] {
  val target: Option[PropertyPack]

  /* Constructor */
  {
    if (target.isDefined) target.get.registerProperties(this)
  }
}

case class PropertyS(key: String, default: String = "")(implicit val target: Option[PropertyPack]) extends Property[String] {
  override val contentType = "String"

  override def deserialize(string: String) = string
}

case class PropertyI(key: String, default: Int = 0)(implicit val target: Option[PropertyPack]) extends Property[Int] {
  override val contentType = "Int"

  override def deserialize(string: String) = string.toInt
}

case class PropertyF(key: String, default: Float = 0)(implicit val target: Option[PropertyPack]) extends Property[Float] {
  override val contentType = "Float"

  override def deserialize(string: String) = string.toFloat
}

case class PropertyL(key: String, default: Long = 0)(implicit val target: Option[PropertyPack]) extends Property[Long] {
  override val contentType = "Long"

  override def deserialize(string: String) = string.toLong
}

trait PropertyLike[P] {

  val key: String
  val default: P
  var value: Option[P] = None
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




