package scoptions

import org.scalatest.{FlatSpec, Ignore}

@Ignore
class Demo extends FlatSpec {
  "DEMO1" should "show you how it works" in {
    val testOptions = TestOptions()

    List(
      "host=newhost",
      "mode=test"
    ).foreach(testOptions.applyArgument)

    println(testOptions)
  }

  "DEMO2" should "show you how it works" in {

    val testOptions = TestOptions()
    testOptions.host := "newhost" // almost `=`
    println(testOptions.host()) // get value
    println(testOptions.host) // toString
    testOptions.port := 17 // set new value
    println(testOptions.port() == 17) //true
    println(testOptions.port() == 58) //false
  }
}
