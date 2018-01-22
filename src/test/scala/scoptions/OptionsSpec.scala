package scoptions

import org.scalatest.FlatSpec

class OptionsSpec extends FlatSpec {
  "Options" should "be correclty confugured by list of arguments" in {
    val options = TestOptions()

    options.applyArguments(List(
      "host=newhost",
      "port=8123",
      "mode=test"
    ))

    assert(options.host() == "newhost")
    assert(options.port() == 8123)
    assert(options.mode() == "test")
  }

  it should "throw exception when trying to apply incorrect argument" in {
    val incorrects = List(
      "singleword",
      "key=value=lolwhat",
      "" //empty,
    )

    val options = TestOptions()
    incorrects.foreach(argument =>
      assertThrows[IllegalArgumentException] {
        options.applyArguments(argument)
      }
    )
  }

  it should "throw exception when tryng to apply argument with unknown key" in {
    val unknown = List(
      "what=value",
      "is=value",
      "this=value"
    )

    val options = TestOptions()
    unknown.foreach(argument =>
      assertThrows[NoSuchElementException] {
        options.applyArguments(argument)
      }
    )
  }

  it should "add nested scioptions" in {
    val options = TestOptions()
    assert(options.registeredSubScoptions contains "inner1")
  }
}
