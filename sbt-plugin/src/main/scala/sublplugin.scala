import sbt._
import Keys._
import complete.DefaultParsers._

object sublplugin extends AutoPlugin {
  val sublSymb = settingKey[List[String]]("List of symbols to lookup.")

  lazy val sublSymbCmd =
    Command.args("subl-symb", "<args>") { (state: State, args) =>
      if(!args.isEmpty){
      Project.evaluateTask(Keys.compile in Compile,
        (Project extract state).append(Seq(sublSymb := args.toList, scalacOptions ++= Seq("-Ystop-after:subl")), state))
      } 
      state
    }

  override lazy val projectSettings: Seq[sbt.Def.Setting[_]] = Seq(
    sublSymb := Nil,
    commands ++= Seq(sublSymbCmd),
    addCompilerPlugin("com.github.begeric" % "proto-plugin_2.11.5" % "0.1-SNAPSHOT"),
    scalacOptions ++= Seq(
      "-P:subl:symb:" + sublSymb.value.mkString(";")))
}