package org.altairtoolkit.twirl

import org.altairtoolkit.SpringBean

/**
 * Deny Prasetyo,S.T
 * Java(Scala) Developer and Trainer
 * Software Engineer | Java Virtual Machine Junkies!
 * jasoet87@gmail.com
 * <p/>
 * http://github.com/jasoet
 * http://bitbucket.com/jasoet
 *
 */

object ContextHelper {
  private var _context: javax.servlet.ServletContext = SpringBean.DEFAULT

  def init(context: javax.servlet.ServletContext) = {
    _context = context
  }

  def assetsOf(path: String): String = {
    _context.getContextPath + "/resources/" + path
  }

  def contextOf(path: String): String = {
    _context.getContextPath + "/" + path
  }

  def contextPath(): String = {
    _context.getContextPath
  }
}


object Route {
  def apply(path: String): String = {
    ContextHelper.contextOf(path)
  }
}

object Asset {
  def apply(path: String): String = {
    ContextHelper.assetsOf(path)
  }
}