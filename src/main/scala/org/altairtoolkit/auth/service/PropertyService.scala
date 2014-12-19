package org.altairtoolkit.auth.service

import com.typesafe.scalalogging.Logger
import net.logstash.logback.marker.LogstashMarker
import net.logstash.logback.marker.Markers._
import org.altairtoolkit.auth.db.{Properties, Property}
import org.altairtoolkit.slick.Page
import org.altairtoolkit.slick.sort.Sort
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.slick.driver.MySQLDriver.simple._
import Database.dynamicSession
import scala.slick.jdbc.JdbcBackend.DatabaseDef
import scala.util.{Failure, Success, Try}

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

trait PropertyService {

  def +=(p: Property): Try[Property]

  def ++=(p: Seq[Property]): Try[Seq[Property]]

  def findByKey(k: String): Try[Property]

  def deleteByKey(k: String): Try[Int]

  def findAll(page:Int=0,size:Int=20,sort: List[Sort]=Nil): Try[Page[Property]]
}

@Service
class PropertyServiceImpl @Autowired()(databasePool: DatabaseDef) extends PropertyService {
  val logger = Logger(LoggerFactory.getLogger(this.getClass))

  override def +=(p: Property): Try[Property] = {
    val logMaker = append("Property", p.toString)
    databasePool withDynTransaction {
      try {
        Properties += p
        Success(p)
      } catch {
        case e: Throwable =>
          logger.error(logMaker.and[LogstashMarker](append("Status", "Failure")), "Exception when Insert Property " + e)
          Failure(e)
      }
    }
  }

  override def ++=(p: Seq[Property]): Try[Seq[Property]] = {
    val logMaker = append("Seq[Property]", p.toString)
    databasePool withDynTransaction {
      try {
        Properties ++= p
        Success(p)
      } catch {
        case e: Throwable =>
          logger.error(logMaker.and[LogstashMarker](append("Status", "Failure")), "Exception when Insert All Property " + e)
          Failure(e)
      }
    }
  }

  override def findByKey(k: String): Try[Property] = {
    val logMaker = append("PropertyKey", k)
    databasePool withDynTransaction {
      try {
        Success(Properties.filter(_.propertyKey === k).first)
      } catch {
        case e: Throwable =>
          logger.error(logMaker.and[LogstashMarker](append("Status", "Failure")), "Exception when findByKey " + e)
          Failure(e)
      }
    }
  }

  override def deleteByKey(k: String): Try[Int] = {
    val logMaker = append("Key", k)
    databasePool withDynTransaction {
      try {
        Success(Properties.filter(_.propertyKey === k).delete)
      } catch {
        case e: Throwable =>
          logger.error(logMaker.and[LogstashMarker](append("Status", "Failure")), "Exception when deleteByKey " + e)
          Failure(e)
      }
    }
  }

  override def findAll(page:Int=0,size:Int=20,sort: List[Sort]=Nil): Try[Page[Property]] = {
    val logMaker = append("Page", page).and[LogstashMarker](append("Size", size).and[LogstashMarker](append("Sory", sort)))
    databasePool withDynTransaction {
      try {
        val result = page match{
           case p if p < 0 => Properties.sortBy(_.sort(sort)).list
           case p => Properties.sortBy(_.sort(sort)).drop(page*size).take(size).list
        }

        val total = Properties.length.run

        logger.info("Execute query [" + Properties.selectStatement + "]")
        Success(Page(result,total,page,size))
      } catch {
        case e: Throwable =>
          logger.error(logMaker.and[LogstashMarker](append("Status", "Failure")), "Exception when FindAll " + e)
          Failure(e)
      }
    }
  }
}