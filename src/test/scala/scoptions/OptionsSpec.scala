package scoptions

import org.scalatest.FlatSpec

class OptionsSpec extends FlatSpec {
  "Options" should "be correctly configured by list of arguments" in {
    val options = TestOptions()

    List(
      "host=newhost",
      "port=8123",
      "mode=test",
      "username=George"
    ).foreach(options.applyArgument)

    assert(options.host() == "newhost")
    assert(options.port() == 8123)
    assert(options.mode() == "test")
    assert(options.inner.username() == "George")
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
        options.applyArgument(argument)
      }
    )
  }

  it should "add nested scoptions" in {
    val options = TestOptions()
    assert(options.registeredSubScoptions contains "inner1")
  }
}
