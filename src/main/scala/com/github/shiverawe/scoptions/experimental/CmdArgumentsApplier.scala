package com.github.shiverawe.scoptions.experimental

/**
  *
  * @tparam O
  */
trait CmdArgumentsApplier[O] {
  /**
    * All incoming arguments will be
    */
  val prefix: String

  /**
    * @return Some[O] - options with applied parameter
    *         None if key is unknown
    */
  def withArgument(options: O, key: String, value: String): Option[O]

  def apply(options: O, arg: String): ApplierState[O] =
    apply(options, List(arg))

  /**
    * Applying unknown property will be ignored
    */
  def apply(options: O, args: List[String]): ApplierState[O] = {
    apply(ApplierState(options, args))
  }

  /**
    * Applies arguments from `ApplierState` to self
    *
    * @param state ApplierState
    * @return
    */
  def apply(state: ApplierState[O]): ApplierState[O] = {
    state.ignore(s => !s.startsWith(prefix))
    applyArgs(state,
      (opt, key, value) => withArgument(opt, key, value)
    )
    state.restoreIgnored()
    state
  }


  def delegate[P](opt: P, slaveApplier: CmdArgumentsApplier[P], state: ApplierState[O]): ApplierState[P] = {
    val delegatedState = state.copyWith(opt)
    slaveApplier.apply(delegatedState)
    state.copyFrom(delegatedState)
    delegatedState
  }

  protected def applyArgs(state: ApplierState[O], applier: (O, String, String) => Option[O]): Unit = {
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

  protected def suffix(prefix: String, str: String): Option[String] =
    if (str.startsWith(prefix)) Some(str.substring(prefix.length)) else None

  protected def split(arg: String): (String, String) = {
    val kv = arg.split("=")
    if (kv.length != 2) throw new IllegalArgumentException(s"'$arg' is not a correct argument")
    (kv(0), kv(1))
  }
}

