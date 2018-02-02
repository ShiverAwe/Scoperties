package com.github.shiverawe.scoptions

import org.scalatest.FlatSpec

class StringPropertySpec extends FlatSpec{
  case class TestScoptions (
    override val outerScope: Scoptions = Scoptions.ROOT_UNDEFINED,
    override val name: String = "") extends Scoptions(outerScope, name) {

    val property = PropertyS(key = "string_property", default = "default_value")
  }

  "String property" should "be correctly set by `set` method" in {
    val options = TestScoptions()

    options.property.set("new_value")

    assert(options.property.get == "new_value")
  }

  "String property" should "be correctly set by applying cmd argument" in {
    val argument = "string_property=new_value"
    val options = TestScoptions()

    options.applyArgument(argument)

    assert(options.property.get == "new_value")
  }

  "String property" should "be accessible by `apply` method" in {
    val options = TestScoptions()

    assert(options.property.get == options.property())
  }

}
