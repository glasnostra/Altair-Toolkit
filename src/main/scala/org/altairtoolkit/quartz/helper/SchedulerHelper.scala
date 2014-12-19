package org.altairtoolkit.quartz.helper

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
  private var _schedules: mutable.Map[String, Scheduler] = mutable.Map()

  def init(schedules: mutable.Map[String, Scheduler]) = {
    this._schedules = schedules
  }

  def get(name: String): Option[Scheduler] = {
    this._schedules.get(name)
  }

  def list(): List[Scheduler] = {
    this._schedules.map(x => x._2).toList
  }

  def map(): Map[String, Scheduler] = {
    this._schedules.toMap
  }

  def startAll(): Try[Unit] = {
    Try(this.map().foreach(sc => {
      if (sc._2.isInStandbyMode || sc._2.isShutdown) {
        sc._2.start()
      }
    }))
  }


  def stopAll(): Try[Unit] = {
    Try(
      this.map().foreach(sc => {
        if (sc._2.isStarted || sc._2.isInStandbyMode) {
          sc._2.shutdown()
        }
      })
    )
  }

}
