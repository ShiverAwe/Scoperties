package com.github.shiverawe

import java.util.NoSuchElementException

import scala.collection.mutable

abstract class AbstractOptions {
  protected val registeredProperties = mutable.Map[String, Property]()

  def applyArguments(arguments: List[String]): Unit = {
    arguments.foreach(argument => {
      val kv = parseArgument(argument)
      if (!registeredProperties.contains(kv._1))
        throw new NoSuchElementException(s"Unknown property `${kv._1}`")
      registeredProperties(kv._1) := kv._2
    })
  }

  def applyArguments(arguments: String*): Unit =
    applyArguments(arguments.toList)

  protected def registerProperty(property: Property, properties: Property*) = {
    registeredProperties += (property.key -> property)
    properties.foreach(p => registeredProperties += (p.key -> p))
  }

  protected def parseArgument(argument: String): (String, String) = {
    val kv = argument.split("=")
    if (kv.length != 2) throw new IllegalArgumentException(s"$argument does not match pattern `key=value`")
    (kv(0), kv(1))
  }
}
