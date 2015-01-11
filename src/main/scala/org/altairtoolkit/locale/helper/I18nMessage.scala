package org.altairtoolkit.locale.helper

import java.util.Locale

import org.altairtoolkit.SpringBean
import org.altairtoolkit.locale.config.LocaleProperties
import org.apache.commons.lang3.LocaleUtils
import org.springframework.context.ApplicationContext

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
  private var _supportedLocale: Array[Locale] = SpringBean.DEFAULT

  def init(webAppContext: ApplicationContext, supportedLocale: Array[Locale]) = {
    require(webAppContext != SpringBean.DEFAULT, "Application Context can not  Null")
    require(supportedLocale != SpringBean.DEFAULT && supportedLocale.nonEmpty, "SupportedLocale Cannot Be Empty")
    _webAppContext = webAppContext
    _supportedLocale = supportedLocale
  }

  def apply(key: String, args: Array[AnyRef] = Array()) = {
    require(_webAppContext != SpringBean.DEFAULT, "LocaleSupport not Initialized, Please check you configuration for LocaleSupport")
    val localeProp = _webAppContext.getBean(classOf[LocaleProperties])
    LocaleMessageBundle(key, localeProp.value, args)
  }

  def locale: Locale = {
    require(_webAppContext != SpringBean.DEFAULT, "LocaleSupport not Initialized, Please check you configuration for LocaleSupport")
    val localeProp = _webAppContext.getBean(classOf[LocaleProperties])
    localeProp.value
  }

  def locale_=(value: Locale) = {
    require(_webAppContext != SpringBean.DEFAULT, "LocaleSupport not Initialized, Please check you configuration for LocaleSupport")
    val localeProp = _webAppContext.getBean(classOf[LocaleProperties])
    localeProp.value = value
  }

  def setLocaleFromString(value: String) = {
    require(_webAppContext != SpringBean.DEFAULT, "LocaleSupport not Initialized, Please check you configuration for LocaleSupport")
    val localeProp = _webAppContext.getBean(classOf[LocaleProperties])
    localeProp.value = Try(LocaleUtils.toLocale(value)).getOrElse(_supportedLocale.head)
  }
}


