package org.altairtoolkit.scalatra.config

import javax.annotation.PostConstruct
import javax.servlet.ServletContext

import com.typesafe.scalalogging.Logger
import org.altairtoolkit.SpringBean
import org.altairtoolkit.annotation.scalatra.Mapping
import org.altairtoolkit.locale.helper.I18nMessage
import org.altairtoolkit.twirl.ContextHelper
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
  val logger = Logger(LoggerFactory.getLogger(this.getClass))

  @PostConstruct
  def init() {
    val richContext = new RichServletContext(servletContext)
    ContextHelper.init(servletContext)
    I18nMessage.init(appContext)
    val resources = appContext.getBeansWithAnnotation(classOf[Mapping])
    resources.values().asScala.toList.sortBy(x => {
      x.getClass.getAnnotation(classOf[Mapping]).order()
    }).reverse.foreach {
      case servlet: ScalatraServlet =>
        var path = servlet.getClass.getAnnotation(classOf[Mapping]).value()
        if (!path.startsWith("/")) path = "/" + path
        logger.info("Mounting " + servlet.getClass.getCanonicalName + " to [" + path + "]")
        richContext.mount(servlet, path)
      case _ =>
    }

    if (postInit != SpringBean.DEFAULT) {
      postInit(appContext, servletContext)
    }

    logger.info("Finish Loading Bootstrap")
  }

  var servletContext: ServletContext = _
  var appContext: ApplicationContext = _

  def setServletContext(servletContext: ServletContext): Unit = this.servletContext = servletContext

  def setApplicationContext(appContext: ApplicationContext): Unit = this.appContext = appContext
}

object ScalatraBootstrap {
  def apply(postInit: (ApplicationContext, ServletContext) => Unit = SpringBean.DEFAULT) = {
    new ScalatraBootstrap(postInit)
  }
}