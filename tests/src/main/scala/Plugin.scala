
package scala.sublime.tests

import scala.tools.nsc.{ Global, Phase, SubComponent }
import scala.tools.nsc.plugins.{ Plugin => NscPlugin, PluginComponent => NscPluginComponent }

class Plugin(val global: Global) extends NscPlugin {
  import global._

  val name = "subl"
  val description = "prototyping typing"

  val components = List[NscPluginComponent](SublPluginComponent)

  override def processOptions(options: List[String], error: String => Unit) {
    options.foreach { opt =>
      if (opt.startsWith("symb:")) {
        SymbTable.symbols = opt.substring("symb:".length).split(";").toList
      } else {
        reporter.error(NoPosition, "Bad Option")
      }
    }
  }

  private object SublPluginComponent extends NscPluginComponent {
    import global._
    val global = Plugin.this.global

    override val runsAfter = List("typer")
    val phaseName = "subl"

    def newPhase(prev: Phase) = new StdPhase(prev) {
      def apply(unit: CompilationUnit) {
        //TODO implement
      }
    }

  }
}