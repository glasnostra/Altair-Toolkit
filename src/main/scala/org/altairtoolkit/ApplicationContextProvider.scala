package org.altairtoolkit

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

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

object ApplicationContextProvider {
  def apply(classes: Array[Class[_]]): ApplicationContext = {
    val applicationContext = new AnnotationConfigApplicationContext()
    applicationContext.register(classes: _*)
    applicationContext.refresh()
    applicationContext
  }
}
