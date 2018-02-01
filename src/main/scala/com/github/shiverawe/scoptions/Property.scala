package com.github.shiverawe.scoptions

abstract class Property[P] extends PropertyLike[P] with SerializableValue[P] {

  def option(defaultNone: Boolean = false): OptionProperty[P] = {
    OptionProperty(this, defaultNone)(target)
  }

  def zoo() = ZooProperty(this)(target)

  //CONSTRUCTOR
  completeRegistration()
}


case class OptionProperty[P](property: Property[P], defaultNone: Boolean = false)(implicit val target: Option[PropertyPack])
  extends PropertyLike[Option[P]] with SerializableOption[P] {

  //--------------------------------FIELDS----------------------------

  override val key: String = property.key
  override val default: Option[P] = if (defaultNone) None else Option(property.default)
  override val contentType = s"Option[${property.contentType}]"

  //-------------------------------------------------------------------

  override def deserialize(string: String): P = property.deserialize(string)

  override def serialize(content: P): String = property.serialize(content)

  //CONSTRUCTOR
  completeRegistration()
  if (!property.isDefault()) this.set(Option(property.get())) // Wrap value with `Option`
}

trait PropertyLike[P] extends Content[P] with Registration[P] {
  val key: String
  val default: P
  var value: Option[P] = None
  val contentType: String

  def apply(): P = getContent()

  def :=(value: P): Unit =
    setContent(value)

  /**
    * Sets effective value to default
    */
  def setDefault(): Unit =
    this.value = None

  /**
    * Lets you see if value is default
    *
    * @return `true` if effective value equals default
    */
  def isDefault(): Boolean =
    getContent() == default

  def disassembly: String = {
    s"$key=${getContent()}"
  }
}

trait SerializableValue[P] {
  self: Content[P] =>

  def get(): P = getContent()

  def set(value: P): Unit = setContent(value)

  def serialize(value: P): String =
    value.toString

  def deserialize(string: String): P

  def serializeContent(v: P): String = serialize(v)

  def deserializeContent(string: String): P = deserialize(string)
}

trait SerializableOption[P] {
  self: Content[Option[P]] =>

  def get(): P = getContent().get

  def getOrElse(v: P): P = getContent().getOrElse(v)

  def set(value: P): Unit = setContent(Option(value))

  def set(value: Option[P]): Unit = setContent(value)

  def isDefined: Boolean = getContent().isDefined

  def isEmpty: Boolean = getContent().isEmpty

  def deserialize(string: String): P

  def serialize(content: P): String =
    content.toString

  def deserializeContent(string: String): Option[P] =
    if (string == "None") None
    else Option(
      deserialize(string.substring(5, string.length - 1))) // Substring cuts `Some(` and `)`

  def serializeContent(content: Option[P]): String =
    if (content.isEmpty) "None"
    else s"Some(${serialize(content.get)})"

}

trait Content[P] {
  val default: P
  var value: Option[P]

  def getContent(): P =
    value.getOrElse(default)

  def setContent(content: Any): Unit = //TODO Shefer 25 jan: Make typesafe
    value = Option(content.asInstanceOf[P])

  def serializeContent(content: P): String

  def deserializeContent(string: String): P

  def equals(a: P, b: P): Boolean =
    a == b

  override def toString(): String = serializeContent(getContent())

  def fromString(string: String) = {
    setContent(deserializeContent(string))
    // TODO delete debut out
    //pri
  }
}

trait Registration[P] {
  self: PropertyLike[P] =>

  /**
    * This value will be implicitly given from scoptions controller
    */
  val target: Option[PropertyPack]

  /**
    * Makes self to be controlled by `target`
    *
    * @param target Scoptions
    */
  protected def registerIn(target: PropertyPack): Unit =
    target.registerProperties(this)

  protected def completeRegistration(): Unit =
    if (target.isDefined) registerIn(target.get)
}