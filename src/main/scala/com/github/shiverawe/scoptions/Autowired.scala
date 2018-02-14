package com.github.shiverawe.scoptions

case class Wiring
(
  outerScope: ScoptionsPack = Scoptions.ROOT_DEFINED,
  name: String = ""
) {}

object Autowired {
  def apply(name: String)(implicit target: ScoptionsPack): Wiring = {
    new Wiring(target, name)
  }
}
