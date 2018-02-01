package com.github.shiverawe.scoptions

trait ScoptionsAssertion{
  def param(key: String, value: String) = s"$key=$value"

  def assertApplied[P](key: String, value: P, property: PropertyLike[P])(implicit options: Scoptions): Unit = {
    options.applyArgument(param(key, property.serializeContent(value)))
    val result = property.getContent()
    if (!property.equals(value, result)) throw new AssertionError(
      s"Argument `$key` was not applied. Expected: `${property.serializeContent(value)}`. Actual: `${property.serializeContent(result)}`.")
  }

  def assertAppliedS(key: String, property: Property[String])(implicit options: Scoptions) = {
    assertApplied(key, "qweriponjrewpoi", property)(options)
    assertApplied(key, "mhxwaliuerhewrx", property)(options)
  }

  def assertAppliedF(key: String, property: Property[Float])(implicit options: Scoptions) = {
    assertApplied(key, 1543.65f, property)(options)
    assertApplied(key, 0000.01f, property)(options)
  }

  def assertAppliedI(key: String, property: Property[Int])(implicit options: Scoptions) = {
    assertApplied(key, 248464, property)(options)
    assertApplied(key, 517572, property)(options)
  }

  def assertAppliedL(key: String, property: Property[Long])(implicit options: Scoptions) =
    assertAppliedI _

  def assertAppliedB(key: String, property: Property[Boolean])(implicit options: Scoptions) = {
    assertApplied(key, true, property)(options)
    assertApplied(key, false, property)(options)
  }

}
