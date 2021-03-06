package org.altairtoolkit.slick.generator

/**
 * Deny Prasetyo,S.T and Robin
 * Java(Scala) Developer and Trainer
 * Software Engineer | Java Virtual Machine Junkie!
 * jasoet87@gmail.com
 * <p/>
 * http://github.com/jasoet
 * http://bitbucket.com/jasoet
 *
 */

import org.altairtoolkit.util.WordUtil._

import scala.slick.codegen.{AbstractSourceCodeGenerator, OutputHelpers}
import scala.slick.{model => m}


class AltairCodeGenerator(model: m.Model)
  extends AbstractSourceCodeGenerator(model) with OutputHelpers {
  // "Tying the knot": making virtual classes concrete
  type Table = TableDef

  def Table = new TableDef(_)

  override def code = {
    "import scala.slick.model.ForeignKeyAction\n" +
      (if (tables.exists(_.hlistEnabled)) {
        "import scala.slick.collection.heterogenous._\n" +
          "import scala.slick.collection.heterogenous.syntax._\n"
      } else ""
        ) +
      (if (tables.exists(_.PlainSqlMapper.enabled)) {
        "// NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.\n" +
          "import scala.slick.jdbc.{GetResult => GR}\n"
      } else ""
        ) +
      "\n/** DDL for all tables. Call .create to execute. */\nlazy val ddl = " + tables.map(t => t.TableValue.name.toString.pluralize + ".ddl").mkString(" ++ ") +
      "\n\n" +
      tables.map(_.code.mkString("\n")).mkString("\n\n")
  }

  override def entityName: (String) => String = (dbName: String) => dbName.toCamelCase

  class TableDef(model: m.Table) extends super.TableDef(model) {
    // Using defs instead of (caching) lazy vals here to provide consitent interface to the user.
    // Performance should really not be critical in the code generator. Models shouldn't be huge.
    // Also lazy vals don't inherit docs from defs
    type EntityType = EntityTypeDef

    def EntityType = new EntityType {}

    type PlainSqlMapper = PlainSqlMapperDef

    def PlainSqlMapper = new PlainSqlMapper {}

    type TableClass = TableClassDef

    def TableClass = new TableClass {
      override def code = {
        val prns = parents.map(" with " + _).mkString("")
        val args = model.name.schema.map(n => s"""Some("$n")""") ++ Seq("\"" + model.name.table + "\"")
        s"""
class ${name}Table(_tableTag: Tag) extends Table[$elementType](_tableTag, ${args.mkString(", ")})$prns {
  ${indent(body.map(_.mkString("\n")).mkString("\n\n"))}
}
        """.trim()
      }
    }

    type TableValue = TableValueDef

    def TableValue = new TableValue {
      override def code: String = s"object ${name.toString.pluralize} extends TableQuery(tag => new ${TableClass.name}Table(tag))"
    }

    type Column = ColumnDef

    def Column = new Column(_)

    type PrimaryKey = PrimaryKeyDef

    def PrimaryKey = new PrimaryKey(_)

    type ForeignKey = ForeignKeyDef

    def ForeignKey = new ForeignKey(_)

    type Index = IndexDef

    def Index = new Index(_)

  }


}

/** A runnable class to execute the code generator without further setup */
object AltairCodeGenerator {

  import scala.reflect.runtime.currentMirror
  import scala.slick.driver.JdbcProfile

  def main(args: Array[String]) = {
    args.toList match {
      case slickDriver :: jdbcDriver :: url :: outputFolder :: pkg :: tail if tail.size == 0 || tail.size == 2 => {
        val driver: JdbcProfile = {
          val module = currentMirror.staticModule(slickDriver)
          val reflectedModule = currentMirror.reflectModule(module)
          val driver = reflectedModule.instance.asInstanceOf[JdbcProfile]
          driver
        }
        val db = driver.simple.Database
        (tail match {
          case user :: password :: Nil => db.forURL(url, driver = jdbcDriver, user = user, password = password)
          case Nil => db.forURL(url, driver = jdbcDriver)
          case _ => throw new Exception("This should never happen.")
        }).withSession { implicit session =>
          new AltairCodeGenerator(driver.createModel()).writeToFile(slickDriver, outputFolder, pkg)
        }
      }
      case _ =>
        println( """
Usage:
  AltairCodeGenerator.main(Array(slickDriver, jdbcDriver, url, outputFolder, pkg))
  AltairCodeGenerator.main(Array(slickDriver, jdbcDriver, url, outputFolder, pkg, user, password))

slickDriver: Fully qualified name of Slick driver class, e.g. "scala.slick.driver.H2Driver"

jdbcDriver: Fully qualified name of jdbc driver class, e.g. "org.h2.Driver"

url: jdbc url, e.g. "jdbc:postgresql://localhost/test"

outputFolder: Place where the package folder structure should be put

pkg: Scala package the generated code should be places in

user: database connection user name

password: database connection password
                 """.trim
        )
    }
  }
}
