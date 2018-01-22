package scoptions

import com.github.shiverawe.scoptions.{PropertyI, PropertyS, Scoptions}

case class TestOptions() extends Scoptions {
  val host = PropertyS("host", "localhost")
  val port = PropertyI("port", 7345)
  val mode = PropertyS("mode", "production")
}
