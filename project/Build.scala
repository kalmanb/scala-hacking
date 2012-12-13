import sbt._
import Keys._

object ScalaHacking extends Build {
  override lazy val settings = super.settings ++ Seq(resolvers := Seq())

  val publishedScalaSettings = Seq(
    scalaVersion := "2.10.0-RC5",
    scalaBinaryVersion <<= scalaVersion,
    libraryDependencies <+= scalaVersion("org.scala-lang" % "scala-compiler" % _),
    libraryDependencies in config("macro") <+= scalaVersion("org.scala-lang" % "scala-compiler" % _),
    resolvers += Resolver.sonatypeRepo("snapshots"),
    libraryDependencies ++= Seq(
      "org.slf4j" % "slf4j-api" % "1.6.4",
      
      // Akka
      "com.typesafe.akka" % "akka" % "2.1.0-RC5",
      "com.typesafe.akka" %% "akka-actor" % "2.1.0-RC5",
      "com.typesafe.akka" %% "akka-agent" % "2.1.0-RC5",
      "com.typesafe.akka" %% "akka-cluster-experimental" % "2.1.0-RC5",
      "com.typesafe.akka" %% "akka-dataflow" % "2.1.0-RC5",
      "com.typesafe.akka" %% "akka-remote" % "2.1.0-RC5",
      "com.typesafe.akka" %% "akka-transactor" % "2.1.0-RC5",
      "com.typesafe.akka" %% "akka-testkit" % "2.1.0-RC5",
//"com.typesafe.akka" %% "akka-actor-tests" % "2.1.0-RC5" % "test",
   
      // Misc for migration demo
      "com.google.guava" % "guava" % "13.0.1",
      "net.sf.opencsv" % "opencsv" % "2.3",
   
      // Test stuff
      "org.scalatest" %% "scalatest" % "2.0.M5-B1" % "test",
      "org.specs2" %% "specs2" % "1.13-SNAPSHOT" % "test",
      "org.mockito" % "mockito-all" % "1.9.5",
      "junit" % "junit" % "4.10" % "test",
      "org.hamcrest" % "hamcrest-core" % "1.3" % "test",
      "org.hamcrest" % "hamcrest-library" % "1.3" % "test"))

  lazy val root = Project(id = "scala-hacking",
    base = file("."),
    settings = Project.defaultSettings ++ publishedScalaSettings)

}
