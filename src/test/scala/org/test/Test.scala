package org.test

import org.altairtoolkit.id.AltairSnowFlake

import scala.collection.mutable
import scala.util.Random

/**
 * Created by Deny Prasetyo,S.T
 * Java(Script) Developer and Trainer
 * Software Engineer
 * jasoet87@gmail.com
 * <p/>
 * http://github.com/jasoet
 * http://bitbucket.com/jasoet
 *
 * @jasoet
 */

object Test extends App {

  val set:mutable.Set[Long] = mutable.Set()
  0 to 200000 foreach { x =>
    val su = AltairSnowFlake(Random.nextInt(5)).nextId()
    set += su
    println(su)
  }

  println(set.size)

}
