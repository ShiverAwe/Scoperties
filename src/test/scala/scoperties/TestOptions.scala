package scoperties

import com.github.shiverawe.{AbstractOptions, Property}

case class TestOptions
(
  host: Property = Property("host", "localhost"),
  port: Property = Property("port", "7345"),
  mode: Property = Property("mode", "production")
) extends AbstractOptions {
  registerProperty(host, port, mode)
}
