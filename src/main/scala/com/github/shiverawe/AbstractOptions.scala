package com.github.shiverawe

import scala.collection.mutable

abstract class AbstractOptions {
  val registeredProperties = mutable.MutableList[Property]()

  protected def registerProperty(property: Property, properties: Property*) = {
    registeredProperties += property
    properties.foreach(p => registeredProperties += p)
  }
}
