package com.github.shiverawe

import scala.collection.mutable

abstract class AbstractOptions {
  val registeredProperties = mutable.Map[String, Property]()

  protected def registerProperty(property: Property, properties: Property*) = {
    registeredProperties += (property.key -> property)
    properties.foreach(p => registeredProperties += (p.key -> p))
  }

  def applyArguments(arguments: List[String]): Unit = {
    arguments.foreach(argument => {
      val kv = parseArgument(argument)
      registeredProperties(kv._1) := kv._2
    })
  }

  protected def parseArgument(argument: String): (String, String) = {
    val kv = argument.split("=")
    if (kv.length != 2) throw new IllegalArgumentException(s"$argument does not match `key=value`")
    (kv(0), kv(1))
  }
}
