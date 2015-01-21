import sbt._
import Keys._
import complete.DefaultParsers._

object sublplugin extends AutoPlugin {
  val sublSymb = settingKey[List[String]]("List of symbols to lookup.")

  lazy val sublSymbCmd =
    Command.args("subl-symb", "<args>") { (state: State, args) =>
      if(!args.isEmpty){
      Project.evaluateTask(Keys.compile in Compile,
        (Project extract state).append(Seq(scalacOptions ++= Seq("-Ystop-after:subl", "-P:subl:symb:"+args.mkString(";"))), state))
      } 
      state
    }

  override lazy val projectSettings: Seq[sbt.Def.Setting[_]] = Seq(
    sublSymb := Nil,
    commands ++= Seq(sublSymbCmd),
    addCompilerPlugin("com.begeric.github" %% "proto-plugin" % "0.1-SNAPSHOT"),
    scalacOptions ++= Seq(
      "-P:subl:symb:" + sublSymb.value.mkString(";")))
}