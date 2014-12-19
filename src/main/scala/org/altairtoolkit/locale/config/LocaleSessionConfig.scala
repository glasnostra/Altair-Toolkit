package org.altairtoolkit.locale.config

import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.context.support.ResourceBundleMessageSource


/**
 * Deny Prasetyo,S.T
 * Java(Scala) Developer and Trainer
 * Software Engineer | Java Virtual Machine Junkies!
 * jasoet87@gmail.com
 * <p/>
 * http://github.com/jasoet
 * http://bitbucket.com/jasoet
 *
 */
@Configuration
class LocaleSessionConfig {

  @Bean
  def messageSource: ResourceBundleMessageSource = {
    val resource = new ResourceBundleMessageSource
    resource.setBasename("i18n/messages")
    resource
  }
}

