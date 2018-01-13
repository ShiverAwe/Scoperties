package scoperties

import com.github.shiverawe.{Scoptions, Property}

case class TestOptions
(
  host: Property = Property("host", "localhost"),
  port: Property = Property("port", "7345"),
  mode: Property = Property("mode", "production")
) extends Scoptions {
  registerProperty(host, port, mode)
}
