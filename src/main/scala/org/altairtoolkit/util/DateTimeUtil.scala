package org.altairtoolkit.util

import java.time.{LocalDate, Instant, ZoneId, LocalDateTime}

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

object DateTimeUtil {

  implicit class LocalDateTimeImprovement(val o: LocalDateTime) {
    def toMilliSeconds: Long = o.atZone(ZoneId.systemDefault).toInstant.toEpochMilli
  }

  implicit class LocalDateImprovement(val o: LocalDate) {
    def toMilliSeconds: Long = o.atStartOfDay().atZone(ZoneId.systemDefault).toInstant.toEpochMilli

  }

  implicit class MilliSecondsToDateTime(val o: Long) {
    def toLocalDateTime: LocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(o), ZoneId.systemDefault)

    def toLocalDate: LocalDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(o), ZoneId.systemDefault).toLocalDate
  }

}
