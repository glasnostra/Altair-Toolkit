package org.altairtoolkit.auth.config

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

trait UserTrait {
  def username: String

  def password: String

  def enabled: Boolean

  def name: String

  def email: String

  def authGroupName: String

  def accountNonExpired: Boolean = true

  def credentialNonExpired: Boolean = true

  def accountNonLocked: Boolean = true
}