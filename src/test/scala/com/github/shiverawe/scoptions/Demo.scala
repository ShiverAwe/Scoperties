package com.github.shiverawe.scoptions

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
    testOptions.string := "newhost" // almost `=`
    println(testOptions.string()) // get value
    println(testOptions.string) // toString
    testOptions.integer := 17 // set new value
  }
}
