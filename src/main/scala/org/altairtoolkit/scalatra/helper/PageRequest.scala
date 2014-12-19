package org.altairtoolkit.scalatra.helper

import org.altairtoolkit.util.StringUtil._
import org.altairtoolkit.slick.sort.Sort
import org.scalatra.Params

/**
 * Created By Jehan Afwazi Ahmad S.Kom.
 * 31/10/14 | 14:06
 * Java & Php Developer
 * Software Engineer
 * jee.archer@gmail.com
 * https://bitbucket.org/jee_
 * https://github.com/jeGit
 */
case class PageRequest(page: Int = 0, size: Int = 20, sort: List[Sort] = Nil)

object PageRequest {
  def apply(params: Params): PageRequest = {
    val sort: List[Sort] = Sort(params.getOrElse("sort", ""))
    val page: Int = params.getOrElse("page", "").toIntOpt.getOrElse(0)
    val size: Int = params.getOrElse("size", "").toIntOpt.getOrElse(20)

    PageRequest(page, size, sort)
  }
}
