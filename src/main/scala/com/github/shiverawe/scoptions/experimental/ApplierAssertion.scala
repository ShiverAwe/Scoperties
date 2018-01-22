package com.github.shiverawe.scoptions.experimental

trait ApplierAssertion {

  def param(key: String, value: String) = s"$key=$value"

  /**
    * Asserts that arument with such `key` will affect field which can be assecced with `getter`
    * @param key     Key of command line argument
    * @param value   Value of command line argument
    * @param getter  Getter of value which was expected to set. NB: `Any` type expected. Value will be automatically `toString`-ed
    * @param applier Argument applier for `P`
    * @param options Target of applying
    * @tparam P Target type
    */
  def assertApplied[P <: AnyRef](key: String, value: Any, getter: (P => Any))(implicit applier: CmdArgumentsApplier[P], options: P): Unit = {
    val strgetter: P => String = getter(_).toString
    val strvalue = value.toString
    val newoptions = applier.apply(options, param(key, strvalue)).options
    val newvalue = strgetter(newoptions)
    if (newvalue != strvalue) throw new AssertionError(
      s"Argument `$key` was not applied. Expected: `$strvalue`. Actual: `$newvalue`.")
  }

  def assertAppliedB[P <: AnyRef](key: String, getter: (P => Boolean))(implicit applier: CmdArgumentsApplier[P], options: P): Unit = {
    assertApplied(key, true, getter)(applier, options)
    assertApplied(key, false, getter)(applier, options)
  }

  def assertAppliedS[P <: AnyRef](key: String, getter: (P => String))(implicit applier: CmdArgumentsApplier[P], options: P): Unit =
    assertApplied(key, "ughndlamjvhsxlck", getter)(applier, options)

  def assertAppliedI[P <: AnyRef](key: String, getter: (P => Int))(implicit applier: CmdArgumentsApplier[P], options: P): Unit =
    assertApplied(key, 5762347, getter)(applier, options)

  def assertAppliedF[P <: AnyRef](key: String, getter: (P => Float))(implicit applier: CmdArgumentsApplier[P], options: P): Unit =
    assertApplied(key, 5762347, getter)(applier, options)

}