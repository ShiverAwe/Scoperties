package com.github.shiverawe.scoptions

import com.github.shiverawe.scoptions._

case class TestOptions() extends Scoptions {
  val host = PropertyS("host", "localhost")
  val port = PropertyI("port", 7345)
  val mode = PropertyS("mode", "production")

  val inner = InnerOptions(outerScope = this, name = "inner1")
}

case class InnerOptions(override val outerScope: Scoptions, override val name: String) extends Scoptions(outerScope, name) {
  val prefix = "inner"
  val username = PropertyS("username", "Vasya")
  val size = PropertyF("size", 181.6f)
}