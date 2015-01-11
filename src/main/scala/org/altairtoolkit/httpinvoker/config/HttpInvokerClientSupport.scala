package org.altairtoolkit.httpinvoker.config

import com.typesafe.scalalogging.Logger
import org.apache.http.impl.client.{CloseableHttpClient, HttpClientBuilder}
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.slf4j.LoggerFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.remoting.httpinvoker.{HttpComponentsHttpInvokerRequestExecutor, HttpInvokerProxyFactoryBean}

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

trait HttpInvokerClientSupport {
  private val logger = Logger(LoggerFactory.getLogger(this.getClass))

  val httpClientPoolMaxTotal: Int = 10
  val httpClientDefaultMaxPerRoute: Int = 5

  def urlResolver(id: String): String

  def serverResolver: String

  def httpClientPoolConfiguration(connManager: PoolingHttpClientConnectionManager) = {}

  def httpInvokerRequestExecutor: HttpComponentsHttpInvokerRequestExecutor = {
    val executor = new HttpComponentsHttpInvokerRequestExecutor()
    executor.setHttpClient(httpClientFactory.getHttpClient)
    executor
  }

  def httpClientFactory: HttpComponentsClientHttpRequestFactory = {
    val connManager: PoolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager
    connManager.setMaxTotal(httpClientPoolMaxTotal)
    connManager.setDefaultMaxPerRoute(httpClientDefaultMaxPerRoute)
    httpClientPoolConfiguration(connManager)
    val httpClient: CloseableHttpClient = HttpClientBuilder.create.setConnectionManager(connManager).build
    logger.info(s"Initialize Pooling Http Client with MaxTotal[${connManager.getMaxTotal}}], MaxPerRoute[${connManager.getDefaultMaxPerRoute}}]")
    new HttpComponentsClientHttpRequestFactory(httpClient)
  }

  def build(id: String, serviceTrait: Class[_]): HttpInvokerProxyFactoryBean = {
    val invoker = new HttpInvokerProxyFactoryBean
    invoker.setServiceUrl(serverResolver + urlResolver(id))
    logger.info(s"Invoker Client Created for [${serverResolver + urlResolver(id)}}]")
    invoker.setServiceInterface(serviceTrait)
    invoker.setHttpInvokerRequestExecutor(httpInvokerRequestExecutor)
    invoker

  }
}
