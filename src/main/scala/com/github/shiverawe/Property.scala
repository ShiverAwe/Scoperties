package com.github.shiverawe

case class Property
(
  key: String,
  default: String
) {
  def register(options: AbstractOptions): _root_.com.github.shiverawe.Property = ??

  var value: Option[String] = None

  def apply: String = value.getOrElse(default)

  def <= (value: String): Unit =
    this.value = Some(value)
}
