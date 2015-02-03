import sbt.Keys._
import sbt._

object build extends Build {
  import Dependencies._
  import Settings._

  lazy val commonDependencies = Seq(
    libraryDependencies <++= (scalaVersion)(sv => Seq(
      compiler(sv) % "provided")))

  lazy val root: Project = Project(
    "root",
    file("."),
    settings = PublishSettings.publishSettings ++ Seq(
      run <<= run in Compile in tests)
  ) aggregate(tests, sbtPlug)

  lazy val tests = Project(
    id = "proto-plugin",
    base = file("tests"),
    settings = sharedSettings ++  publishableSettings ++ commonDependencies ++ List(
      resourceDirectory in Compile := baseDirectory.value / "resources"))

  lazy val sbtPlug: Project = Project(
    id = "subl-sbtplugin",
    base = file("sbt-plugin"),
    settings =  PublishSettings.publishSettings ++ publishableSettings ++ List(sbtPlugin := true, name := "subl-sbtplugin"))

  lazy val serverProxy: Project = Project(
    id = "proxy-server",
    base = file("proxy-server"),
    settings = sharedSettings)

}
