package org.altairtoolkit

import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext

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

object WebApplicationContextProvider {
  def apply(classes: Array[Class[_]]): WebApplicationContext = {
    val applicationContext = new AnnotationConfigWebApplicationContext()
    applicationContext.register(classes:_*)
    applicationContext.refresh()
    applicationContext
  }
}
