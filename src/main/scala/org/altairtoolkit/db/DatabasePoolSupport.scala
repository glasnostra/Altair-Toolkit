package org.altairtoolkit.db

import javax.sql.DataSource

import com.typesafe.scalalogging.Logger
import org.altairtoolkit.SpringBean
import org.apache.tomcat.dbcp.dbcp.BasicDataSource
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean

import scala.slick.jdbc.JdbcBackend.DatabaseDef
import scala.util.Try

/**
 * Created by Deny Prasetyo,S.T
 * Java(Scala) Developer and Trainer
 * Software Engineer
 * jasoet87@gmail.com
 * <p/>
 * http://github.com/jasoet
 * http://bitbucket.com/jasoet
 * http://github.com/AltairLib
 * [at] jasoet
 */

trait DatabasePoolSupport {
  private val logger = Logger(LoggerFactory.getLogger(this.getClass))

  def jdbcConfig: JdbcConfig

  @Bean
  def databasePool: DatabaseDef 

  def databasePoolConfig(dbcp: BasicDataSource) = {}

  @Bean(destroyMethod = "close")
  def dataSource(): DataSource = {
    val dataSource = new BasicDataSource
    dataSource.setUrl(jdbcConfig.url)
    dataSource.setUsername(jdbcConfig.username)
    dataSource.setDriverClassName(jdbcConfig.driver)
    dataSource.setPassword(jdbcConfig.password)
    databasePoolConfig(dataSource)
    logger.info(s"Datasource INITIALIZED")
    Try(dataSource.getConnection) recover {
      case ex: Throwable => logger.error("Failed to Create Connection caused by - " + ex.getMessage, ex)
      case _ => logger.error("Failed to Create Connection Unknown Cause ")
    }
    dataSource
  }
}
