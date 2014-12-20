package org.altairtoolkit.id

import java.util.concurrent.atomic.AtomicInteger

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

case class AltairSnowFlakeId(timestamp: Long, serverId: Int, inc: Int)

object AltairSnowFlake {
  val EPOCH: Long = 1288834974657L

  val incr = new AtomicInteger(0)

  def apply( serverId: Int) = new AltairSnowFlakeIdFactory(serverId, incr)

  def parse(id: Long) = {
    val ts = (id >> 22) + EPOCH
    val max: Long = 64 - 1l
    val machineId = (id >> 16) & max
    val i = id & max
    AltairSnowFlakeId(ts, machineId.toInt, i.toInt)
  }

  implicit def alpha2long(hex: String): Long = java.lang.Long.parseLong(hex.toLowerCase, 36)

  def parse(hexId: String): AltairSnowFlakeId = {
    parse(hexId: Long)
  }
}

class AltairSnowFlakeIdFactory( serverId: Int, incr: AtomicInteger) {
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
