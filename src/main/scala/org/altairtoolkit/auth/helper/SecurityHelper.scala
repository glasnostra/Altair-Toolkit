package org.altairtoolkit.auth.helper

import org.altairtoolkit.auth.config.AltairUserDetails
import org.springframework.security.core.context.SecurityContextHolder

/**
 * Created by Deny Prasetyo,S.T
 * Java(Scala) Developer and Trainer
 * Software Engineer
 * jasoet87@gmail.com
 * <p/>
 * http://github.com/jasoet
 * http://bitbucket.com/jasoet
 * http://github.com/AltairLib
 * [at] jasoet
 */

case class SecurityHelper(username: String, name: String, roles: Seq[String], enable: Boolean = false, groupName: String, email: String, authenticated: Boolean, authObject: Option[AltairUserDetails[_]])

object SecurityHelper extends SecurityTrait

trait SecurityTrait {
  def auth = SecurityContextHolder.getContext.getAuthentication

  def authenticated = auth.isAuthenticated


  def logged = {
    auth.getPrincipal match {
      case userDetails: AltairUserDetails[_] => true
      case username: String => false
      case _ => false
    }
  }

  def loggedCase(loggedText: String, notLoggedText: String): String = {
    auth.getPrincipal match {
      case userDetails: AltairUserDetails[_] => loggedText
      case username: String => notLoggedText
    }
  }

  def principal = {
    auth.getPrincipal match {
      case userDetails: AltairUserDetails[_] =>
        new SecurityHelper(userDetails.getUsername, userDetails.name, userDetails.roles, userDetails.isEnabled, userDetails.groupName, userDetails.email, auth.isAuthenticated, Some(userDetails))
      case username: String =>
        new SecurityHelper(username, username, Seq(), false, "", "", auth.isAuthenticated, None)
    }
  }



}
