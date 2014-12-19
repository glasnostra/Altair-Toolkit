package org.altairtoolkit.auth.service

import java.time.LocalDateTime

import org.altairtoolkit.util.LocalDateTimeUtil
import com.typesafe.scalalogging.Logger
import net.logstash.logback.marker.LogstashMarker
import net.logstash.logback.marker.Markers._
import org.altairtoolkit.auth.db._
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.slick.driver.MySQLDriver.simple._
import Database.dynamicSession
import scala.slick.jdbc.JdbcBackend.DatabaseDef
import scala.util.{Failure, Success, Try}

/**
 * Created by Yanuar Arifin
 * Developer
 * januaripin@gmail.com
 *
 * https://bitbucket.org/januaripin
 * https://github.com/januaripin
 */

case class Group(name: String, description: String, roles: Seq[String])

trait AuthGroupService {
  //Group
  def findGroupAll(): Try[Seq[Group]]

  def addGroup(gr: Group): Try[Group]

  def updateGroup(gr: Group): Try[Int]

  def deleteGroup(k: String): Try[Int]

  def findGroupByKey(k: String): Try[Seq[Group]]

  //Role
  def addRole(r: AuthRole): Try[AuthRole]

  def addRoles(r: Seq[AuthRole]): Try[Seq[AuthRole]]

  def findRoleAll(): Try[Seq[AuthRole]]

  def findRoleByKey(k: String): Try[Seq[AuthRole]]

  def deleteRole(k: String): Try[Int]

  def updateRole(r: AuthRole): Try[Int]
}

@Service
class AuthGroupServiceImpl @Autowired()(databasePool: DatabaseDef) extends AuthGroupService {
  val logger = Logger(LoggerFactory.getLogger(this.getClass))

  override def findGroupAll(): Try[Seq[Group]] = {
    val query = for {
      (ag, gr) <- AuthGroups leftJoin AuthGroupRoles on (_.authGroupName === _.authGroupName)
    } yield (ag.authGroupName, ag.description, gr.authRoleName.?)

    databasePool withDynTransaction {
      try {
        val result = query.run.groupBy(f => {
          (f._1, f._2)
        }).map(x => {
          val ((name, desc), seqRole) = x
          Group(name, desc, seqRole.flatMap(x => x._3))
        }).toList

        Success(result)
      } catch {
        case e: Throwable =>
          logger.error(append("Status", "Failure"), s"Exception when get seq group $e")
          Failure(e)
      }
    }
  }

  override def updateGroup(gr: Group): Try[Int] = {
    val logMaker = append("Group", gr.toString)
    val group = for {
      ag <- AuthGroups if ag.authGroupName === gr.name
    } yield (ag.authGroupName, ag.description, ag.updatedAt)
    databasePool withDynTransaction {
      try {
        AuthGroupRoles.filter(_.authGroupName === gr.name).delete

        val result = group.update((gr.name, gr.description, LocalDateTimeUtil.toMilliSeconds(LocalDateTime.now())))

        val groupRoles = gr.roles.map(s => {
          AuthGroupRole(None, s, gr.name, LocalDateTime.now(), LocalDateTime.now())
        })

        AuthGroupRoles ++= groupRoles

        Success(result)
      } catch {
        case e: Throwable =>
          logger.error(logMaker.and[LogstashMarker](append("Status", "Failure")), "Exception when deleteByKey " + e)
          Failure(e)
      }
    }
  }

  override def addRole(r: AuthRole): Try[AuthRole] = {
    val logMaker = append("AutoRole", r.toString)
    databasePool withDynTransaction {
      try {
        AuthRoles += r
        Success(r)
      } catch {
        case e: Throwable =>
          logger.error(logMaker.and[LogstashMarker](append("Status", "Failure")), s"Exception when insert role $e")
          Failure(e)
      }
    }
  }

  override def addRoles(r: Seq[AuthRole]): Try[Seq[AuthRole]] = {
    val logMaker = append("AuthRole", r.toString)
    databasePool withDynTransaction {
      try {
        AuthRoles ++= r
        Success(r)
      } catch {
        case e: Throwable =>
          logger.error(logMaker.and[LogstashMarker](append("Status", "Failure")), s"Exception when insert seq role $e")
          Failure(e)
      }
    }
  }

