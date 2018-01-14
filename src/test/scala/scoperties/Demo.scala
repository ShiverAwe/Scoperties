package scoperties

import org.scalatest.{FlatSpec, Ignore}

@Ignore
class Demo extends FlatSpec {
  "DEMO1" should "show you how it works" in {
    val testOptions = TestOptions()

    testOptions.applyArguments(
      "host=newhost",
      "mode=test"
    )

    println(testOptions)
  }

  "DEMO2" should "show you how it works" in {

    val testOptions = TestOptions()
    testOptions.host := "newhost" // almost `=`
    println(testOptions.host()) // get value
    println(testOptions.host) // toString
    testOptions.port := "newport" // set new value
    println(testOptions.port == "newport") //true
    println(testOptions.port == "dsfksdf") //false
  }
}
