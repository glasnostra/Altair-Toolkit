package org.altairtoolkit.scalatra.config

import javax.servlet.ServletContext

import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean

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

trait ScalatraSupport {
  private val logger = Logger(LoggerFactory.getLogger(this.getClass))

  def postInit(appContext: ApplicationContext, servletContext: ServletContext): Unit = {}

  @Bean
  def scalatraBootstrap: ScalatraBootstrap = {
    logger.info("Initializing ScalatraSupport")
    new ScalatraBootstrap((appContext, servletContext) => {
      postInit(appContext, servletContext)
    })
  }

}
