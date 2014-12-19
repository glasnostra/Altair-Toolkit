package org.altairtoolkit.json

import org.json4s.JsonAST.JObject

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

trait Jsonable {
  def toJsonObject: JObject
}
