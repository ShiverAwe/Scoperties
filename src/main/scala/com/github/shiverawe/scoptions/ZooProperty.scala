package com.github.shiverawe.scoptions

case class ZooProperty[P](property: Property[P])(implicit val target: Option[PropertyPack])
  extends PropertyLike[P] with SerializableValue[P] {
  override val key = property.key
  override val default = property.default
  override val contentType = property.contentType

  override def getContent(): P = deserialize(getZooOrDefault(serialize(value.getOrElse(default))))

  override def setContent(value: Any): Unit = setZoo(serialize(value.asInstanceOf[P]))

  // TODO Shefer 31 jan: Extract Zoo getter and setter into smth like ZooDataSource
  protected def getZooOrDefault(default: String): String = ConfigClient.get(key, default)

  protected def setZoo(value: String): Unit = ConfigClient.set(key, value)

  override def deserialize(string: String): P = property.deserialize(string)

  override def serialize(content: P): String = property.serialize(content)

  completeRegistration()
}

object ConfigClient {
  def set(key: String, value: String): Unit = ???

  def get(key: String, default: String): _root_.scala.Predef.String = ???

}

