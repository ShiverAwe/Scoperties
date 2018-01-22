package com.github.shiverawe.scoptions

abstract class AbstractProperty[P <: Any] extends Property[P] {
  /* Constructor */
  {
    if (target.isDefined) target.get.registerProperties(this)
  }
}

case class PropertyS(key: String, default: String = "")(implicit val target: Option[Scoptions]) extends AbstractProperty[String] {
  override val contentType = "String"

  override def deserialize(string: String) = string
}

case class PropertyI(key: String, default: Int = 0)(implicit val target: Option[Scoptions]) extends AbstractProperty[Int] {
  override val contentType = "String"

  override def deserialize(string: String) = string.toInt
}

case class PropertyF(key: String, default: Float = 0)(implicit val target: Option[Scoptions]) extends AbstractProperty[Float] {
  override val contentType = "String"

  override def deserialize(string: String) = string.toFloat
}

case class PropertyL(key: String, default: Long = 0)(implicit val target: Option[Scoptions]) extends AbstractProperty[Long] {
  override val contentType = "String"

  override def deserialize(string: String) = string.toLong
}


