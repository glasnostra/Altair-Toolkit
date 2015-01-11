package org.altairtoolkit.locale.config

import java.util.Locale
import javax.annotation.PostConstruct

import org.altairtoolkit.locale.helper.{LocaleMessageBundle, I18nMessage}
import org.springframework.context.{ApplicationContext, ApplicationContextAware}

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


class LocaleBootstrap(basenames: Array[String], supportedLocale: Array[Locale]) extends ApplicationContextAware {
  LocaleMessageBundle.init(basenames)

  var appContext: ApplicationContext = _

  override def setApplicationContext(appContext: ApplicationContext): Unit = this.appContext = appContext

  @PostConstruct
  def init() = {
    I18nMessage.init(appContext,supportedLocale)
  }
}
