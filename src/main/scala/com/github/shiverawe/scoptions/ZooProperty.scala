package com.github.shiverawe.scoptions

case class ZooProperty[P](property: Property[P])(implicit val target: Option[PropertyPack])
  extends PropertyLike[P] with SerializableValue[P] {
  override val key = property.key
  override val default = property.default
  override val contentType = property.contentType

  private val source: DataSource = _

  override def getContent(): P = deserialize(source.getOrElse(key, serialize(value.getOrElse(default))))

  override def setContent(value: Any): Unit = source.set(key, serialize(value.asInstanceOf[P]))

  protected def getOrDefault(default: String): String = source.getOrElse(key, default)

  override def deserialize(string: String): P = property.deserialize(string)

  override def serialize(content: P): String = property.serialize(content)

  completeRegistration()
}
