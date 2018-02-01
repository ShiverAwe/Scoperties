package com.github.shiverawe.scoptions

import org.scalatest.FlatSpec

class ScoptionsCopySpec extends FlatSpec{

  "Scoptions" should "copy inner values from another scoptions" in {
    case class OuterScoptions (
      override val outerScope: Scoptions = Scoptions.ROOT_UNDEFINED,
      override val name: String = "") extends Scoptions(outerScope, name) {

      val inner = InnerScoptions(outerScope = this, name = "inner")
    }

    case class InnerScoptions(
      override val outerScope: Scoptions = Scoptions.ROOT_UNDEFINED,
      override val name: String = "") extends Scoptions(outerScope, name) {

      val property = PropertyS("inner_property", "default_value")
    }

    val original = new OuterScoptions()
    val another = new OuterScoptions()

    original.inner.property.set("new_value")

    another.copyFrom(original)

    assert (another.inner.property.get() == "new_value")
  }




}
