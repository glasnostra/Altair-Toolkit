package org.test

import org.altairtoolkit.id.AltairSnowFlake

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

object Test extends App{
  
  0 to 200000 foreach { x =>
    println(AltairSnowFlake(8).nextId())  
  }
  
  

}
