package org.altairtoolkit.auth.config

import java.util

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

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


class AltairUserDetails[A <: UserTrait](user: A, authorities: Seq[String]) extends UserDetails {

  override def getAuthorities: util.Collection[_ <: GrantedAuthority] = {
    asJavaCollection(authorities.map { s =>
      Authority(s)
    })
  }

  override def isEnabled: Boolean = user.enabled

  override def getPassword: String = user.password

  override def isAccountNonExpired: Boolean = user.accountNonExpired

  override def isCredentialsNonExpired: Boolean = user.credentialNonExpired

  override def isAccountNonLocked: Boolean = user.accountNonLocked

  override def getUsername: String = user.username

  def roles = authorities

  def groupName = user.authGroupName

  def email = user.email

  def name = user.name

  def authObject: A = user

}

object AltairUserDetails {
  def apply[A <: UserTrait](user: A, authorities: Seq[String]) = {
    new AltairUserDetails[A](user, authorities)
  }

}

object Authority {
  def apply(authority: String) = {
    new GrantedAuthority {
      override def getAuthority: String = authority
    }
  }
}

