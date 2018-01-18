package com.github.shiverawe.scoptions.experimental

case class ExperimentalOptions
(
  topic: String = "default_topic",
  url: String = "default_url",
  path: String = "default_path",
  delimiter: String = "default_delimiter",
  ttl: Int = 0,
  size: Float = 0
) {}

object ExperimentalArgumentsApplier extends CmdArgumentsApplier[ExperimentalOptions] {
  override val prefix = "exp_"

  override def withArgument(options: ExperimentalOptions, key: String, value: String): ExperimentalOptions = {
      key match {
        case "topic" => options.copy(topic = value)
        case "url" => options.copy(url = value)
        case "path" => options.copy(path = value)
        case "delimiter" => options.copy(delimiter = value)
        case "ttl" => options.copy(ttl = value.toInt)
        case "size" => options.copy(size = value.toFloat)
        case _ => options
      }
  }

}