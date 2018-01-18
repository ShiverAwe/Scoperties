package com.github.shiverawe.scoptions.experimental

/**
  * Used to detect which arguments was not applied in ArgumentsApplier
  *
  * @param options
  * @param unused
  * @param used
  * @tparam O
  */
case class ApplierState[O]
(
  var options: O,
  var unused: List[String],
  var used: List[String] = List.empty,
  var ignored: List[String] = List.empty
) {
  def setUsed(argument: String): Unit = {
    if (!unused.contains(argument)) throw new NoSuchElementException(s"$argument")
    unused = unused.filter(_ != argument)
    used = argument :: used
  }

  /**
    * Moves all arguments, which do not start with `prefix` to `ignored` list
    */
  def ignore(predicate: (String => Boolean)): Unit = {
    val newUnused = unused.filter(s => !predicate(s))
    val newIgnored = unused.filter(predicate)
    newIgnored.foreach(s => println(s"Ignored $s;"))
    unused = newUnused
    ignored = ignored ++ newIgnored
  }

  @deprecated("Unused. Can be deleted")
  def ignore(argument: String): Unit = {
    if (!unused.contains(argument)) throw new NoSuchElementException(s"$argument")
    unused = unused.filter(_ != argument)
    ignored = argument :: used
  }

  def restoreIgnored(): Unit = {
    unused = unused ++ ignored
    ignored = List.empty
  }

  override def toString: String = {
    s"{\n unused: {$unused}, \n used: {$used}, \n ignored: {$ignored} \n}"
  }

  def copyWith[P](opt: P): ApplierState[P] = {
    ApplierState(opt, unused, used, ignored)
  }

  def copyFrom[P](that: ApplierState[P]): Unit = {
    this.unused = that.unused
    this.used = that.used
    this.ignored = that.ignored
  }
}
