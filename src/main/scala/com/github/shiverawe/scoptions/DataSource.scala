package com.github.shiverawe.scoptions

trait DataSource extends DataSourceOperators {

  def get(key: String): Option[String]

  def getOrElse(key: String, default: String): String

  def set(key: String, value: String): Boolean

  def containsKey(key: String): Boolean

  def remove(key: String): Boolean

}

trait DataSourceOperators {
  self: DataSource =>

  def apply(key: String): String =
    get(key).getOrElse(throw new NoSuchElementException(s"No value for key ${key}"))

}