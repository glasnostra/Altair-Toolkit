package org.altairtoolkit.util

/**
 * Created by Yanuar Arifin
 * Scala and Web Developer
 * januaripin@gmail.com
 *
 * https://bitbucket.org/januaripin
 * https://github.com/januaripin
 */
object StringUtil {

  implicit class StringImprovements(val s: String) {

    import scala.util.control.Exception._

    def toIntOpt = catching(classOf[NumberFormatException]) opt s.toInt

    def toBooleanOpt = catching(classOf[IllegalArgumentException]) opt s.toBoolean

    def toDoubleOpt =  catching(classOf[NumberFormatException]) opt s.toDouble
  }

}
