package org.altairtoolkit.scalatra.helper

import org.altairtoolkit.SpringBean
import org.altairtoolkit.auth.config.AltairUserDetails
import org.altairtoolkit.auth.helper.SecurityTrait
import play.twirl.api.Html

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


object Security extends SecurityTrait {
  def hasRole(role: String)(html: Html = Html("")): Html = {
    if (principal.roles.map(_.toLowerCase).contains(role.toLowerCase)) {
      html
    } else {
      Html("")
    }
  }

  def hasAnyRole(roles: Seq[String])(html: Html = Html("")): Html = {
    if (principal.roles.exists(r => roles.map(_.toLowerCase).contains(r.toLowerCase))) {
      html
    } else {
      Html("")
    }
  }

  def loggedSwitch()(loggedHtml: Html = Html(""))(notLoggedHtml: Html = Html("")): Html = {
    auth.getPrincipal match {
      case userDetails: AltairUserDetails[_] => loggedHtml
      case username: String => notLoggedHtml
    }
  }
}