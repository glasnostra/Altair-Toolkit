package org.altairtoolkit.auth

import java.util

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import play.twirl.api.Html

import scala.collection.JavaConversions._

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

case class SecuritySupport(username: String, name: String, roles: Seq[String], enable: Boolean = false, groupName: String, email: String, authenticated: Boolean)

object SecuritySupport {
  def auth = SecurityContextHolder.getContext.getAuthentication

  def authenticated = auth.isAuthenticated


  def logged = {
    auth.getPrincipal match {
      case userDetails: AltairUserDetails[_] => true
      case username: String => false
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
        new SecuritySupport(userDetails.getUsername, userDetails.getName, userDetails.getRoles, userDetails.isEnabled, userDetails.getGroupName, userDetails.getEmail, auth.isAuthenticated)
      case username: String =>
        new SecuritySupport(username, username, Seq(), false, "", "", auth.isAuthenticated)
    }
  }

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

}

trait User {
  def username: String

  def password: String

  def enabled: Boolean

  def name: String

  def email: String

  def authGroupName: String
}

class AltairUserDetails[A <: User](user: A, authorities: Seq[String]) extends UserDetails {
  def getRoles = authorities

  override def getAuthorities: util.Collection[_ <: GrantedAuthority] = {
    asJavaCollection(authorities.map { s =>
      Authority(s)
    })
  }

  override def isEnabled: Boolean = user.enabled

  override def getPassword: String = user.password

  override def isAccountNonExpired: Boolean = true

  override def isCredentialsNonExpired: Boolean = true

  override def isAccountNonLocked: Boolean = true

  override def getUsername: String = user.username


  def getGroupName = user.authGroupName

  def getEmail = user.email

  def getName = user.name

  def getPrincipal = user


}

object UserDetail {
  def apply[A <: User](user: A, autorities: Seq[String]) = {
    new AltairUserDetails(user, autorities)
  }
}

object Authority {
  def apply(authority: String) = {
    new GrantedAuthority {
      override def getAuthority: String = authority
    }
  }
}

