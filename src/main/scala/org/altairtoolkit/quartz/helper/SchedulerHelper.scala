package org.altairtoolkit.quartz.helper

import org.altairtoolkit.quartz.config.{AltairScheduler, QuartzAnnotationConfig}
import org.quartz.Scheduler

import scala.collection.mutable
import scala.util.Try

/**
 * Deny Prasetyo,S.T
 * Java(Scala) Developer and Trainer
 * Software Engineer | Java Virtual Machine Junkies!
 * jasoet87@gmail.com
 * <p>
 * http://github.com/jasoet
 * http://bitbucket.com/jasoet
 */

object SchedulerHelper {
  private var _schedules: mutable.Map[String, AltairScheduler] = mutable.Map()

  def init(schedules: mutable.Map[String, AltairScheduler]) = {
    this._schedules = schedules
  }

  def get(name: String): Option[AltairScheduler] = {
    this._schedules.get(name)
  }

  def list(): List[AltairScheduler] = {
    this._schedules.map(x => x._2).toList
  }

  def map(): Map[String, AltairScheduler] = {
    this._schedules.toMap
  }
  

  def startAll(): Try[Unit] = {
    Try(this.map().foreach(sc => {
      val (_, altairScheduler) = sc
      if (altairScheduler.scheduler.isInStandbyMode || altairScheduler.scheduler.isShutdown) {
        altairScheduler.scheduler.start()
      }
    }))
  }


  def stopAll(): Try[Unit] = {
    Try(
      this.map().foreach(sc => {
        val (_, altairScheduler) = sc
        if (altairScheduler.scheduler.isStarted || altairScheduler.scheduler.isInStandbyMode) {
          altairScheduler.scheduler.shutdown()
        }
      })
    )
  }

}
