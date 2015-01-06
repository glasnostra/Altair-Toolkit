package org.altairtoolkit.quartz.config

import com.typesafe.scalalogging.Logger
import org.altairtoolkit.SpringBean
import org.altairtoolkit.annotation.scheduler.{CronSchedule, IntervalSchedule, QuartzEnable}
import org.altairtoolkit.quartz.helper.SchedulerHelper
import org.apache.commons.lang3.RandomStringUtils
import org.quartz.{Scheduler, Trigger}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.{DisposableBean, SmartInitializingSingleton}
import org.springframework.context.{ApplicationContext, ApplicationContextAware}
import org.springframework.scheduling.quartz.{CronTriggerFactoryBean, MethodInvokingJobDetailFactoryBean, SchedulerFactoryBean, SimpleTriggerFactoryBean}

import scala.collection.JavaConverters._
import scala.collection.mutable

/**
 * Deny Prasetyo,S.T
 * Java(Scala) Developer and Trainer
 * Software Engineer | Java Virtual Machine Junkies!
 * jasoet87@gmail.com
 * <p>
 * http://github.com/jasoet
 * http://bitbucket.com/jasoet
 */
//TODO Use Mixin if Possible
class ScheduleBeanFactory extends ApplicationContextAware with SmartInitializingSingleton with DisposableBean {

  val logger = Logger(LoggerFactory.getLogger(this.getClass))

  private var applicationContext: ApplicationContext = SpringBean.DEFAULT

  override def setApplicationContext(applicationContext: ApplicationContext): Unit = {
    this.applicationContext = applicationContext
  }

  override def afterSingletonsInstantiated(): Unit = {
    val scheduleMap: mutable.Map[String, Scheduler] = mutable.Map()
    filterAndGroup(this.applicationContext.getBeansWithAnnotation(classOf[QuartzEnable]).asScala.toMap).foreach(bean => {
      bean._2.foreach(qc => {
        val triggers = scala.collection.mutable.ListBuffer.empty[Trigger]
        if (qc.interval.size > 0) {
          qc.interval.foreach(i => {
            val jd = new MethodInvokingJobDetailFactoryBean()
            jd.setTargetObject(qc.bean)
            jd.setTargetMethod(i.methodName())
            jd.setConcurrent(qc.concurrent)
            jd.setName(s"${qc.beanName}Interval${i.methodName().capitalize}JobDetail${RandomStringUtils.randomAlphanumeric(2)}")
            jd.afterPropertiesSet()

            val intv = new SimpleTriggerFactoryBean()
            intv.setStartDelay(i.startDelay())
            intv.setRepeatInterval(i.repeatInterval())
            intv.setRepeatCount(i.repeatCount())
            intv.setJobDetail(jd.getObject)
            intv.setName(s"${qc.beanName}Interval${i.methodName().capitalize}Trigger${RandomStringUtils.randomAlphanumeric(2)}")
            intv.afterPropertiesSet()
            logger.info(s"Register Interval Schedule Name[${qc.beanName}],MethodName[${i.methodName()}],Delay[${i.startDelay()}],Inverval[${i.repeatInterval()}],Count[${i.repeatCount()}]")

            triggers += intv.getObject
          })
        }

        if (qc.cron.size > 0) {
          qc.cron.foreach(c => {
            val jd = new MethodInvokingJobDetailFactoryBean()
            jd.setTargetObject(qc.bean)
            jd.setTargetMethod(c.methodName())
            jd.setConcurrent(qc.concurrent)
            jd.setName(s"${qc.beanName}Cron${c.methodName().capitalize}JobDetail${RandomStringUtils.randomAlphanumeric(2)}")
            jd.afterPropertiesSet()

            val crfb = new CronTriggerFactoryBean()
            crfb.setStartDelay(c.startDelay())
            crfb.setJobDetail(jd.getObject)
            crfb.setCronExpression(c.cronExpression())
            crfb.setName(s"${qc.beanName}Cron${c.methodName().capitalize}Trigger${RandomStringUtils.randomAlphanumeric(2)}")
            crfb.afterPropertiesSet()

            logger.info(s"Register Cron Schedule Name[${qc.beanName}],MethodName[${c.methodName()}],Delay[${c.startDelay()}],Expression[${c.cronExpression()}]")

            triggers += crfb.getObject

          })
        }

        if (triggers.nonEmpty) {
          val schedulerFactoryBean = new SchedulerFactoryBean()
          schedulerFactoryBean.setTriggers(triggers.toList: _*)
          schedulerFactoryBean.setSchedulerName(qc.schedulerName)
          schedulerFactoryBean.afterPropertiesSet()
          scheduleMap.put(qc.schedulerName, schedulerFactoryBean.getObject)
        }

      })
    })
    SchedulerHelper.init(scheduleMap)

    SchedulerHelper.startAll()
    logger.info(s"Start All Scheduler....")
  }

  private case class QuartzAnnotationConfig(schedulerName: String, beanName: String, bean: AnyRef, concurrent: Boolean, interval: List[IntervalSchedule], cron: List[CronSchedule])

  private def filterAndGroup(values: Map[String, AnyRef]): Map[String, List[QuartzAnnotationConfig]] = {
    values.map(value => {
      val name = value._1
      val bean = value._2
      val ann = bean.getClass.getAnnotation(classOf[QuartzEnable])
      QuartzAnnotationConfig(ann.value(), name, bean, ann.concurrent(), ann.interval().toList, ann.cron().toList)
    }).toList.groupBy(value => {
      value.schedulerName
    })
  }

  override def destroy(): Unit = {
    logger.info(s"Stop All Scheduler....")
    SchedulerHelper.stopAll()
  }
}
