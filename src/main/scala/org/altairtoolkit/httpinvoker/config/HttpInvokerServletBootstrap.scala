package org.altairtoolkit.httpinvoker.config

import javax.annotation.PostConstruct
import javax.servlet.ServletContext

import com.typesafe.scalalogging.Logger
import org.altairtoolkit.SpringBean
import org.altairtoolkit.httpinvoker.handler.HttpInvokerHandlerServlet
import org.slf4j.LoggerFactory
import org.springframework.context.{ApplicationContext, ApplicationContextAware}
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter
import org.springframework.web.context.ServletContextAware

import scala.collection.JavaConverters._

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

class HttpInvokerServletBootstrap(urlResolver: (String) => String, postInit: (ApplicationContext, ServletContext) => Unit = SpringBean.DEFAULT) extends ApplicationContextAware with ServletContextAware {
  val logger = Logger(LoggerFactory.getLogger(this.getClass))

  @PostConstruct
  def init() {
    logger.info("Start Bootstrapping HttpInvoker")
    appContext.getBeansOfType(classOf[HttpInvokerServiceExporter]).asScala.foreach(bean => {
      logger.info(s"Register HttpInvoker Servlet ${bean._1} to ${urlResolver(bean._1)} ")
      servletContext.addServlet(bean._1, HttpInvokerHandlerServlet(bean._2)).addMapping(urlResolver(bean._1))
    })
    if (postInit != SpringBean.DEFAULT) {
      postInit(appContext, servletContext)
    }
    logger.info("Finish Bootstrapping HttpInvoker")
  }

  var servletContext: ServletContext = _
  var appContext: ApplicationContext = _

  def setServletContext(servletContext: ServletContext): Unit = this.servletContext = servletContext

  def setApplicationContext(appContext: ApplicationContext): Unit = this.appContext = appContext
}

object HttpInvokerServletBootstrap {
  def apply(urlResolver: (String) => String, postInit: (ApplicationContext, ServletContext) => Unit = SpringBean.DEFAULT) = {
    new HttpInvokerServletBootstrap(urlResolver, postInit)
  }
}
