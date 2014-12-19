package org.altairtoolkit.httpinvoker.handler

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

import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter
import org.springframework.web.HttpRequestMethodNotSupportedException


class HttpInvokerHandlerServlet(target: HttpInvokerServiceExporter) extends HttpServlet {


  override def init() {
  }

  override protected def service(request: HttpServletRequest, response: HttpServletResponse) {
    LocaleContextHolder.setLocale(request.getLocale)
    try {
      this.target.handleRequest(request, response)
    } catch {
      case var8: HttpRequestMethodNotSupportedException =>
        val supportedMethods = var8.getSupportedMethods
        if (supportedMethods != null) {
          response.setHeader("Allow", supportedMethods.mkString(", "))
        }
        response.sendError(405, var8.getMessage)
    } finally {
      LocaleContextHolder.resetLocaleContext()
    }
  }
}

object HttpInvokerHandlerServlet{
  def apply(target: HttpInvokerServiceExporter)={
    new HttpInvokerHandlerServlet(target)
  }
}

