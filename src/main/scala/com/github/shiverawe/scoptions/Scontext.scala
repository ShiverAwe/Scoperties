package com.github.shiverawe.scoptions

case class Scontext
(
  val outer: Option[ScoptionsPack] = None,
  val prefix: String = ""
) {
}

object AutoScontext {
  def apply(prefix: String = "")(implicit targetForInnerOptions: ScoptionsPack): Scontext = Scontext(Option(targetForInnerOptions), prefix)
}
