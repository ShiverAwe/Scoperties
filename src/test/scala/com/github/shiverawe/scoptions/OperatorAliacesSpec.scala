package com.github.shiverawe.scoptions

import org.scalatest.FlatSpec

class OperatorAliacesSpec extends FlatSpec {

  case class TestScoptions() extends Scoptions() {
    val property = PropertyS(key = "string_property", default = "default_value")
  }

  "Property" should "be accessible by `apply` method" in {
    val options = new TestScoptions()

    assert(options.property.get == options.property())
  }

  "Property" should "be configurable by `:=` operator" in {
    val options = new TestScoptions()

    options.property := "new_value"

    assert(options.property.get == "new_value")
  }

}
