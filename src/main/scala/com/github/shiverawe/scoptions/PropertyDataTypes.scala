package com.github.shiverawe.scoptions
import scala.concurrent.duration.FiniteDuration

case class PropertyS(key: String, default: String = "")(implicit val target: Option[PropertyPack]) extends Property[String] {
  override val contentType = "String"

  override def deserialize(string: String) = string

}

case class PropertyI(key: String, default: Int = 0)(implicit val target: Option[PropertyPack]) extends Property[Int] {
  override val contentType = "Int"

  override def deserialize(string: String) = string.toInt

}

case class PropertyF(key: String, default: Float = 0)(implicit val target: Option[PropertyPack]) extends Property[Float] {
  override val contentType = "Float"

  override def deserialize(string: String) = string.toFloat
}

case class PropertyL(key: String, default: Long = 0)(implicit val target: Option[PropertyPack]) extends Property[Long] {
  override val contentType = "Long"

  override def deserialize(string: String) = string.toLong

}

case class PropertyB(key: String, default: Boolean)(implicit val target: Option[PropertyPack]) extends Property[Boolean] {
  override val contentType = "Boolean"

  override def deserialize(string: String) = string.toBoolean
}

case class PropertyFiniteDurationMillis(key: String, default: FiniteDuration)(implicit val target: Option[PropertyPack]) extends Property[FiniteDuration] {

  override val contentType = "FiniteDuration"

  // TODO VShefer 25 jan: write correct serialization
  override def deserialize(string: String) = ??? //TimeUtil.toLong(string).millis

  //  override def serialize(value: FiniteDuration): String =
  //    value.toString().dropRight(" milliseconds".length)

}

/**
  * Just a wrapper above any type
  * Not serializable
  */
case class PropertyHolder[P <: Any](key: String, default: P)(implicit val target: Option[PropertyPack]) extends Property[P] {
  override val contentType = "Properties"

  override def deserialize(string: String): P = ??? //null.asInstanceOf[P]

  override def serialize(value: P): String = ""

  override def disassembly: String = "" // See http://bd-repo.barsum.ru/mkasatkin/bigdata/issues/328#note_8443
}

//case class PropertyRedis(key: String, default: String)(implicit val target: Option[PropertyPack]) extends Property[String] {
//  override val contentType = "Redis"
//
//  override def getContent(): String = ConfigClient.get(key, default)
//
//  def redisPath: String = ConfigClient.redisPath(getContent())
//
//  override def deserialize(string: String): String = string
//}

case class PropertyEnum[P <: Enumeration](key: String, defaultValueOfEnum: Any, val enum: P)(implicit val target: Option[PropertyPack]) extends Property[String] {
  override val contentType = "Enumeration"

  override val default: String = valueOfEnum(defaultValueOfEnum.toString)

  override def setContent(content: Any): Unit = super.setContent(valueOfEnum(content.toString))

  override def deserialize(string: String): String = valueOfEnum(string)

  override def serialize(value: String): String = valueOfEnum(value)

  def valueOfEnum(string: String): String = enum.withName(string).toString
}
