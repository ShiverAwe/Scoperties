package com.github.shiverawe.scoptions

case class Wiring
(
  target: Scoptions = Scoptions.ROOT_DEFINED,
  name: String = ""
) {}

object Autowired {
  def apply(name: String)(implicit target: Option[Scoptions]): Wiring = {
    if (target.isDefined)
      Wiring(target.get, name = name)
    else
      Wiring(name = name)
  }
}
