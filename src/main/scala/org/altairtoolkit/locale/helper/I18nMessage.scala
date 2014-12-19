package org.altairtoolkit.locale.helper

import java.util.Locale
import org.altairtoolkit.SpringBean
import org.apache.commons.lang3.LocaleUtils
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ResourceBundleMessageSource

import scala.util.Try

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

object I18nMessage {
  private var _webAppContext: ApplicationContext = SpringBean.DEFAULT
  private val supportedLocales = List(new Locale("in", "id"), Locale.ENGLISH)

  def init(webAppContext: ApplicationContext) = {
    _webAppContext = webAppContext
  }

  def get(key: String, args: Array[AnyRef] = Array()) = {
    val localeProp = _webAppContext.getBean(classOf[LocaleProperties])
    val _message: ResourceBundleMessageSource = _webAppContext.getBean(classOf[ResourceBundleMessageSource])
    if (supportedLocales.map(l => l.getLanguage).contains(localeProp.value.getLanguage)) {
      Try(_message.getMessage(key, args, localeProp.value)).getOrElse("Message Not Found, Please Check Your Message Files")
    } else {
      Try(_message.getMessage(key, args, supportedLocales.head)).getOrElse("Message Not Found, Please Check Your Message Files")
    }
  }

  def locale: Locale = {
    val localeProp = _webAppContext.getBean(classOf[LocaleProperties])
    localeProp.value
  }

  def locale_=(value: Locale) = {
    val localeProp = _webAppContext.getBean(classOf[LocaleProperties])
    localeProp.value = value
  }

  def setLocaleFromString(value: String) = {
    val localeProp = _webAppContext.getBean(classOf[LocaleProperties])
    localeProp.value = Try(LocaleUtils.toLocale(value)).getOrElse(supportedLocales.head)
  }
}


case class LocaleProperties(var value: Locale)