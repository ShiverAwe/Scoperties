package com.github.shiverawe.scoptions

case class Wiring
(
  name: String,
  target: Scoptions = Scoptions.ROOT_DEFINED
) {}

object Autowired {
  def apply(name: String)(implicit target: Option[Scoptions]): Wiring = {
    if (target.isDefined)
      Wiring(name, target.get)
    else
      Wiring(name)
  }
}
