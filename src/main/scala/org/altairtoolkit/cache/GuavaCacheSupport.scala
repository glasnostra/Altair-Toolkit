package org.altairtoolkit.cache

import java.util.concurrent.TimeUnit

import com.google.common.cache.CacheBuilder
import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.{CachingConfigurer, EnableCaching}
import org.springframework.cache.guava.GuavaCacheManager
import org.springframework.cache.interceptor._
import org.springframework.context.annotation.Bean

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

@EnableCaching
trait GuavaCacheSupport extends CachingConfigurer {
  private val logger = Logger(LoggerFactory.getLogger(this.getClass))
  
  def cacheBuilder = CacheBuilder.newBuilder()
    .expireAfterAccess(1, TimeUnit.HOURS)

  @Bean(name = Array("cacheManager"))
  override def cacheManager: GuavaCacheManager = {
    val builder = cacheBuilder
    val cacheManager = new GuavaCacheManager()
    cacheManager.setCacheBuilder(builder)
    cacheManager
  }

  override def cacheResolver(): CacheResolver = {
    new SimpleCacheResolver
  }

  override def errorHandler(): CacheErrorHandler = {
    new SimpleCacheErrorHandler
  }

  override def keyGenerator(): KeyGenerator = {
    new SimpleKeyGenerator
  }

  def build(config: CacheBuilder[AnyRef, AnyRef] => CacheBuilder[AnyRef, AnyRef]): GuavaCacheManager = {
    val cacheManager = new GuavaCacheManager()
    cacheManager.setCacheBuilder(config(CacheBuilder.newBuilder()))
    cacheManager
  }
}
