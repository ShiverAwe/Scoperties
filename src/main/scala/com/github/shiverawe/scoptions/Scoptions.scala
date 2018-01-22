package com.github.shiverawe.scoptions

import java.util.NoSuchElementException

import scala.collection.mutable

/**
  * Class allows
  *  - bind properties to be managed by Scoptions
  *  - parse and apply command line arguments to properties
  */
abstract class Scoptions(val outerScope: Scoptions = Scoptions.Root, val name: String = "") extends PropertyPack with ScoptionsPack {

  /* Constructor */
  {
    if (outerScope != Scoptions.Root) outerScope.registerSubScoptions(this)
  }

  def applyArgument(argument: String): Boolean =
    applyArgumentThere(argument) || applyArgumentInside(argument)

  /**
    * Properties defined in inherited classes use this value to register
    */
  implicit val target: Option[PropertyPack] = Some(this)

  override def toString: String = {
    var string = ""
    // TODO: optimization
    registeredProperties.foreach(p =>
      string += s"${p._1}=${p._2} "
    )
    string
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
      throw new IllegalArgumentException(s"That scoptions are already [${scoptions.name}]")
    registeredSubScoptions = registeredSubScoptions + (scoptions.name -> scoptions)
  }

  def applyArgumentInside(argument: String): Boolean ={
    registeredSubScoptions.values.foldLeft(false)((b: Boolean, sc :Scoptions) =>
      b || sc.applyArgumentThere(argument) // was applied previously OR just now
    )
  }

}

trait PropertyPack {

  /**
    * Collection of properties, registered as dependent
    */
  protected val registeredProperties = mutable.Map[String, Property[_]]()

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

  /**
    * Use this method in class constructor to define which properties should be managed by this class
    *
    * @param properties properties to be managed by this class
    */
  def registerProperties(properties: Property[_]*) = {
    //registeredProperties += (property.key -> property)
    properties.foreach(p => registeredProperties += (p.key -> p))
  }

  /**
    * Splits a command line argument into key-value pair
    *
    * @param argument command line argument
    * @return (key, value) tuple
    */
  private def parseArgument(argument: String): (String, String) = {
    val kv = argument.split("=")
    if (kv.length != 2) throw new IllegalArgumentException(s"$argument does not match pattern `key=value`")
    (kv(0), kv(1))
  }
}
