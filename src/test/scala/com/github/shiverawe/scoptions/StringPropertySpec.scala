package com.github.shiverawe.scoptions

import org.scalatest.FlatSpec

class StringPropertySpec extends FlatSpec{
  case class TestScoptions () extends Scoptions() {
    val property = PropertyS(key = "string_property", default = "default_value")
  }

  "String property" should "be correctly set by `set` method" in {
    val options = new TestScoptions()

    options.property.set("new_value")

    assert(options.property.get == "new_value")
  }

  "String property" should "be correctly set by applying cmd argument" in {
    val options = new TestScoptions()

    val argument = "string_property=new_value"
    options.applyArgument(argument)

    assert(options.property.get == "new_value")
  }


}
