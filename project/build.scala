import sbt.Keys._
import sbt._

object build extends Build {
  import Dependencies._
  import Settings._

  lazy val commonDependencies = Seq(
    libraryDependencies <++= (scalaVersion)(sv => Seq(
      compiler(sv) % "provided")))

  lazy val tests = Project(
    id = "plugin-",
    base = file("tests"),
    settings = sharedSettings ++ publishableSettings ++ commonDependencies ++ List(
      resourceDirectory in Compile := baseDirectory.value / "resources"))

  lazy val sbtPlug: Project = Project(
    id = "subl-sbtplugin",
    base = file("sbt-plugin"),
    settings = List(sbtPlugin := true, name := "subl-sbtplugin"))

}
