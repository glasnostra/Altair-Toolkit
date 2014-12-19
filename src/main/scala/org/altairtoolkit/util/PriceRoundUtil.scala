package org.altairtoolkit.util

/**
 * Deny Prasetyo,S.T
 * Java(Scala) Developer and Trainer
 * Software Engineer | Java Virtual Machine Junkies!
 * jasoet87@gmail.com
 * <p>
 * http://github.com/jasoet
 * http://bitbucket.com/jasoet
 */

object PriceRoundUtil {
  def apply(number: Double): Double = {
    up(number, 500, 10)
  }

  def up(number: Double, round: Integer, limit: Integer): Double = {
    if (round <= limit) {
      number
    } else {
      val mod = number % round
      if (mod >= limit) {
        number + (round - mod)
      } else {
        number - mod
      }
    }
  }
}
