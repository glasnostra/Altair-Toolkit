package org.altairtoolkit.scalatra.helper

import java.io.File

import org.scalatra.ScalatraServlet
import play.twirl.api.HtmlFormat

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

abstract class AltairDefaultServlet extends ScalatraServlet {
   val errorPage: HtmlFormat.Appendable

  notFound {
    errorPage
  }

  get("/resources/*") {
    serveStaticResource().getOrElse(errorPage)
  }

  override protected def serveStaticResource(): Option[Any] = {
    servletContext.resource(request) map { r =>
      val f = new File(r.getFile)
      if (f.exists && !f.isDirectory) {
        response.setHeader("Cache-control", "max-age=2592000, public, must-revalidate")
        servletContext.getNamedDispatcher("default").forward(request, response)
      } else {
        errorPage
      }
    }
  }

}
