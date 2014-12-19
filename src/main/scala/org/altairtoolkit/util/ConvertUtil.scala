package org.altairtoolkit.util

import java.util

import scala.collection.JavaConverters._
import scala.util.control.Exception

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

object ConvertUtil {
  def getOrElse[T](value: Any, default: T): T = {
    value match {
      case x: java.lang.String => Exception.failAsValue(classOf[Exception])(default) {
        x.asInstanceOf[T]
      }
      case x: java.util.ArrayList[T] => Exception.failAsValue(classOf[Exception])(default) {
        x.asScala.toList.asInstanceOf[T]
      }
      case x: java.util.Map[_, _] => Exception.failAsValue(classOf[Exception])(default) {
        x.asScala.toMap.asInstanceOf[T]
      }
      case _ => Exception.failAsValue(classOf[Exception])(default) {
        value.asInstanceOf[T]
      }
    }
  }

  def get[T](value: Any): Option[T] = {
    value match {
      case x: java.lang.String => Exception.failAsValue(classOf[Exception])(None) {
        Some(x.asInstanceOf[T])
      }
      case x: util.ArrayList[T] => Exception.failAsValue(classOf[Exception])(None) {
        Some(x.asScala.toList.asInstanceOf[T])
      }

      case _ => Exception.failAsValue(classOf[Exception])(None) {
        Some(value.asInstanceOf[T])
      }
    }
  }


}


