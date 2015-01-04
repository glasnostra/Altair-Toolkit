package org.altairtoolkit.slick.sort


/**
 * Deny Prasetyo,S.T
 * Java(Scala) Developer and Trainer
 * Software Engineer | Java Virtual Machine Junkie!
 * jasoet87@gmail.com
 * <p/>
 * http://github.com/jasoet
 * http://bitbucket.com/jasoet
 *
 */

sealed class Sort(_field: String = "", _direction: String = "") {
  def field = _field

  def direction = _direction
 
}

case class Asc(_field: String) extends Sort(_field, "asc")

case class Desc(_field: String) extends Sort(_field, "desc")

object Sort {
  def apply(field: String, direction: String) = {
    direction.toLowerCase match {
      case "desc" => Desc(field)
      case _ => Asc(field)
    }
  }

  def apply(param: String): List[Sort] = {
    param.stripPrefix("[").stripSuffix("]").split(",").flatMap(x => {
      x.split(":").toList match {
        case a :: Nil => Option(Sort(a, "asc"))
        case a :: b :: _ => Option(Sort(a, b))
        case _ => None
      }
    }).toList
  }



}
