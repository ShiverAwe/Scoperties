package com.github.shiverawe

case class AppOptions
(
  host: Property = Property("host", "localhost").register(this),
  port: Property = Property("port", "7345")
){
}