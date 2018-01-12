package com.github.shiverawe

object Main {
  def main(args: Array[String]): Unit = {
    var options = AppOptions()
    options.host <= "newhost"
    println(options)
  }
}
