package com.github.shiverawe.scoptions

class Wiring
(
  val outerScope: ScoptionsPack = Scoptions.ROOT_DEFINED,
  val name: String = ""
) {}

object Autowired {
  def apply(name: String)(implicit outerScope: ScoptionsPack): Wiring = {
    new Wiring(outerScope, name)
  }
}
