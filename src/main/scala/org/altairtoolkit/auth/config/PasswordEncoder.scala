package org.altairtoolkit.auth.config

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

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

object PasswordEncoder {
  val encoder = new BCryptPasswordEncoder()

  def apply() = {
    encoder
  }

  def encode(rawPassword: String): String = {
    encoder.encode(rawPassword)
  }

  def matches(rawPassword: String, encodedPassword: String): Boolean = {
    encoder.matches(rawPassword, encodedPassword)
  }

}
