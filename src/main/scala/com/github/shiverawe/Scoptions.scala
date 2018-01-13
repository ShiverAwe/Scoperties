package com.github.shiverawe

import java.util.NoSuchElementException

import scala.collection.mutable

/**
  * Class allows
  *  - bind properties to be managed by Scoptions
  *  - parse and apply command line arguments to properties
  */
abstract class Scoptions {

  implicit val target = Some(this)

  protected val registeredProperties = mutable.Map[String, Property]()

  /**
    * Allows to parse and apply command line arguments to all registered properties
    *
    * @param arguments
    */
  def applyArguments(arguments: List[String]): Unit = {
    arguments.foreach(argument => {
      val kv = parseArgument(argument)
      if (!registeredProperties.contains(kv._1))
        throw new NoSuchElementException(s"Unknown property `${kv._1}`")
      registeredProperties(kv._1) := kv._2
    })
  }

  /**
    * Just an alias for comfortable use
    *
    * @see applyArguments(arguments: List[String])
    */
  def applyArguments(arguments: String*): Unit =
    applyArguments(arguments.toList)

  /**
    * Use this method in class constructor to define which properties should be managed by this class
    *
    * @param properties properties to be managed by this class
    */
  def registerProperties(properties: Property*) = { //TODO : Rename to ..ties
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
