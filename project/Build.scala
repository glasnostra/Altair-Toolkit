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
      //      publishTo in ThisBuild := Some(Resolver.file("file", new File(Path.userHome.absolutePath + "/.m2/repository"))),
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
        "org.apache.tomcat" % "dbcp" % DbcpVersion,
        "javax.servlet" % "javax.servlet-api" % "3.1.0",
        "com.typesafe.scala-logging" %% "scala-logging" % ScalaLoggingVersion,
        "org.mariadb.jdbc" % "mariadb-java-client" % MariaDbClientVersion,
        "org.json4s" %% "json4s-jackson" % Json4sVersion,
        "org.apache.httpcomponents" % "httpclient" % HttpClientVersion,
        "org.imgscalr" % "imgscalr-lib" % ImgScalrVersion,
        "com.amazonaws" % "aws-java-sdk" % AwsJavaVersion,
        "org.jsoup" % "jsoup" % JsoupVersion,
        "com.sun.jersey" % "jersey-client" % JerseyVersion,
        "com.sun.jersey" % "jersey-core" % JerseyVersion,
        "com.sun.jersey.contribs" % "jersey-multipart" % JerseyVersion,
        "com.github.slugify" % "slugify" % SlugifyVersion,
        "org.quartz-scheduler" % "quartz" % QuartzVersion,
        "com.sksamuel.elastic4s" %% "elastic4s" % Elastic4s,
        "net.logstash.logback" % "logstash-logback-encoder" % LogstashEncoderVersion
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
  ).enablePlugins(play.twirl.sbt.SbtTwirl)
  val Organization = "org.altairtoolkit"
  val Name = "Altair-Toolkit"
  val Version = "1.0.BETA"
  val ScalaVersion = "2.11.4"
  val ScalatraVersion = "2.3.0"
  val SpringVersion = "4.1.1.RELEASE"
  val SpringSecurityVersion = "3.2.5.RELEASE"
  val DbcpVersion = "6.0.41"
  val MariaDbClientVersion = "1.1.7"
  val ScalaLoggingVersion = "3.1.0"
  val SlickVersion = "2.1.0"
  val AkkaVersion = "2.3.6"
  val LogbackVersion = "1.1.2"
  val Json4sVersion = "3.2.9"
  val HttpClientVersion = "4.3.5"
  val ScalaAsyncModule = "0.9.2"
  val Elastic4s = "1.3.2"
  val TypesafeConfigVersion = "1.2.1"
  val HazelcastVersion = "3.3.2"
  val ImgScalrVersion = "4.2"
  val JsoupVersion = "1.8.+"
  val AwsJavaVersion = "1.9.3"
  val SlugifyVersion = "2.1.2"
  val JerseyVersion = "1.18.+"
  val QuartzVersion = "2.2.+"
  val LogstashEncoderVersion = "3.4"

  def publishSettings = Seq(
    pomIncludeRepository := { _ => false},
    publishArtifact in Test := false,
    publishMavenStyle := true,
    credentials += Credentials("Repository Archiva Managed internal Repository", "molokai.hulaa.com", "hulaa", "Localhost123"),
    resolvers += Resolver.url("Hulaa Archiva", url("http://molokai.hulaa.com:8084/repository/internal/"))(Resolver.mavenStylePatterns),
    publishTo <<= version { (v: String) =>
      val archiva = "http://molokai.hulaa.com:8084"
      if (v.trim.endsWith("-SNAPSHOT"))
        Some(Resolver.url("snapshots", new URL(archiva + "/repository/internal/"))(Resolver.mavenStylePatterns))
      else
        Some(Resolver.url("release", new URL(archiva + "/repository/internal/"))(Resolver.mavenStylePatterns))
    })
}