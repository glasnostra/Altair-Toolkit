package org.altairtoolkit.json

import org.json4s.JsonAST.JObject
import org.json4s.JsonDSL._

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
case class Page[T <: Jsonable](data: List[T] = List(), total: Int, page: Int, size: Int) extends Jsonable {
  override def toJsonObject: JObject = {
      ("totalElements" -> total) ~ ("totalPages" -> totalPage) ~ ("size" -> size) ~ ("number" -> page) ~ ("data" -> data.map(c => c.toJsonObject))
  }

  def totalPage: Long = {
    val tot: Int = if (total <= 0) 1 else total
    val siz: Int = if (size <= 0) 1 else size
    (tot / size) + (if (tot % siz > 0) 1 else 0)
  }

  def deserialize(func:T => JObject):JObject={
    ("totalElements" -> total) ~ ("totalPages" -> totalPage) ~ ("size" -> size) ~ ("number" -> page) ~ ("data" -> data.map(func))
  }
}
