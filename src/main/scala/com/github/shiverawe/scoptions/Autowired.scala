package com.github.shiverawe.scoptions

case class Wiring
(
  outerScope: ScoptionsPack = Scoptions.ROOT_DEFINED,
  name: String = ""
) {}

object Autowired {
  def apply(name: String)(implicit target: Option[ScoptionsPack]): Wiring = {
    if (target.isDefined)
      Wiring(outerScope = target.get, name = name)
    else
      Wiring(name = name)
  }
}
