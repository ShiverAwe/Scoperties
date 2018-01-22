package com.github.shiverawe.scoptions.experimental

/**
  *
  * @tparam O
  */
trait CmdArgumentsApplier[O <: AnyRef] {
  /**
    * All incoming arguments will be
    */
  val prefix: String

  /**
    * @return Options with applied parameter
    *         Return the same object or null from this method to
    *         signalize that argument was not applied
    */
  def withArgument(options: O, key: String, value: String): O

  /**
    * Wraps returned from `withArgument()` to detect if argument was not applied.
    */
  private def withArg(options: O, key: String, value: String): Option[O] = {
    val result = withArgument(options, key, value)
    if (result eq options) return None
    Option(result)
  }

  /**
    * Alias
    *
    * @see `apply(ApplierState)`
    */
  def apply(options: O, arg: String): ApplierState[O] =
    apply(options, List(arg))

  /**
    * Alias
    *
    * @see `apply(ApplierState)`
    */
  def apply(options: O, args: List[String]): ApplierState[O] = {
    apply(ApplierState(options, args))
  }

  /**
    * Applies arguments from `ApplierState` to self
    *
    * @param state ApplierState
    */
  def apply(state: ApplierState[O]): ApplierState[O] = {
    state.ignore(s => !s.startsWith(prefix))
    applyArgs(state,
      (opt, key, value) => withArg(opt, key, value)
    )
    state.restoreIgnored()
    state
  }


  private def applyArgs(state: ApplierState[O], applier: (O, String, String) => Option[O]): Unit = {
    state.options =
      state.unused.foldLeft(state.options) { (opt, arg) =>
        val kv = split(arg)
        val result = applier(opt, kv._1, kv._2)
        if (result.isDefined) {
          state.setUsed(arg)
          result.get
        } else opt
      }
  }

  private def delegate[P <: AnyRef](opt: P, slaveApplier: CmdArgumentsApplier[P], state: ApplierState[O]): ApplierState[P] = {
    val delegatedState = state.copyWith(opt)
    slaveApplier.apply(delegatedState)
    state.copyFrom(delegatedState)
    delegatedState
  }

  private def suffix(prefix: String, str: String): Option[String] =
    if (str.startsWith(prefix)) Some(str.substring(prefix.length)) else None

  private def split(arg: String): (String, String) = {
    val kv = arg.split("=")
    if (kv.length != 2) throw new IllegalArgumentException(s"'$arg' is not a correct argument")
    (kv(0), kv(1))
  }
}

