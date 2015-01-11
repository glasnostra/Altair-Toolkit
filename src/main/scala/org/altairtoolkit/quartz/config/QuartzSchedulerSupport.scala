package org.altairtoolkit.quartz.config

import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean

/**
 * Created by Deny Prasetyo,S.T
 * Java(Script) Developer and Trainer
 * Software Engineer
 * jasoet87@gmail.com
 * <p/>
 * http://github.com/jasoet
 * http://bitbucket.com/jasoet
 *
 * [at] jasoet
 */

trait QuartzSchedulerSupport {
  private val logger = Logger(LoggerFactory.getLogger(this.getClass))

  def postInit(schedulerMap: Map[String, AltairScheduler]) = {}

  @Bean
  def schedulerFactoryBean: ScheduleBeanFactory = {
    logger.info("Initializing QuartzSchedulerSupport.....")
    new ScheduleBeanFactory((schedulerMap: Map[String, AltairScheduler]) => {
      postInit(schedulerMap)
    })
  }

}
