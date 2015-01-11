package org.altairtoolkit.id

import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicInteger
import org.altairtoolkit.util.DateTimeUtil._

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

case class AltairSnowFlakeId(timestamp: LocalDateTime, serverId: Int, inc: Int)

private[id] object Increment extends AtomicInteger(0)

object AltairSnowFlake {
  val EPOCH: Long = 1420045200000L

  def apply(serverId: Int) = new AltairSnowFlakeIdFactory(serverId, Increment)

  def parse(id: Long): AltairSnowFlakeId = {
    val ts = (id >> 22) + EPOCH
    val max: Long = 64 - 1l
    val machineId = (id >> 16) & max
    val i = id & max
    AltairSnowFlakeId(ts.toLocalDateTime, machineId.toInt, i.toInt)
  }

  implicit def alpha2long(hex: String): Long = java.lang.Long.parseLong(hex.toLowerCase, 36)

  def parse(hexId: String): AltairSnowFlakeId = {
    parse(hexId: Long)
  }
}

class AltairSnowFlakeIdFactory(serverId: Int, incr: AtomicInteger) {
  assert(serverId < 64, "Maximum machine id number is 63")

  def nextId(): Long = synchronized {
    val currentTs = System.currentTimeMillis()
    val ts: Long = currentTs - AltairSnowFlake.EPOCH
    val max = 16384 - 2
    if (incr.get() >= max) {
      incr.set(0)
    }
    val i = incr.incrementAndGet()
    val result: Long = (ts << 22) | (serverId << 16) | i
    result
  }

  def nextAlpha(): String = synchronized {
    val currentTs = System.currentTimeMillis()
    val ts: Long = currentTs - AltairSnowFlake.EPOCH
    val max = 16384 - 2
    if (incr.get() >= max) {
      incr.set(0)
    }
    val i = incr.incrementAndGet()
    val result: Long = (ts << 22) | (serverId << 16) | i
    java.lang.Long.toString(result, 36).toUpperCase
  }
}
