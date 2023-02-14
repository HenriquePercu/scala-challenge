ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "scala-challenges"
  )

val Fs2Version = "3.2.4"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % Test
libraryDependencies += "co.fs2" %% "fs2-core" % Fs2Version
libraryDependencies += "co.fs2" %% "fs2-io" % "3.6.0"

val Http4sVersion = "1.0.0-M21"
val CirceVersion = "0.14.0-M5"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-blaze-server" % Http4sVersion,
  "org.http4s" %% "http4s-circe" % Http4sVersion,
  "org.http4s" %% "http4s-dsl" % Http4sVersion,
  "io.circe"   %% "circe-generic" % CirceVersion,
)
