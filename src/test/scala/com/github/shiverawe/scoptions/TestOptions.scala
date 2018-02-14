package com.github.shiverawe.scoptions

case class TestOptions() extends Scoptions {
  val string = PropertyS("string") // Default value == ""
  val integer = PropertyI("integer") // Default value == 0
  val float = PropertyF("float") // Defalult value == 0.0

  val inners = InnerOptions(outerScope = this, name = "inner")
}

case class InnerOptions(override val outerScope: Scoptions, override val name: String) extends Scoptions(outerScope, name) {
  val istring = PropertyS("istring") // Default value == ""
  val ifloat = PropertyF("ifloat") // Default value == 0.0
}