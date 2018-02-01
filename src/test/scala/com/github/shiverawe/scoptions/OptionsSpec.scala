package com.github.shiverawe.scoptions

import org.scalatest.FlatSpec

class OptionsSpec extends FlatSpec {
  "Options" should "be correctly configured by list of arguments" in {
    val options = TestOptions()

    List(
      "string=new_string",
      "integer=789",
      "istring=new_inner_string",
      "ifloat=23.45"
    ).foreach(options.applyArgument)

    assert(options.string() == "new_string")
    assert(options.integer() == 789)
    assert(options.inners.istring() == "new_inner_string")

  }

  it should "throw exception when trying to apply incorrect argument" in {
    val incorrects = List(
      "singleword",
      "key=value=lolwhat"
    )

    val options = TestOptions()
    incorrects.foreach(argument =>
      assertThrows[IllegalArgumentException] {
        options.applyArgument(argument)
      }
    )
  }

  it should "add nested com.github.shiverawe.scoptions" in {
    val options = TestOptions()
    assert(options.registeredSubScoptions contains "inner")
  }
}
