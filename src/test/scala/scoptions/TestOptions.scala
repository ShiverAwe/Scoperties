package scoptions

import com.github.shiverawe.scoptions._

case class TestOptions() extends Scoptions(outerScope = Scoptions.Root) {
  val host = PropertyS("host", "localhost")
  val port = PropertyI("port", 7345)
  val mode = PropertyS("mode", "production")

  val inner = InnerOptions(outerScope = this)
}

case class InnerOptions(override val outerScope: Scoptions) extends Scoptions(outerScope){
  val prefix = "inner"
  val name = PropertyS("name", "Vasya")
  val size = PropertyF("size", 181.6f)
}
