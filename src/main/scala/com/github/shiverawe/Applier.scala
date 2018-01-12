package com.github.shiverawe

trait Applier[O] {
  def withArgument(opt: O, key: String, value: String): O
}


