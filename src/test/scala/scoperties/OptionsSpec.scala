package scoperties

import org.scalatest.FlatSpec

class OptionsSpec extends FlatSpec {
  "Options" should "be correclty confugured by list of arguments" in {
    val options = TestOptions()
    options.applyArguments(List(
      "host=newhost",
      "port=8123",
      "mode=test"
    ))

    assert(options.host is "newhost")
    assert(options.port is "8123")
    assert(options.mode is "test")
  }
}
