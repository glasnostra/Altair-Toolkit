package org.altairtoolkit.scalatra.config

import javax.annotation.PostConstruct
import javax.servlet.ServletContext

import com.typesafe.scalalogging.Logger
import org.altairtoolkit.SpringBean
import org.altairtoolkit.annotation.scalatra.ScalatraMapping
import org.altairtoolkit.scalatra.helper.ContextHelper
import org.scalatra.ScalatraServlet
import org.scalatra.servlet.RichServletContext
import org.slf4j.LoggerFactory
import org.springframework.context.{ApplicationContext, ApplicationContextAware}
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


class ScalatraBootstrap(postInit: (ApplicationContext, ServletContext) => Unit = SpringBean.DEFAULT) extends ApplicationContextAware with ServletContextAware {
  private val logger = Logger(LoggerFactory.getLogger(this.getClass))

  @PostConstruct
  def init() {
    logger.info("Start Mounting Scalatra Servlets")
    val richContext = new RichServletContext(servletContext)
    ContextHelper.init(servletContext)
    val resources = appContext.getBeansWithAnnotation(classOf[ScalatraMapping])
    resources.values().asScala.toList.sortBy(x => {
      val annotation = x.getClass.getAnnotation(classOf[ScalatraMapping])
      if (annotation.welcome()) {
        Int.MaxValue
      } else {
        annotation.order()
      }

    }).reverse.foreach {
      case servlet: ScalatraServlet =>
        var path = servlet.getClass.getAnnotation(classOf[ScalatraMapping]).value()
        if (!path.startsWith("/")) path = "/" + path
        logger.info("Mounting " + servlet.getClass.getCanonicalName + " to [" + path + "]")
        richContext.mount(servlet, path)
      case bean => logger.info(s"Unsupported Servlet detected ${bean.getClass.getCanonicalName}")
    }

    if (postInit != SpringBean.DEFAULT) {
      postInit(appContext, servletContext)
    }

    logger.info("Finish Mounting Scalatra Servlets")
  }

  var servletContext: ServletContext = _
  var appContext: ApplicationContext = _

  def setServletContext(servletContext: ServletContext): Unit = {
    require(servletContext != SpringBean.DEFAULT, "Servlet Context can not  Null")
    this.servletContext = servletContext
  }

  def setApplicationContext(appContext: ApplicationContext): Unit = {
    require(appContext != SpringBean.DEFAULT, "Application Context can not  Null")
    this.appContext = appContext
  }
}
 
