package org.altairtoolkit.slick


import org.altairtoolkit.slick.sort.{Asc, Desc, Sort}

import scala.slick.lifted.{Column, ColumnOrdered}

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

trait Sortable {
  val fieldMap: Map[String, Column[_]]

  def sortByString(paramSort: String) = {
    sort(Sort(paramSort))
  }

  def sort(sorts: List[Sort]): (ColumnOrdered[_], ColumnOrdered[_], ColumnOrdered[_], ColumnOrdered[_], ColumnOrdered[_]) = {
    val sd = Asc("default")
    sorts match {
      case a :: b :: c :: d :: e :: _ => (convert(a), convert(b), convert(c), convert(d), convert(e))
      case a :: b :: c :: d :: _ => (convert(a), convert(b), convert(c), convert(d), convert(d))
      case a :: b :: c :: _ => (convert(a), convert(b), convert(c), convert(c), convert(c))
      case a :: b :: _ => (convert(a), convert(b), convert(b), convert(b), convert(b))
      case a :: _ => (convert(a), convert(a), convert(a), convert(a), convert(a))
      case _ => (convert(sd), convert(sd), convert(sd), convert(sd), convert(sd))
    }
  }


  private def convert(sort: Sort): ColumnOrdered[_] = {
    sort match {
      case Asc(x) => fieldMap.getOrElse(x, fieldMap.head._2).asc
      case Desc(x) => fieldMap.getOrElse(x, fieldMap.head._2).desc
      case _ => fieldMap.head._2.asc
    }
  }
}
