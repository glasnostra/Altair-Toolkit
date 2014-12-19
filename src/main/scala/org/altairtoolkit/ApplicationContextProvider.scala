package org.altairtoolkit

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

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

class ApplicationContextProvider(classes: Array[Class[_]]) extends AnnotationConfigApplicationContext {
  classes.foreach { c =>
    register(c)
  }
  refresh()
}

object ApplicationContextProvider {
  def apply(classes: Array[Class[_]]): ApplicationContext = {
    new ApplicationContextProvider(classes)
  }
}
