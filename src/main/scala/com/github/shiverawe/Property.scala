package com.github.shiverawe

case class Property
(
  key: String,
  default: String = "",
  var value: Option[String] = None
)(
  implicit val target: Option[Scoptions]
) {

  /* Constructor */ {
    if (target.isDefined) target.get.registerProperties(this)
  }

  /**
    * Get the value
    *
    * @return effective value of property
    *         "effective value" = `value`, or `default` if `value` is empty
    */
  def apply(): String =
    value.getOrElse(default)

  /**
    * Sets new value of property
    *
    * @param value to be set
    */
  def :=(value: String): Unit =
    this.value = Some(value)

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

  /**
    * Compares effective value with argument
    *
    * @param value string to be compared with effective value
    * @return `true` if given value equals effective value, false otherwise
    */
  def ==(value: String): Boolean =
    apply() == value

  /**
    * This method is opposite to `==`
    *
    * @param value string to be compared with effective value
    * @return `false` if given value equals effective value, `true` otherwise
    */
  def !=(value: String): Boolean =
    apply() != value

  override def toString: String =
    apply()
}
