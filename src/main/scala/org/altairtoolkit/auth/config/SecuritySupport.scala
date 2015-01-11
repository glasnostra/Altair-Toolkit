package org.altairtoolkit.auth.config

import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.{UserDetails, UserDetailsService}
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

trait SecuritySupport extends WebSecurityConfigurerAdapter {

  @Bean
  override def authenticationManager(): AuthenticationManager = super.authenticationManager()

  def configuration(http: HttpSecurity)

  def userResolver(username: String): AltairUserDetails[_]

  protected override def configure(auth: AuthenticationManagerBuilder) {
    auth.authenticationProvider(daoAuthenticationProvider)
  }


  def daoAuthenticationProvider: DaoAuthenticationProvider = {
    val o: DaoAuthenticationProvider = new DaoAuthenticationProvider
    o.setUserDetailsService(new UserDetailsService {
      override def loadUserByUsername(s: String): UserDetails = {
        userResolver(s)
      }
    })
    o.setPasswordEncoder(PasswordEncoder())
    o
  }

  protected override def configure(http: HttpSecurity) = {
    configuration(http)
  }
}
