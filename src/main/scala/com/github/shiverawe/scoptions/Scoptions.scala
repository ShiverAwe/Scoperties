package com.github.shiverawe.scoptions

import scala.collection.mutable

/**
  * Class allows
  *  - bind properties to be managed by Scoptions
  *  - parse and apply command line arguments to properties
  */
abstract class Scoptions(val parent: Wiring = Scoptions.WIRING_UNDEFINED("UNKNOWN")) extends PropertyPack with ScoptionsPack {

  /**
    * Parent scope.
    * Parent scope manages lifecycle of
    */
  val outerScope: Scoptions = parent.target

  /**
    * Name of the instance of scoptions.
    * Name should be unique in parent scope.
    */
  val name: String = parent.name

  /**
    * Properties and subscoptions defined in inherited classes use this value to register
    */
  implicit val target: Option[Scoptions] = Some(this)

  def applyArgument(argument: String): Boolean = {
    applyArgumentThere(argument) || applyArgumentInside(argument)
  }

  def copyFrom(source: Scoptions): this.type = {
    copySubScoptionsFrom(source)
    copyPropertiesFrom(source)
    this
  }


  def disassembly: Seq[String] = {
    disassemblyThere() ++ disassemblyInside()
  }.distinct

  override def toString: String = {
    var string = ""
    registeredProperties.foreach(p =>
      string += s"${p._1}=${p._2} ")
    string
  }

  //--------------------------------CONSTRUCTOR-------------------------
  {
    if (outerScope != Scoptions.ROOT_UNDEFINED &&
      outerScope != Scoptions.ROOT_DEFINED)
      outerScope.registerSubScoptions(this)
  }
}

object Scoptions {
  val ROOT_UNDEFINED: Scoptions = new Scoptions(Wiring(name = "GLOBAL_SCOPTIONS_ROOT_UNDEFINED", null)) {}
  val ROOT_DEFINED: Scoptions = new Scoptions(Wiring(name = "GLOBAL_SCOPTIONS_ROOT", null)) {}

  def WIRING_DEFINED(name: String) = Wiring(name, ROOT_DEFINED)

  def WIRING_UNDEFINED(name: String) = Wiring(name, ROOT_UNDEFINED)

}

trait ScoptionsPack {
  self: Scoptions =>

  var registeredSubScoptions = Map[String, Scoptions]()

  def registerSubScoptions(scoptions: Scoptions): Unit = {
    if (registeredSubScoptions contains scoptions.name)
      throw new IllegalArgumentException(s"That scoptions are already [${scoptions.name}]")
    registeredSubScoptions = registeredSubScoptions + (scoptions.name -> scoptions)
  }

  protected def applyArgumentInside(argument: String): Boolean = {
    registeredSubScoptions.values.foldLeft(false)((alreadyApplied: Boolean, inner: Scoptions) =>
      alreadyApplied || inner.applyArgument(argument) // was applied previously OR just now OR inside
    )
  }

  protected def copySubScoptionsFrom(source: Scoptions): Unit = {
    this.registeredSubScoptions.keys.foreach(key => {
      this.registeredSubScoptions(key).copyFrom(
        source.registeredSubScoptions(key))
    })
  }

  protected def disassemblyInside(): Seq[String] = {
    registeredSubScoptions.values.foldLeft(Seq[String]()) { (seq: Seq[String], inner: Scoptions) =>
      seq ++ inner.disassembly
    }
  }
}

trait PropertyPack {
  self: Scoptions =>

  /**
    * Collection of properties, registered as dependent
    */
  protected val registeredProperties = mutable.Map[String, PropertyLike[_]]()

  /**
    * Allows to parse and apply command line arguments to all registered properties
    */
  def applyArgumentThere(argument: String): Boolean = {
    if (argument == "") return true // TODO VShefer 25 jan: Its a hack. "" means no argument. Need to use Option with None
    val kv = parseArgument(argument)
    if (registeredProperties contains kv._1) {
      registeredProperties(kv._1).fromString(kv._2)
      true
    } else false
  }

  def disassemblyThere(): Seq[String] =
    registeredProperties.values.map(_.disassembly).toSeq

  /**
    * Use this method to define which properties should be managed by this class
    *
    * @param properties properties to be managed by this class
    */
  def registerProperties(properties: PropertyLike[_]*): Unit =
    properties.foreach(p =>
      registeredProperties += (p.key -> p)
    )

  protected def copyPropertiesFrom(source: Scoptions): Unit = {
    source.registeredProperties.keys.foreach(key => {
      registeredProperties(key).setContent(
        source.registeredProperties(key).getContent())
    })
  }

  /**
    * Splits a command line argument into key-value tuple
    *
    * @param argument command line argument
    * @return (key, value) tuple
    */
  private def parseArgument(argument: String): (String, String) = {
    val delimiter = "="
    val pos = argument.indexOf(delimiter)
    if (pos == 0) throw new IllegalArgumentException(s"$argument does not match pattern `key=value`: Starts with `$delimiter`")
    if (pos < 0) throw new IllegalArgumentException(s"$argument does not match pattern `key=value`: No delimiter `$delimiter` found")
    val key = argument.substring(0, pos)
    val value = argument.substring(pos + delimiter.length)
    (key, value)
  }
}
