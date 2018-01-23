package com.github.shiverawe.scoptions

import scala.collection.mutable

/**
  * Class allows
  *  - bind properties to be managed by Scoptions
  *  - parse and apply command line arguments to properties
  */
abstract class Scoptions(val outerScope: Scoptions = Scoptions.Root, val name: String = "") extends PropertyPack with ScoptionsPack {

  /**
    * Properties defined in inherited classes use this value to register
    */
  implicit val target: Option[PropertyPack] = Some(this)

  def applyArgument(argument: String): Boolean =
    applyArgumentThere(argument) || applyArgumentInside(argument)

  def copyFrom(source: Scoptions): this.type = {
    source.disassembly.foreach(applyArgument)
    this
  }

  def disassembly: Seq[String] = {
    disassemblyThere() ++ disassemblyInside()
  }.distinct

  override def toString: String = {
    var string = ""
    registeredProperties.foreach(p =>
      string += s"${p._1}=${p._2} "
    )
    string
  }

  /* Constructor */
  {
    if (outerScope != Scoptions.Root) outerScope.registerSubScoptions(this)
  }
}

object Scoptions {
  val Root: Scoptions = new Scoptions(null) {}
}

trait ScoptionsPack {
  val outerScope: ScoptionsPack

  var registeredSubScoptions = Map[String, Scoptions]()

  def registerSubScoptions(scoptions: Scoptions): Unit = {
    if (registeredSubScoptions contains scoptions.name)
      throw new IllegalArgumentException(s"That com.github.shiverawe.scoptions are already [${scoptions.name}]")
    registeredSubScoptions = registeredSubScoptions + (scoptions.name -> scoptions)
  }

  def applyArgumentInside(argument: String): Boolean = {
    registeredSubScoptions.values.foldLeft(false)((b: Boolean, inner: Scoptions) =>
      b || inner.applyArgumentThere(argument) || inner.applyArgumentInside(argument) // was applied previously OR just now OR inside
    )
  }

  def disassemblyInside(): Seq[String] = {
    registeredSubScoptions.values.foldLeft(Seq[String]()) { (seq: Seq[String], inner: Scoptions) =>
      seq ++ inner.disassemblyThere() ++ inner.disassemblyInside()
    }
  }

}

trait PropertyPack {

  /**
    * Collection of properties, registered as dependent
    */
  protected val registeredProperties = mutable.Map[String, Property[_]]()

  val name: String

  /**
    * Allows to parse and apply command line arguments to all registered properties
    */
  def applyArgumentThere(argument: String): Boolean = {
    val kv = parseArgument(argument)
    if (registeredProperties contains kv._1) {
      registeredProperties(kv._1).fromString(kv._2)
      true
    } else false
  }

  def disassemblyThere(): Seq[String] =
    registeredProperties.values.map(_.disassembly).toSeq


  /**
    * Use this method in class constructor to define which properties should be managed by this class
    *
    * @param properties properties to be managed by this class
    */
  def registerProperties(properties: Property[_]*) =
    properties.foreach(p => registeredProperties += (p.key -> p))

  /**
    * Splits a command line argument into key-value pair
    *
    * @param argument command line argument
    * @return (key, value) tuple
    */
  private def parseArgument(argument: String): (String, String) = {
    val kv = argument.split("=") ++ {if (argument.endsWith("=")) Seq("") else Seq()}
    if (kv.length != 2) throw new IllegalArgumentException(s"$argument does not match pattern `key=value`")
    (kv(0), kv(1))
  }
}
