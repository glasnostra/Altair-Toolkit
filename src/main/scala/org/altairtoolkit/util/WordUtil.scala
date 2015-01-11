package org.altairtoolkit.util


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

object WordUtil {
  val inflector = Inflector.getInstance()

  implicit class WordImproment(val s: String) {
    def pluralize: String = {
      inflector.pluralize(s)
    }

    def singularize: String = {
      inflector.singularize(s)
    }

    def lowerCamelCase(delimiter: Char*): String = {
      inflector.lowerCamelCase(s, delimiter: _*)
    }


    def upperCamelCase(delimiter: Char*): String = {
      inflector.upperCamelCase(s, delimiter: _*)
    }

    def camelCase(upperFirstLetter: Boolean, delimiter: Char*): String = {
      inflector.camelCase(s, upperFirstLetter, delimiter: _*)
    }


    def underscore(delimiter: Char*): String = {
      inflector.underscore(s, delimiter: _*)
    }

    def capitalize: String = {
      inflector.capitalize(s)
    }

    def humanize(removableTokens: String*): String = {
      inflector.humanize(s, removableTokens: _*)
    }

    def titleCase(removableTokens: String*): String = {
      inflector.titleCase(s, removableTokens: _*)
    }
  }

}
