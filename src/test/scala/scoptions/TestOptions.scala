package scoptions

import com.github.shiverawe.Property
import com.github.shiverawe.scoptions.Scoptions

case class TestOptions() extends Scoptions {
  val host: Property = Property("host", "localhost")
  val port: Property = Property("port", "7345")
  val mode: Property = Property("mode", "production")
}
