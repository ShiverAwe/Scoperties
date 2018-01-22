package scoptions

import com.github.shiverawe.scoptions.{Property, PropertyS}
import org.scalatest.FlatSpec

class PropertySpec extends FlatSpec {
  val KEY = "key"
  val DEFAULT_VALUE = "default"
  val NEW_VALUE = "newvalue"
  val UNKNOWN_VALUE = "unknown"

  implicit val target = None

  "Property" should "get with `apply` and and set with `:=`" in {
    val property = PropertyS(KEY, DEFAULT_VALUE)
    assert(property() == DEFAULT_VALUE)

    property := NEW_VALUE
    assert(property() == NEW_VALUE)
  }

  it should "correctly compare with `==` and `!=` methods" in {
    // Comparing default value
    val property = PropertyS(KEY, DEFAULT_VALUE)
    assert(property() == DEFAULT_VALUE)
    assert(property() != UNKNOWN_VALUE)

    // Comparing new value
    property := NEW_VALUE
    assert(property() == NEW_VALUE)
    assert(property() != UNKNOWN_VALUE)
  }
}