  override def findRoleAll(): Try[Seq[AuthRole]] = {
    databasePool withDynTransaction {
      try {
        val result = AuthRoles.run
        Success(result)
      } catch {
        case e: Throwable =>
          logger.error(append("Status", "Failure"), "Exception when get seq role")
          Failure(e)
      }
    }
  }

  override def findRoleByKey(k: String): Try[Seq[AuthRole]] = {
    val logMaker = append("AuthRoleName", k)
    databasePool withDynTransaction {
      try {
        val result = AuthRoles.filter(_.authRoleName === k).run
        Success(result)
      } catch {
        case e: Throwable =>
          logger.error(logMaker.and[LogstashMarker](append("Status", "Failure")), s"Exception when get seq role $e")
          Failure(e)
      }
    }
  }

  override def addGroup(gr: Group): Try[Group] = {
    val logMaker = append("Group", gr.toString)
    databasePool withDynTransaction {
      try {
        AuthGroups += AuthGroup(gr.name, gr.description, LocalDateTime.now(), LocalDateTime.now())

        val groupRoles = gr.roles.map(s => {
          AuthGroupRole(None, s, gr.name, LocalDateTime.now(), LocalDateTime.now())
        })

        AuthGroupRoles ++= groupRoles

        Success(gr)
      } catch {
        case e: Throwable =>
          logger.error(logMaker.and[LogstashMarker](append("Status", "Failure")), s"Exception when insert group role $e")
          Failure(e)
      }
    }
  }

  override def deleteGroup(k: String): Try[Int] = {
    val logMaker = append("AuthGroupName", k)
    databasePool withDynTransaction {
      try {
        AuthGroupRoles.filter(_.authGroupName === k).delete
        Success(AuthGroups.filter(_.authGroupName === k).delete)
      } catch {
        case e: Throwable =>
          logger.error(logMaker.and[LogstashMarker](append("Status", "Failure")), "Exception when deleteByKey " + e)
          Failure(e)
      }
    }
  }

  override def findGroupByKey(k: String): Try[Seq[Group]] = {
    val logMaker = append("AuthGroupName", k)
    val query = for {
      (ag, gr) <- AuthGroups leftJoin AuthGroupRoles on (_.authGroupName === _.authGroupName)
      if ag.authGroupName === k
    } yield (ag.authGroupName, ag.description, gr.authRoleName.?)

    databasePool withDynTransaction {
      try {
        val result = query.run.groupBy(f => {
          (f._1, f._2)
        }).map(x => {
          val ((name, desc), seqRole) = x
          Group(name, desc, seqRole.flatMap(x => x._3))
        }).toList

        Success(result)
      } catch {
        case e: Throwable =>
          logger.error(logMaker.and[LogstashMarker](append("Status", "Failure")), "Exception when findGroupByKey " + e)
          Failure(e)
      }
    }
  }

  override def deleteRole(k: String): Try[Int] = {
    val logMaker = append("AuthRoleName", k)
    databasePool withDynTransaction {
      try {
        AuthGroupRoles.filter(_.authRoleName === k).delete
        Success(AuthRoles.filter(_.authRoleName === k).delete)
      } catch {
        case e: Throwable =>
          logger.error(logMaker.and[LogstashMarker](append("Status", "Failure")), "Exception when deleteByKey " + e)
          Failure(e)
      }
    }
  }

  override def updateRole(r: AuthRole): Try[Int] = {
    val logMaker = append("AuthRoleName", r)
    val query = for {
      ar <- AuthRoles if ar.authRoleName === r.authRoleName
    } yield (ar.description, ar.updatedAt)
    databasePool withDynTransaction {
      try {
        val result = query.update((r.description, LocalDateTimeUtil.toMilliSeconds(r.updatedAt)))
        Success(result)
      } catch {
        case e: Throwable =>
          logger.error(logMaker.and[LogstashMarker](append("Status", "Failure")), "Exception when deleteByKey " + e)
          Failure(e)
      }
    }
  }
}
