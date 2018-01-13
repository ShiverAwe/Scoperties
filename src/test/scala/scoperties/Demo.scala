package scoperties

import org.scalatest.FlatSpec


class Demo extends FlatSpec {
  "demonstration" should "" in {
    val testOptions = TestOptions()

    testOptions.applyArguments(
      "host=NEWhost",
      "mode=NEWmode"
    )

    println(testOptions)
  }

  "demonstration2" should "" in {

    val testOptions = TestOptions()

    testOptions.host := "WOWhost" // almost `=`

    println(testOptions.host()) // get value

    println(testOptions.host) // calls `toString` (string.toString == string)

    testOptions.port := "WOWport"
    println(testOptions.port == "Wowport")

  }
}
