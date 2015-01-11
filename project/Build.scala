import sbt.Keys._
import sbt._

object Build extends Build {
  lazy val project = Project(
    Name,
    file("."),
    settings = publishSettings ++ Seq(
      organization := Organization,
      conflictManager := ConflictManager.latestRevision,
      name := Name,
      version := Version,
      scalaVersion in ThisBuild := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      libraryDependencies in ThisBuild ++= Seq(
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "com.typesafe.slick" %% "slick" % SlickVersion,
        "com.typesafe.slick" %% "slick-codegen" % SlickVersion,
        "ch.qos.logback" % "logback-classic" % LogbackVersion,
        "org.springframework" % "spring-core" % SpringVersion force(),
        "org.springframework" % "spring-web" % SpringVersion force(),
        "org.springframework" % "spring-context-support" % SpringVersion force(),
        "org.springframework" % "spring-tx" % SpringVersion force(),
        "org.springframework.security" % "spring-security-core" % SpringSecurityVersion,
        "org.springframework.security" % "spring-security-config" % SpringSecurityVersion,
        "org.springframework.security" % "spring-security-web" % SpringSecurityVersion,
        "org.apache.tomcat" % "dbcp" % DbcpVersion,
        "javax.servlet" % "javax.servlet-api" % ServletApiVersion,
        "com.typesafe.scala-logging" %% "scala-logging" % ScalaLoggingVersion,
        "org.json4s" %% "json4s-jackson" % Json4sVersion,
        "org.apache.httpcomponents" % "httpclient" % HttpClientVersion,
        "org.quartz-scheduler" % "quartz" % QuartzVersion,
        "net.logstash.logback" % "logstash-logback-encoder" % LogstashEncoderVersion,
        "org.apache.commons" % "commons-lang3" % CommonsLang3Version,
        "com.typesafe.play" %% "twirl-api" % TwirlApiVersion,
        "com.google.guava" % "guava" % GuavaVersion
        //        "org.imgscalr" % "imgscalr-lib" % ImgScalrVersion,
        //        "com.github.slugify" % "slugify" % SlugifyVersion,
        //        "org.jsoup" % "jsoup" % JsoupVersion,
        //        "com.sun.jersey" % "jersey-client" % JerseyVersion,
        //        "com.sun.jersey" % "jersey-core" % JerseyVersion,
        //        "com.sun.jersey.contribs" % "jersey-multipart" % JerseyVersion,
        //        "com.sksamuel.elastic4s" %% "elastic4s" % Elastic4s,
        //        "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
        //        "org.scalatra" %% "scalatra-auth" % ScalatraVersion,
        //        "org.springframework.security" % "spring-security-web" % SpringSecurityVersion,
        //        "org.springframework.security" % "spring-security-config" % SpringSecurityVersion,
        //        "org.scalatra" %% "scalatra-json" % ScalatraVersion,
        //        "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
        //        "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
        //        "org.scala-lang.modules" %% "scala-async" % ScalaAsyncModule,
        //        "com.hazelcast" % "hazelcast-spring" % HazelcastVersion,
        //        "com.hazelcast" % "hazelcast-wm" % HazelcastVersion,
      )
    )
  )

  val Organization = "org.altairtoolkit"
  val Name = "Altair-Toolkit"
  val Version = "1.0.RC2"
  val ScalaVersion = "2.11.4"
  val ScalatraVersion = "2.3.0"
  val SpringVersion = "4.1.4.RELEASE"
  val SpringSecurityVersion = "3.2.5.RELEASE"
  val DbcpVersion = "6.0.41"
  val ScalaLoggingVersion = "3.1.0"
  val SlickVersion = "2.1.0"
  val AkkaVersion = "2.3.6"
  val LogbackVersion = "1.1.2"
  val Json4sVersion = "3.2.9"
  val HttpClientVersion = "4.3.5"
  val ScalaAsyncModule = "0.9.2"
  val Elastic4s = "1.3.2"
  val QuartzVersion = "2.2.+"
  val LogstashEncoderVersion = "3.4"
  val TwirlApiVersion = "1.0.4"
  val CommonsLang3Version = "3.3.2"
  val ServletApiVersion = "3.1.0"
  val GuavaVersion = "18.0"

  def publishSettings = Seq(
    pomIncludeRepository := { _ => false},
    publishArtifact in Test := false,
    publishMavenStyle := true,
    credentials += Credentials("Sonatype Nexus Repository Manager", "104.207.150.166", "deployment", "Localhost123"),
    resolvers += Resolver.url("Jasoet Nexus", url("http://104.207.150.166:8081/nexus/content/repositories/releases/"))(Resolver.mavenStylePatterns),
    publishTo <<= version { (v: String) =>
      val archiva = "http://104.207.150.166:8081/nexus/"
      if (v.trim.endsWith("-SNAPSHOT"))
        Some(Resolver.url("snapshots", new URL(archiva + "content/repositories/snapshots/"))(Resolver.mavenStylePatterns))
      else
        Some(Resolver.url("release", new URL(archiva + "content/repositories/releases/"))(Resolver.mavenStylePatterns))
    })
}
