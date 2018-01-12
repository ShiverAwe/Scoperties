package com.github.shiverawe

case class AppOptions
(
  host: Property = Property("host", "localhost"),
  port: Property = Property("port", "7345")
) extends AbstractOptions {
  registerProperty(host, port)
}


