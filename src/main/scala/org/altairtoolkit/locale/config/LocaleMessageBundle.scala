package org.altairtoolkit.locale.config

import java.util.Locale

import org.altairtoolkit.SpringBean
import org.springframework.context.support.ResourceBundleMessageSource

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

object LocaleMessageBundle {
  var messageBundleResource: ResourceBundleMessageSource = SpringBean.DEFAULT

  def init(baseNames: Array[String]) = {
    require(baseNames.nonEmpty, "Basenames cannot be empty")
    messageBundleResource = new ResourceBundleMessageSource
    messageBundleResource.setBasenames(baseNames: _*)
  }

  def apply(key: String, locale: Locale, args: Array[AnyRef] = Array()): String = {
    try {
      messageBundleResource.getMessage(key, args, locale)
    } catch {
      case x: NoSuchElementException => s"!!!Missing Translation for key[$key}],locale[${locale.toLanguageTag}}],argument[${args.mkString(",")}}]"
      case x: Throwable => throw x
    }
  }

}
