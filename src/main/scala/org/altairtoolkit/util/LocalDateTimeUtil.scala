package org.altairtoolkit.util

import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDate, LocalDateTime, ZoneId}

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

object LocalDateTimeUtil {
  def fromMilliSeconds(milliSecond: Long): LocalDateTime = {
    LocalDateTime.ofInstant(Instant.ofEpochMilli(milliSecond), ZoneId.systemDefault)
  }

  def fromString(dateString: String): LocalDate = {
    LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
  }

  def toMilliSeconds(date: LocalDateTime): Long = {
    date.atZone(ZoneId.systemDefault).toInstant.toEpochMilli
  }

  def defaultFormat(date:LocalDateTime):String={
    date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
  }

  def simpleFormat(date:LocalDate):String={
    date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
  }

  def verySimpleFormat(date:LocalDate):String={
    date.format(DateTimeFormatter.ofPattern("dd-MMM"))
  }
}
