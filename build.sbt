organization := "com.kalmanb"

name := "scala-hacking"

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.9"
)

// Test Specs
lazy val testSpecs = RootProject(uri("https://github.com/kalmanb/scalatest-specs.git#0.1.3"))
lazy val root = project.in(file(".")).dependsOn(testSpecs % "test->test")






