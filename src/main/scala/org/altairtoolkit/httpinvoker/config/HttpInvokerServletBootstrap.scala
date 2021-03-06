package org.altairtoolkit.httpinvoker.config

import javax.annotation.PostConstruct
import javax.servlet.ServletContext

import com.typesafe.scalalogging.Logger
import org.altairtoolkit.SpringBean
import org.altairtoolkit.annotation.invoker.HttpInvokerEnable
import org.altairtoolkit.httpinvoker.handler.HttpInvokerHandlerServlet
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.SmartInitializingSingleton
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

class HttpInvokerServletBootstrap(urlResolver: (String) => String, postInit: (ApplicationContext, ServletContext) => Unit = SpringBean.DEFAULT) extends ApplicationContextAware with SmartInitializingSingleton with ServletContextAware {
  private val logger = Logger(LoggerFactory.getLogger(this.getClass))

  var servletContext: ServletContext = _
  var appContext: ApplicationContext = _

  def setServletContext(servletContext: ServletContext): Unit = this.servletContext = servletContext

  def setApplicationContext(appContext: ApplicationContext): Unit = this.appContext = appContext

  override def afterSingletonsInstantiated(): Unit = {
    logger.info("Start Bootstrapping HttpInvoker")
    appContext.getBeansWithAnnotation(classOf[HttpInvokerEnable]).asScala.foreach(bean => {
      val annotation = bean._2.getClass.getAnnotation(classOf[HttpInvokerEnable])
      logger.info(s"Register HttpInvoker Servlet ${annotation.id()}:${annotation.serviceTrait().getSimpleName} to ${urlResolver(annotation.id())} ")
      val exporter = new HttpInvokerServiceExporter
      exporter.setService(bean._2)
      exporter.setServiceInterface(annotation.serviceTrait())
      exporter.afterPropertiesSet()
      servletContext.addServlet(annotation.id(), HttpInvokerHandlerServlet(exporter)).addMapping(urlResolver(annotation.id()))
    })
    if (postInit != SpringBean.DEFAULT) {
      postInit(appContext, servletContext)
    }
    logger.info("Finish Bootstrapping HttpInvoker")
  }
}
 