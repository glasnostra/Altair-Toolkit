package org.altairtoolkit.locale.config

import java.util.Locale

import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.{Bean, Scope}

/**
 * Created by Deny Prasetyo,S.T
 * Java(Script) Developer and Trainer
 * Software Engineer
 * jasoet87@gmail.com
 * <p/>
 * http://github.com/jasoet
 * http://bitbucket.com/jasoet
 *
 * [at] jasoet
 */

trait LocaleSupport {
  private val logger = Logger(LoggerFactory.getLogger(this.getClass))

  def defaultLocale: Locale = {
    Locale.ENGLISH
  }

  def supportedLocales: Array[Locale] = {
    Array(Locale.ENGLISH, new Locale("in", "id"))
  }

  def baseNames: Array[String] = {
    Array("i18n/messages")
  }

  @Bean
  @Scope(value = "session")
  def localeProperties: LocaleProperties = {
    logger.info(s"Registering Locale with Default ${defaultLocale.toLanguageTag}")
    LocaleProperties(defaultLocale)
  }

  @Bean
  def localeBootstrap: LocaleBootstrap = {
    logger.info("Initializing LocaleSupport")
    new LocaleBootstrap(baseNames,supportedLocales)
  }

}

