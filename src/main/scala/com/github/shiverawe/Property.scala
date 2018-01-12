package com.github.shiverawe

case class Property
(
  key: String,
  default: String = "",
  var value: Option[String] = None
) {
  def apply: String =
    value.getOrElse(default)

  def :=(value: String) =
    this.value = Some(value)

  override def toString: String =
    s"(${key}, ${apply})"
}
